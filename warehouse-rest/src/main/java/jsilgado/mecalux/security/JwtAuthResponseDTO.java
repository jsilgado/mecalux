package jsilgado.mecalux.security;

import lombok.Data;

@Data
public class JwtAuthResponseDTO {

	private String tokenAcess;
	private String tokenType = "Bearer";

	public JwtAuthResponseDTO(String tokenAcess) {
		super();
		this.tokenAcess = tokenAcess;
	}

}
