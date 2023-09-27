package jsilgado.mecalux.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import feign.Response;
import feign.Util;
import jsilgado.mecalux.exception.enums.RestApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class FeignException extends RuntimeException{
	
	private static final long serialVersionUID = -478703596024233385L;

	@Getter
	@Setter
	private RestApiErrorCode code;

	@Getter
	@Setter
	private List<String> errors;
	@Getter
	@Setter
	private List<String> errorCode;

	@Getter
	@Setter
	private Integer status;

	@Getter
	private String exception;

	@Getter
	private String methodKey;

	@Getter
	private String message;

	@Getter
	private String contextPath;

	@Getter
	private String path;

	@Getter
	private String timestamp;

	@Getter
	private String error;

	@Getter
	private String error_description;

	private String cause;

	private String stackTrace;

	@Getter
	private String headers;

	@Getter
	@Setter
	private Map<String, String> additionalData;

	@Getter
	private String localizedMessage;

	public FeignException(String methodKey, Response response) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			if (response.body() != null) {
				String body = Util.toString(response.body().asReader(Charset.defaultCharset()));
				FeignException customFeignException = objectMapper.readValue(body, FeignException.class);

				this.status = response.status();
				this.message = customFeignException.getMessage();
				this.localizedMessage = customFeignException.getLocalizedMessage();
				this.additionalData = customFeignException.getAdditionalData();
				this.cause = customFeignException.getCauseAtt();
				this.stackTrace = customFeignException.getStackTraceAtt();
                this.code = customFeignException.getCode();
				this.errors = customFeignException.getErrors();
				this.errorCode = customFeignException.getErrorCode();
				this.methodKey = methodKey;
			} else {
				this.status = response.status();
				this.headers = response.headers().toString();
			}
		} catch (IOException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.error(errors.toString());
		}
	}

	public String getCauseAtt() {
		return this.cause;
	}

	public String getStackTraceAtt() {
		return this.stackTrace;
	}

	@Override
	public String toString() {
		return "{" + "status=" + status + ", exception='" + exception + '\'' + ", methodKey='" + methodKey + '\''
				+ ", contextPath='" + contextPath + '\'' + ", error='" + error + '\'' + ", error='" + error_description
				+ '\'' + ", message='" + message + '\'' + ", log='" + log + '\'' + '}';
	}

}
