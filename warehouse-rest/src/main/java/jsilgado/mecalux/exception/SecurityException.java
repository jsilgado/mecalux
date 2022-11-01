package jsilgado.mecalux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.UNAUTHORIZED)
public class SecurityException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 38196519032276567L;

	public SecurityException (String message) {
		super(message);
	}


	public SecurityException(String resourceName, String message) {
		super(String.format("Security Exception in %s : %s", resourceName, message));

	}
}
