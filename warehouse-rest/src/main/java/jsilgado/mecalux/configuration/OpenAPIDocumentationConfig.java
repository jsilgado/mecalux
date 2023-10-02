package jsilgado.mecalux.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIDocumentationConfig {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Autowired
	private BuildProperties buildProperties;

	private static final String BEARER = "TOKEN";

	@Bean
	public OpenAPI customOpenAPI() {
		// define the apiKey SecuritySchema
		return new OpenAPI().components(new Components().addSecuritySchemes(BEARER, apiKeySecuritySchema()))
				.info(new Info().version(buildProperties.getVersion()).title("Warehouse API")
						.description("RESTful services documentation with OpenAPI 3."))
				.security(Arrays.asList(new SecurityRequirement().addList(BEARER)))
				.addServersItem(new Server().url(contextPath));
	}

	public SecurityScheme apiKeySecuritySchema() {
		return new SecurityScheme().name("Authorization").description("Description about the TOKEN").type(Type.HTTP)
				.bearerFormat("JWT").scheme("bearer");
	}

}
