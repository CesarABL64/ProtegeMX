package com.proyectovecinal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI proyectoVecinalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Proyecto Vecinal API")
                        .description("API REST para la plataforma de seguridad ciudadana colaborativa")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Proyecto Vecinal")
                                .email("contacto@proyectovecinal.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor local"),
                        new Server().url("https://proyecto-vecinal.onrender.com").description("Producción (Render)")
                ));
    }
}
