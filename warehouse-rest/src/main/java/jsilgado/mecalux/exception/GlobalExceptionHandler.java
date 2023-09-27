package jsilgado.mecalux.exception;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.JwtException;
import jsilgado.mecalux.exception.enums.RestApiErrorCode;
import jsilgado.mecalux.service.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));
		return buildErrorResponse(RestApiErrorCode.INTERNAL, Arrays.asList(exception.getMessage()),
				Arrays.asList(exception.getMessage()), HttpStatus.NOT_FOUND,
				webRequest.getDescription(false));
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorDTO> handleServiceException(ServiceException exception, WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));		
		return buildErrorResponse(RestApiErrorCode.INTERNAL, Arrays.asList(exception.getMessage()),
				Arrays.asList(exception.getMessage()), HttpStatus.BAD_REQUEST,
				webRequest.getDescription(false));
		
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> handleGlobalException(Exception exception, WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));
		return buildErrorResponse(RestApiErrorCode.INTERNAL, Arrays.asList(exception.getMessage()),
				Arrays.asList(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR,
				webRequest.getDescription(false));
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ErrorDTO> handleJwtException(Exception exception, WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));
		return buildErrorResponse(RestApiErrorCode.SECURITY, Arrays.asList("Access denied"),
				Arrays.asList(exception.getMessage()), HttpStatus.BAD_REQUEST,
				webRequest.getDescription(false));
	}

	// Tratamiento errores clientes feigns
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorDTO> feignException(FeignException exception, WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));
		return buildErrorResponse(RestApiErrorCode.THIRD_PARTY, exception.getErrorCode(),
				Arrays.asList(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR,
				webRequest.getDescription(false));
	}
	
	
	// Tratamiento errores clientes feigns
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDTO> usernameNotFoundException(UsernameNotFoundException exception, WebRequest webRequest) {
		log.error(getErrorMessageUsingHttpRequest(webRequest));
		return buildErrorResponse(RestApiErrorCode.THIRD_PARTY, Arrays.asList("User not found"),
				Arrays.asList(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR,
				webRequest.getDescription(false));
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

	protected ResponseEntity<ErrorDTO> buildErrorResponse(RestApiErrorCode restApiErrorCode, List<String> errorCodes,
			List<String> errorMessages, HttpStatus httpStatus, String details) {

		// TODO - restApiErrorCode, poner en ErrorDTO
		// TODO - errorCodes, poner en ErrorDTO
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ErrorDTO error = new ErrorDTO(new Date(), errorMessages, details);
		
		log.error(error.toString());

		return new ResponseEntity<>(error, headers, httpStatus);
	}

	/**
	 * Using the given {@link ServerWebExchange} builds a message with information
	 * about the Http request
	 *
	 * @param request {@link WebRequest} with the request information
	 *
	 * @return error message with Http request information
	 */
	protected String getErrorMessageUsingHttpRequest(WebRequest request) {
		HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
		return format("There was an error trying to execute the request with: %s" + "Http method = %s %s " + "Uri = %s",
				System.lineSeparator(), httpRequest.getMethod(), System.lineSeparator(), httpRequest.getRequestURI());
	}
}
