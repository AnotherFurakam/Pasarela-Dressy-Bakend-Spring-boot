package com.api.pasarela_dressy.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UniqueFieldException extends RuntimeException
{
    private List<String> errors = new ArrayList<>();
    private int status = HttpStatus.BAD_REQUEST.value();
    public UniqueFieldException(String message,List<String> errors){
        super(message);
        this.errors = errors;
    }
}
