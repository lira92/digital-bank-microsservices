package com.investment.api.modules.apiHandler.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;

@Service
public class ApiService {
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private <T> ResponseDto request(String url, HttpMethod method, T body, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity<T> entity = new HttpEntity<>(body);

        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), method, entity, String.class);
            return this.bodyMapper(response);
        } catch (IOException e) {
            throw new ExternalApiException(500, "Error while parsing response: " + e.getMessage());
        } catch (RestClientException e) {
            throw new ExternalApiException(500, "Error while requesting external API: " + e.getMessage());
        }
    }

    private ResponseDto bodyMapper(ResponseEntity<String> response) throws IOException {
        if (response.getBody() == null) {
            return new ResponseDto(response.getStatusCode(), null);
        }

        Map<String, Object> bodyMap = this.objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
        return new ResponseDto(response.getStatusCode(), bodyMap);
    }

    public ResponseDto get(String url, Map<String, String> queryParams) {
        return this.request(url, HttpMethod.GET, null, queryParams);
    }

    public ResponseDto get(String url) {
        return this.request(url, HttpMethod.GET, null, null);
    }

    public ResponseDto post(String url, Object body) {
        return this.request(url, HttpMethod.POST, body, null);
    }

    public ResponseDto patch(String url, Object body) {
        return this.request(url, HttpMethod.PATCH, body, null);
    }
}
