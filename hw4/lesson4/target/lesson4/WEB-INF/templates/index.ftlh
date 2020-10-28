<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?sort_by("id")?reverse as p>
        <@c.genPost p/>
    </#list>
</@c.page>