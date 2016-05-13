package br.com.furnas.integracao.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.emc.documentum.fs.datamodel.core.DataPackage;
import com.emc.documentum.fs.rt.ServiceException;

/**
 * Servicos que realizam operacoes no EMC.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public interface DocumentumServices {

	/**
	 * Metodo generico usado para gravar documentos no EMC.
	 * @param documento - Map para armazenar informa, preencher chaves DocumentumConstants.CHAVE_ARQUIVO, DocumentumConstants.CHAVE_NOME_ARQUIVO, DocumentumConstants.CHAVE_TIPO_DOCUMENTAL 
	 * @param pastas - Lista usa para criar pastas dentro do Documentum se as mesmas nï¿½o existirem.
	 * @param metadados - Map que constrï¿½i os metadados do documento que serï¿½ gravado, usar atributos declarados no enum 
	 * @return Integer - status de retorno da gravacao 
	 * @throws Exception 
	 */
	public DataPackage salvarDocumento(Map<Object, Object> documento, List<String> pastas, Map<Object, Object> metadados) throws Exception;
	
	/**
	 * Busca os metadados de documentos que foram inseridos no EMC.
	 * @param documentoId - id do documento que sera recuperado.
	 * @param nomeTipoDocumental - nome do tipo documental.
	 * @return File - arquivo recuperado   
	 * @throws IOException 
	 */
	public File recuperarDocumento(String documentoId, String nomeTipoDocumental, String ext) throws IOException;
	
	/**
	 * Busca os metadados de documentos que foram inseridos no EMC.
	 * @param params - map com chave e valor representando os parametros de busca.
	 * @return List<Map<String,Object>> - lista de 'maps' com todos os metadados do pertencentes aos documentos encontrados.   
	 */
	public List<Map<Object,Object>> buscarMetadados(Map<Object, Object> params);
	
	/**
	 * Busca os metadados de documentos que foram inseridos no EMC.
	 * @param dql - query usada para buscar dados de documentos armazenados no Documentum
	 * @return List<Map<String,Object>> - lista de 'maps' com todos os metadados do pertencentes aos documentos encontrados.   
	 */	
	public List<Map<Object,Object>> buscarPorDql(String dql);
	
	/**
	 * Atualiza metadados do documento a partir do id do documento gravado no EMC.
	 * @param documentoId - id do documento que sera atualizado.
	 * @param params - map com chave e valor representando os parametros dos metadados.
	 * @return Integer - status da operacao.   
	 */	
	public Integer atualizarMetadados(String documentId, Map<Object, Object> params);	
	
	/**
	 * Remove todos os documentos do fornecedor. 
	 * @param diretorio - Diretorio onde os documentos serï¿½o excluidos
	 * @return Integer - retona 0 caso ocorra algum erro na operacao ou retorna 1 caso o comando seja executado com sucesso. 
	 */
	public Integer removerDocumentosPorDiretorio(String diretorio);
	
	/**
	 * Remove fisicamente um documento a partir do id do documento gravado no EMC.
	 * @param documentoId - id do documento que sera excluido.
	 * @return Integer - status da operacao.   
	 */
	public Integer removerDocumento(String documentId);
	
	/**
	 * Executa uma operacao dql que nao precisa de retorno da consulta.
	 * @param dql - query usada para buscar dados de documentos armazenados no Documentum
	 * @return Integer - status da operacao. 
	 */
	public Integer executarDql(String dql);

	
	public DataPackage checkInDocumento(String pathFile, String objectId);
	/**
	 * Copiar os documentos. 
	 * @param diretorio - Diretorio onde os documentos serão copiados
	 * @throws ServiceException 
	 */
	public DataPackage copiarDocumento(String objectId, String diretorioOrigem, String diretorioCopia) throws ServiceException;
	
	public DataPackage criarPastaRoot(String nameFolder) throws Exception;
	
	public String recuperaURL(String objectId);
}