package jsilgado.mecalux.exception.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RestApiErrorCode {
	INTERNAL("Internal Error"), SECURITY("Security Error"), VALIDATION("Validation Error"),
	FUNCTIONAL("Functional Error"), THIRD_PARTY("ThirdParty Error");

	private String value;

	RestApiErrorCode(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

}
