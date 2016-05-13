//package br.com.furnas.util;
//
//import org.docx4j.openpackaging.exceptions.InvalidFormatException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.Part;
//import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
//import org.docx4j.relationships.Relationship;
//import org.docx4j.wml.STBrType;
//import org.hibernate.validator.internal.util.logging.Log_.logger;
//
////...
//public class MergeUtil implements IMergeUtil {
//
//	//...
//
//	private void mergeDocxFiles(WordprocessingMLPackage initialFile, List<WordprocessingMLPackage> attachementFiles,
//			String outputFile) throws Exception {
//		//...
//		resetSections(wordMLPackageDest);
//		//...
//		addEmptySection(wordMLPackageDest, SectionType.PARAGRAPH);
//
//		for (WordprocessingMLPackage wordprocessingMLPackage : attachementFiles) {
//			//...
//			traverseAndCopyRelationships(wordprocessingMLPackage.getPackage().getRelationshipsPart());
//			traverseAndCopyElements(wordprocessingMLPackage.getPackage().getRelationshipsPart(),
//					wordprocessingMLPackage.getMainDocumentPart().getContent());
//			//...
//
//		}
//
//		addEmptySection(wordMLPackageDest, SectionType.BODY);
//		assignHeaderFooterData(wordMLPackageDest);
//		//...                
//
//	}
//
//	//...
//
//	private void addPageBreak() {
//		logger.debug("Adding page break");
//		org.docx4j.wml.P p = new org.docx4j.wml.P();
//		org.docx4j.wml.R r = new org.docx4j.wml.R();
//		org.docx4j.wml.Br br = new org.docx4j.wml.Br();
//		br.setType(STBrType.PAGE);
//		r.getContent().add(br);
//		p.getContent().add(r);
//		wordMLPackageDest.getMainDocumentPart().addObject(p);
//	}
//
//	@SuppressWarnings({ "restriction", "rawtypes" })
//	private void traverseAndCopyElements(RelationshipsPart rp, List<Object> content) throws InvalidFormatException {
//		for (Object o : content) {
//
//			//...
//			findResourceById(rp, ((org.docx4j.dml.picture.Pic) o6).getBlipFill().getBlip()
//					//...
//					.getEmbed());
//			findResourceByName(wordMLPackageDest.getPackage().getRelationshipsPart(),
//					imageRelPartName);
//			//...  
//		}
//	}
//
//	//...        
//	private void findResourceById(RelationshipsPart rp, String lastId) {
//		for (Relationship r : rp.getRelationships().getRelationship()) {
//			Part part = rp.getPart(r);
//			//...
//			if (part.getRelationshipsPart(false) != null) {
//				findResourceById(part.getRelationshipsPart(false), lastId);
//			}
//		}
//	}
//
//	private void findResourceByName(RelationshipsPart rp, String imageName) {
//		for (Relationship r : rp.getRelationships().getRelationship()) {
//			Part part = rp.getPart(r);
//			//...
//			if (part.getRelationshipsPart(false) != null) {
//				findResourceByName(part.getRelationshipsPart(false), imageName);
//			}
//		}
//	}
//
//	private void traverseAndCopyRelationships(RelationshipsPart rp) throws InvalidFormatException {
//		for (Relationship r : rp.getRelationships().getRelationship()) {
//			Part part = rp.getPart(r);
//			if (part != null) {
//				//...
//				if (part instanceof org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage
//						|| part instanceof org.docx4j.openpackaging.parts.WordprocessingML.FooterPart
//						|| part instanceof org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart) {
//					//...
//				}
//
//				if (part.getRelationshipsPart(false) != null) {
//					traverseAndCopyRelationships(part.getRelationshipsPart(false));
//				}
//			}
//		}
//	}
//	//...
//	private void resetSections(WordprocessingMLPackage wordMLPackage) throws InvalidFormatException {
//		Document doc = (Document) wordMLPackage.getMainDocumentPart().getJaxbElement();
//
//		for (Object o : doc.getBody().getContent()) {
//			if (o instanceof org.docx4j.wml.P) {
//				if (((org.docx4j.wml.P) o).getPPr() != null) {
//					org.docx4j.wml.PPr ppr = ((org.docx4j.wml.P) o).getPPr();
//					if (ppr.getSectPr() != null) {
//						//...
//					}
//				}
//			}
//		}
//
//		wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(null);
//	}
//	//...
//	private void addEmptySection(WordprocessingMLPackage wordMLPackage, SectionType type) {
//		if (type.equals(SectionType.BODY)) {
//			org.docx4j.wml.SectPr sectPr = objectFactory.createSectPr();
//			sectPr.setPgSz(this.defaultSectionpgSz);
//			sectPr.setPgMar(this.defaultSectionpgMar);
//			sectPr.setCols(this.defaultSectioncols);
//			sectPr.setDocGrid(this.defaultSectiondocGrid);
//			wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(sectPr);
//		} else {
//			//...
//		}
//	}
//	//...
//	private void assignHeaderFooterData(WordprocessingMLPackage wordMLPackage) throws InvalidFormatException {
//		Document doc = (Document) wordMLPackage.getMainDocumentPart().getJaxbElement();
//		int sectionCounter = 0;
//		wordMLPackage.getMainDocumentPart().getContent();
//
//		HeaderPart headerPart = new HeaderPart();
//		headerPart.setPackage(wordMLPackage);
//		headerPart.setJaxbElement(objectFactory.createHdr());
//		Relationship rHdr = wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);
//		FooterPart footerPart = new FooterPart();
//		footerPart.setPackage(wordMLPackage);
//		footerPart.setJaxbElement(objectFactory.createFtr());
//		Relationship rFtr = wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
//
//		for (Object o : doc.getBody().getContent()) {
//			if (o instanceof org.docx4j.wml.P) {
//				if (((org.docx4j.wml.P) o).getPPr() != null) {
//					org.docx4j.wml.PPr ppr = ((org.docx4j.wml.P) o).getPPr();
//					if (ppr.getSectPr() != null) {
//						//...
//						if(!StringUtils.isEmpty(hr.getId()))
//							ppr.getSectPr().getEGHdrFtrReferences().add(hr);
//						//...
//					}
//				}
//			}
//		}
//
//		HeaderReference hr = objectFactory.createHeaderReference();
//		hr.setType(HdrFtrRef.DEFAULT);
//		FooterReference fr = objectFactory.createFooterReference();
//		fr.setType(HdrFtrRef.DEFAULT);
//		hr.setId(findRelationshipByTarget(wordMLPackage.getRelationshipsPart(),  String.format("/word/header1_%d.xml", lastHeaderReference)));
//		fr.setId(findRelationshipByTarget(wordMLPackage.getRelationshipsPart(),  String.format("/word/footer1_%d.xml", lastHeaderReference)));
//
//		if(!StringUtils.isEmpty(hr.getId()))
//			wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr().getEGHdrFtrReferences().add(hr);
//		if(!StringUtils.isEmpty(fr.getId()))
//			wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr().getEGHdrFtrReferences().add(fr);
//	}
//
//	private String findRelationshipByTarget(RelationshipsPart rp, String target) throws InvalidFormatException {
//		//...            
//	}
//}