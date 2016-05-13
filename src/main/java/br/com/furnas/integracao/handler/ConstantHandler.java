package br.com.furnas.integracao.handler;

import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;

import br.com.furnas.integracao.annotations.ConstantKey;
import br.com.furnas.integracao.annotations.ConstantValue;

/**
 * Classe usada para criar objetos de constantes de interfaces anotadas via reflection.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public class ConstantHandler implements InvocationHandler {
	
	/**
	 * Interface atual enviada pela classe factory.
	 */
	Object obj;
	
	/**
	 * Construtor padrao
	 * @param obj
	 */
	public ConstantHandler(Object obj) {
		this.obj = obj;
	}
	
	/**
	 * Metodo padrao usado para executar criar objeto de constantes atraves de anotations ou properties.
	 * @param proxy - objeto usado na invocacao do servico
	 * @param method - representa o proprio metodo que esta sendo executado
	 * @param params - parametros que foram passados na execucao do metodo
	 */
	public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
		
		PropertyLoader PropertyLoader = new PropertyLoader();
		
		String key = null;
		
		String defaultValue = null;
		
		try {

			Annotation[] annotations = method.getAnnotations();
			
			for (Annotation annotation : annotations) {
				
				if (annotation instanceof ConstantKey) {
					
					key = ((ConstantKey) annotation).value();
					
				} else if (annotation instanceof ConstantValue) {
					
					defaultValue = ((ConstantValue) annotation).value();
					
				}
				
			}
			
			
			return defaultValue;
			//TODO: Resolver problema de leitura de arquivo
			//String ret = PropertyLoader.get(key);
			
			//return (null == ret) ? defaultValue : ret;

		} catch (Exception e) {

			throw e;
		}
	}
}