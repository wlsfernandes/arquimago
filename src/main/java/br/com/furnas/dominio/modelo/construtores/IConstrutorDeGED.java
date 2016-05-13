package br.com.furnas.dominio.modelo.construtores;

import br.com.furnas.web.FormularioDeGED;


public interface IConstrutorDeGED 
{
	FormularioDeGED carregarListaGED();
	
	FormularioDeGED carregarListaGEDPastas(String identificadorArmarioPasta) throws Exception;
	
	FormularioDeGED carregarListaGEDArquivos(String identificadorPasta);
}
