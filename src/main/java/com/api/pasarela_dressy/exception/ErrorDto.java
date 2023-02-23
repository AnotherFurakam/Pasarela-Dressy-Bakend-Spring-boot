package com.api.pasarela_dressy.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorDto {
    private String message;
    private int status;
}
