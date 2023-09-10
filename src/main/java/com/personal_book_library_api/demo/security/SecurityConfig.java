package com.personal_book_library_api.demo.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig {
    @Autowired
    JwtToUserConverter jwtToUserConverter;
    @Autowired
    KeyUtils keyUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsManager userDetailsManager;

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, active from user where email=?");

        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "WITH email_param AS (SELECT ? AS email_value) " +
                        "SELECT u.email, 'READER' as role FROM user u INNER JOIN reader r ON u.id = r.id WHERE u.email = (SELECT email_value FROM email_param) " +
                        "UNION " +
                        "SELECT u.email, 'WRITER' as role FROM user u INNER JOIN writer w ON u.id = w.id WHERE u.email = (SELECT email_value FROM email_param)"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books/my").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/books/*/add").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/*/read").hasAuthority("READER")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*/remove").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/nextpage").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/previouspage").hasAuthority("READER")
                        .requestMatchers(HttpMethod.GET, "/api/books/*/currentreaders").hasAuthority("WRITER")
                        .requestMatchers(HttpMethod.GET, "/api/books/*/readers").hasAuthority("WRITER")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("WRITER")
                        .anyRequest().permitAll()
                );

        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer((oauth2) ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtToUserConverter))
        );
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
    }

    @Bean
    @Primary
    JwtEncoder jwtAccessTokenEncoder() {
        JWK jwk = new RSAKey
                .Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtils.getRefreshTokenPublicKey()).build();
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder() {
        JWK jwk = new RSAKey
                .Builder(keyUtils.getRefreshTokenPublicKey())
                .privateKey(keyUtils.getRefreshTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsManager);
        return provider;
    }

}
