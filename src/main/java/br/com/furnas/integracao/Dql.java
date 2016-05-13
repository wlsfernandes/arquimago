package br.com.furnas.integracao;

import java.util.List;
import java.util.Map;

import br.com.furnas.integracao.services.DocumentumServices;
import br.com.furnas.integracao.services.impl.DocumentumServicesImpl;

public class Dql {
	
	public static void main(String[] args) {
	
		DocumentumServices service = (DocumentumServices) EMCDocumentumFactory.getService(new DocumentumServicesImpl());
		
		
		
		/* CONTAR QUANTOS DOCUMENTOS EXIWSTEM NA PAGINA.

		select distinct s.object_name, fr.r_folder_path from dm_sysobject (all) s, dm_sysobject_r sr, dm_folder_r fr
		where sr.i_position = -1 and
		sr.r_object_id = s.r_object_id and
		fr.r_object_id = sr.i_folder_id and
		fr.i_position = -1 and
		fr.r_folder_path like '/<ur cabinet name>/%'
		order by fr.r_folder_path, s.object_name

		*/
		
		/* PEGAR A PASTA E OS DOCUMENTOS RECURSIVAMENTE 
		 
		select dm.object_name, fr.r_folder_path
		from dm_sysobject s,
		dm_sysobject_r sr,
		dm_sysobject_r sr3,
		dm_folder_r fr,
		dm_document dm
		where s.i_is_deleted = 0
		and sr3.r_object_id = s.r_object_id
		and sr3.i_position = -1
		and sr.r_object_id = s.r_object_id
		and fr.r_object_id = sr.i_folder_id
		and fr.i_position = -1
		and dm.r_object_id = s.r_object_id
		and dm.r_object_type = 'dm_document'
		and FOLDER('/Temp/Subba Folder', descend)
		order by fr.r_folder_path,s.object_name
		
		*/
		
		
		
		StringBuilder sb = new StringBuilder();
		

		//sb.append("select * from dm_folder");
		//sb.append(" select * from dm_folder where any r_folder_path ='/SUPRIMENTOS/PORTAL_DO_FORNECEDOR/pasta1' ");
		
		//String dql1 = sb.toString();
		
		
		//List<Map<Object,Object>> list1 = service.buscarPorDql(dql1); System.out.println(list1.size()+" encontrados.");
		
		
		
		//String dql2 = "CREATE dm_folder OBJECT SET object_name = 'TESTE_MI_NAME',SET subject = 'xxxxxxx' SET acl_name = 'xxxxxxx'LINK '/SUPRIMENTOS/PORTAL_DO_FORNECEDOR'";
		String dql2 = "CREATE dm_folder OBJECT SET object_name='pasta1' LINK '/SUPRIMENTOS/PORTAL_DO_FORNECEDOR'";
		
		Integer list2 = service.executarDql(dql2); System.out.println(list2+" encontrados.");		
		
	}
	
}
