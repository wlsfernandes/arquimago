package br.com.furnas.integracao;

import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_NOME_ARQUIVO;
import static br.com.furnas.integracao.constants.DocumentumConstants.CHAVE_TIPO_DOCUMENTAL;
import static br.com.furnas.integracao.constants.DocumentumConstants.OBJECT_ID;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.furnas.integracao.services.DocumentumServices;
import br.com.furnas.integracao.services.impl.DocumentumServicesImpl;

public class DocumentumCrud {
	
	public  void gravar() throws Exception {
		
		DocumentumServices service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		String documentoId = "0902a94b80129357";
		String nomeTipoDocumental = "sac4_metadado";
		
		service.recuperarDocumento(documentoId, nomeTipoDocumental, null );
		


	}
}


