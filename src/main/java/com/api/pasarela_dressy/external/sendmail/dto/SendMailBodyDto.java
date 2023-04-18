package com.api.pasarela_dressy.external.sendmail.dto;

public record SendMailBodyDto(
    String nombres,
    String apellidos,
    String correo
)
{
}
