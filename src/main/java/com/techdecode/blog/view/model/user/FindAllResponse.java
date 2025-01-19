package com.techdecode.blog.view.model.user;

import com.techdecode.blog.dto.UserDto;

import java.util.List;

public record FindAllResponse(
        List<UserDto> users,
        Integer size
) {
}
