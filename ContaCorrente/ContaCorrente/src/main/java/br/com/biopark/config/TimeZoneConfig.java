package br.com.biopark.config;

import java.time.ZoneId;
import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

@org.springframework.context.annotation.Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        // Configurando o fuso horário para Brasília (GMT-3)
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("GMT-3")));
    }
}
