package br.com.furnas.aplicacao.servicos.impl;

import br.com.furnas.aplicacao.servicos.IServicoDeArmarios;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDeArmarios;


public class ServicoDeArmarios implements IServicoDeArmarios {
	
	private ConstrutorDeArmarios construtorDeArmarios;

	public ConstrutorDeArmarios getConstrutorDeArmarios() {
		return construtorDeArmarios;
	}

	public void setConstrutorDeArmarios(ConstrutorDeArmarios construtorDeArmarios) {
		this.construtorDeArmarios = construtorDeArmarios;
	}
		
}
