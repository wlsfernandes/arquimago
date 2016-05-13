package br.com.furnas.integracao.enumeration;

/**
 * Atributos usados no EMC para representar os metadados (atributos) dos tipos documentais.
 * @author Diego Costa - diego.csilva@montreal.com.br
 * @version 1.0
 */
public enum EnumDocumentMetadata {
	
	/** Atributos do EMC */
	OBJECT_ID ("r_object_id"),
	
	OBJECT_NAME ("object_name"),

	/** Tabela **/
	TABELA_TIPO_DOCUMENTAL_METADADO ("sac4_metadado"),
	TABELA_TIPO_DOCUMENTAL_MODELO ("sac4_modelo"),
	TABELA_TIPO_DOCUMENTAL_ANEXO_ENVIO_APROVACAO ("sac4_anexo_envio_aprovacao"),
	TABELA_TIPO_DOCUMENTAL_ANEXO_PARECER ("sac4_anexo_parecer"),
	TABELA_TIPO_DOCUMENTAL_EDITAL ("sac4_edital"),
	
	/** Pasta **/ 
	
	PASTA_TABELA_TIPO_DOCUMENTAL_METADADO ("METADADO"),
	
	/** Atributos por tipo documental */
	ID_METADADO ("id_metadado"),
	
	COD_METADADO ("cod_metadado"),
	
    DESC_METADADO ("desc_metadado"),
    
    STAT_METADADO ("stat_metadado"),
    
    TIT_METADADO ("tit_metadado"),
    
    ID_SECAO ("id_secao"), 
    
    DATA_REGISTRO ("atr_data_registro"),
    
    DATA_VALIDADE ("atr_data_validade"),
    
	PASTA_TABELA_TIPO_DOCUMENTAL_MODELO ("MODELO"),
	
	/** Atributos por tipo documental */
	ID_MODELO ("id_modelo"),
	
	COD_MODELO ("cod_modelo"),
	
    DESC_MODELO ("desc_modelo"),
    
    MODAL_MODELO ("modal_modelo"),
    
    NOM_MODELO ("nom_modelo"),
    
    SEQ_MODELO ("seq_modelo"),
    
    STAT_MODELO ("stat_modelo"),
    
    TP_MODELO ("tp_modelo"),
        
    VER_MODELO ("ver_modelo"),
    
    PASTA_TABELA_TIPO_DOCUMENTAL_ANEXO_ENVIO_APROVACAO ("ANEXO_ENVIO_APROVACAO"),
	
	/** Atributos por tipo documental */
	ID_ENVIO_APROVACAO ("id_envio_aprovacao"),
	
	PASTA_TABELA_TIPO_DOCUMENTAL_TAG_MULTIVALORADA ("TAG_MULTIVALORADA"),
		
	/** Atributos Tag Multivaloradas */
	
	ID_TAG_MULTIVALORADA("id_tag_multivalorada"),
	
    ID_TAG("id_tag"),
	
    DESC_TAG_MULTIVALORADA("desc_tag_multivalorada"),
    
    PARAGRAFO_DA_TAG("paragrafodatag"), 
    
    TABELA_TIPO_DOCUMENTAL_TAG("sac4_tag_multivalorada"),
    
    PASTA_TABELA_TIPO_DOCUMENTAL_ANEXO_PARECER ("ANEXO_PARECER"),
	
	/** Atributos por tipo documental */
	ID_PARECER ("id_parecer"),
	
 
	PASTA_TABELA_TIPO_DOCUMENTAL_EDITAL ("EDITAL"),
	
	ID_EDITAL ("id_edital"),
	
	DESC_EDITAL ("desc_edital"),
    
    NOM_EDITAL ("nom_edital"),
    
    DT_PREGAO ("dt_pregao"),
    
    STAT_EDITAL ("stat_edital");
	
	
    private final String name;       

    private EnumDocumentMetadata(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }

}