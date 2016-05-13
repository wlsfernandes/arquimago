package br.com.furnas.dominio.modelo.construtores.impl;

import br.com.furnas.dados.relacional.dfs.IRepositorioDePastas;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDePastas;

public class ConstrutorDePastas extends EntidadeBase implements IConstrutorDePastas {

	private IRepositorioDePastas repositorioPastas;

	public IRepositorioDePastas getRepositorioPastas() {
		return repositorioPastas;
	}

	public void setRepositorioPastas(IRepositorioDePastas repositorioPastas) {
		this.repositorioPastas = repositorioPastas;
	}

}