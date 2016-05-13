package br.com.furnas.autenticacao;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import br.com.furnas.aplicacao.fachada.IFachadaDeAutenticacao;
import br.com.furnas.integracao.constants.RepositoryConstants;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private IFachadaDeAutenticacao fachadaDeAutenticacao;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String REPOSITORIO_NO_DOCUMENTUM = "ged_supr_dev";
		
		DfClientX client = new DfClientX();
		IDfClient dfClient;
		IDfSessionManager sessionManager = null;

		IDfLoginInfo login = new DfLoginInfo();
		login = fachadaDeAutenticacao.recuperarUsuario(authentication.getName().toLowerCase());

		if (login == null) {
			
			throw new BadCredentialsException("Usuário não localizado");
		} else {

			login.setPassword((String) authentication.getCredentials());

		}

		try {

			dfClient = client.getLocalClient();
			sessionManager = dfClient.newSessionManager();
			sessionManager.setIdentity(REPOSITORIO_NO_DOCUMENTUM, login);
			sessionManager.authenticate(REPOSITORIO_NO_DOCUMENTUM);
			sessionManager.clearIdentities();
			sessionManager = null;
		
		} catch (Exception e) {

			throw new BadCredentialsException("Senha não confere.");
		}

		RepositoryConstants.REPOSITORY_NAME = REPOSITORIO_NO_DOCUMENTUM;
		RepositoryConstants.USER_NAME = login.getUser();
		RepositoryConstants.USER_PASSWORD = login.getPassword();
		
		
		Set<GrantedAuthority> authorities = fachadaDeAutenticacao.recuperarCredenciaisDoUsuario(login.getUser());
	
		return new UsernamePasswordAuthenticationToken(login,login.getPassword(), authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	public IFachadaDeAutenticacao getFachadaDeAutenticacao() {
		return fachadaDeAutenticacao;
	}

	public void setFachadaDeAutenticacao(
			IFachadaDeAutenticacao fachadaDeAutenticacao) {
		this.fachadaDeAutenticacao = fachadaDeAutenticacao;
	}
}