package br.com.furnas.integracao.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Representa chave da classe de configuracao. 
 * @author Diego Costa - diego.csilva@montreal.com.br 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConstantKey {
	public String value();
}
