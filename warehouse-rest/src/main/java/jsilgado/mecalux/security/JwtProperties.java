package jsilgado.mecalux.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

	private Security security;
	private String timeZone;
	private String issuer;
	private Token token;
	private Excluded excluded;

	@Data
	public static class Security {
		private boolean enabled;
	}

	@Data
	public static class Token {
		private Auth auth;
		private String secret;
		private int expiresInMs;
	}

	@Data
	public static class Auth {
		private String path;
	}

	@Data
	public static class Excluded {
		private String path;
	}

}
