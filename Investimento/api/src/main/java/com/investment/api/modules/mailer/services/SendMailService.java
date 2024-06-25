package com.investment.api.modules.mailer.services;

import com.investment.api.modules.apiHandler.services.ApiService;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
import com.investment.api.modules.mailer.dto.MailDto;

import org.springframework.stereotype.Service;

@Service
public class SendMailService {
    
    private ApiService apiService;

    public SendMailService(ApiService apiService) {
        this.apiService = apiService;
    }

    public void sendMail(MailDto mailDto) {
        this.apiService.post(UrlEnum.EMAIL.getValue(), mailDto);
    }
}
