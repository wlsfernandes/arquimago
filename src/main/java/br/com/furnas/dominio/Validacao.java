package br.com.furnas.dominio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Validacao.java
 * 
 * Furnas Centrais Elétricas S.A Divisão de Suporte ao Desenvolvimento de
 * Sistemas de Informação Copyright (c) 2014 Todos os direitos reservados à
 * Eletrobras Furnas S.A.
 * 
 * Representa o resultado da avaliação de uma regra de negócio.
 * 
 * @author PrimeUp
 * 
 *         25/11/2014
 */
public class Validacao {

	private Boolean valido;
	private Map<String, List<String>> errosCampos;
	private List<String> errosGerais;
	private String mensagem;
	private String informacaoAdicional;

	@Autowired
	private ServletContext sCtx;

	public Validacao(Boolean valido) {
		super();
		this.valido = valido;
		this.errosCampos = new HashMap<String, List<String>>();
		this.errosGerais = new ArrayList<String>();
	}



	public Validacao(String informacaoAdicional) {
		super();
		this.informacaoAdicional = informacaoAdicional;
	}

	/**
	 * Constrói uma validação inicialmente válida.
	 */
	public Validacao() {
		this(true);
	}

	public Boolean isValido() {
		return valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}

	public Map<String, List<String>> getErrosCampos() {
		return errosCampos;
	}

	public Boolean addErroCampo(String campo, String erro) {
		List<String> errosExistentes = getErrosCampo(campo);
		boolean result = errosExistentes.add(erro);
		valido = false;

		return result;
	}

	public Boolean addErrosCampo(String campo, List<String> erros) {
		List<String> errosExistentes = getErrosCampo(campo);
		boolean result = errosExistentes.addAll(erros);
		valido = false;

		return result;
	}

	private List<String> getErrosCampo(String campo) {
		List<String> errosCampo = errosCampos.get(campo);

		if (errosCampo == null) {
			errosCampo = new ArrayList<String>();
			errosCampos.put(campo, errosCampo);
		}

		return errosCampo;
	}

	public void addAllErrosCampos(Map<String, List<String>> errosCampos) {
		for (String campo : errosCampos.keySet()) {
			addErrosCampo(campo, errosCampos.get(campo));
		}

		valido = false;
	}

	public List<String> getErrosGerais() {
		return errosGerais;
	}

	public Boolean addErroGeral(String erro) {
		boolean result = errosGerais.add(erro);
		valido = false;

		ClassLoader loader = getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream("mensagens.properties");
		Properties p = new Properties();
		try {
			p.load(stream);
			p.getProperty("tipoDePersistencia");

			if (p.getProperty("tipoDePersistencia").equals("jpa")) {
				TransactionInterceptor.currentTransactionStatus()
						.setRollbackOnly();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public Boolean addAllErrosGerais(List<String> erros) {
		boolean result = errosGerais.addAll(erros);
		valido = false;

		return result;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getInformacaoAdicional() {
		return informacaoAdicional;
	}

	public void setInformacaoAdicional(String informacaoAdicional) {
		this.informacaoAdicional = informacaoAdicional;
	}
}
