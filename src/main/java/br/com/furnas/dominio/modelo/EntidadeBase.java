package br.com.furnas.dominio.modelo;

import javax.persistence.MappedSuperclass;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

/**
 * EntidadeBase.java
 * 
 * Furnas Centrais Elétricas S.A Divisão de Suporte ao Desenvolvimento de
 * Sistemas de Informação Copyright (c) 2014 Todos os direitos reservados à
 * Eletrobras Furnas S.A.
 * 
 * Classe base para todas as outras classes do sistema.<br>
 * Implementa a interface {@link IDadosDeEntidadeBase}.
 * 
 * @author PrimeUp
 * 
 *         25/11/2014
 * 
 */
@MappedSuperclass
public abstract class EntidadeBase {

	public DfLoginInfo recuperarUsuario() {
		DfLoginInfo user = (DfLoginInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return user;
	}
}
