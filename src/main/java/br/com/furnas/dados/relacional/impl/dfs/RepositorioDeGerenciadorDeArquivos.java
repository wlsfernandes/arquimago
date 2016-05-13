package br.com.furnas.dados.relacional.impl.dfs;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import br.com.furnas.autenticacao.Autenticacao;
import br.com.furnas.dados.relacional.dfs.IRepositorioDeGerenciadorDeArquivos;
import br.com.furnas.dominio.Validacao;
import br.com.furnas.integracao.constants.DocumentumConstants;
import br.com.furnas.integracao.enumeration.EnumDocumentMetadata;
import br.com.furnas.integracao.services.DocumentumServices;
import br.com.furnas.util.FileUploadUtil;

@Repository
public class RepositorioDeGerenciadorDeArquivos extends
RepositorioGenericoDados implements FileUploadUtil,
IRepositorioDeGerenciadorDeArquivos {

	DocumentumServices service;
	static Logger logger = Logger.getLogger(RepositorioDeGerenciadorDeArquivos.class);

//	@Override
//	public Validacao uploadArquivo(Anexo anexo, String repositorio,	Integer identificador) throws IOException {
//		return new Validacao();
//	}

	@Override
	public void salvarDocx(byte[] bytes, String diretorioDeDestino,
			String nomeDoArquivo) {

	}

	@Override
	public void criarPDF(String arquivoDeOrigem, String arquivoEmPdf)
			throws IOException {
		
		Process p = null;
		
		try {

		/*	// http://mydailyjava.blogspot.com.br/2013/05/converting-microsoft-doc-or-docx-files.html
			Resource resource = new ClassPathResource(
					"META-INF/lib/doc2pdf.vbs");
			File vbScript = new File(resource.getURL().getPath());
			String executarVbScript = "cscript " + "\""
					+ vbScript.getAbsolutePath() + "\"";
			File docPath = new File(arquivoDeOrigem);
			File pdfPath = new File(arquivoEmPdf);
			String command = String.format("%s %s", docPath, pdfPath);
			Process process = Runtime.getRuntime().exec(
					executarVbScript + " " + command);
			process.waitFor();*/
		
			String caminhoExe = "";
			
			if(System.getProperty("os.arch").equals("x86")) //32Bits
			{
				caminhoExe = "META-INF/lib/Script/32Bits/OfficeToPDF.exe";
			}
			else  //64Bits
			{
				caminhoExe = "META-INF/lib/Script/64Bits/doc2pdf.exe";
			}
			
			Resource resource = new ClassPathResource(caminhoExe);
			
			String[] cmd = { resource.getURL().getPath(), arquivoDeOrigem, arquivoEmPdf };
			p = Runtime.getRuntime().exec(cmd);
			int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new IOException("Command exited with " + exitCode);
            }
			System.out.println("Conversion complete!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			p.destroy();
		}

	}

	public File criarArquivoTXT(Integer identificadorDaTag, String conteudo)
			throws IOException {

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(REPOSITORIO_TEMP + "tag_"
							+ identificadorDaTag + ".txt"), "utf-8"));
			writer.write(conteudo);
		} catch (IOException ex) {
			// report
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {/* ignore */
			}
		}

		File file = new File(REPOSITORIO_TEMP + "tag_" + identificadorDaTag
				+ ".txt");

		return file;
	}

	public File recuperarArquivoNoDocumentum(String objectID,
			String tipoDocumental, String ext) throws IOException {
		return this.recuperarDocumento(objectID, tipoDocumental, ext);
	}

