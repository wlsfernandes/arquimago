package br.com.furnas.excecoes;

/**
 * ExcecaoDeFachadaDeAplicacao.java
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
public class ExcecaoDeFachadaDeAplicacao extends Exception{
	private static final long serialVersionUID = -3882077011004814453L;

	public ExcecaoDeFachadaDeAplicacao(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcecaoDeFachadaDeAplicacao(String message) {
		super(message);
	}

	public ExcecaoDeFachadaDeAplicacao(Throwable cause) {
		super(cause);
	}	
}
