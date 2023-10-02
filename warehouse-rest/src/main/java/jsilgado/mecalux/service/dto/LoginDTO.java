package jsilgado.mecalux.service.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class LoginDTO {

	@NotNull(message = "username is mandatory")
	private String username;

	@NotNull(message = "password is mandatory")
	private String password;

}
