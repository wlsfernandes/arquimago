package br.com.furnas.dominio.modelo.construtores.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.furnas.dados.relacional.dfs.IRepositorioDeGerenciadorDeArquivos;
import br.com.furnas.dominio.Validacao;
import br.com.furnas.dominio.modelo.EntidadeBase;
import br.com.furnas.dominio.modelo.construtores.IConstrutorDeGerenciadorDeArquivos;
import br.com.furnas.integracao.enumeration.EnumDocumentMetadata;
import br.com.furnas.util.FileUploadUtil;

public class ConstrutorDeGerenciadorDeArquivos extends EntidadeBase implements FileUploadUtil, IConstrutorDeGerenciadorDeArquivos {

//	private IRepositorioDeAnexo repositorioAnexo;
	
	private IRepositorioDeGerenciadorDeArquivos repositorioGerenciadorDeArquivos;
	
	private String tagNoArquivo = "";
	
	public void criarPDF(String arquivoDeOrigem, String arquivoEmPdf)
			throws IOException {
		repositorioGerenciadorDeArquivos
		.criarPDF(arquivoDeOrigem, arquivoEmPdf);
	}

	public Validacao downloadPDF(String repositorio, Integer identificador,
			HttpSession session, HttpServletResponse response, Class T)
					throws IOException {
		// Fun��o utilizada para recuperar o objectID para obter o File
		// documento pelo DFS. No JPA ele retorna null j� que ele n�o tem
		// nenhuma utilidade.
		String objectID = retornaObjectIdReferenteClass(T, identificador);

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return repositorioGerenciadorDeArquivos.downloadPDF(repositorio,
					identificador, session, response, objectID, T);
	}

	private String retornaObjectIdReferenteClass(Class t, Integer identificador) {

		String objectId = "";

//		if (t.equals(Metadado.class)) {
//			objectId = repositorioMetadado.buscaObjectIdMetadado(identificador);
//		}
		
		return objectId;
	}

	public Validacao docx2PDF(Integer identificador, HttpSession session,
			HttpServletResponse response) throws IOException {
		Validacao validacao = new Validacao();
		try {

			/* recuperando o object_ID documento */

			String objectID = null; //repositorioMetadado.buscaObjectIdMetadado(identificador);

			/* recuperando arquivo no documentum */

			File arquivoTemp = repositorioGerenciadorDeArquivos
					.recuperarArquivoNoDocumentum(
							objectID,
							EnumDocumentMetadata.TABELA_TIPO_DOCUMENTAL_METADADO
							.toString(), null);

			/* criando docx temporario */
			File temp2Docx = new File(REPOSITORIO_TEMP + "tmp" + Math.random()
					+ ".docx");

			/* criando PDF temporario */
			File arquivoEmPdf = new File(REPOSITORIO_TEMP + "tmp"
					+ Math.random() + ".pdf");

			/* Copiando dados do arquivo temporario para arquivo docx */
			FileUtils.copyFile(arquivoTemp, temp2Docx);

			/* criando arquivo em PDF */
			criarPDF(temp2Docx.getAbsolutePath(),
					arquivoEmPdf.getAbsolutePath());

			InputStream inputStream = new FileInputStream(arquivoEmPdf);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ identificador + ".pdf");

			@SuppressWarnings("unused")
			ServletOutputStream output = response.getOutputStream();
			IOUtils.copy(inputStream, response.getOutputStream());

			response.flushBuffer();

			inputStream.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return validacao;
	}

	public static void docxToXml(String entrada, String saida)
			throws Docx4JException {
		WordprocessingMLPackage wmlPackage2 = Docx4J.load(new java.io.File(
				entrada));
		Docx4J.save(wmlPackage2, new File(saida), Docx4J.FLAG_SAVE_FLAT_XML);
	}

	public static String getXmlContent(String inputXmlPath) throws IOException {
		return FileUtils.readFileToString(new File(inputXmlPath), "UTF-8");
	}

	public static void saveXml(String inputXmlPath, String content)
			throws IOException {
		File tempFile = new File(inputXmlPath);
		FileUtils.writeStringToFile(tempFile, content, "UTF-8");
	}

	public static String transformParameters(String chave, String valor,
			String content) throws IOException {
		Pattern padrao = Pattern.compile(chave);
		Matcher m = padrao.matcher(content);
		content = m.replaceAll(valor);
		return content;
	}

	public static void xmlToDocx(String inputXmlPath, String outputDocxPath)
			throws Docx4JException {
		WordprocessingMLPackage wmlPackage = Docx4J
				.load(new File(inputXmlPath));
		Docx4J.save(wmlPackage, new File(outputDocxPath),
				Docx4J.FLAG_SAVE_ZIP_FILE);
	}

	public static String rootDirectory() {
		return File.listRoots()[0].getAbsolutePath();
	}

	public boolean containsIgnoreCase(String haystack, String needle) {
		if (needle.equals(""))
			return true;
		if (haystack == null || needle == null || haystack.equals(""))
			return false;

		Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE
				+ Pattern.LITERAL);
		Matcher m = p.matcher(haystack);
		return m.find();
	}

	public IRepositorioDeGerenciadorDeArquivos getRepositorioGerenciadorDeArquivos() {
		return repositorioGerenciadorDeArquivos;
	}

	public void setRepositorioGerenciadorDeArquivos(
			IRepositorioDeGerenciadorDeArquivos repositorioGerenciadorDeArquivos) {
		this.repositorioGerenciadorDeArquivos = repositorioGerenciadorDeArquivos;
	}
}