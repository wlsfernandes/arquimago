package br.com.furnas.integracao.handler;

import static br.com.furnas.integracao.constants.LoggerConstants.INFO_FINALIZACAO_OPERACAO;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_INICIANDO_OPERACAO;
import static br.com.furnas.integracao.constants.LoggerConstants.INFO_PARAMETROS;

import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe usada para executar metodos das classes de servico atraves de Reflection
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public class ServiceHandler implements InvocationHandler {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(ServiceHandler.class);
	
	/**
	 * Instancia atual do servico enviada pela classe factory.
	 */
	private Object obj;
	
	/**
	 * Construtor
	 * @param obj
	 */
	public ServiceHandler(Object obj) {
		this.obj = obj;
	}
	
	/**
	 * Metodo padrao usado para executar servicos por reflection
	 * @param proxy - objeto usado na invocacao do servico
	 * @param method - representa o proprio metodo que esta sendo executado
	 * @param params - parametros que foram passados na execucao do metodo
	 */
	public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
		
		Object result;
		
		try {
			
			logger.info(INFO_INICIANDO_OPERACAO);
			
			logger.info(INFO_PARAMETROS.concat(Arrays.toString(params)));
			
			result = method.invoke(obj, params);
			
			logger.info(INFO_FINALIZACAO_OPERACAO);
			
		} catch (InvocationTargetException e) {
			
			throw e;
			
		} catch (Exception e) {
			
			throw e;
			
		}
		
		return result;
	}
}