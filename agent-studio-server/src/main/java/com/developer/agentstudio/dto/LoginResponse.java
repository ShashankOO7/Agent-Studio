package com.developer.agentstudio.dto;

public record LoginResponse(
        String accessToken,
        UserDto user
) {
}
