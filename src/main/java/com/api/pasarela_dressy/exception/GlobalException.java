package com.api.pasarela_dressy.exception;

import lombok.*;

@Builder
@Setter
@Getter
public class GlobalException extends Exception
{
    private String message;
    private int status;
}
