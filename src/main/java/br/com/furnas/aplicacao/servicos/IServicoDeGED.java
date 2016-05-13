package br.com.furnas.aplicacao.servicos;

import java.util.List;

import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.web.FormularioDeGED;


public interface IServicoDeGED {

	FormularioDeGED carregarListaGED();
	
	FormularioDeGED carregarListaGEDPastas(String identificadorArmario) throws Exception;
	
	FormularioDeGED carregarListaGEDArquivos(String identificadorPasta);

	String retornoLinkArquivo(String objectId) throws Exception;

	List<Arquivos> carregarListasArquivos(String identificadorPasta) throws Exception;

}
