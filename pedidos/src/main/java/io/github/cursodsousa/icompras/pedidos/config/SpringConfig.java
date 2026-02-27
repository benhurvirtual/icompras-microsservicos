package io.github.cursodsousa.icompras.pedidos.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper(){
        Object mapper = new ObjectMapper();

        ((ObjectMapper) mapper).registerModule(new Jdk8Module());
        ((ObjectMapper) mapper).registerModule(new JavaTimeModule());

        ((ObjectMapper) mapper).setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ((ObjectMapper) mapper).configure(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES, false);

        return (ObjectMapper) mapper;
    }
}
