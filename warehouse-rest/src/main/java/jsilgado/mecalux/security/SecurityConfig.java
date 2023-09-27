package jsilgado.mecalux.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {
			// -- Swagger UI v2
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			// -- Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**",
			// -- H2 Console
			"/h2-console/**"
			// other public endpoints of your API may be appended to this array
	};

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
	    .exceptionHandling()
	    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	    .and()
	    .sessionManagement()
	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
	    .authorizeRequests()
	    	.antMatchers(AUTH_WHITELIST).permitAll()
	    	.antMatchers("/auth/**").permitAll()
	    	.antMatchers(HttpMethod.GET, "/warehouses/rackspermutations/*").permitAll()
	    	.antMatchers(HttpMethod.GET, "/warehouses/**").permitAll()
	    	.antMatchers(HttpMethod.POST, "/**").authenticated()
		    .antMatchers(HttpMethod.PUT, "/**").authenticated()
		    .antMatchers(HttpMethod.DELETE, "/**").authenticated();

		http.headers().frameOptions().sameOrigin();


//		http.csrf().disable()
//	    .exceptionHandling()
//	    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//	    .and()
//	    .sessionManagement()
//	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	    .and()
//	    .authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll()
//	    .antMatchers("/auth/**").permitAll()
//	    .anyRequest()
//	    .authenticated();


//		http.csrf().disable().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and().authorizeRequests()
//			.antMatchers(HttpMethod.GET, "/warehouses/**").permitAll()
//			.and().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
//			.antMatchers("/auth/**").permitAll().anyRequest()
//			.authenticated().and();

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

}
