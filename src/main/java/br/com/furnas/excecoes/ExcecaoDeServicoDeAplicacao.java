package br.com.furnas.excecoes;

/**
 * ExcecaoDeServicoDeAplicacao.java
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
public class ExcecaoDeServicoDeAplicacao extends Exception{
	private static final long serialVersionUID = 6222214271835186436L;

	public ExcecaoDeServicoDeAplicacao(String message) {
		super(message);
	}

	public ExcecaoDeServicoDeAplicacao(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcecaoDeServicoDeAplicacao(Throwable cause) {
		super(cause);
	}

}
