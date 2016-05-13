package br.com.furnas.dominio.modelo.construtores.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeArmarios;
import br.com.furnas.dados.relacional.dfs.IRepositorioDeArquivos;
import br.com.furnas.dados.relacional.dfs.IRepositorioDePastas;
import br.com.furnas.dominio.modelo.Armarios;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.dominio.modelo.Breadcrumb;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.Pastas;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDeGED;
import br.com.furnas.web.FormularioDeGED;

public class ConstrutorDeGED extends EntidadeBase implements IConstrutorDeGED {

	private IRepositorioDePastas repositorioPastas;
	private IRepositorioDeArmarios repositorioArmarios;
	private IRepositorioDeArquivos repositorioArquivos;

	public FormularioDeGED carregarListaGED() {		
		List<Armarios> listaArmarios = repositorioArmarios.buscaArmarios();

		FormularioDeGED formularioDeGED = new FormularioDeGED();
		formularioDeGED.setListaArmarios(listaArmarios);
		formularioDeGED.setModoVisaoUpload(false);
		formularioDeGED.setModoVisaoArquivo(false);
		formularioDeGED.setModoVisaoPasta(true);
		formularioDeGED.setListaBreadcrumb(null);
		return formularioDeGED;
	}

	@Override
	public FormularioDeGED carregarListaGEDPastas(String identificadorArmarioPasta) throws Exception 
	{
		Pastas pasta = repositorioPastas.buscaDadosPasta(identificadorArmarioPasta);
		List<Pastas> listaPastas = repositorioPastas.buscaPastasDiretorio(pasta.getPath());

		List<Arquivos> listaArquivos = null;
		if(listaPastas == null)
		{
			listaArquivos = repositorioArquivos.buscaArquivosPorArmario(identificadorArmarioPasta);
		}
		else
		{
			listaArquivos = repositorioArquivos.buscaArquivosPorPasta(identificadorArmarioPasta);
		}

		FormularioDeGED formularioDeGED = new FormularioDeGED();
		formularioDeGED.setModoVisaoUpload(true);
		formularioDeGED.setModoVisaoPasta(listaPastas.size() == 0 ? false : true);	
		formularioDeGED.setModoVisaoArquivo(true);
		formularioDeGED.setListaPastas(listaPastas);
		formularioDeGED.setListaArquivos(listaArquivos);
		formularioDeGED.setObjectId(identificadorArmarioPasta);
		formularioDeGED.setListaBreadcrumb(montaListaBreadcrumb(identificadorArmarioPasta));        
	    
		return formularioDeGED;
	}

	private List<Breadcrumb> montaListaBreadcrumb(String identificadorArmarioPasta) {
		
		Pastas pasta = repositorioPastas.buscaBreadcrumb(identificadorArmarioPasta);
		
		String[] splitPath = pasta.getPath().split("/");
		String[] splitObjectIdsPastas = pasta.getObjectIdsPasta().split(";");
		
		List<Breadcrumb> listaBreadcrumb = new ArrayList<Breadcrumb>();
		
		for(int i=1; i<=splitPath.length-1; i++)
		{
			Breadcrumb bc = new Breadcrumb();
			bc.setObjectId(splitObjectIdsPastas[i-1]);
			bc.setDescricao(splitPath[i]);
			
			listaBreadcrumb.add(bc);
		}
		
		return listaBreadcrumb;
	}

	@Override
	public FormularioDeGED carregarListaGEDArquivos(String identificadorPasta) {
		// TODO Auto-generated method stub
		return null;
	}


	public IRepositorioDeArmarios getRepositorioArmarios() {
		return repositorioArmarios;
	}

	public void setRepositorioArmarios(IRepositorioDeArmarios repositorioArmarios) {
		this.repositorioArmarios = repositorioArmarios;
	}

	public IRepositorioDeArquivos getRepositorioArquivos() {
		return repositorioArquivos;
	}

	public void setRepositorioArquivos(IRepositorioDeArquivos repositorioArquivos) {
		this.repositorioArquivos = repositorioArquivos;
	}

	public IRepositorioDePastas getRepositorioPastas() {
		return repositorioPastas;
	}

	public void setRepositorioPastas(IRepositorioDePastas repositorioPastas) {
		this.repositorioPastas = repositorioPastas;
	}

	public String retornoLinkArquivo(String objectId) throws Exception {
		return repositorioArquivos.retornaLinkArquivo(objectId);
	}

	public List<Arquivos> carregarListasArquivos(String identificadorPasta) throws Exception {
		List<Arquivos> listaArquivos = repositorioArquivos.buscaArquivosPorArmario(identificadorPasta);
	//	listaArquivos = repositorioArquivos.buscaArquivosPorPasta(identificadorPasta);
		
		return listaArquivos;
	}

}