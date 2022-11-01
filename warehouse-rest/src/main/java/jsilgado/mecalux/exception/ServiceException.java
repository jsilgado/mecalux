package jsilgado.mecalux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.BAD_REQUEST)
public class ServiceException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 38196519032276567L;

	public ServiceException (String message) {
		super(message);
	}


	public ServiceException(String resourceName, String message) {
		super(String.format("Service Exception in %s : %s", resourceName, message));

	}
}
