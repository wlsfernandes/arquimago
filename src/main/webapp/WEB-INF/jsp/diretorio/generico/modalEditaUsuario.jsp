<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:requestEncoding value="UTF-8" />
<fmt:setLocale value="pt_BR" />

<sec:authentication var="user" property="principal.user" />

<div class="modal userpanel fade" id="userpanel" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"><fmt:message key="L0009" /></span></button>
				<ul class="nav nav-pills" role="tablist">
					<li role="presentation" class="active">
						<a href="#upprof" aria-controls="home" role="tab" data-toggle="pill"><i class="fa fa-edit"></i><fmt:message key="L0010" /></a>
					</li>
					<li role="presentation">
						<a href="#upava" aria-controls="home" role="tab" data-toggle="pill"><i class="fa fa-user"></i><fmt:message key="L0011" /></a>
					</li>
				</ul>
			</div>
			
			<div class="modal-body">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade text-center" id="upava">
						<div class="avatar-panel">
							<div class="image-editor">
								<label for="uppavatar" class="upload-wrapper">
									<input type="file" id="uppavatar" class="cropit-image-input"  disabled="disabled">
								</label>
								
								<i class="fa fa-times fa-lg pull-right text-muted remove-avatar"></i>
								
								<div class="updated">
								</div>
								
								<input type="hidden" class="image-name" value="91ec1f9324753048c0096d036a694f86">
								<div class="cropit-image-preview"></div>
								<div class="image-size-wrapper">         
									<input type="range" class="cropit-image-zoom-input slider">
								</div>
							</div>
						</div>
						
						<div class="uppa btn btn-default"><i class="fa fa-upload fa-fw"></i><fmt:message key="L0012" /></div>
						<div class="export btn btn-primary hidden"><i class="fa fa-check-circle fa-fw"></i><fmt:message key="L0013" /></div>
					</div> <!-- tabpanel -->
					
					<div role="tabpanel" class="tab-pane fade in active" id="upprof">
						<form role="form" method="post" id="usrForm" autocomplete="off">
							
							<div class="form-group">
								
								<label for="user_new_name"><fmt:message key="L0007" /></label>
								<input name="user_old_name" type="hidden" readonly class="form-control" value="${user}">
								
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
									<input name="user_new_name" type="text" class="form-control" value="${user}">
								</div>
								
								<label for="user_new_email"><fmt:message key="L0014" /></label>
								<input name="user_old_email" type="hidden" readonly class="form-control" value="">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-envelope fa-fw"></i></span>
									<input name="user_new_email" type="text" 
									class="form-control" value="">
								</div>
								<label for="user_new_pass"><fmt:message key="L0015" /></label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-fw"></i></span>
									<input name="user_new_pass" id="newp" type="password" class="form-control">
								</div>
								
								<label for="user_new_pass_confirm"><fmt:message key="L0016" /></label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock fa-fw"></i></span>
									<input name="user_new_pass_confirm" id="checknewp" type="password" class="form-control">
								</div>
								<label for="user_old_pass"><fmt:message key="L0018" /><fmt:message key="L0017" /></label> 
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-unlock fa-fw"></i></span>
									<input name="user_old_pass" type="password" id="oldp" required class="form-control">
								</div>
								
							</div>
							
							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block" disabled="disabled"><i class="fa fa-refresh"></i><fmt:message key="L0013" /></button>
							</div>
						</form>
					</div> <!-- tabpanel -->
				</div><!-- tab-content -->
			</div> <!-- modal-body -->
		</div> <!-- modal-content -->
	</div> <!-- modal-dialog -->
</div> <!-- modal -->