package br.com.furnas.excecoes;

/**
 * ExcecaoDeModeloDeDominio.java
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
public class ExcecaoDeModeloDeDominio extends Exception{
	private static final long serialVersionUID = -5630212981789446740L;
	
	public ExcecaoDeModeloDeDominio(String mensagem){
		super(mensagem);
	}
	
	public ExcecaoDeModeloDeDominio(String mensagem, Throwable t){
		super(mensagem, t);
	}

	public ExcecaoDeModeloDeDominio(Throwable cause) {
		super(cause);
	}
	
}
