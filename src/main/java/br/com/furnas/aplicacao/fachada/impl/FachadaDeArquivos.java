package br.com.furnas.aplicacao.fachada.impl;

import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDeArquivos;
import br.com.furnas.aplicacao.servicos.IServicoDeArquivos;

@Transactional
public class FachadaDeArquivos implements IFachadaDeArquivos {

	private IServicoDeArquivos servicoDeArquivos;

	public IServicoDeArquivos getServicoDeArquivos() {
		return servicoDeArquivos;
	}

	public void setServicoDeArquivos(IServicoDeArquivos servicoDeArquivos) {
		this.servicoDeArquivos = servicoDeArquivos;
	}
	
}
