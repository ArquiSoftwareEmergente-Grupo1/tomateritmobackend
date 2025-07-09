package com.tomateritmo.arqemergente.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI eventWearPlatformOpenApi() {
    // General configuration
    var openApi = new OpenAPI();
    openApi
            .info(new Info()
                    .title("TomateRitmo Platform API")
                    .description("TomateRitmo Platform application REST API documentation.")
                    .version("v1.0.0")
                    .license(new License().name("Apache 2.0")
                            .url("https://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                    .description("TomateRitmo Platform wiki Documentation")
                    .url("https/TomateRitmo.com/docs"));

    // Return OpenAPI configuration object without security settings
    return openApi;
  }
}