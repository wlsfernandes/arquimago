package br.com.furnas.integracao.constants;

/**
 * Representa os paramentros de configuracao que sera usados para conectar no repositorio do EMC (estatico).
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public final class RepositoryConstants {
	/**
	 * Construtor.
	 */
	private RepositoryConstants() {}
	
	/**
	 * REPOSITORY_NAME.
	 */
	public static String REPOSITORY_NAME = "ged_supr_dev";
	
	/**
	 * USER_NAME.
	 */
	public static  String USER_NAME = "adminged";
	
	/**
	 * USER_PASSWORD.
	 */
	public static String USER_PASSWORD = "furnasged01";
	
	/**
	 * DEFAULT_PATH.
	 */
	public static final String DEFAULT_PATH = "";
	
	/**
	 * DSF_SERVICE_URL.
	 */
	public static final String DFS_SERVICE_URL = "http://localhost:8080/emc-dfs/services";
	
	/**
	 * MODULE_NAME.
	 */
	public static final String MODULE_NAME = "core"; 
	
}
