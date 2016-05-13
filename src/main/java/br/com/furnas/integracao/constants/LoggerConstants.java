package br.com.furnas.integracao.constants;

/**
 * Classe que armazena as mensagens de log.
 * @author Diego Costa - diego.csilva@montreal.com.br
 */
public final class LoggerConstants {
	
	/**
	 * contrutor privado para evitar instancia.
	 */
	private LoggerConstants(){}
	
	/**
	 * Mensagem iniciando EMC.
	 */
	public static final String INFO_INICIANDO_EMC = "Iniciando conexao com o EMC.";
	
	/**
	 * Mensagem conexao EMC realizada com sucesso.
	 */
	public static final String INFO_CONEXAO_EMC = "Conexao com repositorio EMC foi realizada com sucesso.";
	
	/**
	 * Mensagem conexao EMC realizada com sucesso.
	 */
	public static final String ERROR_MISSING_KEY_MAP = "Erro na utiliza��o do m�todo, faltando a chave: %s ";	

	/**
	 * Mensagem de erro propriedade.
	 */
	public static final String ERROR_PROP = "Ocorreu um erro ao tentar recuperar uma propriedade de configuracao : ";	
	
	/**
	 * Mensagem de erro na conexao EMC.
	 */
	public static final String ERROR_CONEXAO_EMC = "Ocorreu um erro ao tentar iniciar conexao com EMC : ";
	
	/**
	 * Mensagem de erro vinda do repositorio EMC.
	 */
	public static final String ERROR_REPOSITORIO_EMC = "O repositorio do EMC disparou o seguinte erro : ";
	
	/**
	 * Mensagem de erro vinda do servico EMC.
	 */
	public static final String ERROR_SERVICO_EMC = "O servi�o do EMC disparou o seguinte erro : ";
	
	/**
	 * Mensagem de inicio da operacao no EMC.
	 */
	public static final String INFO_INICIANDO_OPERACAO = "Iniciando opera��o no EMC";	
	
	/**
	 * Mensagem de inicio da operacao no EMC.
	 */
	public static final String INFO_INICIACAO_OPERACAO = "Iniciando opera��o no EMC, aguarde...";	
	
	/**
	 * Mensagem de fim da operacao no EMC.
	 */
	public static final String INFO_FINALIZACAO_OPERACAO = "Opera��o no EMC finalizada";	
	
	/**
	 * Mensagem parametros de inicializacao com EMC.
	 */
	public static final String INFO_PARAMETROS = "Enviando parametros : ";	
	
	/**
	 * Mensagem do metodo formas de pagamento.
	 */
	public static final String INFO_FORMAS_DE_PAGAMENTO = "Iniciando grava��o do documento FORMAS DE PAGAMENTO";	

	/**
	 * Mensagem do metodo classes comerciais.
	 */
	public static final String INFO_CONTRATO_SOCIAL_ESTATUTO = "Iniciando grava��o do documento CONTRATO SOCIAL / ESTATUTO";	
	
	/**
	 * Mensagem do metodo classes comerciais.
	 */
	public static final String INFO_CLASSES_COMERCIAIS = "Iniciando grava��o do documento CLASSES COMERCIAIS";
	
	/**
	 * Mensagem do metodo procura��o.
	 */
	public static final String INFO_PROCURACAO = "Iniciando grava��o do documento PROCURA��O";

	/**
	 * Mensagem do metodo documente generico.
	 */
	public static final String INFO_DOC_GENERICO = "Iniciando grava��o do documento GENERICO";
	
	/**
	 * Mensagem do metodo BALAN�O PATRIMONIAL.
	 */
	public static final String INFO_BALANCO_PATRIMONIAL = "Iniciando grava��o do documento BALAN�O PATRIMONIAL";	
	
	/**
	 * Mensagem de crindo pasta EMC.
	 */
	public static final String INFO_CREATE_FOLDER = "Criando nova pasta no EMC com o nome : ";	

	/**
	 * Mensagem de criando metadado.
	 */
	public static final String INFO_CREATE_METADATA = "Inserindo metadado : ";	
	
	/**
	 * Mensagem de finalizando gravacao.
	 */
	public static final String INFO_CREATED_FILE_EMC = "Finalizando grava��o do documento : ";		
	
	/**
	 * Mensagem query dql.
	 */
	public static final String INFO_DQL_QUERY = "DQL query executada : ";		
	
	/**
	 * Mensagem cache strategy.
	 */
	public static final String INFO_CACHE_STRATEGY = "Estrat�gia de cache : ";		
	
	/**
	 * Mensagem numero de itens.
	 */
	public static final String INFO_NUMBER_OF_ITENS = "Total de documentos retornados : ";		
	
	/**
	 * Mensagem erro ao criar pasta no Documentum.
	 */
	public static final String ERROR_CREATE_FOLDER = "Erro ao criar pasta : '%1s' no caminho '%2s' no Documentum EMC";		

}
