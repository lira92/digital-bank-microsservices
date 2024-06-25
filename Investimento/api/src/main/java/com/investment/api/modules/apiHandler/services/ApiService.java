package com.investment.api.modules.apiHandler.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.api.modules.apiHandler.dto.ResponseDto;
import com.investment.api.modules.apiHandler.exceptions.ExternalApiException;

@Service
public class ApiService {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ApiService(ObjectMapper objectMapper, WebClient.Builder webClientBuilder) {
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder.build();
    }

    private <T> ResponseDto request(String url, HttpMethod method, T body, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        WebClient.RequestBodySpec requestSpec = this.webClient.method(method).uri(builder.toUriString());

        if (body != null) {
            requestSpec.bodyValue(body);
        }

        try {
            ResponseEntity<String> response = requestSpec.retrieve()
                                             .toEntity(String.class)
                                             .block();
            return this.bodyMapper(response);
        } catch (IOException e) {
            throw new ExternalApiException(500, "Error while parsing response: " + e.getMessage());
        } catch (WebClientResponseException e) {
            throw new ExternalApiException(500, "Error while requesting external API: " + e.getMessage(), e.getResponseBodyAsString());
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
