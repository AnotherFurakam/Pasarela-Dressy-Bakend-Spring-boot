package com.api.pasarela_dressy.auth;

import com.api.pasarela_dressy.model.dto.Auth.LogedUserResponseDto;
import com.api.pasarela_dressy.model.dto.Auth.LoginDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth")
public class AuthController
{
    @Autowired
    IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LogedUserResponseDto> login(
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        LoginDto loginDto
    )
    {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }
}
