package jsilgado.mecalux.persistence.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jsilgado.mecalux.model.Authority;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    /**
	 *
	 */
	private static final long serialVersionUID = -6661865001493266248L;

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

	@Column(name = "username", nullable = false, columnDefinition = "VARCHAR(100)")
    private String username;

	@Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;

	@Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String name;

	@Column(name = "surname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String surname;

	@Column(name = "email", nullable = true, columnDefinition = "VARCHAR(100)")
    private String email;

	@Column(name = "telephone", nullable = true, columnDefinition = "VARCHAR(20)")
    private String telephone;

    private boolean enabled = true;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserRol> lstUserRoles;


    @Override
	public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        lstUserRoles.forEach(userRol -> {
            autoridades.add(new Authority(userRol.getRol().getRol()));
        });
        return autoridades;
    }

    @Override
	public String getPassword() {
        return password;
    }


    @Override
	public boolean isEnabled() {
        return enabled;
    }


}