<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#else>
        <#if user_id??>
            <#if user_idIsNumber>
                <#assign u = c.findBy(users, "id", user_id)!/>
            </#if>
        </#if>
    </#if>

    <#if u?has_content>
        <div class="user-box">
            <div class="user-info">
                <div class="title"><@c.userlink u/></div>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>posts count: <a href="/posts?user_id=${u.id}">${posts?filter(p -> p.user_id == u.id)?size}</a> </li>
                </ul>
            </div>
            <img src="https://userpic.codeforces.com/no-title.jpg" alt="user icon">
        </div>
        <div class="user-navigation">
            <#assign nextU = c.findNext(users, "handle", u.handle)!/>
            <#assign prevU = c.findPrev(users, "handle", u.handle)!/>
            <@getLink prevU "←"/>
            <@getLink nextU "→"/>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro getLink user symbol>
    <#if user?has_content>
        <a class="action-arrow" href="user?handle=${user.handle}">${symbol}</a>
    <#else>
        <div class="not-action-arrow">${symbol}</div>
    </#if>
</#macro>
