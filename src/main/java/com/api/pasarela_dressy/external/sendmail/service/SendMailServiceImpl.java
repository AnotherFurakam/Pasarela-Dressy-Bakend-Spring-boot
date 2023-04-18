package com.api.pasarela_dressy.external.sendmail.service;

import com.api.pasarela_dressy.exception.BadRequestException;
import com.api.pasarela_dressy.external.sendmail.dto.SendMailBodyDto;
import com.api.pasarela_dressy.external.sendmail.dto.SendMailResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendMailServiceImpl
    implements ISendMailService
{
    @Autowired
    private RestTemplate restTemplate;


    @Value("${microservices.sendmail.basepath}")
    private String basePath;

    @Override
    public SendMailResponseDto sendMailEmpleado(SendMailBodyDto mailBodyDto)
    {
        SendMailResponseDto response = restTemplate.postForObject(basePath+"send-mail/empleado", mailBodyDto, SendMailResponseDto.class);

        if (response != null && response.success())
        {
            return response;
        }
        throw new BadRequestException("Error al conectarse con el servicio SendMail");
    }
}
