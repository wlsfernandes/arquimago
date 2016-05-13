package br.com.furnas.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.docx4j.Docx4J;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.ClassFinder;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Document;
import org.docx4j.wml.P;
import org.docx4j.wml.SdtBlock;
import org.docx4j.wml.SdtPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Conversor {

	private static final long K = 1024;
	private static final long M = K * K;
	private static final long G = M * K;
	private static final long T = G * K;

	public static void docxToXml(String inputDocxPath, String outputXmlPath)
			throws Docx4JException {
		WordprocessingMLPackage wmlPackage2 = Docx4J.load(new java.io.File(
				inputDocxPath));
		Docx4J.save(wmlPackage2, new File(outputXmlPath),
				Docx4J.FLAG_SAVE_FLAT_XML);
	}

	public static String getXmlContent(String inputXmlPath) throws IOException {
		return FileUtils.readFileToString(new File(inputXmlPath), "UTF-8");
	}

	public static void saveXml(String inputXmlPath, String content)
			throws IOException {
		File tempFile = new File(inputXmlPath);
		FileUtils.writeStringToFile(tempFile, content, "UTF-8");
	}

	public static void replacePlaceholder2(WordprocessingMLPackage template, String tag, String valorTag ) throws Exception{       

		MainDocumentPart documentPart = template.getMainDocumentPart();
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put(tag, valorTag);
		documentPart.variableReplace(mappings);
	}

	public static void replaceTextValue(WordprocessingMLPackage template, String tag, String valorTag ) throws Exception{     

		List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), SdtBlock.class);

		@SuppressWarnings("deprecation")
		Document doc = (Document) template.getMainDocumentPart().getJaxbElement();

		for (Object o : doc.getBody().getContent()) {
			if (o instanceof org.docx4j.wml.P) {
				if (((org.docx4j.wml.P) o).getPPr() != null) {
					org.docx4j.wml.PPr ppr = ((org.docx4j.wml.P) o).getPPr();
					if (ppr.getSectPr() != null) {
						//...
					}
				}
			}
		}

		for (Object text : texts) {         

			SdtBlock textElement = (SdtBlock) text;
			List<Object> cList = textElement.getSdtContent().getContent();

			SdtPr pr = textElement.getSdtPr();
			List<Object> al = pr.getRPrOrAliasOrLock();

			for (Object alias : al) {   // go through all SdtPr objects

				if ( alias.getClass().toString().contains("org.docx4j.wml.Tag")) {

					String CTagVal = ((org.docx4j.wml.Tag) alias).getVal(); 

					if (CTagVal.equalsIgnoreCase(tag))  {   

						ClassFinder finder = new ClassFinder(Text.class);
						new TraversalUtil(cList, finder);

						for (Object o : finder.results) {
							Object o2 = XmlUtils.unwrap(o);
							if (o2 instanceof org.docx4j.wml.Text) {
								org.docx4j.wml.Text txt = (org.docx4j.wml.Text)o2;
								txt.setValue(valorTag);
							} else {
								System.out.println( XmlUtils.marshaltoString(o, true, true));
							}
						}

					}

				}           

			}
		}
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

	public static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {


				result.addAll(getAllElementFromObject(child, toSearch));
			}

		}
		return result;
	}

	public static void replaceParagraph(List<Object> paragraphs, String tag, String valorTag) throws Exception {

		final StringWriter stringWriter = new StringWriter();

		for (int i = 0; i < paragraphs.size(); i++) 
		{
			Object p = (Object) paragraphs.get(i);
			String paragrafosDoDocumento = p.toString();

			if(paragrafosDoDocumento.trim().contains(tag))
			{
				paragrafosDoDocumento = paragrafosDoDocumento.replaceAll(tag, valorTag);
				//paragraphs.set(i, paragrafosDoDocumento);

				stringWriter.write(paragrafosDoDocumento);
				//XmlUtils.extractText(p, stringWriter);
			}
		}
	}


	public static void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd, WordprocessingMLPackage template) throws Docx4JException, JAXBException 
	{
		List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

		// 1. find the table
		Tbl tempTable = getTemplateTable(tables, placeholders[0]);
		List<Object> rows = Conversor.getAllElementFromObject(tempTable, Tr.class);

		// first row is header, second row is content
		if (rows.size() == 2) {
			// this is our template row
			Tr templateRow = (Tr) rows.get(1);

			for (Map<String, String> replacements : textToAdd) {
				// 2 and 3 are done in this method
				addRowToTable(tempTable, templateRow, replacements);
			}

			// 4. remove the template row
			tempTable.getContent().remove(templateRow);
		}
	}



	private static Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException 
	{
		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = Conversor.getAllElementFromObject(tbl, Text.class);
			for (Object text : textElements) {
				Text textElement = (Text) text;
				if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
					return (Tbl) tbl;
			}
		}

		return null;
	}	

	private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) 
	{
		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
		List<?> textElements = Conversor.getAllElementFromObject(workingRow, Text.class);
		for (Object object : textElements) {
			Text text = (Text) object;
			String replacementValue = (String) replacements.get(text.getValue());
			if (replacementValue != null)
				text.setValue(replacementValue);
		}

		reviewtable.getContent().add(workingRow);
	}

	public static void findAndReplace(List<Object> paragraphs, String toFind, String replacer){
		boolean achouTralha = false;
		boolean achouVariavel = false;
		String tralha = "#";

		for(Object par : paragraphs)
		{
			P p = (P) par;

			List<Object> texts = getAllElementFromObject(p, Text.class);
			for(Object text : texts)
			{
				Text t = (Text)text;

				if(t.getValue().contains(tralha))
				{
					t.setValue(t.getValue().replace(tralha, ""));
					achouTralha = true;

					if(achouVariavel)
					{
						break;
					}
				}

				if(t.getValue().length() == 0)
				{
					continue;	
				}


				if(toFind.contains(t.getValue()))
				{
					t.setValue(replacer);
					achouVariavel = true;
					achouTralha = false;
					continue;	
				}

			}

			if(achouVariavel && achouTralha)
			{
				break;
			}

			achouTralha = false;
			achouVariavel = false;
		}
	}

	public static void substituirParametros(String arquivoDeOrigem, String listaTagsASubstituir, String listaTagsSubstituida) throws IOException {

		try {

			Resource resource = new ClassPathResource("META-INF/lib/replaceParameters.vbs");
			File vbScript = new File(resource.getURL().getPath());
			String executarVbScript = "cscript " +  "\""+vbScript.getAbsolutePath() + "\"";
			File docPath = new File(arquivoDeOrigem);
			String command = String.format("%s %s %s", docPath, "\""+listaTagsASubstituir+"\"", "\""+listaTagsSubstituida+"\"");
			Process process = Runtime.getRuntime().exec(executarVbScript + " " + command);
			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void substituirTagPorArquivoMsWord(String arquivoDeOrigem, String listaTagsASubstituir, String arquivoParaImportar) throws IOException {

		try {

			Resource resource = new ClassPathResource("META-INF/lib/substituirTagPorArquivoMsWord.vbs");
			File vbScript = new File(resource.getURL().getPath());
			String executarVbScript = "cscript " +  "\""+vbScript.getAbsolutePath() + "\"";
			File docPath = new File(arquivoDeOrigem);
			String command = String.format("%s %s %s", docPath, "\""+listaTagsASubstituir+"\"", arquivoParaImportar);
			Process process = Runtime.getRuntime().exec(executarVbScript + " " + command);
			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String converteTipoConteudoParaClasseCSS(String tipoConteudo)
	{
		String classeCSS = "fa-file-o";

		Map<String, String> map = new HashMap<String, String>();
		map.put("excel", "fa-file-excel-o");
		map.put("pdf", "fa-file-pdf-o");
		map.put("msw", "fa-file-word-o");
		map.put("jpeg", "fa-file-image-o");
		map.put("ppt", "fa-file-powerpoint-o");
		map.put("png", "fa-file-image-o");
		map.put("zip", "fa-file-archive-o");

		if(tipoConteudo.length() == 0)
		{
			return classeCSS;
		}

		Iterator<Entry<String,String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String,String> entry = (Entry<String,String>) iterator.next();

			if((tipoConteudo).indexOf(entry.getKey()) != -1)
			{
				classeCSS = entry.getValue();
			}
		}

		return classeCSS;
	}

	public static String converteTamanhosParaUnidadeMedidaDoArquivo(final long value)
	{
		final long[] dividers = new long[] { T, G, M, K, 1 };
		final String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
		if(value < 1)
			throw new IllegalArgumentException("Invalid file size: " + value);
		String result = null;
		for(int i = 0; i < dividers.length; i++){
			final long divider = dividers[i];
			if(value >= divider){
				result = format(value, divider, units[i]);
				break;
			}
		}
		return result;
	}

	private static String format(final long value,
			final long divider,
			final String unit){
		final double result =
				divider > 1 ? (double) value / (double) divider : (double) value;
				return String.format("%.1f %s", Double.valueOf(result), unit);
	}

}
