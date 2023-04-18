package com.api.pasarela_dressy.auth;

import com.api.pasarela_dressy.model.dto.Auth.AuthEmpleadoResponseDto;
import com.api.pasarela_dressy.model.dto.Auth.LogedUserResponseDto;
import com.api.pasarela_dressy.model.dto.Auth.LoginDto;
import com.api.pasarela_dressy.model.entity.AsignacionEntity;
import com.api.pasarela_dressy.model.entity.EmpleadoEntity;
import com.api.pasarela_dressy.repository.AsignacionRepository;
import com.api.pasarela_dressy.repository.EmpleadoRepository;
import com.api.pasarela_dressy.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IAuthServiceImp
    implements IAuthService
{
    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Override
    public LogedUserResponseDto login(LoginDto loginDto)
    {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.correo(), loginDto.contrasenia()));

        EmpleadoEntity empleado = empleadoRepository.getOneByCorreo(loginDto.correo()).orElseThrow();

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", empleado.getAuthorities().toString());

        String jwtToken = jwtService.generateToken(claims, empleado);

        AuthEmpleadoResponseDto empleadoResponseDto = new AuthEmpleadoResponseDto(empleado.getId_empleado().toString(),
                                                                                  empleado.getNombres(),
                                                                                  empleado.getApellido_pat()
        );

        return LogedUserResponseDto.builder().empleado(empleadoResponseDto).token(jwtToken).build();

    }
}
