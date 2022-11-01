package jsilgado.mecalux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
public class JwtTokenException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 38196519032276567L;

	public JwtTokenException (String message) {
		super(message);
	}


	public JwtTokenException(HttpStatus httpStatus, String message) {
		super(String.format("JwtTokenException: %s . %s", httpStatus, message));

	}
}
