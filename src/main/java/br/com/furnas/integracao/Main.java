package br.com.furnas.integracao;

import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_NOME_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_TIPO_DOCUMENTAL;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_ID;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.com.furnas.integracao.services.DocumentumServices;
import br.com.furnas.integracao.services.impl.DocumentumServicesImpl;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		DocumentumServices service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		
		/* Listando conteudo de uma pasta */
		String dqlLista =  "select r_object_id, object_name from dm_document(all) where folder('/SAC4', descend) ";
		List list2 = service.buscarPorDql(dqlLista); 
		System.out.println("listando conteudo de uma pasta" + list2.toString());		
		
		/* Incluindo */
		
		String dqlIncluindo = "insert into TESTE (name) values ('Bruna 2') ";
		
		Integer i = service.executarDql(dqlIncluindo);
			
		
	//	List id = service.buscarPorDql("select r_object_id from TESTE where name = 'Bruna2'");
		
		Integer dqlAtualizando = service.executarDql("update TESTE set name = 'Wilson' where name = 'Bruna'");
		
		List resultado = service.buscarPorDql("select * from TESTE");
		
		System.out.println("Resultado" + resultado.toString());	
		
		String dqlExcluir = "delete from TESTE where name = 'Wilson' limit 0,1";
		
		Integer y = service.executarDql(dqlExcluir);
		
		List resultadoEx = service.buscarPorDql("select * from TESTE");
		
		System.out.println("Resultado" + resultadoEx.toString());	
		
		
	//	String dql3 = "delete obt_instrumento_juridico objects atr_socios where r_object_id = '0902266e8005b120'";
			
	
		
		
		
		
//		/**
//		 * GRAVACAO PARA CRIAR DOCs NO EMC.
//		 * CLASSES COMERCIAIS.
//		 */	
//		
//		/**
//		 * Agora � necess�rio criar um map para preencher as informa��es do documento
//		 */
//		Map<Object, Object> documento = new HashMap<Object, Object>();
//		
//		/** atributo para o conteudo do arquivo */
//		documento.put(CHAVE_ARQUIVO, new File("C:\\Temp\\maven-eclipselink1.pdf"));
//		
//		/** atributo para o nome do arquivo ( construir de acordo com cada caso de uso )*/
//		documento.put(CHAVE_NOME_ARQUIVO, "arquivo_outro_666");
//		
//		/** 
//		 * atributo para o nome do arquivo ( construir de acordo com cada caso de uso )
//		 * OBS: pode ser usado o EnumDocumentType no segundo parametro.
//		 * */
//		documento.put(CHAVE_TIPO_DOCUMENTAL, ATESTADO_DE_CAPACIDADE_TECNICA);
//		
//		/**
//		 * Agora � necess�rio criar uma lista para preencher as informa��es das pastas.
//		 * OBS: Use quantas pastas quiser
//		 */
//		List<String> pastas = new ArrayList<String>();
//		
//		/** Nome da pasta 1*/
//		pastas.add("path2");
//		
//		/** Nome da pasta 2*/
//		pastas.add("pathx");
//		
//		/**
//		 * Agora � necess�rio criar um map para preencher as informa��es de metadados
//		 * OBS: Por favor usar o enum EnumDocumentMetadata no primeiro parametro.
//		 */
//		Map<Object, Object> metadados = new HashMap<Object, Object>();
//		
//		/** Atributo metadado CPF */ 
//		metadados.put(EnumDocumentMetadata.CPF, "09066773782");
//		
//		/** Usando lista multi valorada */
//		String[] classesComerciais = {"Cheese", "Pepperoni", "Black Olives"};
//		
//		/** Atributo metadado CPF */
//		metadados.put(EnumDocumentMetadata.CLASSE_COMERCIAL, classesComerciais);		
//		
//		/** Atributo metadado CPF */
//		metadados.put(EnumDocumentMetadata.NUMERO_SOLICITACAO, "12345678");		
//		
//		/** Atributo metadado CPF */
//		metadados.put(EnumDocumentMetadata.SITUACAO, "Anexado");		
//		
//		/** Atributo metadado CPF */
//		metadados.put(EnumDocumentMetadata.RAZAO_SOCIAL, "RS");		
//		
//		/** Atributo metadado CPF */
//		//service.salvarDocumento(documento, pastas, metadados);
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		/**
//		 * GRAVACAO PARA CRIAR DOCs NO EMC.
//		 * FORMAS DE PAGAMENTO.
//		 */	
//		
//		/** OBS: Usar objetos conforme detalhado acima */
		Map<Object, Object> documento2 = new HashMap<Object, Object>();
		
		documento2.put(CHAVE_ARQUIVO, new File("D:\\Temp\\1.pdf"));
