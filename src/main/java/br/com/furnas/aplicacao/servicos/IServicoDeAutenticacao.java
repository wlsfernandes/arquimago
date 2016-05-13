package br.com.furnas.aplicacao.servicos;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import br.com.furnas.web.FormularioDeGED;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

public interface IServicoDeAutenticacao {

	boolean hasRole(String role);
	
	IDfLoginInfo recuperarUsuario(String username);
	
	HashSet<GrantedAuthority> recuperarCredenciaisDoUsuario(String username);
	
	DfLoginInfo recuperarUsuario();
	
	List<String> carregarListaCabinets();
	
	List<String> carregarListaFolders();
	
	List<String> carregarListaDocs();
	
	FormularioDeGED carregarDiretorios();
}
