package br.com.furnas.dominio.modelo.construtores.impl;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeArmarios;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDeArmarios;

public class ConstrutorDeArmarios extends EntidadeBase implements IConstrutorDeArmarios {

	private IRepositorioDeArmarios repositorioArmarios;

	public IRepositorioDeArmarios getRepositorioArmarios() {
		return repositorioArmarios;
	}

	public void setRepositorioArmarios(IRepositorioDeArmarios repositorioArmarios) {
		this.repositorioArmarios = repositorioArmarios;
	}

}