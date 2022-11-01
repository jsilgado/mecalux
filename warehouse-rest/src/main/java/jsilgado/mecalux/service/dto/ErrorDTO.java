package jsilgado.mecalux.service.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ErrorDTO {

	private Date date;
	private List<String> message;
	private String detail;

	public ErrorDTO(Date date, List<String> message, String detail) {
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
