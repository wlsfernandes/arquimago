package br.com.furnas.integracao.services.impl;

import static br.com.furnas.integracao.constants.DocumentumConstants.ALL;
import static br.com.furnas.integracao.constants.DocumentumConstants.BAR_FOLDER;
import static br.com.furnas.integracao.constants.DocumentumConstants.CABINET;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_NOME_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_TIPO_DOCUMENTAL;
import static br.com.furnas.integracao.constants.DocumentumConstants.DM_DOCUMENT;
import static br.com.furnas.integracao.constants.DocumentumConstants.DM_FOLDER;
import static br.com.furnas.integracao.constants.DocumentumConstants.DQL_CREATE_FOLDER;
import static br.com.furnas.integracao.constants.DocumentumConstants.DQL_SELECT_FOLDER;
import static br.com.furnas.integracao.constants.DocumentumConstants.EMPTY;
import static br.com.furnas.integracao.constants.DocumentumConstants.EQ_QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.FROM;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_ID;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_NAME;
import static br.com.furnas.integracao.constants.DocumentumConstants.PDF;
import static br.com.furnas.integracao.constants.DocumentumConstants.QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.SELECT;
import static br.com.furnas.integracao.constants.DocumentumConstants.SPACE;
import static br.com.furnas.integracao.constants.DocumentumConstants.TITLE;
import static br.com.furnas.integracao.constants.DocumentumConstants.UNDERSCORE;
import static br.com.furnas.integracao.constants.DocumentumConstants.WHERE;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_CONEXAO_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_CREATE_FOLDER;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_MISSING_KEY_MAP;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_REPOSITORIO_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_SERVICO_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_CACHE_STRATEGY;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_CONEXAO_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_CREATED_FILE_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_CREATE_METADATA;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_DQL_QUERY;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_INICIACAO_OPERACAO;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_INICIANDO_EMC;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_NUMBER_OF_ITENS;
import static br.com.furnas.integracao.constants.RepositoryConstants.DFS_SERVICE_URL;
import static br.com.furnas.integracao.constants.RepositoryConstants.MODULE_NAME;
import static br.com.furnas.integracao.constants.RepositoryConstants.REPOSITORY_NAME;
import static br.com.furnas.integracao.constants.RepositoryConstants.USER_NAME;
import static br.com.furnas.integracao.constants.RepositoryConstants.USER_PASSWORD;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.integracao.EMCDocumentumFactory;
import br.com.furnas.integracao.constants.RepositoryConstants;
import br.com.furnas.integracao.constants.impl.RepositoryIdentityConstants;
import br.com.furnas.integracao.services.DocumentumCoreServices;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.emc.documentum.fs.datamodel.core.CacheStrategyType;
import com.emc.documentum.fs.datamodel.core.DataObject;
import com.emc.documentum.fs.datamodel.core.DataPackage;
import com.emc.documentum.fs.datamodel.core.ObjectContentSet;
import com.emc.documentum.fs.datamodel.core.ObjectId;
import com.emc.documentum.fs.datamodel.core.ObjectIdentity;
import com.emc.documentum.fs.datamodel.core.ObjectIdentitySet;
import com.emc.documentum.fs.datamodel.core.ObjectLocation;
import com.emc.documentum.fs.datamodel.core.ObjectPath;
import com.emc.documentum.fs.datamodel.core.ObjectRelationship;
import com.emc.documentum.fs.datamodel.core.OperationOptions;
import com.emc.documentum.fs.datamodel.core.Qualification;
import com.emc.documentum.fs.datamodel.core.ReferenceRelationship;
import com.emc.documentum.fs.datamodel.core.Relationship;
import com.emc.documentum.fs.datamodel.core.VersionStrategy;
import com.emc.documentum.fs.datamodel.core.content.Content;
import com.emc.documentum.fs.datamodel.core.content.FileContent;
import com.emc.documentum.fs.datamodel.core.content.RenditionType;
import com.emc.documentum.fs.datamodel.core.content.UrlContent;
import com.emc.documentum.fs.datamodel.core.context.RepositoryIdentity;
import com.emc.documentum.fs.datamodel.core.profiles.ContentProfile;
import com.emc.documentum.fs.datamodel.core.profiles.ContentTransferProfile;
import com.emc.documentum.fs.datamodel.core.profiles.DeleteProfile;
import com.emc.documentum.fs.datamodel.core.profiles.DepthFilter;
import com.emc.documentum.fs.datamodel.core.profiles.FormatFilter;
import com.emc.documentum.fs.datamodel.core.profiles.PageFilter;
import com.emc.documentum.fs.datamodel.core.profiles.PageModifierFilter;
import com.emc.documentum.fs.datamodel.core.profiles.RelationshipNameFilter;
import com.emc.documentum.fs.datamodel.core.profiles.RelationshipProfile;
import com.emc.documentum.fs.datamodel.core.profiles.ResultDataMode;
import com.emc.documentum.fs.datamodel.core.profiles.TargetRoleFilter;
import com.emc.documentum.fs.datamodel.core.properties.Property;
import com.emc.documentum.fs.datamodel.core.properties.PropertySet;
import com.emc.documentum.fs.datamodel.core.query.PassthroughQuery;
import com.emc.documentum.fs.datamodel.core.query.QueryExecution;
import com.emc.documentum.fs.datamodel.core.query.QueryResult;
import com.emc.documentum.fs.rt.ServiceException;
import com.emc.documentum.fs.rt.ServiceInvocationException;
import com.emc.documentum.fs.rt.context.ContextFactory;
import com.emc.documentum.fs.rt.context.DfcSessionManager;
import com.emc.documentum.fs.rt.context.IServiceContext;
import com.emc.documentum.fs.rt.context.ServiceFactory;
import com.emc.documentum.fs.services.core.CacheException;
import com.emc.documentum.fs.services.core.CoreServiceException;
import com.emc.documentum.fs.services.core.QueryValidationException;
import com.emc.documentum.fs.services.core.client.IObjectService;
import com.emc.documentum.fs.services.core.client.IQueryService;
import com.emc.documentum.fs.services.core.client.IVersionControlService;

/**
 * Serviï¿½os para realizar operacoes de manipulacao no EMC.
 * 
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 2.0
 */
public class DocumentumCoreServicesImpl implements DocumentumCoreServices {

	private static final Logger logger = (Logger) LoggerFactory
			.getLogger(DocumentumCoreServicesImpl.class);
	private static Log log = LogFactory.getLog(DocumentumServicesImpl.class);
	/**
	 * objectService.
	 */
	private IObjectService objectService;

	/**
	 * serviceContext.
	 */
	private IServiceContext serviceContext;

	/**
	 * repositoryIdentityConstants.
	 */
	private RepositoryIdentityConstants repositoryIdentityConstants;

	/**
	 * Getter repositoryIdentityConstants.
	 * 
	 * @return RepositoryIdentityConstants
	 */
	public RepositoryIdentityConstants getRepositoryIdentityConstants() {
		return repositoryIdentityConstants;
	}

	/**
	 * Setter repositoryIdentityConstants.
	 * 
	 * @param repositoryIdentityConstants
	 */
	public void setRepositoryIdentityConstants(
			RepositoryIdentityConstants repositoryIdentityConstants) {
		this.repositoryIdentityConstants = repositoryIdentityConstants;
	}

	/**
	 * Getter objectService.
	 * 
	 * @return IObjectService
	 */
	public IObjectService getObjectService() {

		IObjectService iObjService = null;

		try {

			RepositoryIdentityConstants repositoryIdentityConstants = EMCDocumentumFactory
					.getConstants(RepositoryIdentityConstants.class);

			this.setRepositoryIdentityConstants(repositoryIdentityConstants);

			ContextFactory contextFactory = ContextFactory.getInstance();

			IServiceContext serviceContext = contextFactory.getContext();

			setServiceContext(serviceContext);

			serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
			serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

			RepositoryIdentity repoId = new RepositoryIdentity();

			repoId.setRepositoryName(REPOSITORY_NAME);

			repoId.setUserName(USER_NAME);

			repoId.setPassword(USER_PASSWORD);

			getServiceContext().addIdentity(repoId);

			//contextFactory.register(getServiceContext());
			//iObjectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class, serviceContext, MODULE_NAME, DFS_SERVICE_URL);
			iObjService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

			logger.info(INFO_CONEXAO_EMC);

		} catch (ServiceInvocationException e) {

			logger.error(ERROR_CONEXAO_EMC.concat(e.getLocalizedMessage()));

		}

		this.objectService = iObjService;

		return objectService;
	}

	/**
	 * Getter objectService.
	 * 
	 * @return IObjectService
	 */

	public boolean autenticarUsuarioDFS(String username, String password,
			String repositorio) {

		IObjectService iObjectService = null;

		boolean autenticado = false;
		try {

			RepositoryIdentityConstants repositoryIdentityConstants = EMCDocumentumFactory
					.getConstants(RepositoryIdentityConstants.class);

			this.setRepositoryIdentityConstants(repositoryIdentityConstants);

			ContextFactory contextFactory = ContextFactory.getInstance();

			IServiceContext serviceContext = contextFactory.newContext();
			serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
			serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

			setServiceContext(serviceContext);

			RepositoryIdentity repoId = new RepositoryIdentity();

			repoId.setRepositoryName(repositorio);

			repoId.setUserName(username);

			repoId.setPassword(password);

			getServiceContext().addIdentity(repoId);

			contextFactory.register(getServiceContext());

			//iObjectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class, serviceContext, MODULE_NAME, DFS_SERVICE_URL);
			iObjectService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

			logger.info(INFO_CONEXAO_EMC);

			autenticado = true;
		} catch (ServiceInvocationException e) {

			logger.error(ERROR_CONEXAO_EMC.concat(e.getLocalizedMessage()));

		}
		return autenticado;
	}

