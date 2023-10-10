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
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<String> cdUser = Optional.of(principal.getUsername());
		
		return cdUser;
	}

}
