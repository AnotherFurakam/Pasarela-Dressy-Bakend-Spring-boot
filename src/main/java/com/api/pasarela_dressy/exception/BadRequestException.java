package com.api.pasarela_dressy.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Setter
@Getter
public class BadRequestException extends RuntimeException {

    private int status = HttpStatus.BAD_REQUEST.value();
    public BadRequestException(String message) {
        super(message);
    }
}