//		
	
		documento2.put(CHAVE_NOME_ARQUIVO, "Charlie Brown");
//		
		documento2.put(CHAVE_TIPO_DOCUMENTAL, "dm_document");
		
		documento2.put(OBJECT_ID, 111111);
//
		List<String> pastas2 = new ArrayList<String>();
//		
		pastas2.add("Thiago");
//		
//		pastas2.add("pastax");
//		
		Map<Object, Object> metadados2 = new HashMap<Object, Object>();
//		
		
		metadados2.put(OBJECT_ID,"1212212");
		
//		
//		metadados2.put(EnumDocumentMetadata.BANCO, "12345");
//		
//		metadados2.put(EnumDocumentMetadata.CONTA, "0976");
//		
//		metadados2.put(EnumDocumentMetadata.AGENCIA, "2323");
//		
//		metadados2.put(EnumDocumentMetadata.SITUACAO, "342342");
//		
//		metadados2.put(EnumDocumentMetadata.RAZAO_SOCIAL, "9999");		
//		
		service.salvarDocumento(documento2, pastas2, metadados2);
//		
//		
//		
		
		
		
		
		
//		
//		
//		/**
//		 * BUSCA POR METADADOS
//		 */
//		Map<Object, Object> params1 = new HashMap<Object, Object>();
//		
//		params1.put(OBJECT_ID, "0902266e8004d3d9");
//		
//		//List<Map<Object,Object>> list1 = service.buscarMetadados(params1); System.out.println(list1.size()+" encontrados.");
//		
		
		
		
		
		
		
		
		
		
		
		/**
		 * BUSCA POR DQL
		 */		
//		String dql1 ="select * "; 
				//	String dql2 = " update obt_instrumento_juridico objects truncate atr_socios where r_object_id = '0902266e8005b120'";
		
	//	String dql3 = "delete obt_instrumento_juridico objects atr_socios where r_object_id = '0902266e8005b120'";
		
	//	Integer i = service.executarDql(dql2);
		
		
		
	//	System.out.println("retornados : "+i);
		
	//	List<Map<Object,Object>> list2 = service.buscarPorDql(dql1); 
		//System.out.println(list2.size()+" encontrados.");
		
		
		
		
		
		
		
		
//		
//		
//		
//		
//		/**
//		 * BUSCA POR METADADOS e POSTERIOR UPDATE
//		 */	
//		String documentId = "0902266e8005b120";
//		
//		/** Criando um map de parametros */
//		Map<Object, Object> params2 = new HashMap<Object, Object>();
//		
//		/** Adicionado o ID do documento */
//		params2.put(OBJECT_ID, documentId);
//		
//		/** Realizando a busca do documento */
//		List<Map<Object,Object>> list3 = service.buscarMetadados(params2); System.out.println(list3.size()+" encontrados.");
//		
//		/** Ao retornar documento pelo ID seleciono o primeiro item da lista, esse item � map de metadados */
//		Map<Object,Object> metadataToUpdate = list3.get(0);
//		
//		/** Modifico o parametro que ser� atualizado */
//		metadataToUpdate.put(EnumDocumentMetadata.SOCIOS, new String[]{"socio1", "socio2", "socio3"});
//		metadataToUpdate.put(EnumDocumentMetadata.DATA_REGISTRO, "22/07/2015");
//		
//		/** Chamo o servi�o para atualizar o metadado RAZAO_SOCIAL */
//		//Integer updated = service.atualizarMetadados(documentId, metadataToUpdate); System.out.println(updated+" atualizados.");
//		
//		
//		
//		
//		
//		
//		
		
		
		
//		
//		
//		/**
//		 * REMOVER DOCUMENTOS POR DIRETORIO
//		 */	
//		String documentPath = "PORTAL_DO_FORNECEDOR/PJ/58283185000179";
//		
//		//service.removerDocumentosPorDiretorio(documentPath);
//		
//		
//		
//		
//		
		
		
		
		
		

		

	}
}


