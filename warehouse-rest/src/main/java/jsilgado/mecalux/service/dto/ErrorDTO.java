package jsilgado.mecalux.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ErrorDTO {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime date;
	private List<String> message;
	private String detail;

	public ErrorDTO(LocalDateTime date, List<String> message, String detail) {
		super();
		this.date = date;
		this.message = message;
		this.detail = detail;
	}

	// TODO
//	"timestamp": "2022-10-26T08:57:39.040+00:00",
//    "status": 401,
//    "error": "Unauthorized",
//    "message": "Full authentication is required to access this resource",
//    "path": "/warehouses/ef5fee09-46de-43d1-9288-3b6cb44fd02d"

}
