package com.techdecode.blog.view.model.user;

import com.techdecode.blog.models.roles.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
        @Valid
        @NotBlank
        String name,
        @Valid
        @Email
        String email,
        @Valid
        @Min(8)
        @NotBlank
        String password
        ) {
}
