$(document).ready(function() {
	window.document.controladorDiretorio = new ControladorDiretorio();
});

function ControladorDiretorio() {
	this.listaPasta;
	this.init();
	this.basePath = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname.split('/')[1];
}

ControladorDiretorio.prototype.init = function() {
	this.carregarRepositoriosPastasEDocumentos();
};

ControladorDiretorio.prototype.carregarRepositoriosPastasEDocumentos = function() {
	this.bindSelecaoRepositoriosPastasEDocumentos($('#tabFiltro'));
};

ControladorDiretorio.prototype.bindSelecaoRepositoriosPastas = function($conteudoRepositoriosPastas) {
	var _self = this;

	var $cmbCabinets = $conteudoRepositoriosPastas.find('.cabinets');
	var $cmbFolders = $conteudoRepositoriosPastas.find('.folders');
	var $txtDocs = $conteudoRepositoriosPastas.find('.docs');

	var bind = function()
	{
		var idCabinet = $cmbCabinets.val() == "" ? 0 : $cmbCabinets.val();
		
		if(idCabinet != 0)
		{
			_self.carregarDadosFolder($conteudoRepositoriosPastas, idCabinet);
		} 
		else 
		{
			$cmbFolders.attr('disabled', 'true');
			$txtDocs.attr('disabled', 'true');
		}
	};

	bind();


	$cmbCabinets.change(function(){
		if ($cmbCabinets.val() != "") {
			_self.carregarDadosFolder($conteudoRepositoriosPastas, idCabinet);	
		}
	});
};

ControladorDiretorio.prototype.carregarDadosFolder = function($conteudoRepositoriosPastas, idCabinet) {
	var promiseDadosPastas = this.carregarAjaxDadosPastas(idCabinet);

	promiseDadosPastas.done(function() {
		this.listaPasta = promiseDadosPastas.responseJSON;
		
		var $cmbFolders = $conteudoRepositoriosPastas.find('.folders');
		for ( var posicao in this.listaPasta) {
			$("<option />",
			{
				value : listaPasta[posicao],
				text : listaPasta[posicao]
			}).appendTo($cmbFolders);
		}
	});
};

ControladorDiretorio.prototype.carregarAjaxDadosPastas = function(idCabinet) {
	return $.ajax({
		'dataType' : 'json',
		'type' : 'POST',
		'url' : 'interno/diretorio/carregarPastas',
		'data' : {
			idCabinet : idCabinet
		}
	});
};