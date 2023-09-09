package com.personal_book_library_api.demo.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JsonProperty("isWriter")
    private boolean isWriter;
}
