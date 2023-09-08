package com.personal_book_library_api.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        System.out.println("User Details");
        System.out.println(jdbcUserDetailsManager.toString());

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
                configurer // hasAuthority("WRITER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/*/add").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/*/read").hasAuthority("READER")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*/remove").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/nextpage").hasAuthority("READER")
                        .requestMatchers(HttpMethod.PATCH, "/api/books/previouspage").hasAuthority("READER")
                        .requestMatchers(HttpMethod.GET, "/api/books/*/currentreaders").hasAuthority("WRITER")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("WRITER")
                        .anyRequest().permitAll()
                );
        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
