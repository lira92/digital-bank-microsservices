package com.investment.api.modules.accrual.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;
import com.investment.api.modules.apiHandler.services.ApiService;

@Service
public class GetRateService {

    private final ApiService apiService;

    public GetRateService(ApiService apiService) {
        this.apiService = apiService;
    }

    public double getRate() {
        String url = UrlEnum.BACK_OFFICE.getValue();

        ResponseDto response = this.apiService.get(url);

        if (response.statusCode().is2xxSuccessful()) {
        return
        this.convertToDailyRate(Float.parseFloat(response.body().get("juros").toString()));
        }

        throw new ExternalApiException(500, "Error while requesting back office API:" + response.body());
    }

    private double convertToDailyRate(double anualRate) {
        return Math.pow(1 + anualRate, 1.0 / LocalDate.now().lengthOfYear()) - 1;
    }
}