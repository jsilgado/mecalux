package jsilgado.mecalux.service.dto;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditDTO {

    private String cdUserCreate;
	
	private LocalDateTime dtRowCreate;
	
    private String cdUserUpdate;

    private LocalDateTime dtRowUpdate;
    
    private String cdUserDelete;

    private LocalDateTime dtRowDelete;
    
}
