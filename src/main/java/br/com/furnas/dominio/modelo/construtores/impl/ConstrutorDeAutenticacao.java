package br.com.furnas.dominio.modelo.construtores.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.furnas.autenticacao.GrantedAuthorityImpl;
import br.com.furnas.dados.relacional.dfs.IRepositorioDeAutenticacao;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDeAutenticacao;

import com.documentum.fc.common.IDfLoginInfo;

public class ConstrutorDeAutenticacao extends EntidadeBase implements
		IConstrutorDeAutenticacao {

	private IRepositorioDeAutenticacao repositorioAutenticacao;

	@Override
	public User autenticaUsuario(String username, String Password) {
		return null;
	}

	@Override
	public IDfLoginInfo recuperarUsuario(String username) {
		// TODO Auto-generated method stub
		return repositorioAutenticacao.recuperarUsuario(username);
	}

	@Override
	public HashSet<GrantedAuthority> recuperarCredenciaisDoUsuario(String username) {

		List<String> listaDeCredenciais = repositorioAutenticacao
				.recuperarCredenciaisDoUsuario(username);

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (int i = 0; i < listaDeCredenciais.size(); i++)

		{

			GrantedAuthority authority = new GrantedAuthorityImpl(listaDeCredenciais.get(i).toString());
		    authorities.add(authority);
		
		}
		return  (HashSet<GrantedAuthority>) authorities;

	}

	public IRepositorioDeAutenticacao getRepositorioAutenticacao() {
		return repositorioAutenticacao;
	}

	public void setRepositorioAutenticacao(
			IRepositorioDeAutenticacao repositorioAutenticacao) {
		this.repositorioAutenticacao = repositorioAutenticacao;
	}

	public List<String> carregarListaCabinets() {
		return repositorioAutenticacao.carregarListaCabinets();
	}

	public List<String> carregarListaFolders() {
		return repositorioAutenticacao.carregarListaFolders();
	}

	public List<String> carregarListaDocs() {
		return repositorioAutenticacao.carregarListaDocs();
	}

}