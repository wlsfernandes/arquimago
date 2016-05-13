package br.com.furnas.integracao.common;

import static br.com.furnas.integracao.constants.DocumentumConstants.AND;
import static br.com.furnas.integracao.constants.DocumentumConstants.ANY;
import static br.com.furnas.integracao.constants.DocumentumConstants.COMMA;
import static br.com.furnas.integracao.constants.DocumentumConstants.DATE;
import static br.com.furnas.integracao.constants.DocumentumConstants.DELETE;
import static br.com.furnas.integracao.constants.DocumentumConstants.DQL_PADRAO_ID_TYPE_NAME;
import static br.com.furnas.integracao.constants.DocumentumConstants.EQ;
import static br.com.furnas.integracao.constants.DocumentumConstants.EQ_QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECTS;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_ID;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_TYPE;
import static br.com.furnas.integracao.constants.DocumentumConstants.PARENTESIS_LEFT;
import static br.com.furnas.integracao.constants.DocumentumConstants.PARENTESIS_RIGHT;
import static br.com.furnas.integracao.constants.DocumentumConstants.QUOTE;
import static br.com.furnas.integracao.constants.DocumentumConstants.SET;
import static br.com.furnas.integracao.constants.DocumentumConstants.SPACE;
import static br.com.furnas.integracao.constants.DocumentumConstants.UNION;
import static br.com.furnas.integracao.constants.DocumentumConstants.UPDATE;
import static br.com.furnas.integracao.constants.DocumentumConstants.WHERE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.furnas.integracao.enumeration.EnumDocumentMetadata;
import br.com.furnas.integracao.enumeration.EnumDocumentType;

/**
 * Helper usado para montar queries no EMC.  
 * @author Diego Costa - diego.csilva@montreal.com.br
 */
public final class QueryCommons {

	/**
	 * Campos que possuem um ou mais valores, usados para montar query de consulta.
	 */
	public static final List<String> multivaluedFields = Arrays.asList( "atr_classe_comercial", "atr_socios", "atr_nome", "atr_outorgante", "atr_outorgado");

	public static final List<String> dateFields = Arrays.asList("atr_data_validade", "atr_data_registro");


	/**
	 * Constr�i query de update do metadados do documento de acordo com os partametros.
	 * 
	 * @param documentId - identificador do documento.
	 * @param typeDocument - tipo do documento que ser� atualizado.
	 * @param params - metadados que serao atualizados.
	 * @return String - dql montado a partir dos parametros.
	 */
	
	private static boolean isPrimitive(String value){
        boolean status=true;
        
        if(value.length()<1)
            return false;
        for(int i = 0;i<value.length();i++)
        {
            char c=value.charAt(i);
            if(Character.isDigit(c) || c=='.')
            {
                
            }else{
                status=false;
                break;
            }
        }
        return status;
    }

