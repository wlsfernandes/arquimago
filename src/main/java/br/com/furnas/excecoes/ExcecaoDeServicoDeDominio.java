package br.com.furnas.excecoes;

/**
 * ExcecaoDeServicoDeDominio.java
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
public class ExcecaoDeServicoDeDominio extends Exception{
	private static final long serialVersionUID = 6783489276844112725L;

	public ExcecaoDeServicoDeDominio(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcecaoDeServicoDeDominio(String message) {
		super(message);
	}

	public ExcecaoDeServicoDeDominio(Throwable cause) {
		super(cause);
	}

}
