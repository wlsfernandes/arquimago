package br.com.furnas.integracao.constants.impl;

import br.com.furnas.integracao.annotations.ConstantKey;
import br.com.furnas.integracao.annotations.ConstantValue;
import br.com.furnas.integracao.constants.Constants;

/**
 * Representa os paramentros de configuracao que sera usados para conectar no repositorio do EMC.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public interface RepositoryIdentityConstants extends Constants {
	
	   @ConstantValue("DOCUMENTUM")
	   @ConstantKey("repositoryName")
	   String repositoryName();

	   @ConstantValue("diego.csilva")
	   @ConstantKey("userName")
	   String userName();
	   
	   @ConstantValue("montreal")
	   @ConstantKey("userPassword")
	   String userPassword();
	   
	   @ConstantValue("")
	   @ConstantKey("defaultPath")
	   String defaultPath();
	   
	 //  @ConstantValue("http://10.1.1.140:8080/emc-dfs/services")
	   @ConstantValue("http://localhost:8080/emc-dfs/service")
	   @ConstantKey("dfsServiceUrl")
	   String dfsServiceUrl();
	   
	   @ConstantValue("core")
	   @ConstantKey("moduleName")
	   String moduleName();	   
}
