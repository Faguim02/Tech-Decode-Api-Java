package com.techdecode.blog.view.model.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SignInResponse(
        @Valid
        @NotNull
        String access_token
) {
}
