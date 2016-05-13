$(document).ready(function() {
	window.document.controladorListaArquivo = new ControladorListaArquivo();
});

/**
 * Controlador da lista de Arquivos
 */
function ControladorListaArquivo()
{
	this.Arquivo;
	this.listaArquivo;
	this.basePath = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname.split('/')[1];
	this.init();
}

/**
 * FunÁ„o de inicializaÁ„o do controlador da lista de metadados
 */
ControladorListaArquivo.prototype.init = function() {
	this.$containerListaArquivo = $('#conteudo-lista-arquivos');
	this.iniciarCarregamentoListaArquivo();
};


function limparDivs()
{
	$("#conteudo-lista-arquivos-error" ).html ( "" );
	$("#conteudo-lista-arquivos-success" ).html ( "" );
}

/**
 * Inicializa o carregamento da lista de metadados
 */
ControladorListaArquivo.prototype.iniciarCarregamentoListaArquivo = function() {
	var _self = this;

	//this.prepararCarregamentoConteudoListaArquivo();
	
	var promiseListaArquivo = this.carregarListasArquivos();

	promiseListaArquivo.done(function() {
		_self.listaArquivo = promiseListaArquivo.responseJSON;
		_self.gerarEstruturaHtml();
	});	
	
	promiseListaArquivo.fail(function (){
		console.log( bundle.get('M0076') );
	});
	
	promiseListaArquivo.error( function(xhr, textStatus, error){
      console.log(xhr.statusText);
      console.log(textStatus);
      console.log(error);
	});
};

/**
 * Prepara o conte√∫do da lista de metadados com o loader para carregamento do
 * conte√∫do
 */
ControladorListaArquivo.prototype.prepararCarregamentoConteudoListaArquivo = function() {
	var $carregandoConteudoPerspectivaListaArquivo = $('<div>', {
		'class' : 'carregando-conteudo-lista-arquivos'
	}).appendTo(this.$containerListaArquivo);
	
	$('<img>', {
		'src': 'resources/img/loader-64-50px.gif',
		'alt': bundle.get('M0001')
	}).appendTo($carregandoConteudoPerspectivaListaArquivo);
	
	$('<p>', {
		'text': bundle.get('M0001') 
	}).appendTo($carregandoConteudoPerspectivaListaArquivo);
};


/**
 * Chamada ajax para carregamento da lista de metadados
 */
ControladorListaArquivo.prototype.carregarListasArquivos = function() {
	var identificadorPasta = $('input#objectId').val() == "" ? 0 : $('input#objectId').val();

	return $.ajax({
		'dataType': 'json',
		'type': 'POST',
		'url': 'interno/diretorio/carregarListasArquivos',
		'data' : {
			identificadorPasta : identificadorPasta
		},
	});
};


/**
 * Gera a estrutura html da lista de metadados
 */
ControladorListaArquivo.prototype.gerarEstruturaHtml = function() {
	this.$containerListaArquivo.contents().remove();

	// Crio a tabela com a lista de metadados
	var $tabelaDataTable = $('<table>', {
		'class' : 'table-files'
	}).appendTo(this.$containerListaArquivo);
	
	// Crio o cabe√ßalho da tabela
	var $cabecalhoTabelaDataTable = $('<thead>').appendTo($tabelaDataTable);
	this.montaCabecalho($cabecalhoTabelaDataTable);
		
	var $conteudoTabela = $('<tbody>').appendTo($tabelaDataTable);

	for (arquivo in this.listaArquivo) {
		
		var $linhaTabela = $('<tr>').appendTo($conteudoTabela);
		
		$('<td>').text('Selecionar').appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].descricao).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].tamanhoArquivo).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].dtUltimaAtualizacao).appendTo($linhaTabela);
		$('<td>').text('Editar').appendTo($linhaTabela);
		$('<td>').text('Excluir').appendTo($linhaTabela);
	
	};
	
	for (arquivo in this.listaArquivo) {
		
		var $linhaTabela = $('<tr>').appendTo($conteudoTabela);
		
		$('<td>').text('Selecionar').appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].descricao).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].tamanhoArquivo).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].dtUltimaAtualizacao).appendTo($linhaTabela);
		$('<td>').text('Editar').appendTo($linhaTabela);
		$('<td>').text('Excluir').appendTo($linhaTabela);
	
	};
	
for (arquivo in this.listaArquivo) {
		
		var $linhaTabela = $('<tr>').appendTo($conteudoTabela);
		
		$('<td>').text('Selecionar').appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].descricao).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].tamanhoArquivo).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].dtUltimaAtualizacao).appendTo($linhaTabela);
		$('<td>').text('Editar').appendTo($linhaTabela);
		$('<td>').text('Excluir').appendTo($linhaTabela);
	
	};

	// Aplico o plugin  DataTable na tabela
	this.aplicarPluginDataTable($tabelaDataTable);
};

ControladorListaArquivo.prototype.montaCabecalho = function($cabecalhoTabelaDataTable) {

	var $linhaCabecalho = $('<tr>').appendTo($cabecalhoTabelaDataTable);
	
	$('<th>').text('Selecionar').appendTo($linhaCabecalho);
	$('<th>').text(bundle.get('A0007')).appendTo($linhaCabecalho);
	$('<th>').text(bundle.get('A0006')).appendTo($linhaCabecalho);
	$('<th>').text(bundle.get('A0005')).appendTo($linhaCabecalho);
	$('<th>').text('Editar').appendTo($linhaCabecalho);
	$('<th>').text('Excluir').appendTo($linhaCabecalho);
};


/**
 * Aplica o plugin dataTable sobre uma tabela
 * 
 * @param $tabelaDataTable Tabela que recebebigr√° o plugin
 */
ControladorListaArquivo.prototype.aplicarPluginDataTable = function($tabelaDataTable) 
{
	// Aplico o Data Tables na tabela
	$tabelaDataTable.dataTable(/*{
	    language: {
	    	processing:		bundle.get('J0000'),
	        search:			bundle.get('J0001'),
	        lengthMenu:		bundle.get('J0002'),
	        info:           bundle.get('J0003'),
	        infoEmpty:      bundle.get('J0004'),
	        infoFiltered:   bundle.get('J0005'),
	        infoPostFix:    "",
	        zeroRecords:    bundle.get('J0006'),
	        emptyTable:     bundle.get('J0006'),
	        paginate: {
	            	first:      bundle.get('J0007'),
	            	previous:   bundle.get('J0008'),
	            	next:       bundle.get('J0009'),
	            	last:       bundle.get('J0010')
	        }
	    }
	}*/);
};