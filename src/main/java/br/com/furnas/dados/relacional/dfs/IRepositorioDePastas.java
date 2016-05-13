package br.com.furnas.dados.relacional.dfs;

import java.util.List;

import br.com.furnas.dominio.modelo.Pastas;


public interface IRepositorioDePastas {

	List<Pastas> buscaPastas(String identificadorArmario);
	
	Pastas buscaDadosPasta(String identificadorArmario);

	List<Pastas> buscaPastasEPastas(String identificadorArmarioPasta);
	
	Pastas buscaBreadcrumb(String identificadorArmario);
	
	List<Pastas> buscaPastasDiretorio(String path);	
	
}
