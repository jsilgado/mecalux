package jsilgado.mecalux.configuration;

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
