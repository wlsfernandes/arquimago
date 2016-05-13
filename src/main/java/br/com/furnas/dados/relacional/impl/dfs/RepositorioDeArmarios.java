package br.com.furnas.dados.relacional.impl.dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeArmarios;
import br.com.furnas.dominio.modelo.Armarios;

@Repository
public class RepositorioDeArmarios extends RepositorioGenericoDados implements IRepositorioDeArmarios 
{

	@Override
	public List<Armarios> buscaArmarios() {

		
		String sql = "select cab.r_object_id, cab.object_name, cab.r_link_cnt from dm_cabinet cab ";
		sql += "where (a_is_hidden = 0 and is_private = 0) or (any r_folder_path in (select default_folder from dm_user where user_name = 'adminged'))  ";
		sql += "order by object_name  ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Armarios> listaDeArmarios = new ArrayList<Armarios>();
		
		for (int i = 0; i < results.size(); i++) {
			Armarios newArmarios = new Armarios();
			Map<Object, Object> mapMetadado = results.get(i);

			String objectId = (String) mapMetadado.get("r_object_id");
			String descricaoDoArmario = (String) mapMetadado.get("object_name");
			String qtdPastasArmario =  (String) mapMetadado.get("r_link_cnt");

			newArmarios.setObjectId(objectId);
			newArmarios.setDescricao(descricaoDoArmario);
			newArmarios.setQtdPastas(Long.parseLong(qtdPastasArmario));
			newArmarios.setQtdDocumentos(getQtdDocumentosArmario(objectId));
		
			listaDeArmarios.add(newArmarios);
		}
		
		teste();

		return listaDeArmarios;
	}

	private long getQtdDocumentosArmario(String objectId) {
		
		String sql = "select count(doc.r_object_id) as docs from dm_document doc, dm_folder fld, dm_cabinet cab ";
		sql += "where doc.i_folder_id = fld.r_object_id and fld.i_ancestor_id = cab.r_object_id ";
		sql += "and cab.r_object_id = '" + objectId + "' ";
		sql += "UNION  ";
		sql += "select 0 as docs  ";
		sql += "from dm_cabinet cab where cab.r_link_cnt = 0  ";
		sql += "and cab.r_object_id = '" + objectId + "' "; 
		sql += "order by 1 enable (row_based)  ";
		
		List<Map<Object, Object>> results = this.obterTodos(sql);
		Map<Object, Object> mapMetadado = results.get(0);

		String qtdPastasDocs =  (String) mapMetadado.get("docs");
		long qtdDocs = Long.parseLong(qtdPastasDocs);
		
		return qtdDocs;
	}
	
	
	
	private String teste()
	{
		
		//https://community.emc.com/docs/DOC-9389
		//ObjectService getObjectContentUrls
		
		try {
			recuperaURL("0902a94b800abe2b");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "oi";
	}

	
}
