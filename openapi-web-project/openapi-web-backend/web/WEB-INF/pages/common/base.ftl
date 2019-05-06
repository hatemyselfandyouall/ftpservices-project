<#assign platform="商户运营管理平台-">
<#if model.menu == "index">
	<#assign webTitle="管理平台" />
<#elseif model.menu == "merchant">
	<#assign column="商家资质">
	<#if model.menu_column == "edit">
		<#assign webTitle= platform + column + "-修改商家资料">
	<#elseif model.menu_column == "list">
		<#assign webTitle= platform + column>
	</#if>
<#elseif model.menu == "user">
	<#assign webNav="Y" in model>
</#if>