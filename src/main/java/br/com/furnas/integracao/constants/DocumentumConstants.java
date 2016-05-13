package br.com.furnas.integracao.constants;

/**
 * Constantes usadas pelo servico do documentum.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public final class DocumentumConstants {
	
	/**
	 * contrutor privado para evitar instancia.
	 */
	private DocumentumConstants(){}
	
	/**
	 * select.
	 */
	public static final String SELECT = " select ";		
	
	/**
	 * from.
	 */
	public static final String FROM = " from ";		
	
	/**
	 * update.
	 */
	public static final String UPDATE = " update ";
	
	/**
	 * delete.
	 */
	public static final String DELETE = " delete ";		

	/**
	 * date.
	 */
	public static final String DATE = " date ";
	
	/**
	 * parentesis left.
	 */
	public static final String PARENTESIS_LEFT = " ( ";
	
	/**
	 * parentesis right.
	 */
	public static final String PARENTESIS_RIGHT = " ) ";	
	
	/**
	 * objects.
	 */
	public static final String OBJECTS = " objects ";	
	
	/**
	 * objects.
	 */
	public static final String SET = " set ";
	
	/**
	 * truncate.
	 */
	public static final String TRUNCATE = " truncate ";		
	
	/**
	 * comma.
	 */
	public static final String COMMA = " , ";	
	
	/**
	 * all.
	 */
	public static final String ALL = "*";		
	
	/**
	 * all.
	 */
	public static final String EMPTY = "";		
	
	/**
	 * eq quote.
	 */
	public static final String EQ_QUOTE = "='";	
	
	/**
	 * bar folder.
	 */
	public static final String BAR_FOLDER = "/";	
	
	/**
	 * quote.
	 */
	public static final String QUOTE = "'";
	
	/**
	 * eq.
	 */
	public static final String EQ = "=";	
	
	/**
	 * space.
	 */
	public static final String SPACE = " ";
	
	/**
	 * where.
	 */
	public static final String WHERE = " where ";
	
	/**
	 * or.
	 */
	public static final String OR = " or ";
	
	/**
	 * union.
	 */
	public static final String UNION = " union ";

	/**
	 * and.
	 */
	public static final String AND = " and ";
	
	/**
	 * any.
	 */
	public static final String ANY = " any ";
	
	/**
	 * underscore.
	 */
	public static final String UNDERSCORE = "_";
	
	/**
	 * act.
	 */
	public static final String ACT = "ACT";		
	
	/**
	 * p.
	 */
	public static final String P = "P";		
	
	/**
	 * bp.
	 */
	public static final String BP = "BP";		
	
	/**
	 * dma.
	 */
	public static final String DMA = "DMA";	
	
	/**
	 * ij.
	 */
	public static final String IJ = "IJ";		
	
	
	/**
	 * cdb.
	 */
	public static final String CDB = "CDB";		
	
	/**
	 * title.
	 */
	public static final String TITLE = "title";	
	
	/**
	 * a_content_type.
	 */
	public static final String A_CONTENT_TYPE = "a_content_type";	
	
	/**
	 * pdf.
	 */
	public static final String PDF = "pdf";	
	
	/**
	 * WORD.
	 */
	public static final String WORD = "msw12";
	
	/**
	 * TXT.
	 */
	public static final String TXT = "crtext";
	
	
	/**
	 * dm_document.
	 */
	public static final String DM_DOCUMENT = "dm_document";
	
	/**
	 * dm_folder.
	 */
	public static final String DM_FOLDER = "dm_folder";
	
	/**
	 * object_name.
	 */
	public static final String OBJECT_NAME = "object_name";
	
	/**
	 * r_object_id.
	 */
	public static final String OBJECT_ID = "r_object_id";
	
	/**
	 * r_object_type.
	 */
	public static final String OBJECT_TYPE = "r_object_type";
	
	/**
	 * cabinet - especificado como pasta nivel 1
	 */
	public static final String CABINET = "SAC4";
	
	/**
	 * chave arquivo.
	 */
	public static final String CHAVE_ARQUIVO = "arquivo";	
	
	/**
	 * chave nome arquivo.
	 */
	public static final String CHAVE_NOME_ARQUIVO = "nome";	
	
	/**
	 * chave metadados.
	 */
	public static final String CHAVE_METADADOS = "metadados";	
	
	/**
	 * chave tipo documental.
	 */
	public static final String CHAVE_TIPO_DOCUMENTAL = "r_object_type";	
	
	/**
	 * dql padrao.
	 */
	public static final String DQL_PADRAO_ID_TYPE_NAME = " select r_object_id, object_name, r_object_type from %s ";
	
	/**
	 * dql select folder.
	 */
	public static final String DQL_SELECT_FOLDER = " select * from dm_folder where any r_folder_path = '%s'";
	
	/**
	 * dql create folder.
	 */
	public static final String DQL_CREATE_FOLDER = " create dm_folder object set object_name='%1s' LINK '%2s' ";
	/**
	 * dql procura dados dentro de um arquivo dentro de um cabinet especifico.
	 */
	public static final String DQL_FIND_METADATA_DOCUMENT = "SELECT r_object_id FROM dm_document SEARCH DOCUMENT CONTAINS '%1s' WHERE folder('%2s') ENABLE(FTDQL);";
	
}
