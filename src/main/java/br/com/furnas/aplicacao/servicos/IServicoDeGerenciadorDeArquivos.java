package br.com.furnas.aplicacao.servicos;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.furnas.dominio.Validacao;


public interface IServicoDeGerenciadorDeArquivos {
	
    
	Validacao downloadPDF(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, Class T) throws IOException;
	
}
