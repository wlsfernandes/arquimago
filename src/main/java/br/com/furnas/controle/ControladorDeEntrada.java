package br.com.furnas.controle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.furnas.aplicacao.fachada.IFachadaDeAutenticacao;
import br.com.furnas.dominio.ValidacaoUtil;

/**
 * ControladorDeEntrada.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 *
 */
@Controller
public class ControladorDeEntrada {
	
	@Autowired
	private ValidacaoUtil validacaoUtil;
	/**
	 * Nomes das visões tratadas por esse controlador
	 */
	private static final String VISAO_REDIRECT_LISTA_BASE = "redirect:/interno/diretorio";
	private static final String VISAO_REDIRECT_ENTRADA = "entrada";

	private IFachadaDeAutenticacao fachadaDeAutenticacao;
	

	
	public IFachadaDeAutenticacao getFachadaDeAutenticacao() {
		return fachadaDeAutenticacao;
	}


	public void setFachadaDeAutenticacao(
			IFachadaDeAutenticacao fachadaDeAutenticacao) {
		this.fachadaDeAutenticacao = fachadaDeAutenticacao;
	}


	/**
	 * Ação de entrada no sistema.<br>
	 * Faz o redirecionamento página interna se o usuário estiver
	 * autenticado ou para a página interna se estiver.
	 */
	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public ModelAndView entrar() 
	{
		
		return new ModelAndView(VISAO_REDIRECT_ENTRADA);
	}

	@RequestMapping(value = "/entrada", method = { RequestMethod.GET })
	public ModelAndView entrada(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView(VISAO_REDIRECT_ENTRADA);
		
				
		if (error != null) {
			model.addObject("error", validacaoUtil.retornaMensagem("S0037")); 
		}

		return model;
	}


	@RequestMapping(value = "/entrada/menu", method = { RequestMethod.GET })
	public ModelAndView menu() {

		String mav = VISAO_REDIRECT_LISTA_BASE;
	
		return new ModelAndView(mav);
	}
	
	@RequestMapping(value = "/i18n", method = { RequestMethod.GET })
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pRes) throws ServletException, IOException
	{
		Locale localeUsuario = pReq.getLocale();
		System.out.println("localeUsuario.getCountry() = " + localeUsuario.getCountry());	
		
		
		pRes.setContentType("text/html; charset=UTF-8");		
		PrintWriter out = pRes.getWriter();
		
		out.print(getJsonMensagens(localeUsuario));
		
		out.flush();
		out.close();
	}

	private String getJsonMensagens(Locale localeUsuario) throws IOException
	{
		
		Properties props = new Properties();
		URL resource = getClass().getClassLoader().getResource("mensagens.properties");         
		props.load(new InputStreamReader(resource.openStream(), "UTF8"));
	
		StringBuilder json = new StringBuilder();
		json.append("[");

		Set<Object> keys = props.keySet(); //mensagens.keySet();

		for(Object key : keys)
		{
			json.append("{key:'"+key+"', " + "value:'"+ props.get(key)+"'},");
		}

		int i = json.lastIndexOf(",");
		json.replace(i, i + 1, "");
		
		json.append("]");

		System.out.println("json.toString() = " + json.toString());
		
		return json.toString();

	}


	
}