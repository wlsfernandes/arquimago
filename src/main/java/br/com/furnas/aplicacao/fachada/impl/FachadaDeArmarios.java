package br.com.furnas.aplicacao.fachada.impl;

import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDeArmarios;
import br.com.furnas.aplicacao.servicos.IServicoDeArmarios;

@Transactional
public class FachadaDeArmarios implements IFachadaDeArmarios  {

	private IServicoDeArmarios servicoDeArmarios;

	public IServicoDeArmarios getServicoDeArmarios() {
		return servicoDeArmarios;
	}

	public void setServicoDeArmarios(IServicoDeArmarios servicoDeArmarios) {
		this.servicoDeArmarios = servicoDeArmarios;
	}

		
}
