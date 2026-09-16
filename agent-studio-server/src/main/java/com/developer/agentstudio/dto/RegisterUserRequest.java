package com.developer.agentstudio.dto;

import com.developer.agentstudio.entity.Role;

public record RegisterUserRequest(
        String username,
        String email,
        String password
) {
}
