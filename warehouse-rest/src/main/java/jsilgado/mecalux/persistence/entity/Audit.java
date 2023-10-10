package jsilgado.mecalux.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Audit {

	
	
	@Column(name = "cd_user_create")
	@CreatedBy
    private String cdUserCreate;
	
	@Column(name = "dt_row_create")
	@CreatedDate
	private LocalDateTime dtRowCreate;
	
	@Column(name = "cd_user_update")
    @LastModifiedBy
    private String cdUserUpdate;

    @Column(name = "dt_row_update")
    @LastModifiedDate
    private LocalDateTime dtRowUpdate;
    
    @Column(name = "cd_user_delete")
    private String cdUserDelete;

    @Column(name = "dt_row_delete")
    private LocalDateTime dtRowDelete;
    
}
