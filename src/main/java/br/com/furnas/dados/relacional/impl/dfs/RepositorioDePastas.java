package br.com.furnas.dados.relacional.impl.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.furnas.dados.relacional.dfs.IRepositorioDePastas;
import br.com.furnas.dominio.modelo.Pastas;

@Repository
public class RepositorioDePastas extends RepositorioGenericoDados implements IRepositorioDePastas
{

	@Override
	public List<Pastas> buscaPastas(String identificadorArmario) {
		
		String sql = "select fld.r_object_id, fld.object_name, fld.r_link_cnt from dm_folder fld   ";
		sql += "where fld.i_ancestor_id  != fld.r_object_id  ";
		sql += "and fld.i_ancestor_id = '" + identificadorArmario + "' ";
		sql += "order by object_name enable (row_based)   ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Pastas> listaDePastas = new ArrayList<Pastas>();
		
		for (int i = 0; i < results.size(); i++) {
			Pastas newPastas = new Pastas();
			Map<Object, Object> map = results.get(i);

			String objectId = (String) map.get("r_object_id");
			String descricaoDoArmario = (String) map.get("object_name");
			String qtdPastasArmario =  (String) map.get("r_link_cnt");
				
			newPastas.setObjectId(objectId);
			newPastas.setDescricao(descricaoDoArmario);
			newPastas.setQtdPastas(Long.parseLong(qtdPastasArmario));
			newPastas.setQtdDocumentos(getQtdDocumentosPasta(objectId));
		
			listaDePastas.add(newPastas);
		}

		return listaDePastas;
	}
	
	@Override
	public Pastas buscaBreadcrumb(String identificadorArmario) {
		
		String sql = "select fld.r_object_id, fld.object_name, fld.i_ancestor_id, fld.r_folder_path  from dm_folder fld   ";
		sql += "where fld.r_object_id = '" + identificadorArmario + "' ";
		sql += "order by fld.i_ancestor_id desc ENABLE(ROW_BASED)";
				
		List<Map<Object, Object>> results = this.obterTodos(sql);
		Pastas newPastas = new Pastas();
		
		Map<Object, Object> mapInfo = results.get(0);
		String objectId = (String) mapInfo.get("r_object_id");
		String descricaoDoArmario = (String) mapInfo.get("object_name");
		
		String pathPasta = "", i_ancestor_id = "";
		
		for (int i = 0; i < results.size(); i++) 
		{
			Map<Object, Object> map = results.get(i);

			String auxObjectIds = (String) map.get("i_ancestor_id");
			String auxPathPasta = (String) map.get("r_folder_path");
			
			if(auxPathPasta.length() > 0)
			{
				pathPasta = (String) map.get("r_folder_path");
			}
			
			i_ancestor_id += auxObjectIds + ";";
		}
		
		i_ancestor_id = i_ancestor_id.substring(0, i_ancestor_id.length()-1);
		
		newPastas.setObjectId(objectId);
		newPastas.setDescricao(descricaoDoArmario);
		newPastas.setPath(pathPasta);
		newPastas.setObjectIdsPasta(i_ancestor_id);
		
		return newPastas;
	}

	private long getQtdDocumentosPasta(String objectId) {
		
		String sql = "select count(doc.r_object_id) as docs from dm_document doc, dm_folder fld, dm_cabinet cab ";
		sql += "where doc.i_folder_id = fld.r_object_id and fld.i_ancestor_id = cab.r_object_id ";
		sql += "and fld.r_object_id = '" + objectId + "' ";
		sql += "UNION  ";
		sql += "select 0 as docs  ";
		sql += "from dm_folder fld where fld.r_link_cnt = 0  ";
		sql += "and fld.r_object_id = '" + objectId + "' "; 
		sql += "order by 1 enable (row_based)  ";
		
		List<Map<Object, Object>> results = this.obterTodos(sql);
		Map<Object, Object> map = results.get(0);

		String qtdPastasDocs =  (String) map.get("docs");
		long qtdDocs = Long.parseLong(qtdPastasDocs);
		
		return qtdDocs;
	}

	@Override
	public List<Pastas> buscaPastasEPastas(String identificadorArmarioPasta) {
		
		String sql = "select fld.r_object_id, fld.object_name, fld.r_link_cnt from dm_folder fld   ";
		sql += "where fld.i_ancestor_id  != fld.r_object_id  ";
		sql += "and fld.r_object_id = '" + identificadorArmarioPasta + "' ";
		sql += "order by object_name enable (row_based)   ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Pastas> listaDePastas = new ArrayList<Pastas>();
		
		for (int i = 0; i < results.size(); i++) {
			Pastas newPastas = new Pastas();
			Map<Object, Object> map = results.get(i);

			String objectId = (String) map.get("r_object_id");
			String descricaoDoArmario = (String) map.get("object_name");
			String qtdPastasArmario =  (String) map.get("r_link_cnt");

			newPastas.setObjectId(objectId);
			newPastas.setDescricao(descricaoDoArmario);
			newPastas.setQtdPastas(Long.parseLong(qtdPastasArmario));
			newPastas.setQtdDocumentos(getQtdDocumentosPasta(objectId));
		
			listaDePastas.add(newPastas);
		}

		return listaDePastas;
	}

	@Override
	public Pastas buscaDadosPasta(String identificadorArmario) {
		String sql = "select r_object_id, object_name, acl_name, r_folder_path, r_link_cnt from dm_folder ";
		sql += "where r_object_id = '" + identificadorArmario + "' ";
		
		List<Map<Object, Object>> results = this.obterTodos(sql);
		Map<Object, Object> map = results.get(0);
		
		Pastas newPastas = new Pastas();

		String objectId = (String) map.get("r_object_id");
		String descricaoDoArmario = (String) map.get("object_name");
		String nomeACL = (String) map.get("acl_name");
		String path = (String) map.get("r_folder_path");
		String qtdPastasArmario =  (String) map.get("r_link_cnt");
				
		newPastas.setObjectId(objectId);
		newPastas.setDescricao(descricaoDoArmario);
		newPastas.setPath(path);

		return newPastas;
	}
	
	@Override
	public List<Pastas> buscaPastasDiretorio(String path) {
		
		String sql = "select fld.r_object_id, fld.object_name, fld.r_link_cnt from dm_folder fld   ";
		sql += " WHERE folder('"+path+"') ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Pastas> listaDePastas = new ArrayList<Pastas>();
		
		for (int i = 0; i < results.size(); i++) {
			Pastas newPastas = new Pastas();
			Map<Object, Object> map = results.get(i);

			String objectId = (String) map.get("r_object_id");
			String descricaoDoArmario = (String) map.get("object_name");
			String qtdPastasArmario =  (String) map.get("r_link_cnt");
				
			newPastas.setObjectId(objectId);
			newPastas.setDescricao(descricaoDoArmario);
			newPastas.setQtdPastas(Long.parseLong(qtdPastasArmario));
			newPastas.setQtdDocumentos(getQtdDocumentosPasta(objectId));
		
			listaDePastas.add(newPastas);
		}

		return listaDePastas;
	}
	
}
