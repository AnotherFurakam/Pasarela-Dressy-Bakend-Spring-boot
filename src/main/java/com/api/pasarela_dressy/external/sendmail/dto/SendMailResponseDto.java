package com.api.pasarela_dressy.external.sendmail.dto;

public record SendMailResponseDto(
    String message,
    boolean success
)
{
}
