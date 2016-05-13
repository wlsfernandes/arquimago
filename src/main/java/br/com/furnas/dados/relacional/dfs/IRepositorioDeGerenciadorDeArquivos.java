package br.com.furnas.dados.relacional.dfs;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.furnas.dominio.Validacao;

public interface IRepositorioDeGerenciadorDeArquivos {
	
//	Validacao uploadArquivo(Anexo anexo, String repositorio, Integer identificador) throws IOException; 

	void salvarDocx(byte[] bytes, String diretorioDeDestino, String nomeDoArquivo);
	
	void criarPDF(String arquivoDeOrigem, String arquivoEmPdf) throws IOException;

//	Validacao removerArquivo(Anexo anexo);

	File recuperarArquivoNoDocumentum(String objectID, String tipoDocumental, String ext) throws IOException;

	Validacao downloadPDF(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, String objectID, Class T);

	Validacao downloadPDFAnexo(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, String objectID, Class T);
	
//	Validacao uploadArquivoExtensao(Anexo anexo, String extensao, String repositorio, Integer identificador);

//	Validacao uploadArquivoImportadoNoEdital(Anexo anexo, Integer identificadorDoEdital, String identificadorDaTag);

//	Validacao uploadDeArquivoParaValidacao(Anexo anexo);

//	Validacao uploadArquivoParecerTemporario(Anexo anexo, Integer identificador);
	
//	Validacao uploadArquivoParecer(Anexo anexo, String extensao, String repositorio, Integer identificador);

	Validacao downloadPDFComGlossario(String repositorio,
			Integer identificador, HttpSession session,
			HttpServletResponse response, String objectID, Class T);
}
