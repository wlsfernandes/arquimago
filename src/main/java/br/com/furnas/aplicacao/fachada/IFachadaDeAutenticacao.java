package br.com.furnas.aplicacao.fachada;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import br.com.furnas.web.FormularioDeGED;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

@Component
public interface IFachadaDeAutenticacao
{
	DfLoginInfo recuperarUsuario();

	IDfLoginInfo recuperarUsuario(String username);

	HashSet<GrantedAuthority> recuperarCredenciaisDoUsuario(String username);

	List<String> carregarListaCabinets();

	List<String> carregarListaFolders();

	List<String> carregarListaDocs();

	FormularioDeGED carregarDiretorios();
	
}
