package br.com.furnas.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.furnas.integracao.constants.DocumentumConstants;
import br.com.furnas.integracao.services.DocumentumServices;

public class DocumentumUtil {
	private DocumentumServices documentumServices;
	String dql = null;
/*	UtilService utilService = new UtilService();
	SequenciaisDocumentalDao seqDocumentalDao = new SequenciaisDocumentalDao();*/
	
	public DocumentumUtil() 
	{
		try {
			documentumServices = (DocumentumServices) InitialContext.doLookup("ejb.DocumentumServices#br.com.furnas.proxy.services.DocumentumServices");
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para salvar o documento
	 * 
	 * @param arquivo
	 * @param nomeArquivo
	 * @param tpDocumento
	 * @param pastas
	 * @param metadados
	 * @throws TimeoutException 
	 */
	private void gravaDocumento(File arquivo, String nomeArquivo,
			String tpDocumento, List<String> pastas,
			Map<Object, Object> metadados, String ext) throws TimeoutException, Exception  {

		Map<Object, Object> documento = new HashMap<Object, Object>();

		documento.put(DocumentumConstants.CHAVE_ARQUIVO, arquivo);

		documento.put(DocumentumConstants.CHAVE_NOME_ARQUIVO, nomeArquivo);

		documento.put(DocumentumConstants.CHAVE_TIPO_DOCUMENTAL, tpDocumento);

		/** chamando o método para gravar no Documentum */
		documentumServices.salvarDocumento(documento, pastas, metadados);

	}

	/**
	 * Retorna uma lista com todos os documentos(e metadados) para os parâmetros
	 * passados.
	 * 
	 * @param cpfCnpj
	 *            - CPF/CNPJ do fornecedor
	 * @param tipoFornecedor
	 *            - descrição do nome do tipo fornecedor (Pessoa Jurídica, por
	 *            exemplo)
	 * 
	 * */
	public List<Map<Object, Object>> retornaListaDocumentum(
			String cpfCnpj, String tipoFornecedor) {

		if(cpfCnpj != null && tipoFornecedor != null){
			String dql = "select object_name, r_object_id, r_object_type from dm_document where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend)";

			return documentumServices.buscarPorDql(dql);
		}else{
			
			return null;
		}
		
	}
	
	/**
	 * Retorna uma lista com todos os documentos(e metadados) para os parâmetros
	 * passados.
	 * 
	 * @param cpfCnpj
	 *            - CPF/CNPJ do fornecedor
	 * @param tipoFornecedor
	 *            - descrição do nome do tipo fornecedor (Pessoa Jurídica, por
	 *            exemplo)
	 * 
	 * */
	public List<Map<Object, Object>> retornaListaDocumentumEMetadados(String cpfCnpj, String tipoFornecedor) {

		if(cpfCnpj != null && tipoFornecedor != null){
			String dql = " select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade,r_object_type as atr_norma,r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_fazenda_federal where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
							+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade,r_object_type as atr_norma, r_object_type as atr_numero_oficio, atr_subtipo_documental "
							+ "from obt_fazenda_municipal where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date , object_name, r_object_id, atr_situacao, atr_data_validade,r_object_type as atr_norma, r_object_type as atr_numero_oficio, atr_subtipo_documental "
							+ "from obt_fazenda_estadual where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_fazenda_df where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade,r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type  as atr_subtipo_documental "
							+ "from obt_inss where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_fgts where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, atr_numero_oficio, r_object_type  as atr_subtipo_documental "
							+ "from obt_falencia_concordata where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type  as atr_subtipo_documental "
							+ "from obt_cndt where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_procuracao where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_interdicoes_tutelas where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_titulos_protestados where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_acoes_executivas where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend)"
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, atr_data_validade, atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_certificacao where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend)"
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, atr_subtipo_documental "
							+ "from obt_instrumento_juridico where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend)"
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, atr_subtipo_documental "
							+ "from obt_declaracao_modelo where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio,  r_object_type as atr_subtipo_documental "
							+ "from obt_entidade_fiscalizadora where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_requisitos_lei_especial where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_cnpj where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_cpf where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_inscricao_municipal where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_inscricao_estadual where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
					+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_balanco_patrimonial where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_folha_assinatura where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_cedula_identidade where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
						+ " UNION all  select r_object_type, r_creation_date ,  object_name, r_object_id, atr_situacao, r_creation_date as atr_data_validade, r_object_type as atr_norma, r_object_type as atr_numero_oficio, r_object_type as atr_subtipo_documental "
							+ "from obt_junta_comercial where folder ('/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/"
					+ tipoFornecedor + "/" + cpfCnpj + "',descend) "
							+ "order by 1 desc, 2 desc";

			return documentumServices.buscarPorDql(dql);
		}else{
			
			return null;
		}
		
	}


	public void removerPorId(String object_Id) {

		/**
		 * REMOVER DOCUMENTOS POR ID
		 */
		String documentRemoveId = object_Id;

		documentumServices.removerDocumento(documentRemoveId);
	}

	/*
	
	*//**
	 * UC 03-13 Balanço patrimonial - gravar
	 * 
	 * @param arquivo
	 * @param tpFornecedor
	 * @param descTpFornecedor
	 * @param cpfCnpj
	 * @param codSolicitacao
	 * @param tipoDocumento
	 * @param capitalSocial
	 * @param patrimonioLiquido
	 * @param anoExercicio
	 * @param object_id
	 * @throws Exception 
	 * @throws TimeoutException 
	 *//*
	public void gravarAnexarBalancoPatrimonial(File arquivo,
			String tpFornecedor, String descTpFornecedor, String cpfCnpj,
			Integer codSolicitacao, String tipoDocumento, String capitalSocial,
			String patrimonioLiquido, String anoExercicio, String object_id, String ext) throws TimeoutException, Exception {

		List<String> pastas = new ArrayList<String>();
		pastas.add(tpFornecedor);
		pastas.add(cpfCnpj);
		pastas.add(EnumDocumentType.OBT_BALANCO_PATRIMONIAL.getDocumentType());

		String nomeArquivo = DocumentumConstants.BP + "_" + cpfCnpj;

		Map<Object, Object> metadados = new HashMap<Object, Object>();
		if (utilService.validaCNPJ(cpfCnpj)) {
			metadados.put(EnumDocumentMetadata.CNPJ, cpfCnpj);
		} else {
			metadados.put(EnumDocumentMetadata.CPF, cpfCnpj);
		}

		metadados.put(EnumDocumentMetadata.NUMERO_SOLICITACAO,
				String.valueOf(codSolicitacao));
		metadados.put(EnumDocumentMetadata.SITUACAO,
				StatusDocumetoEnum.ANEXADO.getDescricao());
		metadados.put(EnumDocumentMetadata.TIPO_FORNECEDOR, descTpFornecedor);
		metadados.put(EnumDocumentMetadata.CAPITAL_SOCIAL, capitalSocial);
		metadados.put(EnumDocumentMetadata.PATRIMONIO_LIQUIDO,
				patrimonioLiquido);
		metadados.put(EnumDocumentMetadata.EXERCICIO, anoExercicio);

		if (!object_id.equalsIgnoreCase("") && arquivo== null){
			
			Map<Object, Object> metadataToUpdate = new HashMap<Object, Object>();
			
			*//** Modifico o parametro que será atualizado *//*		
			metadataToUpdate.put(EnumDocumentMetadata.CAPITAL_SOCIAL, capitalSocial);
			metadataToUpdate.put(EnumDocumentMetadata.PATRIMONIO_LIQUIDO, patrimonioLiquido);
			metadataToUpdate.put(EnumDocumentMetadata.EXERCICIO, anoExercicio);			
			
			this.atualizarMetadados(object_id, metadataToUpdate);
		} else {
			gravaDocumento(arquivo, nomeArquivo,
					EnumDocumentType.OBT_BALANCO_PATRIMONIAL.getDocumentType(), pastas, metadados, ext);
		}
	}*/

	public void atualizarMetadados(String document_id,
			Map<Object, Object> metadados) {

		// params2.put(OBJECT_ID, documentId);

		/** Chamo o serviço para atualizar os metadados */
		Integer updated = documentumServices.atualizarMetadados(document_id, metadados);
		System.out.println(updated + " atualizados.");

	}

	/**
	 * Busca os metadados de um documentum através do object_id passado por parâmetro.
	 * 
	 * @param objectId
	 */
	public List<Map<Object, Object>> buscarDocumentoPorId(String objectId, String tipoDocumental) {
	
		String dql = "select * from " + tipoDocumental + " where r_object_id = '" + objectId + "'";
		
		return documentumServices.buscarPorDql(dql);
	}
	
	
	public List<Map<Object, Object>> buscarMetadadosDocPorNome(String nomeDocumento, String tipoDocumental) {

		dql = "select * from "+ tipoDocumental +"  where object_name = '"	+ nomeDocumento+"'";		
		List<Map<Object, Object>> listaDoc = documentumServices.buscarPorDql(dql);

		return listaDoc;
	}
	

}
