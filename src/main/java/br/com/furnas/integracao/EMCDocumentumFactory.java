package br.com.furnas.integracao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

import br.com.furnas.integracao.constants.Constants;
import br.com.furnas.integracao.handler.ConstantHandler;
import br.com.furnas.integracao.handler.ServiceHandler;

/**
 * Utilitario para contruir instancias de classes de 
 * servico e intercaces com constantes atraves de reflection.
 *  
 * @author Diego Costa - diego.csilva@montreal.com.br
 */
public final class EMCDocumentumFactory {
	/**
	 * Cria um novo proxy para interface que armazena constantes.
	 * @param cons - Interface que contem as anotacoes para constantes.
	 * @return Constants
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Constants> T getConstants(Class<T> cons) {
		
		
		T object = (T) Proxy.newProxyInstance(cons
				.getClassLoader(), new Class[] { cons },
				new ConstantHandler(cons));  
		
		return object;
		
	}
	
	/**
	 * Cria um novo proxy para classe que possui os servicos.
	 * @param clazz - Classe que sera criada pelo proxy.
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getService(Object obj) {
		
		return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				new Class<?>[] { (Class<T>) obj.getClass().getInterfaces()[0] }, new ServiceHandler(obj));
	}	
	
}
