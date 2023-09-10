package com.personal_book_library_api.demo.dtos.auth;

import com.personal_book_library_api.demo.entities.User;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private Long id;
    private String idCard;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;
    private String role;

    public static UserDTO from(User user, String role, String idCard) {
        return builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .active(user.isActive())
                .idCard(idCard)
                .role(role)
                .build();
    }
}
