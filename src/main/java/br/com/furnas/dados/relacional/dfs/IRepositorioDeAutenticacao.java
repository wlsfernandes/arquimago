package br.com.furnas.dados.relacional.dfs;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.documentum.fc.common.IDfLoginInfo;

public interface IRepositorioDeAutenticacao {
	

	UserDetails recuperarDadosDoUsuario(Map<Object, Object> mapUsuario);
	IDfLoginInfo constroiUsuarioAPartirDoBanco(Map<Object, Object> mapUsuario);
	IDfLoginInfo recuperarUsuario(String username);
	List<String> recuperarCredenciaisDoUsuario(String username);
	List<String> recuperarEmailDoGrupoEspecifico(String groupname);
	List<String> carregarListaCabinets();
	List<String> carregarListaFolders();
	List<String> carregarListaDocs();
}
