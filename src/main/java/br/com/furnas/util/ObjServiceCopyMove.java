package br.com.furnas.util;

import java.io.File;

import com.emc.documentum.fs.datamodel.core.content.ContentTransferMode;
import com.emc.documentum.fs.datamodel.core.content.FileContent;
import com.emc.documentum.fs.datamodel.core.profiles.ContentTransferProfile;
import com.emc.documentum.fs.datamodel.core.properties.PropertySet;
import com.emc.documentum.fs.datamodel.core.*;
import com.emc.documentum.fs.rt.ServiceException;
import com.emc.documentum.fs.rt.ServiceInvocationException;
import com.emc.documentum.fs.rt.context.ServiceFactory;
import com.emc.documentum.fs.services.core.client.IObjectService;

/*
 * Demonstrates move and copy object service operations
 * */
public class ObjServiceCopyMove
{
	
	String defaultRepositoryName="";
	/*
	public ObjServiceCopyMove()
	{
		super();
	}

	public ObjServiceCopyMove(String repositoryName, String userName, String password, boolean remoteMode)	throws ServiceInvocationException
	{
		super(repositoryName, userName, password, remoteMode);
	}

	public ObjServiceCopyMove(String repositoryName, String userName, String password, String secondaryRepositoryName, boolean remoteMode)	throws ServiceInvocationException
	{
		super(repositoryName, userName, password, 
				secondaryRepositoryName, remoteMode);
	}


	public void objServiceCopy(String sourceObjectPathString, String targetLocPathString) throws ServiceException
	{
		// identify the object to copy
		ObjectPath objPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> docToCopy = new ObjectIdentity<ObjectPath>();
		docToCopy.setValue(objPath);
		docToCopy.setRepositoryName(defaultRepositoryName);

		// identify the folder to copy to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);
		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(defaultRepositoryName);
		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		objectService.copy(new ObjectIdentitySet(docToCopy), toLocation, new DataPackage(), operationOptions);
	}

	public void objServiceMove(String sourceObjectPathString, String targetLocPathString, String sourceLocPathString) throws ServiceException {
		// identify the object to move
		ObjectPath objPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> docToCopy = new ObjectIdentity<ObjectPath>();
		docToCopy.setValue(objPath);
		docToCopy.setRepositoryName(defaultRepositoryName);

		// identify the folder to move from
		ObjectPath fromFolderPath = new ObjectPath();
		fromFolderPath.setPath(sourceLocPathString);
		ObjectIdentity<ObjectPath> fromFolderIdentity = new ObjectIdentity<ObjectPath>();
		fromFolderIdentity.setValue(fromFolderPath);
		fromFolderIdentity.setRepositoryName(defaultRepositoryName);
		ObjectLocation fromLocation = new ObjectLocation();
		fromLocation.setObjectIdentity(fromFolderIdentity);

		// identify the folder to move to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);
		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(defaultRepositoryName);
		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		objectService.move(new ObjectIdentitySet(docToCopy),
				fromLocation,
				toLocation,
				new DataPackage(),
				operationOptions);
					}

	public void objServiceCopyAcrossRepositories(String sourceObjectPathString,	String targetLocPathString)	throws ServiceException	{
		// identify the object to copy
		ObjectPath objPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> docToCopy = new ObjectIdentity<ObjectPath>();
		docToCopy.setValue(objPath);
		docToCopy.setRepositoryName(defaultRepositoryName);

		// identify the folder to copy to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);
		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(secondaryRepositoryName);
		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		OperationOptions operationOptions = null;
		objectService.copy(new ObjectIdentitySet(docToCopy), toLocation, null, operationOptions);
	}

	public void objServiceCopyWithMods(String sourceObjectPathString,
			String targetLocPathString)
					throws ServiceException
					{
		// identify the object to copy
		ObjectPath objPath = new ObjectPath(sourceObjectPathString);
		ObjectIdentity<ObjectPath> docToCopy = new ObjectIdentity<ObjectPath>();
		docToCopy.setValue(objPath);
		docToCopy.setRepositoryName(defaultRepositoryName);

		// identify the folder to copy to
		ObjectPath folderPath = new ObjectPath();
		folderPath.setPath(targetLocPathString);
		ObjectIdentity<ObjectPath> toFolderIdentity = new ObjectIdentity<ObjectPath>();
		toFolderIdentity.setValue(folderPath);
		toFolderIdentity.setRepositoryName(defaultRepositoryName);
		ObjectLocation toLocation = new ObjectLocation();
		toLocation.setObjectIdentity(toFolderIdentity);

		// specify changes to make when copying
		DataObject modDataObject = new DataObject(docToCopy);
		modDataObject.setType("dm_document");
		PropertySet modProperties = modDataObject.getProperties();
		modProperties.set("object_name", "copiedDocument-" + System.currentTimeMillis());
		DataPackage dataPackage = new DataPackage(modDataObject);

		ObjectIdentitySet objIdSet = new ObjectIdentitySet();
		objIdSet.getIdentities().add(docToCopy);
		OperationOptions operationOptions = null;
		objectService.copy(objIdSet, toLocation, dataPackage, operationOptions);
					}
	public DataPackage createContentlessDocument()
			throws ServiceException
			{
		ObjectIdentity objectIdentity = new ObjectIdentity(defaultRepositoryName);
		DataObject dataObject = new DataObject(objectIdentity, "dm_document");
		PropertySet properties = new PropertySet();
		properties.set("object_name", "contentless-" + System.currentTimeMillis());
		dataObject.setProperties(properties);
		DataPackage dataPackage = new DataPackage(dataObject);

		OperationOptions operationOptions = null;
		return objectService.create(dataPackage, operationOptions);
			}


	public DataPackage createWithContentDefaultContext(String filePath)
			throws ServiceException
			{
		File testFile = new File(filePath);

		if (!testFile.exists())
		{
			throw new RuntimeException("Test file: " + testFile.toString() + " does not exist");
		}

		ObjectIdentity objIdentity = new ObjectIdentity(defaultRepositoryName);
		DataObject dataObject = new DataObject(objIdentity, "dm_document");
		PropertySet properties = dataObject.getProperties();
		properties.set("object_name", "MyImage");
		properties.set("title", "MyImage");
		properties.set("a_content_type", "gif");
		dataObject.getContents().add(new FileContent(testFile.getAbsolutePath(), "gif"));

		OperationOptions operationOptions = null;
		return objectService.create(new DataPackage(dataObject), operationOptions);
			}


	public DataPackage createNewFolder()
			throws ServiceException
			{
		ObjectIdentity folderIdentity = new ObjectIdentity();
		folderIdentity.setRepositoryName(defaultRepositoryName);
		DataObject dataObject = new DataObject(folderIdentity, "dm_folder");
		PropertySet properties = new PropertySet();
		String folderName = "aTestFolder-" + System.currentTimeMillis();
		properties.set("object_name", folderName);
		dataObject.setProperties(properties);

		DataPackage dataPackage = new DataPackage(dataObject);

		OperationOptions operationOptions = null;
		return objectService.create(dataPackage, operationOptions);
			}


	public DataPackage createFolderAndLinkedDoc() throws ServiceException
	{
		// create a folder data object
		String folderName = "0test-folder-" + System.currentTimeMillis();
		DataObject folderDataObj = new DataObject(new ObjectIdentity(defaultRepositoryName), "dm_folder");
		PropertySet folderDataObjProperties = new PropertySet();
		folderDataObjProperties.set("object_name", folderName);
		folderDataObj.setProperties(folderDataObjProperties);

		// create a contentless document DataObject
		String doc1Name = "aTestDoc-" + System.currentTimeMillis();
		DataObject docDataObj = new DataObject(new ObjectIdentity(defaultRepositoryName), "dm_document");
		PropertySet properties = new PropertySet();
		properties.set("object_name", doc1Name);
		docDataObj.setProperties(properties);

		// add the folder as a parent of the folder
		ObjectRelationship objRelationship = new ObjectRelationship();
		objRelationship.setTarget(folderDataObj);
		objRelationship.setName(Relationship.RELATIONSHIP_FOLDER);
		objRelationship.setTargetRole(Relationship.ROLE_PARENT);
		docDataObj.getRelationships().add(new ObjectRelationship(objRelationship));

		// create the folder and linked document
		DataPackage dataPackage = new DataPackage();
		dataPackage.addDataObject(docDataObj);
		OperationOptions operationOptions = null;
		return objectService.create(dataPackage, operationOptions);
	}

	public DataObject createAndLinkToFolder(String folderPath)
	{
		// create a contentless document to link into folder
		String objectName = "linkedDocument" + System.currentTimeMillis();
		String repositoryName = defaultRepositoryName;
		ObjectIdentity sampleObjId = new ObjectIdentity(repositoryName);
		DataObject sampleDataObject = new DataObject(sampleObjId, "dm_document");
		sampleDataObject.getProperties().set("object_name", objectName);

		// add the folder to link to as a ReferenceRelationship
		ObjectPath objectPath = new ObjectPath(folderPath);
		ObjectIdentity<ObjectPath> sampleFolderIdentity = new ObjectIdentity<ObjectPath>(objectPath, defaultRepositoryName);
		ReferenceRelationship sampleFolderRelationship = new ReferenceRelationship();
		sampleFolderRelationship.setName(Relationship.RELATIONSHIP_FOLDER);
		sampleFolderRelationship.setTarget(sampleFolderIdentity);
		sampleFolderRelationship.setTargetRole(Relationship.ROLE_PARENT);
		sampleDataObject.getRelationships().add(sampleFolderRelationship);

		// create a new document linked into parent folder
		try
		{
			OperationOptions operationOptions = null;
			DataPackage dataPackage = new DataPackage(sampleDataObject);
			objectService.create(dataPackage, operationOptions);
		}
		catch (ServiceException e)
		{
			throw new RuntimeException(e);
		}

		return sampleDataObject;
	}
	*/
}
