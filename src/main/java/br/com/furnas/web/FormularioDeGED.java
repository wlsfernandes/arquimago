package br.com.furnas.web;

import java.util.List;

import br.com.furnas.dominio.modelo.Armarios;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.dominio.modelo.Breadcrumb;
import br.com.furnas.dominio.modelo.Pastas;

public class FormularioDeGED {
	
	private Integer identificador;

	private String objectId;
	
	private boolean modoVisaoPasta;
	
	private boolean modoVisaoArquivo;

	private boolean modoVisaoUpload;

	private long contadorDePastas;
	
	private List<Armarios> listaArmarios;
	
	private List<Pastas> listaPastas;
	
	private List<Arquivos> listaArquivos;
	
	private List<Breadcrumb> listaBreadcrumb;  
	
	private String classeTipoArquivo;
	
	public String getClasseTipoArquivo() {
		return classeTipoArquivo;
	}

	public void setClasseTipoArquivo(String classeTipoArquivo) {
		this.classeTipoArquivo = classeTipoArquivo;
	}

	public List<Breadcrumb> getListaBreadcrumb() {
		return listaBreadcrumb;
	}

	public void setListaBreadcrumb(List<Breadcrumb> listaBreadcrumb) {
		this.listaBreadcrumb = listaBreadcrumb;
	}

	public long getContadorDePastas() {
		return contadorDePastas;
	}

	public void setContadorDePastas(long contadorDePastas) {
		this.contadorDePastas = contadorDePastas;
	}

	public long getContadorDeDocumentos() {
		return contadorDeDocumentos;
	}

	public void setContadorDeDocumentos(long contadorDeDocumentos) {
		this.contadorDeDocumentos = contadorDeDocumentos;
	}

	private long contadorDeDocumentos;

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public boolean isModoVisaoPasta() {
		return modoVisaoPasta;
	}

	public void setModoVisaoPasta(boolean modoVisaoPasta) {
		this.modoVisaoPasta = modoVisaoPasta;
	}

	public boolean isModoVisaoArquivo() {
		return modoVisaoArquivo;
	}

	public void setModoVisaoArquivo(boolean modoVisaoArquivo) {
		this.modoVisaoArquivo = modoVisaoArquivo;
	}

	public boolean isModoVisaoUpload() {
		return modoVisaoUpload;
	}

	public void setModoVisaoUpload(boolean modoVisaoUpload) {
		this.modoVisaoUpload = modoVisaoUpload;
	}

	public List<Armarios> getListaArmarios() {
		return listaArmarios;
	}

	public void setListaArmarios(List<Armarios> listaArmarios) {
		this.listaArmarios = listaArmarios;
	}

	public List<Pastas> getListaPastas() {
		return listaPastas;
	}

	public void setListaPastas(List<Pastas> listaPastas) {
		this.listaPastas = listaPastas;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public List<Arquivos> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

}