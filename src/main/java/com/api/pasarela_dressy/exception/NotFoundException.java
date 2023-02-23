package com.api.pasarela_dressy.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Setter
@Getter
public class NotFoundException extends RuntimeException {

    private int status = HttpStatus.NOT_FOUND.value();
    public NotFoundException(String message) {
        super(message);
    }
}
