# Mecalux - Warehouses and Racks (OpenApi)

## :hammer_and_wrench: Technologies

- **Open API** **[openapis.org](https://www.openapis.org/)**

> OpenAPI is a standard specification for describing RESTful APIs. It allows both humans and machines to discover the capabilities of an API without the need to read documentation or understand the implementation.
> CustomOpenAPI is a tool that helps developers create OpenAPI specifications. It provides a graphical user interface that makes it easy to define the endpoints, parameters, and other aspects of an API.
> In short, OpenAPI is a standard for describing APIs, and customOpenAPI is a tool that helps developers create OpenAPI specifications..

- **Integrating.** Add the OpenAPI dependency to your project.
```bash
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
</dependency>
```

- **OpenAPI configuration.**
> Spring Boot configuration file: application.yml
```bash
  swagger-ui:
    display-request-duration: true #Enables the display of request duration in the Swagger user interface.
    tags-sorter: alpha #Specifies how to order tags in the Swagger user interface.
    operations-sorter: alpha #Specifies how to order operations in the Swagger user interface
```

- **Generate build information.**
> https://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/maven-plugin/examples/build-info.html#
> Spring Boot Actuator displays build-related information if a META-INF/build-info.properties file is present. The build-info goal generates such file with the coordinates of the project and the build time. It also allows you to add an arbitrary number of additional properties:
```bash
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <executions>
    <execution>
      <id>build-info</id>
      <goals>
        <goal>build-info</goal>
      </goals>
      <configuration>
      <additionalProperties>
        <sourceEncoding>${project.build.sourceEncoding}</sourceEncoding>
        <custom>custom value</custom>
        <java.version>${java.version}</java.version>
        <java.vendor>${java.vendor}</java.vendor>
        </additionalProperties>
      </configuration>
    </execution>
  </executions>
</plugin>
```
> This configuration will generate a build-info.properties at the expected location with four additional keys. Note that maven.compiler.source and maven.compiler.target are expected to be regular properties available in the project. They will be interpolated as you would expect.

- **Customize OpenApi.**
```bash
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
```
