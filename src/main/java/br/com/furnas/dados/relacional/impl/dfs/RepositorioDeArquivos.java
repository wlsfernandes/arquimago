package br.com.furnas.dados.relacional.impl.dfs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeArquivos;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.util.Conversor;

@Repository
public class RepositorioDeArquivos extends RepositorioGenericoDados implements IRepositorioDeArquivos 
{
	@SuppressWarnings("deprecation")
	@Override
	public List<Arquivos> buscaArquivosPorArmario(String identificadorArmario) throws Exception {
		
		String sql = "select doc.r_object_id, doc.object_name, datetostring(doc.r_modify_date, 'dd/mm/yyyy hh:mi:ss') as r_modify_date, doc.r_content_size, doc.a_content_type ";
		sql += "from dm_document doc, dm_folder fld, dm_cabinet cab ";
		sql += "where doc.i_folder_id = fld.r_object_id and fld.i_ancestor_id = cab.r_object_id and fld.r_object_id = cab.r_object_id ";
		sql += "and cab.r_object_id = '" + identificadorArmario + "'";
		sql += "order by 1 enable (row_based)   ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Arquivos> listaDeArquivos = new ArrayList<Arquivos>();
		
		for (int i = 0; i < results.size(); i++) {
			Arquivos newArquivos = new Arquivos();
			Map<Object, Object> map = results.get(i);

			String objectId = (String) map.get("r_object_id");
			String descricaoDoArmario = (String) map.get("object_name");
			String dtUltimaAlteracao = (String) map.get("r_modify_date");
			String tamanhoArquivo = (String) map.get("r_content_size");
			String tipoArquivo = (String) map.get("a_content_type");
			
			newArquivos.setObjectId(objectId);
			newArquivos.setDescricao(descricaoDoArmario);
			newArquivos.setDtUltimaAtualizacao(dtUltimaAlteracao);
			
			String size = "";
			if(("0").equals(tamanhoArquivo))
			{
				size = tamanhoArquivo;
			}
			else
			{
				size = Conversor.converteTamanhosParaUnidadeMedidaDoArquivo(Long.parseLong(tamanhoArquivo));
			}
			newArquivos.setTamanhoArquivo(size);
			
			newArquivos.setLinkArquivo(this.recuperaURL(objectId));
			newArquivos.setTipoConteudo(Conversor.converteTipoConteudoParaClasseCSS(tipoArquivo));
			listaDeArquivos.add(newArquivos);
		}
		
		return listaDeArquivos;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Arquivos> buscaArquivosPorPasta(String identificadorPasta) throws Exception {
		String sql = "select doc.r_object_id, doc.object_name, datetostring(doc.r_modify_date, 'dd/mm/yyyy hh:mi:ss') as r_modify_date, doc.r_content_size, doc.a_content_type ";
		sql += "from dm_document doc, dm_folder fld, dm_cabinet cab ";
		sql += "where doc.i_folder_id = fld.r_object_id and fld.i_ancestor_id = cab.r_object_id ";
		sql += "and fld.r_object_id = '" + identificadorPasta + "'";
		sql += "order by 1 enable (row_based)   ";

		List<Map<Object, Object>> results = this.obterTodos(sql);
		List<Arquivos> listaDeArquivos = new ArrayList<Arquivos>();
		
		for (int i = 0; i < results.size(); i++) {
			Arquivos newArquivos = new Arquivos();
			Map<Object, Object> map = results.get(i);

			String objectId = (String) map.get("r_object_id");
			String descricaoDoArmario = (String) map.get("object_name");
			String dtUltimaAlteracao = (String) map.get("r_modify_date");
			String tamanhoArquivo = (String) map.get("r_content_size");
			String tipoArquivo = (String) map.get("a_content_type");
			
			newArquivos.setObjectId(objectId);
			newArquivos.setDescricao(descricaoDoArmario);
			
			try {
				Date dt = new Date(dtUltimaAlteracao);
				newArquivos.setDtUltimaAtualizacao(dtUltimaAlteracao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String size = "";
			
			if(("0").equals(tamanhoArquivo))
			{
				size = tamanhoArquivo;
			}
			else
			{
				size = Conversor.converteTamanhosParaUnidadeMedidaDoArquivo(Long.parseLong(tamanhoArquivo));
			}
			newArquivos.setTamanhoArquivo(size);
			
			newArquivos.setLinkArquivo(this.recuperaURL(objectId));
			newArquivos.setTipoConteudo(Conversor.converteTipoConteudoParaClasseCSS(tipoArquivo));
			
			listaDeArquivos.add(newArquivos);
		}
		
		return listaDeArquivos;
	}

	@Override
	public String retornaLinkArquivo(String objectId) throws Exception {
		return this.recuperaURL(objectId);
	}

}
