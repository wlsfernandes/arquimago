<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="pt_BR" />

<c:set var="pageReq" value="${pageContext.request}" />
<c:set var="urlBase" value="${fn:replace(pageReq.requestURL, pageReq.requestURI, pageReq.contextPath)}/" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${urlBase}">

<title><fmt:message key="S0000" /></title>

<link rel="shortcut icon" href="resources/img/favicon.ico">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/vfm-style.css">
<!-- ++++++++++++++++++++++++++++++++++++ DEMO STUFF ++++++++++++++++++++++++++++++++++++ -->
<link rel="author" href="https://plus.google.com/110132773023219475486/"/>
<link rel="publisher" href="https://plus.google.com/110132773023219475486" />

<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/cs-navy.css">
<!-- <link rel="stylesheet" href="resources/css/vfm-2016.css"> -->
<link rel="stylesheet" href="resources/css/demo-style.css">

<script type="text/javascript" src="resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resources/js/app.js"></script>
<script type="text/javascript" src="resources/js/login.js"></script>
</head>

<body>
	<%-- <c:import url="/WEB-INF/jsp/diretorio/generico/modalEscolhaCor.jsp" charEncoding="UTF-8" /> --%>
	
	<!--  Menu do usuário com nome do usuário, avatar e log out-->
	<c:import url="/WEB-INF/jsp/diretorio/generico/cabecalhoLogin.jsp" charEncoding="UTF-8" />

	<c:import url="/WEB-INF/jsp/diretorio/generico/login.jsp" charEncoding="UTF-8" />
	
	<c:import charEncoding="UTF-8" url="/WEB-INF/jsp/diretorio/generico/footer.jsp" />
</body>
</html>