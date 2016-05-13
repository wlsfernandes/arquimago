package br.com.furnas.autenticacao;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class Autenticacao {

	public static final String SIGLA_GRUPO_ANALISTA_COMPRAS = "sac4_compras";
	
	public User getUserDetails() {

		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return user;

		/*
		 * System.out.println(userDetails.getPassword());
		 * System.out.println(userDetails.getUsername());
		 * System.out.println(userDetails.isEnabled()); Authorities
		 */
	}
	
	public boolean hasRole(String role) {
		  @SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
		  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		  boolean hasRole = false;
		  for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals(role);
		     if (hasRole) {
			  break;
		     }
		  }
		  return hasRole;
		}

}
