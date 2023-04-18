package com.api.pasarela_dressy.model.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogedUserResponseDto
{
    private AuthEmpleadoResponseDto empleado;
    private String token;
}
