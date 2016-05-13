package br.com.furnas.excecoes;

/**
 * ExcecaoDeDadosRelacional.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 *
 */
public class ExcecaoDeDadosRelacional extends Exception{
	private static final long serialVersionUID = -5470795881369751117L;

	public ExcecaoDeDadosRelacional(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcecaoDeDadosRelacional(String message) {
		super(message);
	}

	public ExcecaoDeDadosRelacional(Throwable cause) {
		super(cause);
	}

}
