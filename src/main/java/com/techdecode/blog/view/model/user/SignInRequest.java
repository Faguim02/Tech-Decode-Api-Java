package com.techdecode.blog.view.model.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @Valid
        @Email
        @NotBlank
        String email,
        @Valid
        @NotBlank
        String password
) {
}
