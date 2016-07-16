<%
    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("nuform", "jquery.form.js")
    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

%>
<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.escapeJs(ui.message("nuform.nuformList.title")) }"}
    ]
</script>

<h2>List of Active NuForms</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Last Edited On</th>
        <th>Created On</th>
        <th>Patient ID</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>

    <% nuforms.each { %>
    <tr>
        <td>${it.id}</td>
        <td>${it.last_edited_on}</td>
        <td>${it.created_on}</td>
        <td>${it.patientId}</td>
        <% if (it.status == NUFORM_CONSTANTS.ACTIVE) { %>
        <td>
            <a href="${
                    ui.pageLink("nuform", "nuform", [lesionmap: it.lesionmap, patientId: it.patientId, nuformId: it.id, nuformDefId: nuformDefId])}">
                <i class="icon-pencil edit-action" title="Edit"></i>
            </a>
            <a href="${ui.actionLink("nuform", "nuformUtils", "toggleNuform", [nuformId: it.id])}">
                <i class="icon-remove delete-action" title="Delete"></i>
            </a>

            <% } else { %>
        <td bgcolor="#a9a9a9">
            <a href="${ui.actionLink("nuform", "nuformUtils", "toggleNuform", [nuformId: it.id])}">
                <i class="icon-undo delete-action" title="UnDelete"></i>
            </a>
            <% } %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
