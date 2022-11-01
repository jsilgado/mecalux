package jsilgado.mecalux.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    /**
	 *
	 */
	private static final long serialVersionUID = -3567768042743123830L;

	private String authorityName;

    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }

}
