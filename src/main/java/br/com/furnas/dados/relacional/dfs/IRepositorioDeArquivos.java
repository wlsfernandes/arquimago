package br.com.furnas.dados.relacional.dfs;

import java.util.List;

import br.com.furnas.dominio.modelo.Arquivos;

public interface IRepositorioDeArquivos {

	List<Arquivos> buscaArquivosPorArmario(String identificadorArmario) throws Exception;
	
	List<Arquivos> buscaArquivosPorPasta(String identificadorPasta) throws Exception;
	
	String retornaLinkArquivo(String objectId) throws Exception;

}
