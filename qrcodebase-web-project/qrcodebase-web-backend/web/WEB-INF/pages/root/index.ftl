<#assign webMenu="index" in model />
<#assign webTitle="管理平台" in model/>
<@model.webhead />
<#include "/common/menu_top.ftl">
<div id="wrapper">
	<div class="content">
        <#include "/common/menu_left.ftl">
		<div class="index"></div>
	</div>
</div>
<#assign webEnd in model >
<#-- vue static pre cached -->
<link rel="stylesheet" href="${model.static_domain}/static/css/main.css">
<script src="${model.static_domain}/static/js/manifest.js"></script>
<script src="${model.static_domain}/static/js/vendor.js"></script>
</#assign>
<@model.webend />