//	@Override
//	public Validacao removerArquivo(Anexo anexo) {
//		return new Validacao();
//	}

	@Override
	public Validacao downloadPDF(String repositorio, Integer identificador, HttpSession session, HttpServletResponse response, String objectID, Class T) {
		Autenticacao auth = new Autenticacao();
		Validacao validacao = new Validacao();
		try {

			System.out.println("###################################################################################");
			logger.info("###################################################################################");

			System.out.println("0 - Entra na função de downloadPDF e utiliza o repositório:"+REPOSITORIO_TEMP);
			logger.info("0 - Entra na função de downloadPDF e utiliza o repositório:"+REPOSITORIO_TEMP);

			/* recuperando arquivo no documentum */
			File arquivoTemp = recuperarArquivoNoDocumentum(objectID, EnumDocumentMetadata.TABELA_TIPO_DOCUMENTAL_METADADO.toString(), null);

			System.out.println("1 - Recupera arquivo do Documentum (arquivoTemp="+arquivoTemp.getAbsolutePath()+")");
			logger.info("1 - Recupera arquivo do Documentum (arquivoTemp="+arquivoTemp.getAbsolutePath()+")");

			/* criando docx temporario */
			File temp2Docx = new File(REPOSITORIO_TEMP + "tmp" + Math.random() + ".docx");

			System.out.println("2 - Cria o arquivo .docx temporário (temp2Docx="+temp2Docx.getAbsolutePath()+")");
			logger.info("2 - Cria o arquivo .docx temporário (temp2Docx="+temp2Docx.getAbsolutePath()+")");

			/* criando PDF temporario */
			File arquivoEmPdf = new File(REPOSITORIO_TEMP + "tmp"+ Math.random() + ".pdf");

			System.out.println("3 - Cria o arquivo .pdf temporário (arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");			
			logger.info("3 - Cria o arquivo .pdf temporário (arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");
		
			/* Copiando dados do arquivo temporario para arquivo docx */
			FileUtils.copyFile(arquivoTemp, temp2Docx);

			System.out.println("4 - Copia o arquivo do Documentum para o arquivo temporário (arquivoTemp="+arquivoTemp.getAbsolutePath()+", temp2Docx="+temp2Docx.getAbsolutePath()+")");
			logger.info("4 - Copia o arquivo do Documentum para o arquivo temporário (arquivoTemp="+arquivoTemp.getAbsolutePath()+", temp2Docx="+temp2Docx.getAbsolutePath()+")");
			/* criando arquivo em PDF */
			criarPDF(temp2Docx.getAbsolutePath(), arquivoEmPdf.getAbsolutePath());

			System.out.println("5 - Cria o arquivo PDF baseado no arquivo gerado acima (temp2Docx="+temp2Docx.getAbsolutePath()+", arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");
			logger.info("5 - Cria o arquivo PDF baseado no arquivo gerado acima (temp2Docx="+temp2Docx.getAbsolutePath()+", arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");
 	
			InputStream inputStream = new FileInputStream(arquivoEmPdf);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ identificador + ".pdf");

			System.out.println("6 - Chama o método de forçar download (arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");
			logger.info("6 - Chama o método de forçar download (arquivoEmPdf="+arquivoEmPdf.getAbsolutePath()+")");

			@SuppressWarnings("unused")
			ServletOutputStream output = response.getOutputStream();
			IOUtils.copy(inputStream, response.getOutputStream());

			System.out.println("7 - chama funções do response ");
			logger.info("7 - chama funções do response");

			response.flushBuffer();
			inputStream.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

			System.out.println("8 - Limpa as variáveis do response. Usa response.flushBuffer, inputStream.close, response.getOutputStream().flush e close");
			logger.info("8 - Limpa as variáveis do response. Usa response.flushBuffer, inputStream.close, response.getOutputStream().flush e close");

			temp2Docx.delete();
			arquivoEmPdf.delete();

			System.out.println("9 - Deleta os arquivos temporários ");
			logger.info("9 - Deleta os arquivos temporários ");

		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println("10 - Sai da função ");
		logger.info("10 - Sai da função ");

		System.out.println("###################################################################################");
		logger.info("###################################################################################");

		return validacao;
	}
	
	public void criarPDFexeEmbutido(String arquivoEmDocx, String resultadoEmPdf) throws IOException{
		
		try 
		{
			//Replace PDFConvert directory, source PDF file name and output directory
		     /* String command = "\"D:\\SAC4\\OFFICETOPDF.EXE\" /cs 10000 /i \"D:\\SAC4\\1.docx\" /o \"D:\\SAC4\\r1.pdf\" /pwo 2";
		      Process p = Runtime.getRuntime().exec(command);
		      System.out.println("Conversion complete!");*/

			Resource resource = new ClassPathResource("META-INF/lib/OfficeToPDF.exe");
			String[] cmd = { resource.getURL().getPath(), arquivoEmDocx, resultadoEmPdf };
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			System.out.println("Conversion complete!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Validacao downloadPDFAnexo(String repositorio,
			Integer identificador, HttpSession session,
			HttpServletResponse response, String objectID, Class T) {
		Validacao validacao = new Validacao();
		try {
			/* recuperando arquivo no documentum */
			File arquivoTemp = recuperarArquivoNoDocumentum(objectID,
					EnumDocumentMetadata.TABELA_TIPO_DOCUMENTAL_METADADO.toString(), DocumentumConstants.PDF);

			/* criando PDF temporario */
			File arquivoEmPdf = new File(REPOSITORIO_TEMP + "tmp"
					+ Math.random() + ".pdf");

			/* Copiando dados do arquivo temporario para arquivo docx */
			FileUtils.copyFile(arquivoTemp, arquivoEmPdf);

			InputStream inputStream = new FileInputStream(arquivoEmPdf);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ arquivoEmPdf.getName() + ".pdf");

			@SuppressWarnings("unused")
			ServletOutputStream output = response.getOutputStream();
			IOUtils.copy(inputStream, response.getOutputStream());

			response.flushBuffer();
			inputStream.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

			arquivoTemp.delete();
			arquivoEmPdf.delete();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return validacao;
	}


//	@Override
//	public Validacao uploadArquivoExtensao(Anexo anexo, String extensao,
//			String repositorio, Integer identificador) {
//		return new Validacao();
//	}

	/*@Override
	public Validacao uploadArquivoImportadoNoEdital(Anexo anexo,
			Integer identificadorDoEdital, String identificadorDaTag) {
		Validacao validacao = new Validacao();

		try {

			byte[] bytes = anexo.getFile().getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(REPOSITORIO_TEMP + anexo.getNome()
							+ "." + anexo.getExtensao()));

			stream.write(bytes);
			stream.close();

		} catch (Exception e) {

		}

		return validacao;
	}*/

//	@Override
//	public Validacao uploadDeArquivoParaValidacao(Anexo anexo) {
//		Validacao validacao = new Validacao();
//
//		try {
//
//			byte[] bytes = anexo.getFile().getBytes();
//			BufferedOutputStream stream = new BufferedOutputStream(
//					new FileOutputStream(REPOSITORIO_TEMP + anexo.getNome()
//							+ "." + anexo.getExtensao()));
//
//			stream.write(bytes);
//			stream.close();
//
//		} catch (Exception e) {
//			validacao.addErroGeral("Erro na leitura do arqvio Ms-Word");
//		}
//		return validacao;
//	}

//	@Override
//	public Validacao uploadArquivoParecerTemporario(Anexo anexo,
//			Integer identificador) {
//		Validacao validacao = new Validacao();
//
//		String strDir = REPOSITORIO_TEMP;
//
//		try {
//
//			byte[] bytes = anexo.getFile().getBytes();
//			anexo.getFile().getOriginalFilename();
//			BufferedOutputStream stream = new BufferedOutputStream(
//					new FileOutputStream(strDir + anexo.getNome() + ".pdf"));
//
//			stream.write(bytes);
//			stream.close();
//
//		} catch (Exception e) {
//
//		}
//
//		return validacao;
//	}

//	@Override
//	public Validacao uploadArquivoParecer(Anexo anexo, String extensao,
//			String repositorio, Integer identificador) {
//		return new Validacao();
//	}

	@Override
	public Validacao downloadPDFComGlossario(String repositorio,
			Integer identificador, HttpSession session,
			HttpServletResponse response, String objectID, Class T) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public String gerarGlossarioDoModelo(String fileTemp, List<Tag>
	 * listaDeTags) {
	 * 
	 * try {
	 * 
	 * WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
	 * .createPackage();
	 * 
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText("");
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText("");
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText("");
	 * wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title",
	 * "GlossÃ¡rio:");
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText("");
	 * wordMLPackage.
	 * getMainDocumentPart().addStyledParagraphOfText("Bold","wewewewewewewe :"
	 * );
	 * 
	 * 
	 * for (Tag tag : listaDeTags) {
	 * 
	 * String regraDePreenchimento = "";
	 * 
	 * if (tag.isTagObrigatoria() == true) { regraDePreenchimento =
	 * "obrigatÃ³rio"; } else { regraDePreenchimento = "facultativo"; }
	 * 
	 * if (tag.isPermiteImportar() == true) {
	 * wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
	 * tag.getTitTag().toLowerCase() + " :");
	 * 
	 * wordMLPackage .getMainDocumentPart() .addParagraphOfText(
	 * "Esta palavra chave Ã© de preenchimento " + regraDePreenchimento +
	 * " e serÃ¡ substituÃ­da por uma imagem"); } else if
	 * (tag.isTagMultivalorada() == true) {
	 * wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
	 * tag.getTitTag().toLowerCase() + " :"); wordMLPackage
	 * .getMainDocumentPart()
	 * .addParagraphOfText("Esta palavra chave Ã© de preenchimento " +
	 * regraDePreenchimento + " e poderÃ¡ substituÃ­da por um dos textos:");
	 * 
	 * for (int i = 0; i < tag.getTagsMultivaloradas().size(); i++) {
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText(
	 * tag.getTagsMultivaloradas().get(i) .getDescricaoDaTag()); }
	 * 
	 * } else if (tag.isPossuiParagrafo() == true) {
	 * wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
	 * tag.getTitTag().toLowerCase() + " :"); wordMLPackage
	 * .getMainDocumentPart()
	 * .addParagraphOfText("Esta palavra chave Ã© de preenchimento " +
	 * regraDePreenchimento + " e poderÃ¡ substituÃ­da por um dos ParÃ¡grafos:");
	 * 
	 * for (int i = 0; i < tag.getTagsMultivaloradas().size(); i++) {
	 * wordMLPackage.getMainDocumentPart().addParagraphOfText(
	 * tag.getTagsMultivaloradas().get(i) .getParagrafoDaTag()); }
	 * 
	 * } else {
	 * wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
	 * tag.getTitTag().toLowerCase() + " :"); wordMLPackage
	 * .getMainDocumentPart()
	 * .addParagraphOfText("Esta palavra chave Ã© de preenchimento " +
	 * regraDePreenchimento +
	 * " e poderÃ¡ substituÃ­da por um texto de digitaÃ§Ã£o livre:"); } }
	 * 
	 * wordMLPackage.save(new File(fileTemp));
	 * 
	 * } catch (Exception e) {
	 * 
	 * } return fileTemp; }
	 */
}
