package br.com.furnas.dominio.modelo.construtores;

import java.util.HashSet;

import org.springframework.security.core.userdetails.User;

import com.documentum.fc.common.IDfLoginInfo;

public interface IConstrutorDeAutenticacao {

	
	User autenticaUsuario(String username, String Password);
	IDfLoginInfo recuperarUsuario(String username);
	HashSet recuperarCredenciaisDoUsuario(String username);
}
