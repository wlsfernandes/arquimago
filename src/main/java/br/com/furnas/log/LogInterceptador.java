package br.com.furnas.log;


/**
 * LogInterceptador.java
 * 
 * Furnas Centrais Elétricas S.A
 * Divisão de Suporte ao Desenvolvimento de Sistemas de Informação
 * Copyright (c) 2014 Todos os direitos reservados à Eletrobras Furnas S.A.
 * 
 * Classe que intercepta chamadas de métodos para incluir log de operação.
 * 
 * @author PrimeUp
 * 
 * 25/11/2014
 *
 */
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.furnas.dominio.ValidacaoUtil;


@Aspect
public class LogInterceptador {
	
	@Autowired
	private ValidacaoUtil validacaoUtil;
	
	@Around("execution(* br.com.furnas.aplicacao.servicos.*.ServicoDe*.*(..))")
	public Object criarLogServico(ProceedingJoinPoint joinPoint) throws Throwable{
		
		Object resultado = null;
		
		Log.debug(validacaoUtil.retornaMensagem("M0135") + joinPoint.getSignature().getName());
		try{
			resultado = joinPoint.proceed();
			
			Log.debug(validacaoUtil.retornaMensagem("M0136"));  
			
			return resultado;
		} catch (Exception e) {
			Log.error(validacaoUtil.retornaMensagem("M0137") + joinPoint.getSignature().getName() + ".", e);
			throw e;
		}
		
		//
	}

}
