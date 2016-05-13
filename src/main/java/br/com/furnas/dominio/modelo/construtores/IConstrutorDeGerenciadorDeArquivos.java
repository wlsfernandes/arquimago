package br.com.furnas.dominio.modelo.construtores;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.furnas.dominio.Validacao;

public interface IConstrutorDeGerenciadorDeArquivos {

	void criarPDF(String arquivoDeOrigem, String arquivoEmPdf) throws IOException;
	
	Validacao downloadPDF(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, Class T) throws IOException;
	
}
