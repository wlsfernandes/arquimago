package br.com.furnas.aplicacao.servicos.impl;

import br.com.furnas.aplicacao.servicos.IServicoDeArquivos;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDeArquivos;


public class ServicoDeArquivos implements IServicoDeArquivos {
	
	private ConstrutorDeArquivos construtorDeArquivos;

	public ConstrutorDeArquivos getConstrutorDeArquivos() {
		return construtorDeArquivos;
	}

	public void setConstrutorDeArquivos(ConstrutorDeArquivos construtorDeArquivos) {
		this.construtorDeArquivos = construtorDeArquivos;
	}
		
}
