package br.com.furnas.integracao.services.impl;

import static br.com.furnas.integracao.constants.DocumentumConstants.BAR_FOLDER;
import static br.com.furnas.integracao.constants.DocumentumConstants.CABINET;
import static br.com.furnas.integracao.constants.DocumentumConstants.EQ_QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECTS;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_ID;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_TYPE;
import static br.com.furnas.integracao.constants.DocumentumConstants.QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.SPACE;
import static br.com.furnas.integracao.constants.DocumentumConstants.TRUNCATE;
import static br.com.furnas.integracao.constants.DocumentumConstants.UPDATE;
import static br.com.furnas.integracao.constants.DocumentumConstants.WHERE;
import static br.com.furnas.integracao.constants.RepositoryConstants.REPOSITORY_NAME;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.furnas.integracao.common.QueryCommons;
import br.com.furnas.integracao.enumeration.EnumDocumentMetadata;
import br.com.furnas.integracao.services.DocumentumServices;

import com.emc.documentum.fs.datamodel.core.DataPackage;
import com.emc.documentum.fs.datamodel.core.ObjectId;
import com.emc.documentum.fs.datamodel.core.ObjectIdentity;
import com.emc.documentum.fs.rt.ServiceException;

/**
 * Servi�os de manipulacao de documentos no EMC de acordo 
 * com os casos de uso do projeto portal do fornecedor.
 *   
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public class DocumentumServicesImpl extends DocumentumCoreServicesImpl 
									implements DocumentumServices{

	private static final Logger logger = (Logger) LoggerFactory.getLogger(DocumentumServicesImpl.class);
	
	@Override
	public List<Map<Object,Object>> buscarMetadados(Map<Object, Object> params) {
		
		String dql = QueryCommons.buildQueryAllDocuments(params);
		
		List<Map<Object,Object>> listaDocumentos = this.searchMetadataByDql(dql);
		
		return listaDocumentos;
		
	}
	
	@Override
	public List<Map<Object,Object>> buscarPorDql(String dql) {
		
		List<Map<Object,Object>> listaDocumentos = this.searchMetadataByDql(dql);
		
		return listaDocumentos;
		
	}
	
	@Override
	public Integer atualizarMetadados(String documentId, Map<Object, Object> params) {
		
		Set<String> keyUpdate = new HashSet<String>();
		
		for(Object key : params.keySet()) {
			
			keyUpdate.add(String.valueOf(key));
			
		}
		
		Map<Object, Object> queryType = new HashMap<Object, Object>();
		
		queryType.put(OBJECT_ID, documentId);
		
		List<Map<Object,Object>> documents = this.buscarMetadados(queryType);

		Map<Object,Object> document = documents.get(0);
		
		String typeDocument = (String) document.get(OBJECT_TYPE);		

		for (String currentField : QueryCommons.multivaluedFields) {
		
			if (keyUpdate.contains(currentField)) {
				
				StringBuilder queryUpField = new StringBuilder();
				
				queryUpField.append(UPDATE).append(typeDocument).append(OBJECTS);
				
				queryUpField.append(TRUNCATE);
				
				queryUpField.append(currentField);
				
				queryUpField.append(WHERE);
				
				queryUpField.append(SPACE).append(OBJECT_ID).append(SPACE);
				
				queryUpField.append(EQ_QUOTE).append(documentId).append(QUOTE);
				
				this.executeQuery(queryUpField.toString());
				
			}

		}

		String dql = QueryCommons.buildUpdateDocument(documentId, typeDocument, params);
		
		return this.executeQuery(dql);
		
	}

	@Override
	public File recuperarDocumento(String documentoId, String nomeTipoDocumental, String ext) throws IOException 
	{
		ext = (ext == null ? "msw12" : ext);
		return getDocumentById(documentoId, nomeTipoDocumental, ext);
	}
	
	public DataPackage copiarDocumento(String objectId, String diretorioOrigem, String diretorioCopia) throws ServiceException
	{
		List<String> infoPasta = new ArrayList<String>();
		String[] splitDir = diretorioCopia.split("/");
		infoPasta.add(splitDir[0]);
		infoPasta.add(splitDir[1]);
				
		try {
			this.createFolders(infoPasta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sbOrigem = new StringBuilder();
		sbOrigem.append(BAR_FOLDER).append(CABINET);
		sbOrigem.append(BAR_FOLDER).append(diretorioOrigem);

		StringBuilder sbDestino = new StringBuilder();
		sbDestino.append(BAR_FOLDER).append(CABINET);
		sbDestino.append(BAR_FOLDER).append(diretorioCopia);

		return this.copyDocument(objectId, sbOrigem.toString(), sbDestino.toString());
	}
	
	@Override
	public Integer removerDocumento(String documentId) {
		
		Map<Object, Object> query = new HashMap<Object, Object>();
		
		query.put(OBJECT_ID, documentId);
		
		List<Map<Object,Object>> documents = this.buscarMetadados(query);

		Map<Object,Object> document = documents.get(0);
		
		String typeDocument = (String) document.get(OBJECT_TYPE);
		
		String dql = QueryCommons.buildDeleteDocument(documentId, typeDocument);
		
		return this.executeQuery(dql);
		
	}	
	
	@Override
	public Integer removerDocumentosPorDiretorio(String diretorio) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(BAR_FOLDER).append(CABINET);
		
		sb.append(BAR_FOLDER).append(diretorio);

		return this.deleteDocumentsByPath(sb.toString());
		
	}

	@Override
	public DataPackage criarPastaRoot(String nameFolder) throws Exception {
		return this.createFolder(nameFolder);
	}
	
	@Override
	public DataPackage salvarDocumento(Map<Object, Object> documento, List<String> pastas, Map<Object, Object> metadados) throws Exception {
		String ext="";
		DataPackage dataPackage = this.createFolderAndDocument(documento, pastas,  metadados, ext);
		
		return dataPackage;
		
	}
	
	@Override
	public Integer executarDql(String dql) {
		
		return this.executeQuery(dql);
		
	}
	
	
	public DataPackage checkInDocumento(String pathFile, String objectId) {
		DataPackage dtPackage = null;
		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);
		ObjectId objId= new ObjectId(objectId);
		objIdentity.setValue(objId);
		
		
		try {
			dtPackage = checkinDFS(pathFile, objIdentity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtPackage;
		
	}
	
	public String recuperaURL(String objectId) {
		DataPackage dtPackage = null;
		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);
		ObjectId objId= new ObjectId(objectId);
		objIdentity.setValue(objId);
		
		return recuperaURLDocumento(objIdentity);
	}
}
