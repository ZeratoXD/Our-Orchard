package com.generation.NossoPomar.configuration;

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
	OpenAPI springOurOrchardOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Project Our Ouchard")
						.description("Project Our Ouchard - Generation Brasil")
						.version("v0.0.1")
						.license(new License()
								.name("Generation Brasil")
								.url("https://brazil.generation.org/"))
						.contact(new Contact()
								.name("Generation Brasil")
								.url("https://github.com/conteudoGeneration")
								.email("nossopomargeneration@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Github")
						.url("https://github.com/projeto-integrador-nossopomar"));
	}

	@Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
		
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
					.forEach(operation -> {
						
						ApiResponses apiResponses = operation.getResponses();
						
						apiResponses.addApiResponse("200", createApiResponse("Sucess!"));
						apiResponses.addApiResponse("201", createApiResponse("Object Persisted!!"));
						apiResponses.addApiResponse("204", createApiResponse("Object Deleted!!"));
						apiResponses.addApiResponse("400", createApiResponse("Request Error!"));
						apiResponses.addApiResponse("401", createApiResponse("Unauthorized access!"));
						apiResponses.addApiResponse("403", createApiResponse("Access Forbidden!"));
						apiResponses.addApiResponse("404", createApiResponse("Object not found!"));
						apiResponses.addApiResponse("500", createApiResponse("Application error!"));
				
					}));			
		};
	}
	
	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);
	}
	
}
