package com.bookstore.dto.user;

import com.bookstore.lib.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Size(min = 8, max = 50)
        @Email
        String email,
        @PasswordValidator
        String password
) {
}
