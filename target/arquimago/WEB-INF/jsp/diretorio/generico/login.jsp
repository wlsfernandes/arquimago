<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
	<div id="error">
		<noscript>
			<div class="response boh"><span><i class="fa fa-exclamation-triangle"></i><fmt:message key="L0005" /></span></div>
		</noscript>
		<div class="response nope" style="display: none;"><fmt:message key="L0006" /> <i class="fa fa-times closealert"></i></div>
	</div> <!-- div id="error" -->
	
	<section class="vfmblock">
        <div class="login">
			<div class="panel panel-default">
                <div class="panel-body">
                    <form name="f" action="j_spring_security_check" method="post" >
                        <div id="login_bar" class="form-group">
                            <div class="form-group">
                                <label class="sr-only" for="user_name"><fmt:message key='L0007' /></label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
                                    <input type="text" id="username" name="j_username" class="form-control ricevi1" placeholder="<fmt:message key='L0007' />" />
								</div>
							</div>
                            <div class="form-group">
                                <label class="sr-only" for="user_pass"><fmt:message key="L0008" /></label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-fw"></i></span>
                                    <input type="password" id="password" name="j_password" class="form-control ricevi2" 
                                    placeholder="<fmt:message key='L0008' />" />
								</div>
							</div>
							
                            <div class="checkbox">
                                <label><input type="checkbox" name="vfm_remember" value="yes"><fmt:message key="L0001" /></label>
							</div>
							
							<button type="submit" class="btn btn-primary btn-block" /><i class="fa fa-sign-in"></i><fmt:message key="L0002" /></button>
						</div>
					</form>
					<p><a href="javascript:alert('Em desenvolvimento.');"><fmt:message key="L0003" /></a></p>
				</div>
			</div>
			<p><a class="btn btn-default btn-block" href="javascript:alert('Em desenvolvimento.');"><i class="fa fa-user-plus"></i><fmt:message key="L0004" /></a></p>
		</div>
	</section>
</div> <!-- div class="container"" -->

<!-- 
<div class="modal fade zoomview" id="zoomview" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<div class="modal-title">
						<a class="vfmlink btn btn-primary" href="#"><i class="fa fa-download fa-lg"></i></a> <span class="thumbtitle"></span>
					</div>
				</div>
				<div class="modal-body">
					<div class="vfm-zoom"></div>
					<div style="position:absolute; right:10px; bottom:10px;">Custom Watermark</div>                
				</div>
			</div> <! -- div class="modal-content" - ->
		</div> <! -- div class="modal-dialog" - ->
	</div> <! -- div class="modal fade zoomview" - ->
-->

