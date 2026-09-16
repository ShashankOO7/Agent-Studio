package com.developer.agentstudio.dto;

import com.developer.agentstudio.entity.Role;

public record UserDto(
        Long id,
        String username,
        String email,
        Role role
) {
}
