package br.com.furnas.aplicacao.fachada.impl;

import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDePastas;
import br.com.furnas.aplicacao.servicos.IServicoDePastas;

@Transactional
public class FachadaDePastas implements IFachadaDePastas {

	private IServicoDePastas servicoDePastas;

	public IServicoDePastas getServicoDePastas() {
		return servicoDePastas;
	}

	public void setServicoDePastas(IServicoDePastas servicoDePastas) {
		this.servicoDePastas = servicoDePastas;
	}
	
}
