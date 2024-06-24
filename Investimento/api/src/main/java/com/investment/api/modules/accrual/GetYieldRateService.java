package com.investment.api.modules.accrual;

import org.springframework.stereotype.Service;

import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.enums.UrlEnum;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;
import com.investment.api.modules.apiHandler.services.ApiService;

@Service
public class GetYieldRateService {
    
    private final ApiService apiService;
    
    public GetYieldRateService(ApiService apiService) {
        this.apiService = apiService;
    }

    public float getYieldRate(Long userId) {
        String url = UrlEnum.BACK_OFFICE.getValue();

        ResponseDto response = this.apiService.get(url);

        if (response.statusCode().is2xxSuccessful()) {
            return Float.parseFloat(response.body().get("juros").toString());
        }

        throw new ExternalApiException(500, "Error while requesting back office API");
    }
}
