package com.api.pasarela_dressy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class Capitalize
{
    public String CapitalizeText(String text)
    {
        return text.substring(0,1).toUpperCase() + text.substring(1);
    }
}
