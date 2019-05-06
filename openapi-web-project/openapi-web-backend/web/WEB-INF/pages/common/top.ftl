<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>${webTitle?default("管理平台")}</title>
		<link rel="shortcut icon" href="${model.static_domain}/images/admin/ui/favicon.ico" type="image/x-icon">
		<link rel="stylesheet" href="${model.static_domain}/css/admin/base.css?v=${model.static_version}">
		<link rel="stylesheet" href="${model.static_domain}/css/admin/global.css?v=${model.static_version}">
		<script src="${model.static_domain}/js/lits/jquery.1.7.2.min.js"></script>
		<script src="${model.static_domain}/js/global.min.js?v=${model.static_version}"></script>
		<script src="${model.static_domain}/js/plugins/cookie/jquery.cookie.js?v=${model.static_version}"></script>
		<script src="${model.static_domain}/js/admin/common/menu.min.js?v=${model.static_version}"></script>
		${webHead?if_exists}
	</head>
	<body <#if webNav??>class="bg"</#if>>