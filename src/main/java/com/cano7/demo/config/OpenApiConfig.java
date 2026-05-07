package com.cano7.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI yogurtMakerOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("Yogurt Maker API")
                .description("API para gestionar recetas, lotes y monitoreo de producción de yogurt.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Cano7")
                    .email("contact@cano7.dev")
                    .url("https://github.com/cano7"))
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
