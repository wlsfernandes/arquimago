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

	this.prepararCarregamentoConteudoListaArquivo();
	
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
		'alt': bundle.get('M0077')
	}).appendTo($carregandoConteudoPerspectivaListaArquivo);
	
	$('<p>', {
		'text': bundle.get('M0077') 
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
		'class' : 'table dataTable no-footer'
	}).appendTo(this.$containerListaArquivo);
	
	// Crio o cabe√ßalho da tabela
	var $cabecalhoTabelaDataTable = $('<thead>').appendTo($tabelaDataTable);
	this.montaCabecalho($cabecalhoTabelaDataTable);
		
	var $conteudoTabela = $('<tbody>').appendTo($tabelaDataTable);
	
	var cont = 1;
		
	for (arquivo in this.listaArquivo) {
		var $parImpar = (cont % 2 == 0) ? "even" : "odd";
		
		var $tr =  $('<tr>', {'class' : 'rowa gallindex '+ $parImpar});
		var $linhaTabela = $tr.appendTo($conteudoTabela);
		
		var $tdSelect =  $('<td>', {'class' : 'checkb centertext'});
		var $divSelect =  $('<div>', {'class' : 'checkbox checkbox-primary checkbox-circle'});
		var $labelSelect =  $('<label>', {'class' : 'round-butt'});
		var $inputSelect = $('<input>', {'type' : 'checkbox', 'class' : 'selecta', 'value' : this.listaArquivo[arquivo].objectId});
		$inputSelect.appendTo($labelSelect); $labelSelect.appendTo($divSelect); $divSelect.appendTo($tdSelect); $tdSelect.appendTo($linhaTabela);
		
		$('<td>').text(this.listaArquivo[arquivo].descricao).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].tamanhoArquivo).appendTo($linhaTabela);
		$('<td>').text(this.listaArquivo[arquivo].dtUltimaAtualizacao).appendTo($linhaTabela);
		$('<td>').text(' ');
		$('<td>').text(' ');
		
		var $menuAcoes = $('<div>', {
			'class' : 'menu-acoes' 
		}).data('identificador', this.listaArquivo[arquivo].identificador);

		$('<a>', {
			'class' : 'glyphicon glyphicon-eye-open black visualizar-metadado',
			'data-toggle' : 'tooltip',
			'href' : this.basePath + '/interno/metadado/visualizarArquivo/' + this.listaArquivo[arquivo].objectId,
			'title': bundle.get('M0079') 
		}).appendTo($menuAcoes);
		
		$('<a>', {
			'class' : 'icon-pencil editar-metadado',
			'data-toggle' : 'tooltip',
			'href' : this.basePath + '/interno/metadado/editarArquivo/' + this.listaArquivo[arquivo].objectId,
			'title': bundle.get('M0080') 
		}).appendTo($menuAcoes);

		
/*		$menuAcoes.appendTo($('<td>').appendTo($linhaTabela));*/
		cont++;
	};

	// Aplico o plugin  DataTable na tabela
	this.aplicarPluginDataTable($tabelaDataTable);
};

