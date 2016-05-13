package br.com.furnas.integracao.enumeration;

import java.util.HashMap;
/**
 * Atributos usados no EMC para representar os tipos documentais.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public enum EnumDocumentType {
	
	SAC4_ENTIDADE_METADADO(1, "sac4_metadado"),
	
	SAC4_ENTIDADE_MODELO(2, "sac4_modelo"),
	
	SAC4_ENTIDADE_EDITAL(3, "sac4_edital"),
	
	SAC4_ENTIDADE_ANEXO_ENVIO_APROVACAO(4, "sac4_anexo_envio_aprovacao"),
	
	SAC4_ENTIDADE_ANEXO_PARECER(5, "sac4_anexo_parecer"),
	
	SAC4_ENTIDADE_TAG_MULTIVALORADA(6, "sac4_tag_multivalorada")
	;
	
	private static HashMap<Integer, EnumDocumentType> map = new HashMap<Integer, EnumDocumentType>();

	private Integer documentId;
	
	private String documentType;
	
    static {
    	
        for (EnumDocumentType enumm : EnumDocumentType.values()) {
        	
            map.put(enumm.documentId, enumm);
            
        }
        
    }

	public String getDocumentType() {
		return documentType;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	EnumDocumentType(Integer documentId, String documentType) {
		
		this.documentId = documentId;
		
		this.documentType = documentType;
		
	}
    
    public static EnumDocumentType valueOf(Integer documentId) {
    	
        return map.get(documentId);
        
    }

}
