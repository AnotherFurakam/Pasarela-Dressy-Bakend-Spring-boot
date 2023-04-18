package com.api.pasarela_dressy.external.sendmail.service;

import com.api.pasarela_dressy.external.sendmail.dto.SendMailBodyDto;
import com.api.pasarela_dressy.external.sendmail.dto.SendMailResponseDto;

public interface ISendMailService
{
    SendMailResponseDto sendMailEmpleado(SendMailBodyDto mailBodyDto);
}
