package br.com.furnas.aplicacao.servicos.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.furnas.aplicacao.servicos.IServicoDeAutenticacao;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDeAutenticacao;
import br.com.furnas.web.FormularioDeGED;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;


public class ServicoDeAutenticacao implements IServicoDeAutenticacao {
	
	private ConstrutorDeAutenticacao construtorDeAutenticacao;
	
	@Override
	public DfLoginInfo recuperarUsuario() {
		DfLoginInfo user = (DfLoginInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return user;
	}

	@Override
	public boolean hasRole(String role) {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

	@Override
	public IDfLoginInfo recuperarUsuario(String username) {
		// TODO Auto-generated method stub
		return construtorDeAutenticacao.recuperarUsuario(username);
	}	
	

	@Override
	public HashSet<GrantedAuthority> recuperarCredenciaisDoUsuario(String username) {
		return construtorDeAutenticacao.recuperarCredenciaisDoUsuario(username);
	}
	
	public ConstrutorDeAutenticacao getConstrutorDeAutenticacao() {
		return construtorDeAutenticacao;
	}

	public void setConstrutorDeAutenticacao(
			ConstrutorDeAutenticacao construtorDeAutenticacao) {
		this.construtorDeAutenticacao = construtorDeAutenticacao;
	}

	@Override
	public List<String> carregarListaCabinets() {
		return construtorDeAutenticacao.carregarListaCabinets();
	}

	@Override
	public List<String> carregarListaFolders() {
		return construtorDeAutenticacao.carregarListaFolders();
	}

	@Override
	public List<String> carregarListaDocs() {
		return construtorDeAutenticacao.carregarListaDocs();
	}

	@Override
	public FormularioDeGED carregarDiretorios() {
		List<String> listaCabinets = construtorDeAutenticacao.carregarListaCabinets();
		
		return null;
	}
	
	
}
