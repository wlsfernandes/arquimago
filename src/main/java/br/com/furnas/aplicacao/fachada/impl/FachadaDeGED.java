package br.com.furnas.aplicacao.fachada.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.furnas.aplicacao.fachada.IFachadaDeGED;
import br.com.furnas.aplicacao.servicos.IServicoDeGED;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.web.FormularioDeGED;

@Transactional
public class FachadaDeGED implements IFachadaDeGED {

	private IServicoDeGED servicoDeGED;

	public IServicoDeGED getServicoDeGED() {
		return servicoDeGED;
	}

	public void setServicoDeGED(IServicoDeGED servicoDeGED) {
		this.servicoDeGED = servicoDeGED;
	}

	@Override
	public FormularioDeGED carregarListaGED() {
		return servicoDeGED.carregarListaGED();
	}

	@Override
	public FormularioDeGED carregarListaGEDPastas(String identificadorArmario) throws Exception {
		return servicoDeGED.carregarListaGEDPastas(identificadorArmario);
	}

	@Override
	public FormularioDeGED carregarListaGEDArquivos(String identificadorPasta) {
		return servicoDeGED.carregarListaGEDArquivos(identificadorPasta);
	}

	@Override
	public String retornoLinkArquivo(String objectId) throws Exception {
		return servicoDeGED.retornoLinkArquivo(objectId);
	}

	@Override
	public List<Arquivos> carregarListasArquivos(String identificadorPasta) throws Exception {
		return servicoDeGED.carregarListasArquivos(identificadorPasta);
	}

}
