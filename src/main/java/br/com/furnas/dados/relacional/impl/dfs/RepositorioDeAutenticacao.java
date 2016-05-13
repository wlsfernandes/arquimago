package br.com.furnas.dados.relacional.impl.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeAutenticacao;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

@Repository
@SuppressWarnings("rawtypes") 
public class RepositorioDeAutenticacao extends RepositorioGenericoDados
		implements IRepositorioDeAutenticacao {

	@Override
	public IDfLoginInfo constroiUsuarioAPartirDoBanco(
			Map<Object, Object> mapUsuario) {

		IDfLoginInfo usuario = new DfLoginInfo();
		String username = (String) mapUsuario.get("user_name");
		String password = (String) mapUsuario.get("user_password");
		
		usuario.setUser(username);
		usuario.setPassword(password);
		return usuario;
	}

	@Override
	public IDfLoginInfo recuperarUsuario(String username) {
		List<Map<Object, Object>> results = this
				.obterTodos("SELECT user_name, user_password, user_address FROM dm_user WHERE user_os_name = '"
						+ username + "'");

		IDfLoginInfo usuario = null;

		Map<Object, Object> mapUsuario = results.size() != 0 ? results.get(0)
				: null;

		if (mapUsuario != null) {
			usuario = constroiUsuarioAPartirDoBanco(mapUsuario);
		}

		return usuario;
	}

	@Override
	public UserDetails recuperarDadosDoUsuario(Map<Object, Object> mapUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> recuperarCredenciaisDoUsuario(String username) {

		// List<Map<String, Object>> maps = new ArrayList<Map<String,
		// Object>>();
		List<Map<Object, Object>> results = this
				.obterTodos("select group_name from dm_group where any users_names = '"
						+ username + "' and group_class = 'group'");

		List<String> listaDeCredenciais = new ArrayList<String>();
		for (Map map : results) { 
			listaDeCredenciais.addAll(map.values()); 
		}

		return listaDeCredenciais;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> recuperarEmailDoGrupoEspecifico(String groupname) {

		String sql = "SELECT u.user_address FROM dm_user u, dm_group g ";
		sql += " WHERE ANY g.users_names = u.user_name and r_is_group = 0 and u.user_state=0 "; 
		sql += " and g.group_name='"+ groupname +"' order by u.user_name";
		
		List<Map<Object, Object>> results = this.obterTodos(sql);

		List<String> listaEmails = new ArrayList<String>();
		for (Map map : results) { 
			listaEmails.addAll(map.values()); 
		}

		return listaEmails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> carregarListaCabinets() {
		String sql = "select object_name, r_object_id, owner_name from dm_cabinet order by object_name";
		List<Map<Object, Object>> results = this.obterTodos(sql);

		List<String> listaDeCabinets = new ArrayList<String>();
		for (Map map : results) { 
			listaDeCabinets.addAll(map.values()); 
		}

		return listaDeCabinets;
	}

	@SuppressWarnings("unchecked")
	@Override
	//public List<String> carregarListaFolders(String nomeRepositorio, String tipoOrdenacao) {
	public List<String> carregarListaFolders() {
		String sql = "select distinct r_object_type from dm_document(all) where folder('/SAC4', descend)";
		
		//String sql = "select distinct r_object_type from dm_document(all) where folder('"+ nomeRepositorio +"', " + tipoOrdenacao + ")";
		
		List<Map<Object, Object>> results = this.obterTodos(sql);

		List<String> listaDeFolders = new ArrayList<String>();
		for (Map map : results) { 
			listaDeFolders.addAll(map.values()); 
		}

		return listaDeFolders;
	}

	@SuppressWarnings("unchecked")
	@Override
	//public List<String> carregarListaDocs(String nomeFolder) {
	public List<String> carregarListaDocs() {
		String sql = "select object_name, r_object_id, r_object_type from dm_document(all) where r_object_type like '%sac4_metadado%'";
		
		//String sql = "select object_name, r_object_id, r_object_type from dm_document(all) where r_object_type like '%" + nomeFolder + "%'";		 
		List<Map<Object, Object>> results = this.obterTodos(sql);

		List<String> listaDocs = new ArrayList<String>();
		for (Map map : results) { 
			listaDocs.addAll(map.values()); 
		}

		return listaDocs;
	}
}
