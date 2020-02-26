<#assign webTitle="管理平台" in model>
<#assign webHead in model>
<link rel="stylesheet" href="${model.static_domain}/css/admin/login.css?v=${model.static_version}">
<script src="/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript">
	function autoSubmit(){
		var projectName = document.getElementById("projectName").value;
		var projectType = document.getElementById("projectType").value;
		var reportURL = FR.cjkEncode("../ReportServer?reportlet=ProjectTaskBookQuery.cpt&projectName="+projectName+"&projectType="+projectType);
		document.paraForm.action=reportURL;
		document.paraForm.submit();
	}
</script>
</#assign>
<@model.webhead />
<div id="wrapper">
	<div class="content">
		<div>
			<form name="paraForm" method="post" target="reportFrame">
				项目名称：<input type="text" id="projectName" name="projectName" /> 
				项目类型：<select id="projectType" name="projectType" style="width:150px;">
							<option value="">全部</option>
							<option value="1">类型1</option>
							<option value="2">类型2</option>
					   </select>
				<input type="button" id="doQuery"  value="查询"   onclick="autoSubmit()"/>
			</form>
		</div>
		<div>
			<iframe id="reportFrame" name="reportFrame" width="100%" height="500" scrolling="no" border="0"  />
		</div>
	</div>
</div>

<#assign webEnd in model >
<script src="${model.static_domain}/js/admin/root/encrypt.min.js?v=${model.static_version}"></script>
<script src="${model.static_domain}/js/admin/root/login.min.js?v=${model.static_version}"></script>

</#assign>
<@model.webend />

