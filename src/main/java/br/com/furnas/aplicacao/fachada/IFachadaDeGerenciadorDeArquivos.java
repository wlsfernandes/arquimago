package br.com.furnas.aplicacao.fachada;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import br.com.furnas.dominio.Validacao;

@Component
public interface IFachadaDeGerenciadorDeArquivos 
{
	Validacao downloadPDF(String repositorio, Integer Identificador, HttpSession session,HttpServletResponse response, Class T) throws IOException;
}
