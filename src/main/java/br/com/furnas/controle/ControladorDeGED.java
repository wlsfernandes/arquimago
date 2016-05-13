package br.com.furnas.controle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.furnas.aplicacao.fachada.IFachadaDeAutenticacao;
import br.com.furnas.aplicacao.fachada.IFachadaDeGED;
import br.com.furnas.dominio.ValidacaoUtil;
import br.com.furnas.dominio.modelo.Arquivos;
import br.com.furnas.web.FormularioDeGED;

@Controller
@RequestMapping("/interno/diretorio")
public class ControladorDeGED {

	private static final String VISAO_LISTA_GED= "diretorio/listaGED";
	private static final String VISAO_LISTA_ARMARIOS_PASTAS = "diretorio/listaArmariosPastas";
	
	
	private IFachadaDeAutenticacao fachadaDeAutenticacao;
	private IFachadaDeGED fachadaDeGED;

	@Autowired
	private Validator validator;

	@Autowired
	private ValidacaoUtil validacaoUtil;

	@Autowired
	private ServletContext sCtx;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		binder.setValidator(validator);
	}

	@RequestMapping(value = "", method = { RequestMethod.GET })
	public ModelAndView listarMetadado() {
		ModelAndView mav = new ModelAndView(VISAO_LISTA_GED);
		initModel(mav);
		FormularioDeGED formularioDeGED = fachadaDeGED.carregarListaGED();
		mav.addObject("formularioDeGED", formularioDeGED);
	    	
		return mav;
	}
	
	private void initModel(ModelAndView mav)
	{
		mav.addObject("listaCabinets", fachadaDeAutenticacao.carregarListaCabinets());
	}

	@RequestMapping(value = "/listaArmariosPastas/{identificadorPasta}", method = { RequestMethod.GET })
	public ModelAndView listaArmariosPastas(@PathVariable String identificadorPasta) throws Exception {
		ModelAndView mav = new ModelAndView(VISAO_LISTA_GED);
		initModel(mav);
		
		FormularioDeGED formularioDeGED = fachadaDeGED.carregarListaGEDPastas(identificadorPasta);
		mav.addObject("formularioDeGED", formularioDeGED);
		return mav;
	}
	
	@RequestMapping(value = "/carregarListasArquivos", method = { RequestMethod.POST })
	public @ResponseBody List<Arquivos> carregarListasArquivos(String identificadorPasta) throws Exception {
		return fachadaDeGED.carregarListasArquivos(identificadorPasta);
	}
		
	@RequestMapping(value = "/carregarPastas/{identificadorMetadado}", method = { RequestMethod.POST })
	public @ResponseBody List<String> carregarListaMetadado() {
		return fachadaDeAutenticacao.carregarListaFolders();
	}
	
	public IFachadaDeAutenticacao getFachadaDeAutenticacao() {
		return fachadaDeAutenticacao;
	}

	public void setFachadaDeAutenticacao(
			IFachadaDeAutenticacao fachadaDeAutenticacao) {
		this.fachadaDeAutenticacao = fachadaDeAutenticacao;
	}

	public IFachadaDeGED getFachadaDeGED() {
		return fachadaDeGED;
	}

	public void setFachadaDeGED(IFachadaDeGED fachadaDeGED) {
		this.fachadaDeGED = fachadaDeGED;
	}
}