package br.com.furnas.aplicacao.fachada.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDeGerenciadorDeArquivos;
import br.com.furnas.aplicacao.servicos.IServicoDeGerenciadorDeArquivos;
import br.com.furnas.dominio.Validacao;

@Transactional
public class FachadaDeGerenciadorDeArquivos implements IFachadaDeGerenciadorDeArquivos {

	private IServicoDeGerenciadorDeArquivos servicoDeGerenciadorDeArquivos;

	public IServicoDeGerenciadorDeArquivos getServicoDeGerenciadorDeArquivos() {
		return servicoDeGerenciadorDeArquivos;
	}

	public void setServicoDeGerenciadorDeArquivos(
			IServicoDeGerenciadorDeArquivos servicoDeGerenciadorDeArquivos) {
		this.servicoDeGerenciadorDeArquivos = servicoDeGerenciadorDeArquivos;
	}

	@Override
	public Validacao downloadPDF(String repositorio, Integer Identificador, HttpSession session, HttpServletResponse response, Class T) throws IOException {
		return servicoDeGerenciadorDeArquivos.downloadPDF(repositorio, Identificador, session, response, T);
	}	
}
