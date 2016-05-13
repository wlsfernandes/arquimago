package br.com.furnas.aplicacao.fachada.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDeAutenticacao;
import br.com.furnas.aplicacao.servicos.IServicoDeAutenticacao;
import br.com.furnas.web.FormularioDeGED;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

@Transactional
public class FachadaDeAutenticacao implements IFachadaDeAutenticacao {

	private IServicoDeAutenticacao servicoDeAutenticacao;

	@Override
	public DfLoginInfo recuperarUsuario() {
		return servicoDeAutenticacao.recuperarUsuario();
	}

	@Override
	public IDfLoginInfo recuperarUsuario(String username) {
		
		return servicoDeAutenticacao.recuperarUsuario(username);
	}

	@Override
	public HashSet<GrantedAuthority> recuperarCredenciaisDoUsuario(String username) {
		return servicoDeAutenticacao.recuperarCredenciaisDoUsuario(username);
	}
	
	public IServicoDeAutenticacao getServicoDeAutenticacao() {
		return servicoDeAutenticacao;
	}

	public void setServicoDeAutenticacao(
			IServicoDeAutenticacao servicoDeAutenticacao) {
		this.servicoDeAutenticacao = servicoDeAutenticacao;
	}

	@Override
	public List<String> carregarListaCabinets() {
		return servicoDeAutenticacao.carregarListaCabinets();
	}

	@Override
	public List<String> carregarListaFolders() {
		return servicoDeAutenticacao.carregarListaFolders();
	}

	@Override
	public List<String> carregarListaDocs() {
		return servicoDeAutenticacao.carregarListaDocs();
	}

	@Override
	public FormularioDeGED carregarDiretorios() {
		List<String> listaFolders = servicoDeAutenticacao.carregarListaCabinets();
		//return servicoDeAutenticacao.adaptarDiretorio(listaFolders);
		return null;
	}

	
}
