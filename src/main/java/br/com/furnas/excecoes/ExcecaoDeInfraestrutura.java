package br.com.furnas.excecoes;

/***
 * ExcecaoDeInfraestrutura.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * Exceção arremessada pela camada de infraestrutura quando 
 * ocorrem problemas durante o processo de CRUD dos objetos de domínio.
 *  
 * @author PrimeUp
 * 
 * 25/11/2014
 */
public class ExcecaoDeInfraestrutura extends Exception {
	private static final long serialVersionUID = 7261066898790242061L;

	public ExcecaoDeInfraestrutura(String mensagemDeErro, Throwable t){
		super(mensagemDeErro, t);
	}
	
	public ExcecaoDeInfraestrutura(String mensagemDeErro){
		super(mensagemDeErro);
	}
}
