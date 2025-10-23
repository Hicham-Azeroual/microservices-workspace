package com.eazybytes.dto;

public record AccountsMsgDto(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {
}
