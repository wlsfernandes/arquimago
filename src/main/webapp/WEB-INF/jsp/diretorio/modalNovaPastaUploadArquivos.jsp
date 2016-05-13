<div class="style_novaPasta">
    <div class="label_novaPasta"> <i class="fa fa-upload"></i></div>
    <div class="stylepanel_novaPasta">
	   <section class="vfmblock uploadarea">        
		<form enctype="multipart/form-data" method="post" id="upForm">
			<input type="hidden" name="location" value="./uploads/Project-01/">       
			<div id="upload_container" class="input-group pull-left span-6">
				<span class="input-group-addon ie_hidden">
					<i class="fa fa-files-o fa-fw"></i>
				</span>
				<span class="input-group-btn" id="upload_file">
					<span class="upfile btn btn-primary btn-file">
						<i class="fa fa-files-o fa-fw"></i>
						<!-- <input name="userfile[]" type="file" class="upload_file" multiple onclick="javascript:alert('Em desenvolvimento!');" />  -->
					</span>
				</span>
				<input class="form-control" type="text" readonly name="fileToUpload" id="fileToUpload" onchange="fileSelected();" placeholder="<fmt:message key='U0001' />">
				<span class="input-group-btn">
					<button class="upload_sumbit btn btn-primary" type="submit" id="upformsubmit" disabled><i class="fa fa-upload fa-fw"></i></button>
					<a href="javascript:void(0);" class="btn btn-primary" id="upchunk1" disabled="disabled"><i class="fa fa-upload fa-fw"></i></a>
				</span>
			</div>
		</form>
		
<!-- 		<script type="text/javascript">
			resumableJsSetup(
			"no", 
			"%2Fvar%2Fwww%2Ffilemanager.veno.it%2Fdemo%2Fuploads%2FProject-01%2F&logloc=.%2Fuploads%2FProject-01%2F", 
			"Selecione um ou mais arquivos",
			0            );
		</script> -->
		<form enctype="multipart/form-data" method="post">
			<div id="newdir_container" class="input-group pull-right span-6">
				<span class="input-group-addon"><i class="fa fa-folder-open-o fa-fw"></i></span>
				<input name="userdir" type="text" class="upload_dirname form-control" placeholder="<fmt:message key='U0002' />" />
				<span class="input-group-btn">
					<button class="btn btn-primary upfolder" type="submit" onclick="javascript:alert('Em desenvolvimento!');">
						<i class="fa fa-plus fa-fw"></i>
					</button>
				</span>
			</div>
		</form>
		<div class="intero">           
			<div class="progress progress-striped active" id="progress-up">
				<div class="upbar progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
					<p class="pull-left propercent"></p>
				</div>
			</div>
		</div>
	</section>
    </div>
</div>