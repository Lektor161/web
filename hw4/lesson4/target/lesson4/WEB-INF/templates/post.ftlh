<#-- @ftlvariable name="post_id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign p = c.findBy(posts, "id", post_id)!/>

    <#if p?has_content>
        <@c.genPost p true/>
    <#else>
        No such post
    </#if>
</@c.page>
