package jsilgado.mecalux.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date datNow = new Date();
		Date datExpiration = new Date(datNow.getTime() + jwtExpirationInMs);

		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(datExpiration)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUsernameJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex) {
			throw new JwtException("Firma JWT no valida");
		}
		catch (MalformedJwtException ex) {
			throw new JwtException("Token JWT no valida");
		}
		catch (ExpiredJwtException ex) {
			throw new JwtException("Token JWT caducado");
		}
		catch (UnsupportedJwtException ex) {
			throw new JwtException("Token JWT no compatible");
		}
		catch (IllegalArgumentException ex) {
			throw new JwtException("La cadena claims JWT esta vacia");
		}
	}
}

