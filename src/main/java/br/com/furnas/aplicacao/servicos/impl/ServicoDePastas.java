package br.com.furnas.aplicacao.servicos.impl;

import br.com.furnas.aplicacao.servicos.IServicoDePastas;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDePastas;


public class ServicoDePastas implements IServicoDePastas {
	
	private ConstrutorDePastas construtorDePastas;

	public ConstrutorDePastas getConstrutorDePastas() {
		return construtorDePastas;
	}

	public void setConstrutorDePastas(ConstrutorDePastas construtorDePastas) {
		this.construtorDePastas = construtorDePastas;
	}
		
}
