package br.com.furnas.dominio;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

/**
 * ValidacaoUtil.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * Utilitários para tratamento das validações de regras.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 */
public class ValidacaoUtil {

	/**
	 * Locale para recuperação das mensagens.
	 */
	private static final Locale LOCALE = new Locale("pt", "BR");
	
	/**
	 * Início da mensagem de erros de validação
	 */
	private static final String MENSAGEM_ERROS_VALIDACAO = "Erros de validação:";
	
	/**
	 * Caracter de quebra de linha
	 */
	private static final Character QUEBRA_LINHA = '\n';
	
	@Autowired
	private MessageSource messageSource;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * Adiciona em um objeto de erros do Spring, os erros identificados em uma
	 * validação de regra de negócio. 
	 * 
	 * @param errors Objeto de erros do Spring
	 * @param validacao Resultado da validação da regra de negócio
	 */
	public void adicionarErrosValidacao(BindingResult bindingResult, Validacao validacao){
		Map<String, List<String>> mapaErrosCampo =  validacao.getErrosCampos();
		
		for (String campo : mapaErrosCampo.keySet()){
			for (String erro : mapaErrosCampo.get(campo)){
				bindingResult.rejectValue(campo, erro);
			}	
		}
		
		for (String erro : validacao.getErrosGerais()){
			bindingResult.reject(erro);
		}
	}

	/**
	 * Gera uma mensagem com os erros de validação.
	 * 
	 * @param validacao Validação de origem dos dados 
	 * @return Mensagem com os erros de validação, null se não houverem erros
	 * de validação
	 */
	public String gerarMensagemErro(Validacao validacao) {
		if (validacao.isValido()) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer(MENSAGEM_ERROS_VALIDACAO);
		
		for (List<String> errosCampos : validacao.getErrosCampos().values()) {
			for (String erroCampo : errosCampos) {
				sb.append(QUEBRA_LINHA);
				sb.append(messageSource.getMessage(erroCampo, null, LOCALE));
			}
		}
		
		for (String erroGeral : validacao.getErrosGerais()) {
			sb.append(QUEBRA_LINHA);   
			 sb.append(messageSource.getMessage(erroGeral, null, LOCALE));
		}
		
		return sb.toString();
	}
	
	public String gerarMensagemListaErro(String message, Object[] obj)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(messageSource.getMessage(message, obj, LOCALE));
		
		return sb.toString();
	}
	
	public String retornaMensagem(String codigo)
	{
		return retornaMensagem(codigo, null);
	}
	
	public String retornaMensagem(String codigo, String[] params)
	{
		String msg = "";
		
		ResourceBundle mensagens = ResourceBundle.getBundle("mensagens", LOCALE);

		StringBuilder json = new StringBuilder();
		json.append("[");

		Set<String> keys = mensagens.keySet();

		for(String key : keys)
		{
			if(codigo.equalsIgnoreCase(key))
			{
				msg = mensagens.getString(key);
				break;
			}
		}
		
		if(params != null)
		{
			for(int i = 0; i< params.length; i++)
			{
				String er ="{"+ (i)+"}";
				
				msg = msg.replace(er, params[i]);
			}
		}
		
		return msg;
	}
}
