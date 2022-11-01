package jsilgado.mecalux.persistence.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_ROL")
public class UserRol {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

}
