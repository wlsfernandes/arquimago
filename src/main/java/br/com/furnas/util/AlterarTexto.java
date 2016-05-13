package br.com.furnas.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;


public class AlterarTexto implements FileUploadUtil {

	public void recuperarTodosOsElementosDoArquivo(WordprocessingMLPackage doc)
			throws Docx4JException, IOException {

		WordprocessingMLPackage docDest = WordprocessingMLPackage.load(new FileInputStream(new File(REPOSITORIO_DE_METADADO+"arquivo.docx")));  
		WordprocessingMLPackage docSource = WordprocessingMLPackage.load(new FileInputStream(new File(REPOSITORIO_DE_METADADO+"teste.docx")));  
		append(docDest, docSource);
				
	}
	

	public static void append(WordprocessingMLPackage docDest, WordprocessingMLPackage docSource) throws Docx4JException {
		List<Object> objects = docSource.getMainDocumentPart().getContent();
		    for(Object o : objects){
		        docDest.getMainDocumentPart().getContent().add(o);
		        docDest.save(new java.io.File(REPOSITORIO_DE_METADADO + "resultadoImagem2.docx"));
	    	
		    }
		}
	
}