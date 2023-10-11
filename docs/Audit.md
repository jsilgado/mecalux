# Mecalux - Warehouses and Racks (Auditing with Spring Data annotations)

## :hammer_and_wrench: Technologies

- **Auditing with Spring Data annotations**

> Audit some fields of our table using only annotations. These annotations are inspired by SQL triggers which will trigger an event that will perform the required insertion in the database.

- **Integrating.** Add Spring Data dependency in Maven (If you don't have it yet).
```bash
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
</dependency>
```

- **Configuration.**
> In order to enable auditing we will add @EnableJpaAuditing in our @Configuration class.
```java
@Configuration
@EnableJpaAuditing
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
```

- **Add Entity Listener**
> Next we are going to add an @EntitlyListeners, to capture and audit information on the persistence and update of our entities. In this process we will be helped by the already created Spring class AuditingEntityListener. And the configuration will be as follows:
```java
@Entity
@EntityListeners(AuditingEntityListener.class)
```

- **Add Audit class @MappedSuperclass**
> Normally we want all our entities to be audited, so we will create an abstract class with the @MapperSuperclass annotation where we will create our common audit fields for all entities. Later we will extend our entities to this class.
```java 
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

}
```

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "WAREHOUSE")
public class Warehouse extends Audit {
```

- **AuditorAware**
> In many occasions we see that we want to add additional logic when saving user information. In those cases we are going to implement AuditorAware, with which we can overwrite some of its methods and add the information we need.
```java
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jsilgado.mecalux.persistence.entity.User;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Optional<String> cdUser = Optional.empty();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof User) {
			User user = (User) principal;
			cdUser = Optional.of(user.getUsername());
		}
		
		return cdUser;
	}

}
```
> In order to make use of the AuditorAware functionality, we will have to enable the JPA autidory with the auditorProvider, @EnableJpaAuditing(auditorAwareRef="auditorProvider") and a bean of this type to be able to perform the initialisation of the class.
```java
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
```