package com.personal_book_library_api.demo.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}