
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id??>
        <#if user_idIsNumber>
            <#assign u = c.findBy(users, "id", user_id)!/>
        </#if>
    </#if>

    <#if u?has_content>
        <#list posts?filter(p -> p.user_id == user_id)?sort_by("id")?reverse as p>
            <@c.genPost p/>
        </#list>
    <#else>
        no such user
    </#if>
</@c.page>

