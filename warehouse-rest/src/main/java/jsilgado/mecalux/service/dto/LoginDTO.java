package jsilgado.mecalux.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginDTO {

	@NotNull(message = "username is mandatory")
	private String username;

	@NotNull(message = "password is mandatory")
	private String password;

}
