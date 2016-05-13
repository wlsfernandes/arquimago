<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="vfmblock tableblock" style="opacity: 1;">
	<table class="table table-striped table-condensed" width="100%" id="sortable">
		<thead> <!--  Lista com os ícones de modo de visão -->
			<tr class="rowa two">
				<td class="firstfolderitem icon text-center col-xs-4 col-sm-2">
					<a class="active" id="orderAlpha"><i class="fa fa-sort-alpha-asc"></i></a> 
					<a id="orderNumber"><i class="fa fa-calendar"></i></a>
				</td>
				<td></td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${not empty formularioDeGED.listaArmarios ? formularioDeGED.listaArmarios : formularioDeGED.listaPastas}" var="lista">
				<tr class="rowa">
					<td class="icon">
						<a href="${urlBase}interno/diretorio/listaArmariosPastas/${lista.objectId}">
							<span class="badge hidden-xs">
								<i class="fa fa-folder-o"></i>${lista.qtdPastas}
							</span>
							<span class="badge">
								<i class="fa fa-files-o"></i>${lista.qtdDocumentos}
							</span>
						</a>
					</td>
					<td class="name">
						<div class="relative">
							<a href="${urlBase}interno/diretorio/listaArmariosPastas/${lista.objectId}">
								<span class="icon text-center"><i class="fa fa-folder"></i></span>
								${lista.descricao} 
							</a>
							<span class="hover">
								<i class="fa fa-folder-open-o fa-fw"></i>
							</span>
						</div>
					</td>
				</tr>
			</c:forEach>						
		</tbody>
	</table>
</section>