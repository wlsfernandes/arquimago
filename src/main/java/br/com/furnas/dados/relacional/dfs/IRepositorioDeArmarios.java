package br.com.furnas.dados.relacional.dfs;

import java.util.List;

import br.com.furnas.dominio.modelo.Armarios;


public interface IRepositorioDeArmarios {

	List<Armarios> buscaArmarios();
	
}
