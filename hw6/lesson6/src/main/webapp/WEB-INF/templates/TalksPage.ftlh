<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="" class="new-message">
        <input type="hidden" name="action" value="send"/>
        <select name="target-user-id">
            <#list users as u>
                <option value="${u.id}">${u.login}</option>
            </#list>
        </select>
        <div class="value">
            <textarea rows="10" cols="45" id="message" name="message"></textarea>
        </div>
        <div class="button-field">
            <input type="submit" value="Send">
        </div>
    </form>

    <div class="datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Message</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${c.findBy(users "id" talk.sourceUserId).login!}</td>
                    <td>${c.findBy(users "id" talk.targetUserId).login!}</td>
                    <td>${talk.text!}</td>
                    <td>${talk.creationTime!}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>