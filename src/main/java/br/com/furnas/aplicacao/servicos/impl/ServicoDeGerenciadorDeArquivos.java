package br.com.furnas.aplicacao.servicos.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import br.com.furnas.aplicacao.servicos.IServicoDeGerenciadorDeArquivos;
import br.com.furnas.dominio.Validacao;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDeGerenciadorDeArquivos;
import br.com.furnas.util.FileUploadUtil;

@Service
public class ServicoDeGerenciadorDeArquivos implements IServicoDeGerenciadorDeArquivos, FileUploadUtil {

	private ConstrutorDeGerenciadorDeArquivos construtorDeGerenciadorDeArquivos;

	@Override
	public Validacao downloadPDF(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, Class T) throws IOException {
		
		return construtorDeGerenciadorDeArquivos.downloadPDF(repositorio, identificador, session, response, T);
	}
		
	public ConstrutorDeGerenciadorDeArquivos getConstrutorDeGerenciadorDeArquivos() {
		return construtorDeGerenciadorDeArquivos;
	}

	public void setConstrutorDeGerenciadorDeArquivos(
			ConstrutorDeGerenciadorDeArquivos construtorDeGerenciadorDeArquivos) {
		this.construtorDeGerenciadorDeArquivos = construtorDeGerenciadorDeArquivos;
	}
}