	/**
	 * Setter objectService.
	 * 
	 * @param objectService
	 */
	public void setObjectService(IObjectService objectService) {
		this.objectService = objectService;
	}

	/**
	 * Getter serviceContext.
	 * 
	 * @return IServiceContext
	 */
	public IServiceContext getServiceContext() {
		return serviceContext;
	}

	/**
	 * Setter serviceContext.
	 * 
	 * @param serviceContext
	 */
	public void setServiceContext(IServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	/**
	 * Construtor inicia a conexao com repositorio do EMC.
	 */
	public DocumentumCoreServicesImpl() {

		IObjectService iObjService = null;

		if (this.objectService == null) {

			logger.info(INFO_INICIANDO_EMC);

			try {

				RepositoryIdentityConstants repositoryIdentityConstants = EMCDocumentumFactory
						.getConstants(RepositoryIdentityConstants.class);

				this.setRepositoryIdentityConstants(repositoryIdentityConstants);

				ContextFactory contextFactory = ContextFactory.getInstance();

				IServiceContext serviceContext = contextFactory.getContext();
				serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
				serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

				setServiceContext(serviceContext);

				RepositoryIdentity repoId = new RepositoryIdentity();

				repoId.setRepositoryName(REPOSITORY_NAME);

				repoId.setUserName(USER_NAME);

				repoId.setPassword(USER_PASSWORD);

				getServiceContext().addIdentity(repoId);

				//		contextFactory.register(getServiceContext());

				//iObjectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class, serviceContext, MODULE_NAME, DFS_SERVICE_URL);
				iObjService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

				logger.info(INFO_CONEXAO_EMC);

				this.objectService = iObjService;

			} catch (ServiceInvocationException e) {

				logger.error(ERROR_CONEXAO_EMC.concat(e.getLocalizedMessage()));

			}

			this.setObjectService(iObjService);
		}

	}

	public void objServiceCopy(String sourceObjectPathString,
			String targetLocPathString) throws ServiceException {

		// create a contentless document to link into folder
		String objectName = "linkedDocument" + System.currentTimeMillis();
		ObjectIdentity sampleObjId = new ObjectIdentity(REPOSITORY_NAME);
		DataObject sampleDataObject = new DataObject(sampleObjId, "dm_document");
		sampleDataObject.getProperties().set("object_name", objectName);

		// add the folder to link to as a ReferenceRelationship
		ObjectPath objectPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> sampleFolderIdentity = new ObjectIdentity<ObjectPath>(
				objectPath, REPOSITORY_NAME);
		ReferenceRelationship sampleFolderRelationship = new ReferenceRelationship();
		sampleFolderRelationship.setName(Relationship.RELATIONSHIP_FOLDER);
		sampleFolderRelationship.setTarget(sampleFolderIdentity);
		sampleFolderRelationship.setTargetRole(Relationship.ROLE_PARENT);
		sampleDataObject.getRelationships().add(sampleFolderRelationship);

		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);

		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(REPOSITORY_NAME);

		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		DataPackage dataPackage = new DataPackage(sampleDataObject);
		OperationOptions operationOptions = null;

		this.objectService.copy(new ObjectIdentitySet(sampleObjId),
				toLocation, dataPackage, operationOptions);

		/*
		 * // identify the object to copy ObjectPath objPath = new
		 * ObjectPath(sourceObjectPathString); ObjectIdentity<ObjectPath>
		 * docToCopy = new ObjectIdentity<ObjectPath>();
		 * docToCopy.setValue(objPath);
		 * docToCopy.setRepositoryName(REPOSITORY_NAME);
		 * 
		 * // identify the folder to copy to ObjectPath folderPath = new
		 * ObjectPath(); folderPath.setPath(targetLocPathString);
		 * 
		 * 
		 * ObjectIdentity<ObjectPath> toFolderIdentity = new
		 * ObjectIdentity<ObjectPath>(); toFolderIdentity.setValue(folderPath);
		 * toFolderIdentity.setRepositoryName(REPOSITORY_NAME); ObjectLocation
		 * toLocation = new ObjectLocation();
		 * toLocation.setObjectIdentity(toFolderIdentity);
		 * 
		 * DataPackage dataPackage = new DataPackage();
		 * 
		 * OperationOptions operationOptions = null; DataObject docDataObj = new
		 * DataObject(docToCopy, DM_DOCUMENT);
		 * 
		 * //Copy copy = Copy();
		 * 
		 * //SysObjectCopyOperation copyOperation = new SysObjectCopyOperation()
		 * 
		 * ObjectRelationship objRelationship = new ObjectRelationship();
		 * objRelationship.setName(Relationship.VIRTUAL_DOCUMENT_RELATIONSHIP);
		 * objRelationship.setTargetRole(Relationship.ROLE_CHILD);
		 * 
		 * docDataObj.getRelationships().add(new
		 * ObjectRelationship(objRelationship));
		 * dataPackage.addDataObject(docDataObj);
		 */

		// this.objectService.copy(new ObjectIdentitySet(docToCopy),
		// toLocation, dataPackage, operationOptions);
	}

	public DataPackage copyDocument(String objectId,
			String sourceObjectPathString, String targetLocPathString)
					throws ServiceException //
					{
		// identify the object to copy
		StringBuilder sbDql = new StringBuilder();
		sbDql.append(DM_DOCUMENT).append(WHERE).append(OBJECT_ID)
		.append(EQ_QUOTE).append(objectId).append(QUOTE);
		Qualification qualification1 = new Qualification(sbDql.toString());
		ObjectIdentity targetObjectIdentity1 = new ObjectIdentity(
				qualification1, REPOSITORY_NAME);
		ObjectIdentitySet docToCopy = new ObjectIdentitySet();
		docToCopy.addIdentity(targetObjectIdentity1);

		// identify the folder to copy to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);

		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(REPOSITORY_NAME);

		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		return this.objectService.copy(docToCopy, toLocation,
				new DataPackage(), operationOptions);
					}

