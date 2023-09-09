package com.personal_book_library_api.demo.security;

import com.personal_book_library_api.demo.daos.UserRepository;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.User;
import com.personal_book_library_api.demo.entities.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.Collections;
import java.util.Optional;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        Long userId = Long.valueOf(source.getSubject());
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }

        User user = optionalUser.get();

        return new UsernamePasswordAuthenticationToken(user, source, user.getAuthorities());
    }
}
