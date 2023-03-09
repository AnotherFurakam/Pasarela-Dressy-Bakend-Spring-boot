package com.api.pasarela_dressy.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UniqueFieldException extends RuntimeException
{
    private List<String> errors = new ArrayList<>();
    private int status = HttpStatus.BAD_REQUEST.value();

    public UniqueFieldException(List<String> errors){
        this.errors = errors;
    }
}
