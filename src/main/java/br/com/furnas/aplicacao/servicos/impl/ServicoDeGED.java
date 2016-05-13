package br.com.furnas.aplicacao.servicos.impl;

import java.util.List;

import br.com.furnas.aplicacao.servicos.IServicoDeGED;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.dominio.modelo.construtores.impl.ConstrutorDeGED;
import br.com.furnas.web.FormularioDeGED;


public class ServicoDeGED implements IServicoDeGED {
	
	private ConstrutorDeGED construtorDeGED;

	public ConstrutorDeGED getConstrutorDeGED() {
		return construtorDeGED;
	}

	public void setConstrutorDeGED(ConstrutorDeGED construtorDeGED) {
		this.construtorDeGED = construtorDeGED;
	}

	@Override
	public FormularioDeGED carregarListaGED() {		
		return construtorDeGED.carregarListaGED();
	}

	@Override
	public FormularioDeGED carregarListaGEDPastas(String identificadorArmario) throws Exception {
		return construtorDeGED.carregarListaGEDPastas(identificadorArmario);
	}

	@Override
	public FormularioDeGED carregarListaGEDArquivos(String identificadorPasta) {
		return construtorDeGED.carregarListaGEDArquivos(identificadorPasta);
	}

	@Override
	public String retornoLinkArquivo(String objectId) throws Exception {
		return construtorDeGED.retornoLinkArquivo(objectId);
	}

	@Override
	public List<Arquivos> carregarListasArquivos(String identificadorPasta) throws Exception {
		return construtorDeGED.carregarListasArquivos(identificadorPasta);
	}
		
}
