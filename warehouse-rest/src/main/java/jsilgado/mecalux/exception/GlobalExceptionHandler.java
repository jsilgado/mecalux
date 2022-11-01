package jsilgado.mecalux.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.JwtException;
import jsilgado.mecalux.service.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		ErrorDTO errorDTO = new ErrorDTO(new Date(), Arrays.asList(exception.getMessage()), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorDTO> handleServiceException(ServiceException exception, WebRequest webRequest){
		ErrorDTO errorDTO = new ErrorDTO(new Date(), Arrays.asList(exception.getMessage()), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> handleGlobalException(Exception exception, WebRequest webRequest){
		ErrorDTO errorDTO = new ErrorDTO(new Date(), Arrays.asList(exception.getMessage()), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorDTO> handleJwtException(Exception exception, WebRequest webRequest){
		ErrorDTO errorDTO = new ErrorDTO(new Date(), Arrays.asList(exception.getMessage()), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.add(field + " : " + message);

		});

		ErrorDTO errorDTO = new ErrorDTO(new Date(), errors, webRequest.getDescription(false));

		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}
}
