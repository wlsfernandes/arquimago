package br.com.furnas.util;


public interface FileUploadUtil {

	static final String REPOSITORIO_TEMP = 	"D:\\SAC4\\";
	static final String REPOSITORIO_DE_ENTRADA =  REPOSITORIO_TEMP + "\\entrada\\";
	static final String REPOSITORIO_DE_METADADO = REPOSITORIO_TEMP + "\\METADADO\\";
	static final String REPOSITORIO_DE_MODELO = REPOSITORIO_TEMP + "\\MODELO\\";
	static final String REPOSITORIO_DE_EDITAL = REPOSITORIO_TEMP + "\\EDITAL\\";
	static final String REPOSITORIO_DE_PARECER = "\\PARECER\\";
	static final String REPOSITORIO_DE_ANEXO = "ANEXO\\";
	static final String REPOSITORIO_DE_TAG = "\\TAG\\";
	static final String REPOSITORIO_DE_HISTORICO = "\\HISTORICO";
	static final String TIPO_DOCUMENTAL_METATADO = "sac4_metadado";
	
	public class DataFieldName {
		
		String name;
		
		public DataFieldName(String name) {
			
			this.name = name.toUpperCase();
		}
		
		
		@Override public boolean equals(Object aThat) {
		    
			if (aThat instanceof DataFieldName) {
				return ( name.equals(
						((DataFieldName)aThat).name
						) );	    
			} else {
				return super.equals(aThat);
			}
		}
		
		@Override public int hashCode() {
			return name.hashCode();
		}

	}
}
