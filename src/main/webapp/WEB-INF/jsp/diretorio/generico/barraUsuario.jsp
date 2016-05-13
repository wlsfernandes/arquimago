<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<sec:authentication var="user" property="principal.user" />

<div class="overdrag"></div>
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="javascript:void(0);"><fmt:message key="S0001" /></a>
		</div>
		
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a class="edituser" href="javascript:alert('Em desenvolvimento!!');" data-toggle="modal" > <!-- data-target="#userpanel" -->
						<img class="img-circle avatar" width="28px" height="28px" src="resources/img/avatars/default.png" />
						<span class="hidden-sm">
							<strong>${user}</strong>
						</span>
					</a>
				</li>
				<li>
					<a id="exit" title='<fmt:message key="S0013" />' href='<c:url value="j_spring_security_logout" />'>
						<i class="fa fa-sign-out fa-fw"></i>
						<span class="hidden-sm"> 
							<fmt:message key="S0013" />
						</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</nav>
