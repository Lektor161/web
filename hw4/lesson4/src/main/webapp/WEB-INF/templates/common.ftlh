<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.href == uri>
                            <a class="cur-menu-item" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as p>
            <section>
                <div class="header">Post #${p.id}</div>
                <div class="body"><@genPostBody p/></div>
                <div class="footer">
                    <a href="/post?post_id=${p.id}">View all</a>
                </div>
            </section>
        </#list>

        <section>
            <div class="header">Information</div>
            <div class="body">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis
                fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
        <section>
            <div class="header">Information</div>
            <div class="body">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis
                fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
        <section>
            <div class="header">Information</div>
            <div class="body">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita facilis
                fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a style="color: ${user.color}; text-decoration: none; font-weight: bold"
           href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>


<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item_index + 1]/>
        </#if>
    </#list>
</#function>

<#function findPrev items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item_index - 1]/>
        </#if>
    </#list>
</#function>

<#macro genPost p fullText=false>
    <article>
        <#assign u=findBy(users, "id", p.user_id)!/>
        <a class="title" href="/post?post_id=${p.id}">
            <div class="title">${p.title}</div>
        </a>

        <div class="information">By <@userlink u/> , 2 days ago, translation</div>
        <div class="body">
            <@genPostBody p fullText/>
        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro genPostBody p fullText=false>
    <#if p.text?length <= 250>
        ${p.text}
    <#else>
        <#if fullText>
            ${p.text}
        <#else>
            ${p.text[0..250]}...
        </#if>
    </#if>
</#macro>