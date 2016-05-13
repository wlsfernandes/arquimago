package br.com.furnas.dominio.modelo.construtores.impl;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeArquivos;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDeArquivos;

public class ConstrutorDeArquivos extends EntidadeBase implements IConstrutorDeArquivos {

	private IRepositorioDeArquivos repositorioArquivos;

	public IRepositorioDeArquivos getRepositorioArquivos() {
		return repositorioArquivos;
	}

	public void setRepositorioArquivos(IRepositorioDeArquivos repositorioArquivos) {
		this.repositorioArquivos = repositorioArquivos;
	}

}