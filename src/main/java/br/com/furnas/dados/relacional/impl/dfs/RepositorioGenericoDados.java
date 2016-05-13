package br.com.furnas.dados.relacional.impl.dfs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.furnas.dados.relacional.dfs.IRepositorioGenericoDados;
import br.com.furnas.dominio.Validacao;
import br.com.furnas.integracao.EMCDocumentumFactory;
import br.com.furnas.integracao.services.DocumentumServices;
import br.com.furnas.integracao.services.impl.DocumentumServicesImpl;

import com.emc.documentum.fs.datamodel.core.DataPackage;

/**
 * RepositorioGenericoDados.java
 * 
 * Furnas Centrais El?tricas S.A
 * Divis?o de Suporte ao Desenvolvimento de Sistemas de Informa??o
 * Copyright (c) 2014 Todos os direitos reservados ? Eletrobras Furnas S.A.
 * 
 * Reposit?rio Gen?rico o qual cont?m as opera??es de CRUD. <br>
 * Deve ser utilizado pelos demais reposit?rios para execu??o das 
 * opera??es necess?rias junto ao mecanismo de persist?ncia escolhido.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 */
@Repository
public abstract class RepositorioGenericoDados implements IRepositorioGenericoDados {
		
	DocumentumServices service;
		
	public DataPackage criarPasta(Map<Object, Object> infoTipoDocumental, List<String> infoPasta, Map<Object, Object> infoDadosNaoDocumentais) throws Exception {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.salvarDocumento(infoTipoDocumental, infoPasta, infoDadosNaoDocumentais);
		/*
		Map<Object, Object> erro = new HashMap<Object, Object>();
		Map<Object, Object> erro2 = new HashMap<Object, Object>();
		List<String> erro3 = new ArrayList<String>();
		return service.salvarDocumento(erro, infoPasta, erro2);*/
		
	}

	public DataPackage salvarTipoDocumental(Map<Object, Object> infoTipoDocumental, List<String> infoPasta, Map<Object, Object> infoDadosNaoDocumentais) throws Exception {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.salvarDocumento(infoTipoDocumental, infoPasta, infoDadosNaoDocumentais);
		/*
		Map<Object, Object> erro = new HashMap<Object, Object>();
		Map<Object, Object> erro2 = new HashMap<Object, Object>();
		List<String> erro3 = new ArrayList<String>();
		return service.salvarDocumento(erro, infoPasta, erro2);*/
		
	}
	
	public Integer excluirTipoDocumental(String diretorio) {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.removerDocumentosPorDiretorio(diretorio);
	}
	
	public Integer alterarTipoDocumental(String objectId, Map<Object, Object> infoDadosNaoDocumentais) throws Exception {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.atualizarMetadados(objectId, infoDadosNaoDocumentais);
	}
	
	public DataPackage copiarTipoDocumental(String objectId, String diretorio, String diretorioCopia) throws Exception {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.copiarDocumento(objectId, diretorio, diretorioCopia);
	}	
	
	public File recuperarDocumento(String objectID, String tipoDocumental, String ext) throws IOException
	{
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.recuperarDocumento(objectID, tipoDocumental, ext);
	}	
	
	public DataPackage checkInDocumento(File newFile, String objectId) throws Exception
	{
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.checkInDocumento(newFile.getAbsolutePath(), objectId); //updateDocument
	}
	
	
	public String recuperaURL(String objectId) throws Exception
	{
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		return service.recuperaURL(objectId); //updateDocument
	}
	
	
	public void salvar(String sql) {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		 
		service.executarDql(sql);
	}
	
	public void excluir(String sql) {
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
	    
		service.executarDql(sql);
	}
 

	public void alterar(String sql) {
		
		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		 
		service.executarDql(sql);
	}

	
	public List<Map<Object, Object>> obterTodos(String sql) {

		service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		 
		List<Map<Object, Object>> resultado = service.buscarPorDql(sql);
		
		return resultado;
	}
	
	public Integer validaCampoBooleanInteiro(boolean campo)
	{
		Integer intCampo = -1;
		
		intCampo = campo ? 1 : 0;		
		
		return intCampo;
	}


	public boolean validaCampoInteiroBoolean(Integer campo)
	{
		boolean boolCampo = false;
		
		boolCampo = campo == 1 ? true : false;		
		
		return boolCampo;
	}

	
	public String validaCampoNullString(String campo)
	{
		String stCampo = "";
		
		stCampo = campo == null ? "" : campo;		
		
		return stCampo;
	}
	
	public Integer validaCampoNullInteiro(String campo)
	{
		Integer stCampo = 0;
		
		stCampo = campo == null ? 0 : Integer.parseInt(campo);		
		
		return stCampo;
	}
	public Validacao downloadPDF(String repositorio, Integer identificador,
			HttpSession session, HttpServletResponse response, String objectID) {
		// TODO Auto-generated method stub
		return null;
	}

}
