package br.com.furnas.integracao.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.furnas.integracao.constants.impl.RepositoryIdentityConstants;
import static br.com.furnas.integracao.constants.LoggerConstants.ERROR_PROP;

/**
 * Carrega as constantes via reflection para conexao com o EMC e sobrescreve o 
 * valor caso exista uma propriedade com o mesmo nome.
 * 
 * @author Diego Costa - diego.csilva@montreal.com.br
 */
public class PropertyLoader {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(PropertyLoader.class);
	/**
	 * Propriedade carregada ao chamar metodo get(key).
	 */
	private static Properties prop = null;
	
	/**
	 * Usado para carregar as propriedades.
	 */
	private HashMap<String, String> fileProperties = new HashMap<String, String>();
	
	/**
	 * Construtor.
	 */
	public PropertyLoader(){
		
		fileProperties.put(RepositoryIdentityConstants.class.getName(), "/resources/config.properties");
		
	}
	
	/**
	 * Usada na classe ConstantHandler para buscar por determinada propriedade 
	 * seja ela no arquivo de configura��o ou em uma interface anotada.
	 * 
	 * @param key - nome da propriedade 
	 * @return String - valor da propriedade
	 */
	public String get(String key) {
		
		if (null == prop) {
			
			init();
			
		}
		
		return (String) prop.get(key);
		
	}
	
	/**
	 * Carrega os valores apartir do arquivo de configuracao se necessario. 
	 */
	private void init() {
		
		prop = new Properties();
		
		InputStream in = Properties.class.getResourceAsStream(fileProperties.get(RepositoryIdentityConstants.class.getName()));
		
		try {
			
			prop.load(in);
			
		} catch (IOException e) {
			
			logger.error(ERROR_PROP.concat(e.getMessage()));
			
		}

	}
}