	/**
	 * Cria um diretorio no repositorio EMC.
	 * 
	 * @param nameFolder
	 *            - nome do diretorio
	 * @return DataObject da pasta que sera retornada, caso nao exista retorna
	 *         'null'.
	 */
	protected DataPackage createFolder(String nameFolder) {

		ObjectIdentity<?> folderIdentity = new ObjectIdentity();

		folderIdentity.setRepositoryName(REPOSITORY_NAME);

		DataObject dataObject = new DataObject(folderIdentity, DM_FOLDER);

		PropertySet properties = new PropertySet();

		properties.set(OBJECT_NAME, nameFolder);

		dataObject.setProperties(properties);

		DataPackage dataPackage = new DataPackage(dataObject);

		OperationOptions operationOptions = null;

		try {

			dataPackage = this.objectService.create(dataPackage,
					operationOptions);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;

	}

	/**
	 * Retorna um data object representando a pasta do EMC.
	 * 
	 * @param folderName
	 *            - nome da pasta que deve ser recuperada.
	 * @return DataObject da pasta que sera retornada, caso nao exista retorna
	 *         'null'.
	 */
	protected DataObject getFolderDataObject(String folderName) {

		DataObject dataObject = null;

		try {

			StringBuilder sbDql = new StringBuilder();

			sbDql.append(SELECT).append(ALL).append(FROM);

			sbDql.append(DM_FOLDER).append(WHERE).append(OBJECT_NAME)
			.append(EQ_QUOTE).append(folderName).append(QUOTE);

			QueryResult queryResult = this.getQueryResult(sbDql.toString());

			DataPackage dataPackage = queryResult.getDataPackage();

			List<DataObject> dataObjects = dataPackage.getDataObjects();

			if (dataObjects != null && dataObjects.size() > 0) {

				dataObject = dataObjects.get(0);

				logger.info("\n\nDEBUG:\n\n".concat(dataObjects.get(0)
						.toString()));

			}

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataObject;

	}

	/**
	 * Remove uma pasta do EMC caso ela esteja vazia.
	 * 
	 * @param folderName
	 *            - nome da pasta que deve ser deletada.
	 */
	protected void deleteFolder(String folderName) {

		this.deleteObjects(folderName, DM_FOLDER);

	}

	/**
	 * Remove um documento do EMC.
	 * 
	 * @param folderName
	 *            - nome da documento que deve ser deletado.
	 */
	protected void deleteDocument(String documentName) {

		this.deleteObjects(documentName, DM_DOCUMENT);

	}

	/**
	 * Usado para remover genericamente objetos no EMC por nome e tipo.
	 * 
	 * @param objectName
	 * @param objectType
	 */
	private void deleteObjects(String objectName, String objectType) {

		ContentProfile contentProfile = new ContentProfile();

		contentProfile.setFormatFilter(FormatFilter.SPECIFIED);

		contentProfile.setContentReturnType(FileContent.class);

		OperationOptions operationOptions = new OperationOptions();

		operationOptions.setContentProfile(contentProfile);

		ContentTransferProfile transferProfile = new ContentTransferProfile();

		operationOptions.setContentTransferProfile(transferProfile);

		StringBuilder sbDql = new StringBuilder();

		sbDql.append(objectType).append(WHERE).append(OBJECT_NAME)
		.append(EQ_QUOTE).append(objectName).append(QUOTE);

		Qualification qualification1 = new Qualification(sbDql.toString());

		ObjectIdentity targetObjectIdentity1 = new ObjectIdentity(
				qualification1, REPOSITORY_NAME);

		ObjectIdentitySet objIdSet = new ObjectIdentitySet();

		objIdSet.addIdentity(targetObjectIdentity1);

		try {

			this.objectService.delete(objIdSet, operationOptions);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

	}

	/*
	 * public List<Repository> listRepositories() {
	 * 
	 * String hostPort = "10.1.1.140:8080";
	 * 
	 * String docbase = REPOSITORY_NAME;
	 * 
	 * String user = "diego.csilva";
	 * 
	 * String pass = "montreal";
	 * 
	 * List<String> listRepositories = new ArrayList<String>();
	 * 
	 * List<Repository> list = new ArrayList<Repository>();
	 * 
	 * ContextFactory contextFactory = ContextFactory.getInstance();
	 * 
	 * IServiceContext context = contextFactory.newContext();
	 * 
	 * RepositoryIdentity repoId = new RepositoryIdentity();
	 * 
	 * repoId.setRepositoryName(docbase);
	 * 
	 * repoId.setUserName(user);
	 * 
	 * repoId.setPassword(pass);
	 * 
	 * context.addIdentity(repoId);
	 * 
	 * ServiceFactory serviceFactory = ServiceFactory.getInstance();
	 * 
	 * ISearchService mySvc;
	 * 
	 * try {
	 * 
	 * mySvc = serviceFactory.getRemoteService(ISearchService.class, context,
	 * "core", "http://" + hostPort + "/emc-dfs/services");
	 * 
	 * List<Repository> reps = mySvc.getRepositoryList(null);
	 * 
	 * list = reps;
	 * 
	 * for (int i = 0; i < reps.size(); i++) {
	 * 
	 * listRepositories.add(reps.get(i).getName());
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * logger.info(e.getLocalizedMessage());
	 * 
	 * }
	 * 
	 * return list; }
	 */

	/**
	 * Executa uma operacao dql que nao precisa de retorno da consulta.
	 * 
	 * @param dql
	 *            - DQL
	 * @return Integer - status da operacao informando se a query foi executada
	 *         corretamente.
	 */
	protected Integer executeQuery(String dql) {

		Integer updated = 0;

		try {

			QueryResult queryResult = this.getQueryResult(dql);

			updated = queryResult.getQueryStatus().getRepositoryStatusInfos()
					.size();

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return updated;

	}

	/**
	 * Busca os metadados de um documentum atravÃ©s do object_id passado por
	 * parÃ¢metro.
	 * 
	 * @param objectId
	 */
	public List<Map<Object, Object>> getDocumentById(String objectId,
			String tipoDocumental) {

		String dql = "select * from " + tipoDocumental
				+ " where r_object_id = '" + objectId + "'";

		return this.searchMetadataByDql(dql);
	}

	/**
	 * Retorna o primeiro metadado do documento por DQL.
	 * 
	 * @param dql
	 *            - DQL de consulta.
	 * @return Map - map com chave e valor representando os metadados
	 *         encontrados.
	 */
	protected Map<String, Object> searchDocumentAndMetadataByDql(String dql) {

		Map<String, Object> mapDocumentAndMetadata = new HashMap<String, Object>();

		try {

			QueryResult queryResult = this.getQueryResult(dql);

			DataPackage resultDp = queryResult.getDataPackage();

			List<DataObject> dataObjects = resultDp.getDataObjects();

			int numberOfObjects = dataObjects.size();

			if (numberOfObjects > 0) {

				DataObject currentDataObject = dataObjects.get(0);

				PropertySet docProperties = currentDataObject.getProperties();

				Iterator<Property> iterator = docProperties.iterator();

				while (iterator.hasNext()) {

					Property prop = iterator.next();

					mapDocumentAndMetadata.put(prop.getName(),
							prop.getValueAsString());

				}

			}

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return mapDocumentAndMetadata;

	}

	/**
	 * Executa uma consulta DQL.
	 * 
	 * @param dql
	 *            - DQL de consulta.
	 * @return QueryResult
	 */
	private QueryResult getQueryResult(String dql) throws CacheException,
	QueryValidationException, CoreServiceException, ServiceException {

		ContextFactory contextFactoryDql = ContextFactory.getInstance();

		IServiceContext serviceContextDql = contextFactoryDql.newContext();
		serviceContextDql.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
		serviceContextDql.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

		RepositoryIdentity repoId = new RepositoryIdentity();

		repoId.setRepositoryName(REPOSITORY_NAME);

		repoId.setUserName(USER_NAME);

		repoId.setPassword(USER_PASSWORD);

		serviceContextDql.addIdentity(repoId);

		contextFactoryDql.register(serviceContextDql);

		ServiceFactory serviceFactory = ServiceFactory.getInstance();

		IQueryService querySvc = serviceFactory.getLocalService(IQueryService.class, serviceContextDql);

		//IQueryService querySvc = serviceFactory.getRemoteService(IQueryService.class, serviceContextDql);

		PassthroughQuery query = new PassthroughQuery();

		query.setQueryString(dql);

		query.addRepository(REPOSITORY_NAME);

		QueryExecution queryEx = new QueryExecution();

		queryEx.setCacheStrategyType(CacheStrategyType.DEFAULT_CACHE_STRATEGY);

		OperationOptions operationOptions = null;

		QueryResult queryResult = querySvc.execute(query, queryEx, operationOptions);

		logger.info(INFO_DQL_QUERY.concat(query.getQueryString()));

		logger.info(INFO_CACHE_STRATEGY.concat(String.valueOf(queryEx
				.getCacheStrategyType())));

		return queryResult;

	}

	/**
	 * Procura DataObjects no documento de acordo com o DQL.
	 * 
	 * @param dql
	 *            - DQL de consulta.
	 * @return List - lista os DataObjects encontrados.
	 */
	protected List<DataObject> getDataObjectsByDql(String dql) {

		List<DataObject> emcDataObjectList = new ArrayList<DataObject>();

		try {

			QueryResult queryResult = this.getQueryResult(dql);

			DataPackage resultDp = queryResult.getDataPackage();

			List<DataObject> dataObjects = resultDp.getDataObjects();

			int numberOfObjects = dataObjects.size();

			logger.info(INFO_NUMBER_OF_ITENS.concat(String
					.valueOf(numberOfObjects)));

			for (DataObject dObj : dataObjects) {

				emcDataObjectList.add(dObj);

			}

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return emcDataObjectList;

	}

	/**
	 * Procura metadados no documento de acordo com o DQL.
	 * 
	 * @param dql
	 *            - DQL de consulta.
	 * @return List - lista com mapas que representam os metadados encontrados.
	 */
	protected List<Map<Object, Object>> searchMetadataByDql(String dql) {

		List<Map<Object, Object>> emcDocumentsList = new ArrayList<Map<Object, Object>>();

		try {

			QueryResult queryResult = this.getQueryResult(dql);

			DataPackage resultDp = queryResult.getDataPackage();

			List<DataObject> dataObjects = resultDp.getDataObjects();

			int numberOfObjects = dataObjects.size();

			logger.info(INFO_NUMBER_OF_ITENS.concat(String
					.valueOf(numberOfObjects)));

			for (DataObject dObj : dataObjects) {

				PropertySet docProperties = dObj.getProperties();

				Iterator<Property> iterator = docProperties.iterator();

				Map<Object, Object> ecmDocumentMap = new HashMap<Object, Object>();

				while (iterator.hasNext()) {

					Property prop = iterator.next();

					ecmDocumentMap.put(prop.getName(), prop.getValueAsString());

				}

				emcDocumentsList.add(ecmDocumentMap);

			}

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return emcDocumentsList;

	}

	/**
	 * Remove os documentos dados um determinado diretorio no Documentum.
	 * 
	 * @param path
	 *            - diretorio do EMC.
	 * @return Integer - Status da operacao.
	 */
	protected Integer deleteDocumentsByPath(String path) {

		Integer statusReturn = 0;

		ObjectPath docPath = new ObjectPath(path);

		ObjectIdentity<ObjectPath> objIdentity = new ObjectIdentity<ObjectPath>();

		objIdentity.setValue(docPath);

		objIdentity.setRepositoryName(REPOSITORY_NAME);

		ObjectIdentitySet objectIdSet = new ObjectIdentitySet(objIdentity);

		DeleteProfile deleteProfile = new DeleteProfile();

		deleteProfile.setDeepDeleteFolders(true);

		deleteProfile.setDeepDeleteChildrenInFolders(true);

		OperationOptions operationOptions = new OperationOptions();

		operationOptions.setDeleteProfile(deleteProfile);

		try {

			this.objectService.delete(objectIdSet, operationOptions);

			statusReturn = 1;

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return statusReturn;

	}

	/**
	 * Recupera um arquivo no EMC buscando pelo nome.
	 * 
	 * @param documentId
	 *            - id do documento buscado
	 * @param documentType
	 *            - nome do tipo documental
	 * @return File - arquivo retornado
	 */
	protected File getDocumentById(String documentId, String documentType,
			String ext) {

		StringBuilder sbDql = new StringBuilder();

		sbDql.append(documentType).append(WHERE).append(OBJECT_ID)
		.append(EQ_QUOTE).append(documentId).append(QUOTE);

		log.error("============== sbDql.toString()=============== "
				+ sbDql.toString());
		log.error("============== ext=============== " + ext);
		return this.getDocument(sbDql.toString(), ext);

	}

	/**
	 * Recupera um arquivo no EMC buscando pelo nome.
	 * 
	 * @param documentName
	 *            - nome do documento buscado
	 * @param documentType
	 *            - nome do tipo documental
	 * @return File - arquivo retornado
	 */
	protected File getDocumentByName(String documentName, String documentType,
			String ext) {

		StringBuilder sbDql = new StringBuilder();

		sbDql.append(documentType).append(WHERE).append(OBJECT_NAME)
		.append(EQ_QUOTE).append(documentName).append(QUOTE);

		return this.getDocument(sbDql.toString(), ext);

	}

	/**
	 * Recupera um arquivo no repositorio do EMC.
	 * 
	 * @param documentName
	 *            - nome do documento
	 * @return File - arquivo retornado
	 */
	protected File getDocument(String dql, String ext) {
		log.error("====================FUNï¿½ï¿½O getDocument NA CLASSE DocumentumCoreServicesImpl==================");
		log.error("====================PARAMETROS RECEBIDOS================== DQL "
				+ dql + " ================== ext " + ext);
		File fileReturn = null;

		ContentProfile contentProfile = new ContentProfile();

		contentProfile.setFormatFilter(FormatFilter.SPECIFIED);

		contentProfile.setFormat(ext);
		log.error("======================= contentProfile.setFormat====== "
				+ ext);
		contentProfile.setContentReturnType(FileContent.class);

		OperationOptions operationOptions = new OperationOptions();

		operationOptions.setContentProfile(contentProfile);
		log.error("======================= operationOptions.setContentProfile====== "
				+ contentProfile);
		ContentTransferProfile transferProfile = new ContentTransferProfile();

		operationOptions.setContentTransferProfile(transferProfile);

		Qualification qualification1 = new Qualification(dql);

		ObjectIdentity targetObjectIdentity1 = new ObjectIdentity(
				qualification1, REPOSITORY_NAME);
		log.error("======================= targetObjectIdentity1 (qualification1, REPOSITORY_NAME)====== "
				+ targetObjectIdentity1);
		ObjectIdentitySet objIdSet = new ObjectIdentitySet();

		objIdSet.addIdentity(targetObjectIdentity1);

		DataPackage dataPackage;

		try {

			dataPackage = this.objectService.get(objIdSet,
					operationOptions);
			log.error("======================= objIdSet ====== " + objIdSet);
			log.error("======================= operationOptions ====== "
					+ operationOptions);

			DataObject dataObject = dataPackage.getDataObjects().get(0);
			log.error("======================= dataObject ====== "
					+ dataPackage.getDataObjects().get(0));
			Content resultContent = dataObject.getContents().get(0);
			log.error("======================= resultContent ====== "
					+ dataObject.getContents().get(0));

			FileContent fileContent = (FileContent) resultContent;
			fileReturn = fileContent.getAsFile();

		} catch (CoreServiceException e) {
			System.out.println("CoreServiceException \n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println(e.getMessage());
			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n CoreServiceException");
		} catch (ServiceException e) {
			System.out.println("ServiceException \n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println(e.getMessage());
			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n ServiceException");

		}
		log.error("==================== FIM FUNï¿½ï¿½O getDocument NA CLASSE DocumentumCoreServicesImpl==================");

		/*
		 * File source = new File(fileReturn.getPath()); File dest = new
		 * File("D:\\Temp\\file2.docx"); try { FileUtils.copyFile(source, dest);
		 * } catch (IOException e) { e.printStackTrace(); }
		 */

		return fileReturn;

	}

	/**
	 * Cria documento no EMC.
	 * 
	 * @param file
	 *            - arquivo que serï¿½ armazenado no repositorio
	 * @param documentId
	 *            - id relacionado ao documento da tabela dbo.DOCUMENTO
	 * @param metadados
	 *            - arquivo onde serï¿½ preenchidos os metatadados e os valores
	 * @return DataPackage
	 */
	protected DataPackage createDocument(File file, Integer documentId,
			Map<String, String> props, String ext) {

		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataObject dataObject = new DataObject(objIdentity, DM_DOCUMENT);

		PropertySet properties = dataObject.getProperties();

		properties.set(OBJECT_NAME, file.getName());

		properties.set(TITLE, file.getName());

		// properties.set(A_CONTENT_TYPE, ext);

		dataObject.getContents().add(
				new FileContent(file.getAbsolutePath(), ext));

		DataPackage dataPackage = null;

		try {

			dataPackage = this.objectService.create(
					new DataPackage(dataObject), null);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;
	}

	/**
	 * Cria documento no EMC.
	 * 
	 * @param file
	 *            - arquivo que serï¿½ armazenado no repositorio
	 * @return DataPackage
	 */
	protected DataPackage createDocument(File file, String ext) {

		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataObject dataObject = new DataObject(objIdentity, DM_DOCUMENT);

		PropertySet properties = dataObject.getProperties();

		properties.set(OBJECT_NAME, file.getName());

		properties.set(TITLE, file.getName());

		// properties.set(A_CONTENT_TYPE, ext);

		dataObject.getContents().add(
				new FileContent(file.getAbsolutePath(), ext));

		DataPackage dataPackage = null;

		try {

			dataPackage = this.objectService.create(
					new DataPackage(dataObject), null);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;
	}

	public void objServiceMove(String sourceObjectPathString,
			String targetLocPathString, String sourceLocPathString)
					throws ServiceException {
		// identify the object to move
		ObjectPath objPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> docToCopy = new ObjectIdentity<ObjectPath>();
		docToCopy.setValue(objPath);
		docToCopy.setRepositoryName(REPOSITORY_NAME);

		// identify the folder to move from
		ObjectPath fromFolderPath = new ObjectPath();
		fromFolderPath.setPath(sourceLocPathString);
		ObjectIdentity<ObjectPath> fromFolderIdentity = new ObjectIdentity<ObjectPath>();
		fromFolderIdentity.setValue(fromFolderPath);
		fromFolderIdentity.setRepositoryName(REPOSITORY_NAME);
		ObjectLocation fromLocation = new ObjectLocation();
		fromLocation.setObjectIdentity(fromFolderIdentity);

		// identify the folder to move to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);
		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(REPOSITORY_NAME);
		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		this.objectService.move(new ObjectIdentitySet(docToCopy),
				fromLocation, toLocation, new DataPackage(), operationOptions);

	}

	protected DataPackage createFolders(List<String> folders) throws Exception {

		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataPackage dataPackage = new DataPackage();

		try {

			StringBuilder sPath = new StringBuilder();

			sPath.append(BAR_FOLDER);

			sPath.append(CABINET);

			String path = sPath.toString();

			DataObject folderObject = null;

			for (String folder : folders) {

				path = path.concat(BAR_FOLDER + folder);

				StringBuilder sb = new StringBuilder();

				sb.append(String.format(DQL_SELECT_FOLDER, path));

				String dqlSelectFolder = sb.toString();

				List<DataObject> dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

				if (dataObjectFolder.size() == 0) {

					String containnerPath = path;

					containnerPath = containnerPath.replace(
							BAR_FOLDER + folder, EMPTY);

					String dqlCreateFolder = String.format(DQL_CREATE_FOLDER,
							folder, containnerPath);

					Integer returnCreateFolder = executeQuery(dqlCreateFolder);

					if (returnCreateFolder == 1) {

						dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

						folderObject = dataObjectFolder.get(0);

					} else {

						throw new Exception(String.format(ERROR_CREATE_FOLDER,
								folder, containnerPath));

					}

				} else {

					folderObject = dataObjectFolder.get(0);

				}

			}

			ObjectRelationship objRelationship = new ObjectRelationship();

			objRelationship.setTarget(folderObject);

			objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);

			objRelationship.setTargetRole(Relationship.ROLE_PARENT);

			RelationshipProfile relationProfile = new RelationshipProfile();

			relationProfile.setResultDataMode(ResultDataMode.REFERENCE);

			relationProfile.setTargetRoleFilter(TargetRoleFilter.ANY);

			relationProfile.setNameFilter(RelationshipNameFilter.ANY);

			relationProfile.setDepthFilter(DepthFilter.SPECIFIED);

			relationProfile.setDepth(2);

			OperationOptions operationOptions = new OperationOptions();

			operationOptions.setRelationshipProfile(relationProfile);

			logger.info(INFO_INICIACAO_OPERACAO);

			dataPackage = this.objectService.create(dataPackage,
					operationOptions);

			logger.info(INFO_CREATED_FILE_EMC);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;
	}

	/**
	 * Criar pastas, documento e metadados de acordo com os parametros passados.
	 * 
	 * @param document
	 *            - Map com informacoes do documento.
	 * @param folders
	 *            - Lista que possui strings que representam as pastas.
	 * @param metadatas
	 *            - Map com chave valor que representa os metadados.
	 * @return DataPackage
	 */
	protected DataPackage createFolderAndDocument(Map<Object, Object> document,	List<String> folders, Map<Object, Object> metadatas, String ext)
			throws Exception {

		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataPackage dataPackage = new DataPackage();

		try {

			StringBuilder sPath = new StringBuilder();

			sPath.append(BAR_FOLDER);

			sPath.append(CABINET);

			// sPath.append(BAR_FOLDER);

			// sPath.append(FOLDER_LEVEL_2);

			String path = sPath.toString();

			DataObject folderObject = null;

			for (String folder : folders) {

				path = path.concat(BAR_FOLDER + folder);

				StringBuilder sb = new StringBuilder();

				sb.append(String.format(DQL_SELECT_FOLDER, path));

				String dqlSelectFolder = sb.toString();

				List<DataObject> dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

				if (dataObjectFolder.size() == 0) {

					String containnerPath = path;

					containnerPath = containnerPath.replace(
							BAR_FOLDER + folder, EMPTY);

					String dqlCreateFolder = String.format(DQL_CREATE_FOLDER,
							folder, containnerPath);

					Integer returnCreateFolder = executeQuery(dqlCreateFolder);

					if (returnCreateFolder == 1) {

						dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

						folderObject = dataObjectFolder.get(0);

					} else {

						throw new Exception(String.format(ERROR_CREATE_FOLDER,
								folder, containnerPath));

					}

				} else {

					folderObject = dataObjectFolder.get(0);

				}

			}

			DataObject docDataObj = null;

			if (document.containsKey(CHAVE_TIPO_DOCUMENTAL)) {

				docDataObj = new DataObject(objIdentity,
						String.valueOf(document.get(CHAVE_TIPO_DOCUMENTAL)));

			} else {

				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_TIPO_DOCUMENTAL));

			}

			PropertySet properties = new PropertySet();

			if (document.containsKey(CHAVE_ARQUIVO)) {

				properties.set(TITLE,
						((File) document.get(CHAVE_ARQUIVO)).getName());

			} else {

				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_ARQUIVO));

			}

			// properties.set(A_CONTENT_TYPE, ext);

			if (null != metadatas && metadatas.size() > 0) {

				for (Entry<Object, Object> metadata : metadatas.entrySet()) {

					logger.info(INFO_CREATE_METADATA
							.concat(String.valueOf(metadata.getKey()))
							.concat(SPACE).concat(UNDERSCORE).concat(SPACE)
							.concat(String.valueOf(metadata.getValue())));

					properties.set(String.valueOf(metadata.getKey()),
							metadata.getValue());

				}

			}

			docDataObj.setProperties(properties);

			docDataObj.getContents().add(
					new FileContent(((File) document.get(CHAVE_ARQUIVO))
							.getAbsolutePath(), PDF));

			ObjectRelationship objRelationship = new ObjectRelationship();

			objRelationship.setTarget(folderObject);

			objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);

			objRelationship.setTargetRole(Relationship.ROLE_PARENT);

			docDataObj.getRelationships().add(
					new ObjectRelationship(objRelationship));

			dataPackage.addDataObject(docDataObj);

			RelationshipProfile relationProfile = new RelationshipProfile();

			relationProfile.setResultDataMode(ResultDataMode.REFERENCE);

			relationProfile.setTargetRoleFilter(TargetRoleFilter.ANY);

			relationProfile.setNameFilter(RelationshipNameFilter.ANY);

			relationProfile.setDepthFilter(DepthFilter.SPECIFIED);

			relationProfile.setDepth(2);

			OperationOptions operationOptions = new OperationOptions();

			operationOptions.setRelationshipProfile(relationProfile);

			String fileName = null;

			if (document.containsKey(CHAVE_NOME_ARQUIVO)) {

				fileName = (String) document.get(CHAVE_NOME_ARQUIVO);

				properties.set(OBJECT_NAME, fileName);

			} else {

				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_NOME_ARQUIVO));

			}

			logger.info(INFO_INICIACAO_OPERACAO);

			dataPackage = this.objectService.create(dataPackage,
					operationOptions);

			logger.info(INFO_CREATED_FILE_EMC.concat(fileName));

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;
	}


	/**
	 * Criar pastas, documento e metadados de acordo com os parametros passados.
	 * 
	 * @param document
	 *            - Map com informacoes do documento.
	 * @param folders
	 *            - Lista que possui strings que representam as pastas.
	 * @param metadatas
	 *            - Map com chave valor que representa os metadados.
	 * @return DataPackage
	 */

	protected boolean criarPastaDocumento(Map<Object, Object> document,
			List<String> folders, Map<Object, Object> metadatas, String ext)
					throws Exception {

		boolean error = false;
		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataPackage dataPackage = new DataPackage();

		try {

			StringBuilder sPath = new StringBuilder();

			sPath.append(BAR_FOLDER);

			sPath.append(CABINET);

			// sPath.append(BAR_FOLDER);

			// sPath.append(FOLDER_LEVEL_2);

			String path = sPath.toString();

			DataObject folderObject = null;

			for (String folder : folders) {

				path = path.concat(BAR_FOLDER + folder);

				StringBuilder sb = new StringBuilder();

				sb.append(String.format(DQL_SELECT_FOLDER, path));

				String dqlSelectFolder = sb.toString();

				List<DataObject> dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

				if (dataObjectFolder.size() == 0) {

					String containnerPath = path;

					containnerPath = containnerPath.replace(
							BAR_FOLDER + folder, EMPTY);

					String dqlCreateFolder = String.format(DQL_CREATE_FOLDER,
							folder, containnerPath);

					Integer returnCreateFolder = executeQuery(dqlCreateFolder);

					if (returnCreateFolder == 1) {

						dataObjectFolder = getDataObjectsByDql(dqlSelectFolder);

						folderObject = dataObjectFolder.get(0);

					} else {

						throw new Exception(String.format(ERROR_CREATE_FOLDER,
								folder, containnerPath));

					}

				} else {

					folderObject = dataObjectFolder.get(0);

				}

			}

			DataObject docDataObj = null;

			if (document.containsKey(CHAVE_TIPO_DOCUMENTAL)) {

				docDataObj = new DataObject(objIdentity,
						String.valueOf(document.get(CHAVE_TIPO_DOCUMENTAL)));

			} else {

				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_TIPO_DOCUMENTAL));

			}

			PropertySet properties = new PropertySet();

			if (document.containsKey(CHAVE_ARQUIVO)) {

				properties.set(TITLE,
						((File) document.get(CHAVE_ARQUIVO)).getName());

			} else {

				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_ARQUIVO));

			}

			// properties.set(A_CONTENT_TYPE, ext);

			if (null != metadatas && metadatas.size() > 0) {

				for (Entry<Object, Object> metadata : metadatas.entrySet()) {

					logger.info(INFO_CREATE_METADATA
							.concat(String.valueOf(metadata.getKey()))
							.concat(SPACE).concat(UNDERSCORE).concat(SPACE)
							.concat(String.valueOf(metadata.getValue())));

					properties.set(String.valueOf(metadata.getKey()),
							metadata.getValue());

				}

			}

			docDataObj.setProperties(properties);

			docDataObj.getContents().add(
					new FileContent(((File) document.get(CHAVE_ARQUIVO))
							.getAbsolutePath(), PDF));

			ObjectRelationship objRelationship = new ObjectRelationship();

			objRelationship.setTarget(folderObject);

			objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);

			objRelationship.setTargetRole(Relationship.ROLE_PARENT);

			docDataObj.getRelationships().add(
					new ObjectRelationship(objRelationship));

			dataPackage.addDataObject(docDataObj);

			RelationshipProfile relationProfile = new RelationshipProfile();

			relationProfile.setResultDataMode(ResultDataMode.REFERENCE);

			relationProfile.setTargetRoleFilter(TargetRoleFilter.ANY);

			relationProfile.setNameFilter(RelationshipNameFilter.ANY);

			relationProfile.setDepthFilter(DepthFilter.SPECIFIED);

			relationProfile.setDepth(2);

			OperationOptions operationOptions = new OperationOptions();

			operationOptions.setRelationshipProfile(relationProfile);

			String fileName = null;

			if (document.containsKey(CHAVE_NOME_ARQUIVO)) {

				fileName = (String) document.get(CHAVE_NOME_ARQUIVO);

				properties.set(OBJECT_NAME, fileName);

			} else {

				error = true;
				throw new Exception(String.format(ERROR_MISSING_KEY_MAP,
						CHAVE_NOME_ARQUIVO));

			}

			logger.info(INFO_INICIACAO_OPERACAO);

			dataPackage = this.objectService.create(dataPackage,
					operationOptions);

			logger.info(INFO_CREATED_FILE_EMC.concat(fileName));

		} catch (CoreServiceException e) {

			error = true;
			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			error = true;
			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return error;
	}





	/**
	 * Cria pasta e documento, associa documento a pasta criada.
	 * 
	 * @param file
	 *            - arquivo que sera gravado o EMC
	 * @param folderName
	 *            - nome da pasta que sera criada
	 * @return DataPackage
	 */
	protected DataPackage createFolderAndDocument(File file, String folderName,
			String ext) {

		ObjectIdentity objIdentity = new ObjectIdentity(REPOSITORY_NAME);

		DataObject folderDataObj = new DataObject(objIdentity, DM_FOLDER);

		PropertySet folderDataObjProperties = new PropertySet();

		folderDataObjProperties.set(OBJECT_NAME, folderName);

		folderDataObj.setProperties(folderDataObjProperties);

		String doc1Name = file.getName();

		DataObject docDataObj = new DataObject(objIdentity, DM_DOCUMENT);

		PropertySet properties = new PropertySet();

		properties.set(OBJECT_NAME, doc1Name);

		properties.set(TITLE, file.getName());

		docDataObj.setProperties(properties);

		docDataObj.getContents().add(
				new FileContent(file.getAbsolutePath(), PDF));

		ObjectRelationship objRelationship = new ObjectRelationship();

		objRelationship.setTarget(folderDataObj);

		objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);

		objRelationship.setTargetRole(Relationship.ROLE_PARENT);

		docDataObj.getRelationships().add(
				new ObjectRelationship(objRelationship));

		DataPackage dataPackage = new DataPackage();

		dataPackage.addDataObject(docDataObj);

		RelationshipProfile relationProfile = new RelationshipProfile();

		relationProfile.setResultDataMode(ResultDataMode.REFERENCE);

		relationProfile.setTargetRoleFilter(TargetRoleFilter.ANY);

		relationProfile.setNameFilter(RelationshipNameFilter.ANY);

		relationProfile.setDepthFilter(DepthFilter.SPECIFIED);

		relationProfile.setDepth(2);

		OperationOptions operationOptions = new OperationOptions();

		operationOptions.setRelationshipProfile(relationProfile);

		try {

			dataPackage = this.objectService.create(dataPackage,
					operationOptions);

		} catch (CoreServiceException e) {

			logger.error(ERROR_REPOSITORIO_EMC.concat(e.getLocalizedMessage()));

		} catch (ServiceException e) {

			logger.error(ERROR_SERVICO_EMC.concat(e.getLocalizedMessage()));

		}

		return dataPackage;
	}

	public String recuperaURLDocumento(ObjectIdentity objIdentity)
	{		
		ObjectIdentitySet objIdSet = new ObjectIdentitySet();
		objIdSet.getIdentities().add(objIdentity);

		IObjectService service = null;
		try {
			service = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);
		} catch (ServiceInvocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String link = "";

		try 
		{
			List<ObjectContentSet> listaObjectContentSet = service.getObjectContentUrls(objIdSet);
			if(listaObjectContentSet != null)
			{
				List<Content> listaUrlContents = listaObjectContentSet.get(0).getContents();

				//ObjectContentSet objContentSet = (ObjectContentSet) lista.get(0);
				for (Content content : listaUrlContents) {
					if (content instanceof UrlContent) {
						link = ((UrlContent) content).getUrl();
					}
				}

			}
		}
		catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return link;

	}

	

	public DataPackage checkinDFS(String newFilePath, ObjectIdentity objIdentity)
			throws Exception {

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IVersionControlService versionSvc = serviceFactory.getService(
				IVersionControlService.class, serviceContext);

		ObjectIdentitySet objIdSet = new ObjectIdentitySet();
		objIdSet.getIdentities().add(objIdentity);

		OperationOptions operationOptions = null;
		ContentProfile contentProfile = new ContentProfile(FormatFilter.ANY,
				null, PageFilter.ANY, 1, PageModifierFilter.ANY, null);

		DataPackage checkinPackage = versionSvc.checkout(objIdSet,
				operationOptions);
		DataObject checkinObj = checkinPackage.getDataObjects().get(0);
		checkinObj.setContents(null);

		FileContent newContent = new FileContent();
		newContent.setLocalPath(newFilePath);
		newContent.setRenditionType(RenditionType.PRIMARY);
		newContent.setFormat("msw12");
		checkinObj.getContents().add(newContent);

		/*
		 * FileContent newContentPDF = new FileContent();
		 * 
		 * File file = new File(newFilePath);
		 * newContentPDF.setLocalPath(file.getAbsolutePath());
		 * newContentPDF.setRenditionType(RenditionType.CLIENT);
		 * newContentPDF.setContentTransferMode(ContentTransferMode.MTOM);
		 * newContentPDF.setFormat(PDF);
		 * checkinObj.getContents().add(newContentPDF);
		 */

		boolean retainLock = false;

		List<String> labels = new ArrayList<String>();
		labels.add("CURRENT");

		DataPackage resultDp;

		try {
			resultDp = versionSvc.checkin(checkinPackage,
					VersionStrategy.NEXT_MAJOR, retainLock, labels,
					operationOptions);
		} catch (ServiceException sE) {
			sE.printStackTrace();
			throw new RuntimeException(sE);
		}

		return resultDp;
	}

	//	static
	//	{
	//		Factory localFactory = null;
	//		try {
	//			localFactory = new Factory("TransactionManager.java", Class.forName("com.documentum.fc.client.transaction.impl.TransactionManager"));
	//		} catch (ClassNotFoundException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		
	//		JoinPoint.StaticPart ajc$tjp_10 = localFactory.makeSJP("initialization", localFactory.makeConstructorSig("1", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", ""), 16);
	//		JoinPoint.StaticPart ajc$tjp_2 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("2", "getTransactionThatMustExist", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "com.documentum.fc.client.transaction.IDfTransaction"), 35);
	//		JoinPoint.StaticPart ajc$tjp_3 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "getStatus", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "int"), 44);
	//		JoinPoint.StaticPart ajc$tjp_4 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "rollback", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "void"), 53);
	//		JoinPoint.StaticPart ajc$tjp_5 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "setRollbackOnly", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "void"), 58);
	//		JoinPoint.StaticPart ajc$tjp_6 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "getTransaction", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "com.documentum.fc.client.transaction.IDfTransaction"), 63);
	//		JoinPoint.StaticPart ajc$tjp_7 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "resume", "com.documentum.fc.client.transaction.impl.TransactionManager", "com.documentum.fc.client.transaction.IDfTransaction:", "transaction:", "javax.transaction.InvalidTransactionException:", "void"), 68);
	//		JoinPoint.StaticPart ajc$tjp_8 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "suspend", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "com.documentum.fc.client.transaction.IDfTransaction"), 73);
	//		JoinPoint.StaticPart ajc$tjp_9 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1008", "access$200", "com.documentum.fc.client.transaction.impl.TransactionManager", "com.documentum.fc.client.transaction.impl.TransactionManager:", "x0:", "", "java.lang.ThreadLocal"), 12);
	//	}

	@Override
	public void testeTransacao2()  {
		// TODO Auto-generated method stub

		try {
			/*		System.out.println("Inicia Factory");
					Factory localFactory = new Factory("TransactionManager.java", Class.forName("com.documentum.fc.client.transaction.impl.TransactionManager"));
					System.out.println("begin Transaction Factory");
					 JoinPoint localJoinPoint = null;

					 JoinPoint.StaticPart ajc$tjp_0 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "begin", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "", "void"), 22);
					 localJoinPoint = Factory.makeJP(ajc$tjp_0, null, null);
					 TracingAspect.aspectOf().ajc$afterReturning$com_documentum_fc_tracing_impl_aspects_TracingAspect$6$509ea924(localJoinPoint);

					//(void com.documentum.fc.client.transaction.impl.TransactionManager.begin())

					StringBuilder sbDql = new StringBuilder();
					sbDql.append("select * from sac4_metadado").append(WHERE).append(OBJECT_ID).append(EQ_QUOTE).append("0902a94b80142cea").append(QUOTE);

					QueryResult queryResult = this.getQueryResult(sbDql.toString());
				//	QueryResult queryResult2 = this.getQueryResult(sbDql.toString());

					DataPackage dataPackage = queryResult.getDataPackage();

					List<DataObject> dataObjects = dataPackage.getDataObjects();


					JoinPoint.StaticPart ajc$tjp_1 = localFactory.makeSJP("method-execution", localFactory.makeMethodSig("1", "commit", "com.documentum.fc.client.transaction.impl.TransactionManager", "", "", "com.documentum.fc.common.DfException:", "void"), 30);
					localJoinPoint = Factory.makeJP(ajc$tjp_1, null, null);
					TracingAspect.aspectOf().ajc$before$com_documentum_fc_tracing_impl_aspects_TracingAspect$3$cbec3e36(localJoinPoint);

					System.out.println("commit Factory");*/

			System.out.println("###########################################################################################################################################");
			System.out.println("###########################################################################################################################################");
			System.out.println("###########################################################################################################################################");

			changeDocument(2, "titulo 1 v4", "titulo 2 v4");

			//testTransaction0(2,  "titulo 1 v1", "titulo 2 v1");
			//testTransaction (false, null, 2, "titulo 1 v1", "titulo 2 v1");

			//	teste1234T();

			//teste123T();

			System.out.println("###########################################################################################################################################");
			System.out.println("###########################################################################################################################################");
			System.out.println("###########################################################################################################################################");

			//teste123();

			//testTransaction2(false, 1, "titulo 1", "titulo 2", objectIdentity, options);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}



	public DataPackage testTransaction2 (boolean isRemote, int abortAt, String titleUpdate1, String titleUpdate2, ObjectIdentity objectIdentity, OperationOptions options) throws Exception{

		System.out.println("testTransaction.begin");
		if (abortAt == 0)
			throw new ServiceException("Asked to abort at beginning");

		ContextFactory contextFactory = ContextFactory.getInstance();
		IServiceContext context = contextFactory.getContext();
		context.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
		context.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

		// object service, get either local or remote	
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		//IObjectService service = (IObjectService) serviceFactory.getLocalService(IObjectService.class, context);
		//IObjectService service = ServiceFactory.getInstance().getRemoteService(IObjectService.class, serviceContext);
		IObjectService service = ServiceFactory.getInstance().getService(IObjectService.class, serviceContext);
		DataPackage dtPk = new DataPackage();

		// UPDATE document
		DataObject dataobj = new DataObject(objectIdentity, "sac4_metadado");

		ObjectRelationship objRelationship = new ObjectRelationship();

		dataobj.getProperties().set("title", titleUpdate1.toString());

		//objRelationship.setTarget(CABINET + BAR_FOLDER + "METADADO");

		objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);

		objRelationship.setTargetRole(Relationship.ROLE_PARENT);

		dataobj.getRelationships().add(new ObjectRelationship(objRelationship));

		dtPk.addDataObject(dataobj);

		RelationshipProfile relationProfile = new RelationshipProfile();

		relationProfile.setResultDataMode(ResultDataMode.REFERENCE);

		relationProfile.setTargetRoleFilter(TargetRoleFilter.ANY);

		relationProfile.setNameFilter(RelationshipNameFilter.ANY);

		relationProfile.setDepthFilter(DepthFilter.SPECIFIED);

		relationProfile.setDepth(2);

		OperationOptions operationOptions = new OperationOptions();

		operationOptions.setRelationshipProfile(relationProfile);


		DataPackage results = service.update(dtPk, options);

		System.out.println("Executed first update of title");
		if (abortAt == 1)
			throw new CoreServiceException("Asked to abort after first update");
		// UPDATE document
		dataobj.getProperties().set("object_name", titleUpdate2);
		results = service.update(new DataPackage(dataobj), options);
		System.out.println("Executed second update of title");
		if (abortAt == 2)
			throw new ServiceException("Asked to abort after second update");



		/*		// identify the object to copy
		StringBuilder sbDql = new StringBuilder();
		sbDql.append(DM_DOCUMENT).append(WHERE).append(OBJECT_ID)
				.append(EQ_QUOTE).append(objectId).append(QUOTE);
		Qualification qualification1 = new Qualification(sbDql.toString());
		ObjectIdentity targetObjectIdentity1 = new ObjectIdentity(
				qualification1, REPOSITORY_NAME);
		ObjectIdentitySet docToCopy = new ObjectIdentitySet();
		docToCopy.addIdentity(targetObjectIdentity1);

		// identify the folder to copy to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);

		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(REPOSITORY_NAME);

		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		return this.objectService.copy(docToCopy, toLocation,
				new DataPackage(), operationOptions);*/
		return results;
	}

	public void insertTables()
	{
		// -------- Properties ---------------------------------------
		/*	String repositoryName = REPOSITORY_NAME;
		String userName = RepositoryConstants.USER_NAME;
		String userPassword = RepositoryConstants.USER_PASSWORD;
		String contextRoot = DFS_SERVICE_URL;
		String serviceModule = MODULE_NAME; 

		try
		{
			// Get a ContextFactory so that we can create a ServiceContext
			ContextFactory contextFactory = ContextFactory.getInstance();
			// Use the ContextFactory to create a ServiceContext for a service 
			IServiceContext serviceContext = contextFactory.getContext();

			serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
			serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

			// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
			RepositoryIdentity repoId =new RepositoryIdentity();
			repoId.setRepositoryName(repositoryName);
			repoId.setUserName(userName);
			repoId.setPassword(userPassword);
			// Add the populated RepositoryIdentity to the ServiceContext
			serviceContext.addIdentity(repoId);
			// Get an ObjectService from the ServiceFactory
			//IObjectService objectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class,serviceContext,serviceModule,contextRoot);
			IObjectService objectService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

			ObjectId objId=new ObjectId("0902a94b80142cea");
			ObjectIdentity objIdentity =new ObjectIdentity(objId,repositoryName);

			DataObject dataObject =new DataObject(objIdentity);
			dataObject.setIdentity(objIdentity);
			dataObject.setType("dm_document");


			String dqlIncluindo = "insert into TESTE3 (name) values ('Bruna v2') ";

			log.debug("Start 1st update operation on the Object Service.");
			Integer i = objectService.executarDql(dqlIncluindo);

			String dqlIncluindo2 = "insert into TESTE4 (name1) values ('Bruna v2') ";

			Integer y = objectService.executarDql(dqlIncluindo2);




			dataPackage = objectService.update(dataPackage,null);
			log.debug("End 1st update operation on the Object Service.");
			if (abortAt == 1)
				throw new CoreServiceException("Asked to abort after first update");

			properties = new PropertySet();
			properties.set("title", titulo2);
			dataObject.setProperties(properties);

			dataPackage =new DataPackage();
			dataPackage.getDataObjects().add(dataObject);
			log.debug("Start 2nd update operation on the Object Service.");			
			dataPackage = objectService.update(dataPackage, null);		
			log.debug("End 2nd update operation on the Object Service.");

		//	if (abortAt == 2)
		//		throw new CoreServiceException("Asked to abort after first update");
		}
		catch (Exception e) {
			log.debug("Exception - "+e.getMessage());
			throw new ServiceException("Abort after second update");
		} */
	}

	@Transactional(rollbackFor=ServiceException.class)
	public void changeDocument(int abortAt, String titulo1, String titulo2) throws Exception
	{
		// -------- Properties ---------------------------------------
		String repositoryName = REPOSITORY_NAME;
		String userName = RepositoryConstants.USER_NAME;
		String userPassword = RepositoryConstants.USER_PASSWORD;
		String contextRoot = DFS_SERVICE_URL;
		String serviceModule = MODULE_NAME; 

		try
		{
			// Get a ContextFactory so that we can create a ServiceContext
			ContextFactory contextFactory = ContextFactory.getInstance();
			// Use the ContextFactory to create a ServiceContext for a service 
			IServiceContext serviceContext = contextFactory.getContext();

			serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
			serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

			// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
			RepositoryIdentity repoId =new RepositoryIdentity();
			repoId.setRepositoryName(repositoryName);
			repoId.setUserName(userName);
			repoId.setPassword(userPassword);
			// Add the populated RepositoryIdentity to the ServiceContext
			serviceContext.addIdentity(repoId);
			// Get an ObjectService from the ServiceFactory
			//IObjectService objectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class,serviceContext,serviceModule,contextRoot);
			IObjectService objectService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

			ObjectId objId=new ObjectId("0902a94b80142cea");
			ObjectIdentity objIdentity =new ObjectIdentity(objId,repositoryName);

			DataObject dataObject =new DataObject(objIdentity);
			dataObject.setIdentity(objIdentity);
			dataObject.setType("dm_document");

			PropertySet properties =new PropertySet();
			//properties.set("object_name", document.getDocumentProperties().get("object_name"));
			properties.set("title", titulo1);
			dataObject.setProperties(properties);

			DataPackage dataPackage =new DataPackage();
			dataPackage.getDataObjects().add(dataObject);

			log.debug("Start 1st update operation on the Object Service.");
			dataPackage = objectService.update(dataPackage,null);
			log.debug("End 1st update operation on the Object Service.");
			if (abortAt == 1)
				throw new CoreServiceException("Asked to abort after first update");

			properties = new PropertySet();
			properties.set("title2", titulo2);
			dataObject.setProperties(properties);

			dataPackage =new DataPackage();
			dataPackage.getDataObjects().add(dataObject);
			log.debug("Start 2nd update operation on the Object Service.");			
			dataPackage = objectService.update(dataPackage, null);		
			log.debug("End 2nd update operation on the Object Service.");

			if (abortAt == 2)
				throw new ServiceException("Asked to abort after first update");
		}
		catch (ServiceException e) {
			log.debug("Exception - "+e.getMessage());
			throw new ServiceException("Abort after second update");
		}
	}


	public DataPackage testTransaction0 (int abortAt, String titleUpdate1, String titleUpdate2) throws Exception
	{
		System.out.println("testTransaction.begin");

		if (abortAt == 0)
			throw new ServiceException("Asked to abort at beginning");

		ObjectId objId=new ObjectId("0902a94b80142ced");
		ObjectIdentity objIdentity =new ObjectIdentity(objId, REPOSITORY_NAME);

		DataObject dataObject =new DataObject(objIdentity);
		dataObject.setIdentity(objIdentity);
		dataObject.setType("dm_document");

		dataObject.getProperties().set("title", titleUpdate1);
		DataPackage results = this.objectService.update(new DataPackage(dataObject), null);
		System.out.println("Executed first update of title");

		if (abortAt == 1)
			throw new CoreServiceException("Asked to abort after first update");

		// UPDATE document
		dataObject.getProperties().set("tit_metadado", titleUpdate2);
		results = this.objectService.update(new DataPackage(dataObject), null);
		System.out.println("Executed second update of title");

		if (abortAt == 2)
			throw new ServiceException("Asked to abort after second update");

		return results;
	}


	public IDfSessionManager createSessionManager(String docbase, String user, String pass)
			throws Exception {
		//create Client objects
		IDfClientX clientx = new DfClientX();
		IDfClient client = clientx.getLocalClient();

		//create a Session Manager object
		// IDfSessionManager sMgr = client.newSessionManager();
		IDfSessionManager sMgr = DfcSessionManager.getSessionManager();
		// IDfSessionManager sMgr = DfcSessionManager.getSessionManager();
		IDfLoginInfo loginInfo = sMgr.getIdentity(((RepositoryIdentity) ContextFactory.getInstance().getContext().getIdentity(0)).getRepositoryName());
		sMgr.setIdentity(IDfSessionManager.ALL_DOCBASES, loginInfo);



		/*	        //create an IDfLoginInfo object named loginInfoObj
	        IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
	        loginInfoObj.setUser(user);
	        loginInfoObj.setPassword(pass);
	        loginInfoObj.setDomain(null);

	        //bind the Session Manager to the login info
	        sMgr.setIdentity(docbase, loginInfoObj);*/
		//could also set identity for more than one Docbases:
		// sMgr.setIdentity( strDocbase2, loginInfoObj );
		//use the following to use the same loginInfObj for all Docbases (DFC 5.2 or later)
		// sMgr.setIdentity( * , loginInfoObj );
		return sMgr;
	}



	public void teste1234T() throws DfServiceException
	{
		boolean doCommit = true;
		String repositoryName = REPOSITORY_NAME;
		String userName = RepositoryConstants.USER_NAME;
		String userPassword = RepositoryConstants.USER_PASSWORD;
		String serviceModule = MODULE_NAME; 
		String contextRoot = DFS_SERVICE_URL;

		IDfClientX clientx = new DfClientX();
		try {
			IDfClient client = clientx.getLocalClient();
		} catch (DfException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IDfSessionManager sessionManager = null;
		IDfSession session =null;

		try {
			//create a Session Manager object
			sessionManager = DfcSessionManager.getSessionManager();

			//create an IDfLoginInfo object named loginInfoObj
			IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
			loginInfoObj.setUser(userName);
			loginInfoObj.setPassword(userPassword);
			loginInfoObj.setDomain(null);
			sessionManager.setIdentity(REPOSITORY_NAME, loginInfoObj);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sessionManager.beginTransaction();
		session = sessionManager.getSession(repositoryName);
		System.err.println("established DCTM session to " + repositoryName);

		try {

			IDfId idObj = clientx.getId("0902a94b80142ced");        
			IDfSysObject sysObj = (IDfSysObject) session.getObject(idObj);  
			sysObj.setString("title", "TESTE 1 v1");
			sysObj.save();
			System.out.println("Executed first update of title");

			sysObj.setString("tit_metadado", "TESTE 2 v1");
			sysObj.save();
			System.out.println("Executed second update of title");

			if (doCommit) {
				System.out.println("Commiting Transaction!");
				sessionManager.commitTransaction();
			} else {
				System.out.println("Aborting Transaction!");
				throw new Exception("Aborting on purpose!");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			sessionManager.abortTransaction();
		} finally {
			if (session != null && sessionManager != null)
				sessionManager.release(session);
		}
	}
	public void teste123T() throws DfServiceException
	{
		boolean doCommit = false;
		String repositoryName = REPOSITORY_NAME;
		String userName = RepositoryConstants.USER_NAME;
		String userPassword = RepositoryConstants.USER_PASSWORD;


		ContextFactory contextFactory = ContextFactory.getInstance();
		IServiceContext context = contextFactory.getContext();
		//		context.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_NOT_REQUIRED);
		//		context.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

		// object service, get either local or remote
		ServiceFactory serviceFactory = ServiceFactory.getInstance();

		IObjectService service = null;
		try {
			service = serviceFactory.getLocalService(IObjectService.class,	context);
		} catch (ServiceInvocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
		RepositoryIdentity repoId =new RepositoryIdentity();
		repoId.setRepositoryName(repositoryName);
		repoId.setUserName(userName);
		repoId.setPassword(userPassword);
		// Add the populated RepositoryIdentity to the ServiceContext
		context.addIdentity(repoId);


		IDfSessionManager sessionManager = null;
		IDfSession session =null;

		try {
			sessionManager = createSessionManager(repositoryName, userName, userPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sessionManager.beginTransaction();
		session = sessionManager.getSession(repositoryName);
		System.err.println("established DCTM session to " + repositoryName);

		try {


			ObjectId objId=new ObjectId("0902a94b80142ced");
			ObjectIdentity objIdentity =new ObjectIdentity(objId, REPOSITORY_NAME);

			DataObject dataObject =new DataObject(objIdentity);
			dataObject.setIdentity(objIdentity);
			dataObject.setType("dm_document");

			dataObject.getProperties().set("title", "TESTE 1 v1");
			DataPackage results = service.update(new DataPackage(dataObject), null);
			System.out.println("Executed first update of title");

			// UPDATE document
			dataObject.getProperties().set("tit_metadado", "TESTE 2 v1");
			results = service.update(new DataPackage(dataObject), null);
			System.out.println("Executed second update of title");

			if (doCommit) {
				System.out.println("Commiting Transaction!");
				sessionManager.commitTransaction();
			} else {
				System.out.println("Aborting Transaction!");
				throw new Exception("Aborting on purpose!");
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			sessionManager.abortTransaction();
		} finally {
			if (session != null && sessionManager != null)
				sessionManager.release(session);
		}
	}


	@Transactional(rollbackFor=ServiceException.class)
	public DataPackage testTransaction (boolean isRemote, OperationOptions options, int abortAt, String titleUpdate1, String titleUpdate2) throws Exception
	{
		System.out.println("testTransaction.begin");
		String repositoryName = REPOSITORY_NAME;
		String userName = RepositoryConstants.USER_NAME;
		String userPassword = RepositoryConstants.USER_PASSWORD;

		if (abortAt == 0)
			throw new ServiceException("Asked to abort at beginning");

		ContextFactory contextFactory = ContextFactory.getInstance();
		IServiceContext context = contextFactory.newContext();
		context.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
		context.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

		// object service, get either local or remote
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IObjectService service = null;
		if (isRemote)
		{
			service = (IObjectService) serviceFactory.getRemoteService(
					IObjectService.class,
					context,
					"core",
					"http://" +  "/services"
					);
		}
		else
		{
			service = serviceFactory.getLocalService(
					IObjectService.class,
					context
					);
		}

		// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
		RepositoryIdentity repoId =new RepositoryIdentity();
		repoId.setRepositoryName(repositoryName);
		repoId.setUserName(userName);
		repoId.setPassword(userPassword);
		// Add the populated RepositoryIdentity to the ServiceContext
		context.addIdentity(repoId);

		ObjectId objId=new ObjectId("0902a94b80142ced");
		ObjectIdentity objIdentity =new ObjectIdentity(objId, REPOSITORY_NAME);

		DataObject dataObject =new DataObject(objIdentity);
		dataObject.setIdentity(objIdentity);
		dataObject.setType("dm_document");

		dataObject.getProperties().set("title", titleUpdate1);
		DataPackage results = service.update(new DataPackage(dataObject), options);
		System.out.println("Executed first update of title");
		if (abortAt == 1)
			throw new CoreServiceException("Asked to abort after first update");
		// UPDATE document
		dataObject.getProperties().set("tit_metadado", titleUpdate2);
		results = service.update(new DataPackage(dataObject), options);
		System.out.println("Executed second update of title");
		if (abortAt == 2)
			throw new ServiceException("Asked to abort after second update");
		return results;
	}
}


/*
 IDfClient dfClient;
IDfSession session = null;
IDfSessionManager sessionManager = null;
DfClientX client = new DfClientX();

try {
//	dfClient = client.getLocalClient();
//    sessionManager = dfClient.newSessionManager();
//    IDfLoginInfo loginInfo = new DfLoginInfo(userName, userPassword);
//    sessionManager.setIdentity(repositoryName, loginInfo);
//    session = sessionManager.getSession(repositoryName);

    // some logic

	serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
	serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");


//	ContextFactory contextFactory = ContextFactory.getInstance();
	// Use the ContextFactory to create a ServiceContext for a service 
//	IServiceContext serviceContext = contextFactory.getContext();


//	// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
//	RepositoryIdentity repoId =new RepositoryIdentity();
//	repoId.setRepositoryName(repositoryName);
//	repoId.setUserName(userName);
//	repoId.setPassword(userPassword);
//	// Add the populated RepositoryIdentity to the ServiceContext
//	serviceContext.addIdentity(repoId);
	// Get an ObjectService from the ServiceFactory
	//IObjectService objectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class,serviceContext,serviceModule,contextRoot);
	IObjectService objectService = (IObjectService) ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

//	ContentTransferProfile contentTransferProfile = new ContentTransferProfile();
//    contentTransferProfile.setTransferMode(ContentTransferMode.MTOM);
//    contentTransferProfile.setGeolocation("Pleasanton");
//    serviceContext.setProfile(contentTransferProfile);

	ObjectId objId=new ObjectId("0902a94b80142cea");
	ObjectIdentity objIdentity =new ObjectIdentity(objId,repositoryName);

	DataObject dataObject =new DataObject(objIdentity);
	dataObject.setIdentity(objIdentity);
	dataObject.setType("dm_document");

	PropertySet properties =new PropertySet();
	//properties.set("object_name", document.getDocumentProperties().get("object_name"));
	properties.set("title", titulo1);
	dataObject.setProperties(properties);

	DataPackage dataPackage =new DataPackage();
	dataPackage.getDataObjects().add(dataObject);

	log.debug("Start 1st update operation on the Object Service.");
	dataPackage = objectService.update(dataPackage,null);
	log.debug("End 1st update operation on the Object Service.");
	if (abortAt == 1)
		throw new CoreServiceException("Asked to abort after first update");

	properties = new PropertySet();
	properties.set("title", titulo2);
	dataObject.setProperties(properties);

	dataPackage =new DataPackage();
	dataPackage.getDataObjects().add(dataObject);
	log.debug("Start 2nd update operation on the Object Service.");			
	dataPackage = objectService.update(dataPackage, null);		
	log.debug("End 2nd update operation on the Object Service.");

} finally {
	session.abortTrans();sessionManager.abortTransaction();
    if (session != null) {
        sessionManager.release(session);
        sessionManager.flushSessions();
    }
}

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

DfClientX client = new DfClientX();
IDfClient dfClient;
IDfSessionManager sessionManager = null;
IDfLoginInfo login = new DfLoginInfo();

// -------- Properties ---------------------------------------
String repositoryName = REPOSITORY_NAME;
String userName = RepositoryConstants.USER_NAME;
String userPassword = RepositoryConstants.USER_PASSWORD;
String contextRoot = DFS_SERVICE_URL;
String serviceModule = MODULE_NAME; 

login.setUser(userName);
login.setPassword(userPassword);

dfClient = client.getLocalClient();
sessionManager = dfClient.newSessionManager();
sessionManager.setIdentity(repositoryName, login);
sessionManager.authenticate(repositoryName);

try
{
	sessionManager.beginTransaction();
	// Get a ContextFactory so that we can create a ServiceContext
	ContextFactory contextFactory = ContextFactory.getInstance();
	// Use the ContextFactory to create a ServiceContext for a service 
	IServiceContext serviceContext = contextFactory.getContext();

//	serviceContext.setRuntimeProperty(IServiceContext.USER_TRANSACTION_HINT, IServiceContext.TRANSACTION_REQUIRED);
//	serviceContext.setRuntimeProperty(IServiceContext.PAYLOAD_PROCESSING_POLICY, "PAYLOAD_FAIL_ON_EXCEPTION");

	// Create a RepositoryIdentity to store the repository credentials on the ServiceContext
	RepositoryIdentity repoId =new RepositoryIdentity();
	repoId.setRepositoryName(repositoryName);
	repoId.setUserName(userName);
	repoId.setPassword(userPassword);
	// Add the populated RepositoryIdentity to the ServiceContext
	serviceContext.addIdentity(repoId);
	// Get an ObjectService from the ServiceFactory
	//IObjectService objectService = ServiceFactory.getInstance().getRemoteService(IObjectService.class,serviceContext,serviceModule,contextRoot);
	IObjectService objectService = ServiceFactory.getInstance().getLocalService(IObjectService.class, serviceContext);

	ObjectId objId=new ObjectId("0902a94b80142cea");
	ObjectIdentity objIdentity =new ObjectIdentity(objId,repositoryName);

	DataObject dataObject =new DataObject(objIdentity);
	dataObject.setIdentity(objIdentity);
	dataObject.setType("dm_document");

	PropertySet properties =new PropertySet();
	//properties.set("object_name", document.getDocumentProperties().get("object_name"));
	properties.set("title", titulo1);
	dataObject.setProperties(properties);

	DataPackage dataPackage =new DataPackage();
	dataPackage.getDataObjects().add(dataObject);

	log.debug("Start 1st update operation on the Object Service.");
	dataPackage = objectService.update(dataPackage,null);
	log.debug("End 1st update operation on the Object Service.");
	if (abortAt == 1)
		throw new CoreServiceException("Asked to abort after first update");

	properties = new PropertySet();
	properties.set("title2", titulo2);
	dataObject.setProperties(properties);

	dataPackage =new DataPackage();
	dataPackage.getDataObjects().add(dataObject);
	log.debug("Start 2nd update operation on the Object Service.");			
	dataPackage = objectService.update(dataPackage, null);		
	log.debug("End 2nd update operation on the Object Service.");

	sessionManager.commitTransaction();

//	if (abortAt == 2)
//		throw new CoreServiceException("Asked to abort after first update");
}
catch (Exception e) {
	log.debug("Exception - "+e.getMessage());
	sessionManager.abortTransaction();
	throw new ServiceException("Abort after second update");
}
finally{
	sessionManager.clearIdentities();
}
 */