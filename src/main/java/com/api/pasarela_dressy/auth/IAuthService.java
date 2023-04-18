package com.api.pasarela_dressy.auth;

import com.api.pasarela_dressy.model.dto.Auth.LogedUserResponseDto;
import com.api.pasarela_dressy.model.dto.Auth.LoginDto;

public interface IAuthService
{
    LogedUserResponseDto login(LoginDto loginDto);
}
