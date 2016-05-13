package br.com.furnas.dados.relacional.dfs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * IRepositorioGenericoDados.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * Representa o repositório genérico.
 * 
 * @param <T> Tipo da classe.
 * @param <TipoId> Tipo do id da classe.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 */
public interface IRepositorioGenericoDados {
	
	/**
	 * Persiste uma entidade no banco de dados.
	 * 
	 * @param entidade a ser persistida.
	 */
	void salvar(String sql);

	/**
	 * Exclui uma entidade do banco de dados.
	 * 
	 * @param entidade a ser excluida.
	 */
	void excluir(String sql);

	/**
	 * Altera uma entidade já existente do banco de dados.
	 * 
	 * @param entidade a ser alterada.
	 */
	void alterar(String sql);

	/**
	 * Obtém todas as entidades persistidas no banco de dados.
	 * 
	 * @return Uma lista com todas as entidades da classe no banco de dados.
	 */
	List<Map<Object, Object>> obterTodos(String sql);
	
		
}
