package com.api.pasarela_dressy.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ValidationErrorDto
{
    private String message;
    private List<String> fieldErrors = new ArrayList<>() ;

}
