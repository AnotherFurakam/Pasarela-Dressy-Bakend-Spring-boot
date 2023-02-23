package com.api.pasarela_dressy.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class UniqueErrorDto
{
    private String  message;
    private int status;
    private List<String> errors;
}
