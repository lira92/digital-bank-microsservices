package br.com.biopark.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Async
	 public void sendWelcomeNotification(String email, String nome, Long numero, double saldo) {
       try {
           String url = "http://localhost:3002/notifications";
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);

           Map<String, Object> requestBody = new HashMap<>();
           List<String> recipients = new ArrayList<>();
           recipients.add(email);
           requestBody.put("messageRecipients", recipients);
           requestBody.put("messageSubject", "Bem vindo ao banco, " + nome + "!");
           requestBody.put("messageBody", "Seja bem vindo ao nosso banco, " + nome + "! A sua conta foi cadastrada com sucesso e o seu número é " + numero + ". Seu saldo atual é de " + saldo + " reais. Esperamos que sua experiência seja incrível conosco!");
           HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
           restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
       } catch (Exception e) {
           System.out.println("Não foi possível enviar o email. Erro: " + e.getMessage());
       }
   }
}
