package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI springBlogPessoalOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Blog Pessoal")
                .description("API do Projeto de Blog Pessoal - Generation Brasil")
                .version("v0.0.1")
                .license(new License()
                    .name("Generation  Brasil")
                    .url("https://brazil.generation.org/"))
                .contact(new Contact()
                    .name("Rodrigo Santos")
                    .url("https://github.com/rudr1gu")
                    .email("rodrigo.santos.ii@hotmail.com")))
            .externalDocs(new ExternalDocumentation()
                .description("Github")
                .url("https://github.com/rudr1gu/BlogPessoal_Generation"));
    }

    @Bean
    OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach( pathItem -> pathItem.readOperations()
                .forEach( operation -> {
                    ApiResponses apiResponses = operation.getResponses();

                    apiResponses.addApiResponse("200",  createApiResponse("Requisição bem sucedida"));
                    apiResponses.addApiResponse("201",  createApiResponse("Recurso criado"));
                    apiResponses.addApiResponse("204",  createApiResponse("Recurso deletado"));
                    apiResponses.addApiResponse("400",  createApiResponse("Requisição inválida"));
                    apiResponses.addApiResponse("401",  createApiResponse("Não autorizado"));
                    apiResponses.addApiResponse("403",  createApiResponse("Proibido"));
                    apiResponses.addApiResponse("404",  createApiResponse("Não encontrado"));
                    apiResponses.addApiResponse("500",  createApiResponse("Erro interno no servidor"));
            }));
        };
    }

    private ApiResponse createApiResponse(String description) {
        return new ApiResponse().description(description);
    }
}
