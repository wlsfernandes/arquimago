<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="pt_BR" />
<fmt:setBundle basename="mensagens" />

<c:set var="pageReq" value="${pageContext.request}" scope="page" />
<c:set var="urlBase" value="${fn:replace(pageReq.requestURL, pageReq.requestURI, pageReq.contextPath)}/" scope="request" />

<html>
<head>
	<meta charset="UTF-8">
	<base href="${urlBase}">
	<link rel="shortcut icon" href="resources/img/favicon.ico">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/vfm-style.css">
	<link rel="stylesheet" href="resources/css/font-awesome.min.css">
	<link rel="stylesheet" href="resources/css/cs-navy.css">
	<!-- <link rel="stylesheet" href="resources/css/vfm-2016.css"> --> 
	<link rel="stylesheet" href="resources/css/demo-style.css">
	<link rel="stylesheet" href="resources/css/modalPesquisaArmariosPastasArquivos.css">
	<link rel="stylesheet" href="resources/css/modalNovaPastaUploadArquivos.css">
	<script type="text/javascript" src="resources/js/jquery-2.1.1.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/modals.js"></script>
 	<script type="text/javascript" src="resources/js/app.js"></script>	
	<script src="resources/js/avatars.js"></script>
	<script type="text/javascript" src="resources/js/resourceBundle.js"></script>
	<script src="resources/js/datatables.js"></script>
	<script src="resources/js/uploaders.js"></script>
	<script type="text/javascript" src="resources/js/jquery_media.js"></script>
	
	<script src="resources/js/menuLateralFuncionalidades.js"></script>
	<script type="text/javascript">

    $(document).ready(function(){
      Avatars('resources/img/avatars/default.png', 'resources/img/avatars/default.png');
    });
    </script>
    
	
</head>

<body id="uparea" class="vfm-body">
	<form:form name="form" class="formulario"	method="post" commandName="formularioDeGED">
		<%--  <c:import charEncoding="UTF-8" url="/WEB-INF/jsp/diretorio/generico/cabecalho.jsp" /> --%>
	 
	 		<c:if test="${formularioDeGED.modoVisaoUpload}">
	 			<c:import url="/WEB-INF/jsp/diretorio/modalNovaPastaUploadArquivos.jsp" charEncoding="UTF-8" />
	 			<c:import url="/WEB-INF/jsp/diretorio/modalPesquisaArmariosPastasArquivos.jsp" charEncoding="UTF-8" />
	 		</c:if> 
			<div class="container">
				<div id="error">
				<noscript>
					<div class="response boh">
                        <span><i class="fa fa-exclamation-triangle"></i> 
                            Please activate JavaScript</span>
                    </div>
                    
                 	<div class="response boh">
                        <span><i class="fa fa-exclamation-triangle"></i> 
                        	<fmt:message key='L0005' />
                        </span>
                    </div>
				</noscript>
				<!--  div class="response yep"> <strong>fajar2</strong> updated successfully<i class="fa fa-times closealert"></i></div> -->
				</div>
				
				<%-- <c:if test="${formularioDeGED.modoVisaoUpload}">
					<c:import charEncoding="UTF-8" url="/WEB-INF/jsp/diretorio/listaUpload.jsp" />
				</c:if> --%>
								
				<ol class="breadcrumb">
					<li><a href="interno/diretorio"><i class="fa fa-folder-open"></i>Root</a></li>
					<c:forEach items="${formularioDeGED.listaBreadcrumb}" var="listBreadcrumb">
						<!--  KEY: ${listBreadcrumb.objectId}  - VALUE: ${listBreadcrumb.descricao} -->
						<li><a href="${urlBase}interno/diretorio/listaArmariosPastas/${listBreadcrumb.objectId}"><i class="fa fa-folder-open-o"></i>${listBreadcrumb.descricao}</a></li>
					</c:forEach>
				</ol>
				  
				<c:if test="${formularioDeGED.modoVisaoPasta}">
					<c:import charEncoding="UTF-8" url="/WEB-INF/jsp/diretorio/listaArmariosPastas.jsp" />		
				</c:if>
	
				<c:if test="${formularioDeGED.modoVisaoArquivo}">
					<c:import charEncoding="UTF-8" url="/WEB-INF/jsp/diretorio/listaArquivos.jsp" />
				</c:if>
					
				<form:input type="hidden" path="identificador" />
				<form:input type="hidden" path="objectId" />
				<form:input type="hidden" path="listaBreadcrumb" />
			</div>
			
			<script type="text/javascript">

				$(document).ready(function() {
                	callFilesTable(
                    	'full_numbers',
                    	true,
                    	true,
                    	10,
                    	6,
                    	'desc'
                	);
            	});
			</script>	
			
		</form:form>
    </body>
</html>