package jsilgado.mecalux.security;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {

	private String tokenAcess;
	private String tokenType = "Bearer";

	public JWTAuthResponseDTO(String tokenAcess) {
		super();
		this.tokenAcess = tokenAcess;
	}

}