	public static boolean isNumeric(String str)
	{
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public static String buildUpdateDocument(String documentId, String typeDocument, Map<Object, Object> params) {

		StringBuilder query = new StringBuilder();

		Integer mapSizeParams = params.size();

		Integer countParams = 0;

		query.append(UPDATE).append(typeDocument).append(OBJECTS);

		for (Entry<Object, Object> param : params.entrySet()) {

			countParams++;

			if((String.valueOf(param.getKey()).equalsIgnoreCase(String.valueOf(EnumDocumentMetadata.DT_PREGAO))))
			{
				query.append(SET);
				query.append(String.valueOf(param.getKey()));
				query.append(EQ).append(DATE).append(PARENTESIS_LEFT).append(QUOTE).append(String.valueOf(param.getValue())).append(QUOTE).append(PARENTESIS_RIGHT);
			}
			else if((!String.valueOf(param.getKey()).equalsIgnoreCase(OBJECT_ID)) && (!String.valueOf(param.getKey()).equalsIgnoreCase(OBJECT_TYPE)))
			{
				query.append(SET);

				query.append(String.valueOf(param.getKey()));

				if(param.getValue() instanceof Integer)
				{
					query.append(EQ).append(String.valueOf(param.getValue()));
				}
				else
				{
					query.append(EQ_QUOTE).append(String.valueOf(param.getValue())).append(QUOTE);	
				}
				/*		
				else if((String.valueOf(param.getKey()).equalsIgnoreCase(String.valueOf(EnumDocumentMetadata.DATA_REGISTRO))) || 
				(String.valueOf(param.getKey()).equalsIgnoreCase(String.valueOf(EnumDocumentMetadata.DATA_VALIDADE))))
				{

					query.append(SET);
					query.append(String.valueOf(param.getKey()));
					query.append(EQ).append(DATE).append(PARENTESIS_LEFT).append(QUOTE).append(String.valueOf(param.getValue())).append(QUOTE).append(PARENTESIS_RIGHT);

				}
				else if(multivaluedFields.contains(String.valueOf(param.getKey()))){

					String[] values = (String[]) param.getValue();

					for(int a = 0; a < values.length; a++) {

						query.append(SET);

						query.append(String.valueOf(param.getKey())+"["+a+"]");

						query.append(EQ_QUOTE).append(values[a]).append(QUOTE);	

						if((a != ((values.length) -1)) && values.length > 1){

							query.append(COMMA);
						}
				}
				 */
				if(countParams != mapSizeParams){

					query.append(COMMA);
				}

			}

		}

		query.append(WHERE);

		query.append(SPACE).append(OBJECT_ID).append(SPACE);

		query.append(EQ_QUOTE).append(documentId).append(QUOTE);

		return query.toString();

	}

	/**
	 * Constr�i query de delete por id de um documento do EMC.
	 * 
	 * @param documentId - identificador do documento.
	 * @param typeDocument - tipo do documento que ser� atualizado.
	 * @return String - dql montado a partir dos parametros.
	 */
	public static String buildDeleteDocument(String documentId, String typeDocument) {		

		StringBuilder query = new StringBuilder();

		query.append(DELETE).append(typeDocument).append(OBJECTS);

		query.append(WHERE);

		query.append(SPACE).append(OBJECT_ID).append(SPACE);

		query.append(EQ_QUOTE).append(documentId).append(QUOTE);

		return query.toString();

	}


	/**
	 * Monta um dql com union entre todos os tipos documentais filtrando por cpf ou cnpj.
	 * 
	 * @param params - map que possui chave e valor para fazer filtro e buscar em todos os tipo documentais.
	 * @return String - resultado do dql gerado
	 */
	public static String buildQueryAllDocuments(Map<Object, Object> params) {

		Integer setSizeTypes = getDocumentTypes().size();

		Integer mapSizeParams = params.size();

		Integer countDocTypes = 0;

		StringBuilder query = new StringBuilder();

		for (String s : getDocumentTypes()) {

			Integer countDocParams = 0;

			query.append(String.format(DQL_PADRAO_ID_TYPE_NAME, s));

			query.append(WHERE);

			for (Entry<Object, Object> param : params.entrySet()) {

				if (multivaluedFields.contains(String.valueOf(param.getKey()))) {

					query.append(ANY);

				}

				query.append(String.valueOf(param.getKey()));

				query.append(EQ_QUOTE).append(String.valueOf(param.getValue())).append(QUOTE);

				countDocParams++;

				if(countDocParams != mapSizeParams){

					query.append(AND);

				}				

			}

			countDocTypes++;

			if(countDocTypes != setSizeTypes){

				query.append(UNION);

			}

		}

		return query.toString();

	}

	/**
	 * Obtem conjunto com nome unicos para os tipos documentais.
	 * 
	 * @return Set<String> - conjunto com os tipos documentais.
	 */
	private static Set<String> getDocumentTypes() {

		Set<String> set = new HashSet<String>();

		for (EnumDocumentType enumm : EnumDocumentType.values()) {

			set.add(enumm.getDocumentType());

		}

		return set;

	}


}
