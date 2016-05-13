<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<base href="${urlBase}">

<script type="text/javascript">
	$(function() {
		
		var widthModal = $(this).find('.media_pdf').css('width');
		widthModal = (widthModal == "0px" ? ($(this).width() * 0.85)
		: widthModal);
		$('a.media_pdf').media({
			width : widthModal
		});
	});
	
	/* $(document).ready(function() {
		
		$('#sort').dataTable({
			language : {
				processing : "<fmt:message key='J0000' />",
				search : "<fmt:message key='J0001' />",
				lengthMenu : "<fmt:message key='J0002' />",
				info : "<fmt:message key='J0003' />",
				infoEmpty : "<fmt:message key='J0004' />",
				infoFiltered : "<fmt:message key='J0005' />",
				infoPostFix : "",
				zeroRecords : "<fmt:message key='J0006' />",
				emptyTable : "<fmt:message key='J0006' />",
				paginate : {
					first : "<fmt:message key='J0007' />",
					previous : "<fmt:message key='J0008' />",
					next : "<fmt:message key='J0009' />",
					last : "<fmt:message key='J0010' />"
				}
			},
			
			"aoColumnDefs" : [ {
				"bSortable" : false,
				"aTargets" : [ 1 ]
			} ]
		});
	}); */
</script>

<div id="conteudo-lista-arquivos"></div> 
<%-- 
<section class="vfmblock tableblock" style="opacity: 1;">
	<div class="action-group">
		<div class="btn-group">
			<button type="button"
			class="btn btn-default dropdown-toggle groupact"
			data-toggle="dropdown" disabled="disabled">
				<i class="fa fa-cog"></i>
				<fmt:message key='A0001' />
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li><a class="multid"
					href="javscript:alert('Em desenvolvimento');"><i
						class="fa fa-cloud-download"></i>
					<fmt:message key='A0002' /></a></li>
					<li><a class="multic"
						href="javscript:alert('Em desenvolvimento');"><i
							class="fa fa-trash-o"></i>
						<fmt:message key='A0003' /></a></li>
			</ul>
		</div>
		<!-- .btn-group -->
		<button class="btn btn-default manda" disabled="disabled">
			<i class="fa fa-paper-plane"></i>
			<fmt:message key='A0004' />
		</button>
	</div>
	<!-- .action-group -->
	<form id="tableform">
		<div id="sort_wrapper" class="dataTables_wrapper no-footer">
			<table class="table table-striped dataTable no-footer" width="100%"
			id="sort" role="grid" aria-describedby="sort_info"
			style="width: 100%;">
				<thead>
					<tr class="rowa one" role="row">
						<th class="text-center sorting_disabled" rowspan="1" colspan="1"
						style="width: 64px;"><a href="javascript:void();"
						id="selectall"><i class="fa fa-check fa-lg"></i></a></th>
						<th class="mini header" tabindex="0" rowspan="1" colspan="1"
						style="width: 400px;"><span class="sorta nowrap"><fmt:message
						key='A0007' /></span></th>
						<th class="taglia reduce mini header" tabindex="0" rowspan="1"
						colspan="1" style="width: 165px;"><span
							class="hidden-xs centertext sorta nowrap"><fmt:message
							key='A0006' /></span></th>
							<th class="reduce mini header headerSortUp" tabindex="0"
							rowspan="1" colspan="1" style="width: 284px;"><span
								class="hidden-xs centertext sorta nowrap"><fmt:message
								key='A0005' /></span></th>
								<th class="mini centertext header" tabindex="0" rowspan="1"
								colspan="1" style="width: 62px;"><i class="fa fa-pencil"></i></th>
								<th class="mini centertext header" tabindex="0" rowspan="1"
								colspan="1" style="width: 53px;"><i class="fa fa-trash-o"></i></th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${formularioDeGED.listaArquivos}" var="lista">
						<tr class="rowa odd" role="row">
							<td class="checkb centertext itemicon"><input
								type="checkbox" name="selecta" class="selecta"
							value="${lista.objectId}"></td>
							<td class="icon centertext"><a href="${lista.objectId}"
								data-name="${lista.descricao}" data-link="${lista.linkArquivo}"
								class="full-lenght item file thumb"><i
								class="fa ${lista.tipoConteudo}"></i></a></td>
								<td class="name">
									<div class="relative">
										<a href="#myModal${lista.objectId}" class="media" role="button"
										data-toggle="modal" data-target="#myModal${lista.objectId}">${lista.descricao}</a>
										<span class="hover"><i class="fa fa-eye fa-fw"></i></span>
									</div>
								</td>
								
								<td class="mini reduce nowrap"><span
								class="hidden-xs centertext"> ${lista.tamanhoArquivo}</span></td>
								<td class="mini reduce sorting_1"><span
								class="hidden-xs centertext">${lista.dtUltimaAtualizacao}</span></td>
								
								
								<td class="icon rename centertext"><a
									href="javascript:alert('Em desenvolvimento!')"> <!-- data-thisdir="uploads/Project-01/" data-thisext="pdf" data-thisname="drush-cheat-sheet" -->
										<i class="fa fa-pencil-square-o"></i>
									</a></td>
									<td class="del centertext"><a
										data-name="drush-cheat-sheet.pdf"
										href="javascript:alert('Em desenvolvimento!')"> <i
											class="fa fa-times"></i>
										</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<c:forEach items="${formularioDeGED.listaArquivos}" var="lista">
			<div class="modal fade" id="myModal${lista.objectId}" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel"></h4>
						</div>
						<div class="modal-body">
							<a class="media_pdf" href="${lista.linkArquivo}"></a>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</form>
</section> --%>