ControladorListaArquivo.prototype.montaCabecalho = function($cabecalhoTabelaDataTable) {

var $linhaCabecalho = $('<tr>').appendTo($cabecalhoTabelaDataTable);
	
	var $aSelectAllMenu = $('<a>', {'id' : 'selectall'});
	$('<i>', { 'class' : 'fa fa-check fa-lg' }).appendTo($aSelectAllMenu);
	$aSelectAllMenu.appendTo($('<th>').appendTo($linhaCabecalho));
	
	var $thFileNameMenu = $('<th>', { 'class' : 'mini h-filename' });
	$('<span>', { 'class' : 'sorta nowrap', 'text' : bundle.get('A0007') }).appendTo($thFileNameMenu);
	$thFileNameMenu.appendTo($linhaCabecalho);

	var $thSizeMenu = $('<th>', { 'class' : 'taglia reduce mini h-filesize' });
	$('<span>', { 'class' : 'centertext sorta nowrap', 'text' : bundle.get('A0006') }).appendTo($thSizeMenu);
	$thSizeMenu.appendTo($linhaCabecalho);

	var $thDtModificacaoMenu = $('<th>', { 'class' : 'reduce mini h-filedate' });
	$('<span>', { 'class' : 'centertext sorta nowrap', 'text' : bundle.get('A0005') }).appendTo($thDtModificacaoMenu);
	$thDtModificacaoMenu.appendTo($linhaCabecalho);
	
	var $aEditMenu = $('<a>', {
		'id' : 'selectall',
		'href' : 'javascript:alert("Em desenvolvimento");',
		'class' : 'mini centertext',
		'style' : 'width: 62px;'
	});
	
	$('<i>', { 'class' : 'fa fa-pencil' }).appendTo($aEditMenu);
	$aEditMenu.appendTo($('<th>').appendTo($linhaCabecalho));
	
	var $aDelMenu = $('<a>', {
		'id' : 'selectall',
		'href' : 'javascript:alert("Em desenvolvimento");',
		'class' : 'mini centertext',
		'style' : 'width: 53px;'
	});
	
	$('<a>', { 'class' : 'fa fa-trash-o' }).appendTo($aDelMenu);
	$aDelMenu.appendTo($('<th>').appendTo($linhaCabecalho));
};


/**
 * Aplica o plugin dataTable sobre uma tabela
 * 
 * @param $tabelaDataTable Tabela que recebebigr√° o plugin
 */
ControladorListaArquivo.prototype.aplicarPluginDataTable = function($tabelaDataTable) {
/*
    $('#sort').addClass('ghost');

    $.extend($.fn.dataTableExt.oStdClasses, {
        "sSortAsc": "header headerSortDown",
        "sSortDesc": "header headerSortUp",
        "sSortable": "header"
    }); 


    oTable = $('#sort').dataTable({ 
        "pagingType": 'full_numbers',
        "paging": true,
        "searching": true,
        "pageLength": 10,
      
        // hide pagination if we have only one page
        "drawCallback": function() { 
            var paginateRow = $(this).parent().find('.dataTables_paginate');                        
            paginateRow.css('display', 'block');
        },
      
        "columnDefs": [ 
            { 
                "targets": [ 0, 1, 3, 5], 
                "orderable": false, 
                "searchable": false
            },
            { 
                "targets": 2 , 
                "orderable": true, 
                "searchable": true,
                "type": 'natural'
            },
            { 
                "targets": 4 , 
                "orderData": 3,
                "searchable": false
            },
            { 
                "targets": 6 , 
                "orderData": 5,
                "searchable": false
            }
        ] ,

        "language": {
            "sEmptyTable":      "--",
            "sInfo":            "_START_ / _END_ - _TOTAL_ ",
            "sInfoEmpty":       "",
            "sInfoFiltered":    "",
            "sInfoPostFix":     "",
            "sLengthMenu":      "<i class='fa fa-list-ol'></i> _MENU_",
            "sLoadingRecords":  "<i class='fa fa-refresh fa-spin'></i>",
            "sProcessing":      "<i class='fa fa-refresh fa-spin'></i>",
            "sSearch":          "<span class='input-group-addon'><i class='fa fa-search'></i></span> ",
            "sZeroRecords":     "--",
            "oPaginate": {
                "sFirst": "<i class='fa fa-angle-double-left'></i>",
                "sLast": "<i class='fa fa-angle-double-right'></i>",
                "sPrevious": "<i class='fa fa-angle-left'></i>",
                "sNext": "<i class='fa fa-angle-right'></i>"
            }
        }
    });
      
    oTable.fnSort( [ [0, 'desc'] ] );

    $('#sort').removeClass('ghost');*/
};