package br.com.furnas.log;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Log.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * Classe que representa o log da aplicação.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 *
 */
public class Log {

	private static Logger log = LogManager.getLogger(Log.class);

	public static void debug(Object message, Throwable t) {
		log.debug(message, t);
	}

	public static void debug(Object message) {
		log.debug(message);
	}

	public static void error(String classe, String metodo, Object chaveDoErro, Throwable t) {
		log.error(mensagemFormatada(classe, metodo, chaveDoErro), t);
	}

	private static Object mensagemFormatada(String classe, String metodo, Object chaveDoErro) {
		StringBuilder retorno = new StringBuilder("Erro na Classe: ")
				.append(classe)
				.append("\t Método: ")
				.append(metodo)
				.append("\t Chave do erro: ")
				.append(chaveDoErro);

		return retorno.toString();
	}

	public static void error(Object message, Throwable t) {
		log.error(message, t);
	}

	public static void error(Object message) {
		log.error(message);
	}

	public static void fatal(Object message, Throwable t) {
		log.fatal(message, t);
	}

	public static void fatal(Object message) {
		log.fatal(message);
	}

	public static void info(Object message, Throwable t) {
		log.info(message, t);
	}

	public static void info(Object message) {
		log.info(message);
	}

	public static void trace(Object message, Throwable t) {
		log.trace(message, t);
	}

	public static void trace(Object message) {
		log.trace(message);
	}

	public static void warn(Object message, Throwable t) {
		log.warn(message, t);
	}

	public static void warn(Object message) {
		log.warn(message);
	}

}
