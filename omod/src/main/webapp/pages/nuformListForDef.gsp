<%
    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("nuform", "jquery.form.js")
    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

    ui.includeCss("nuform", "nuform.css")

%>
<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.escapeJs(ui.message("nuform.nuformList.title")) }"}
    ]
</script>


<h2>List of NuForms</h2>
<table class="nuformTable">
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

    <% nuforms.each {
        if (!it.patient) {
            patientId = null
        } else {
            patientId = it.patient.id
        }
    %>
    <tr<% if (it.status != NUFORM_CONSTANTS.ACTIVE) { %> class="inactive" <% } %>>
        <td>${it.id}</td>
        <td>${it.last_edited_on}</td>
        <td>${it.created_on}</td>
        <td>${patientId}</td>
        <% if (it.status == NUFORM_CONSTANTS.ACTIVE) { %>
        <td>
            <a href="${
                    ui.pageLink("nuform", "nuform", [patientId: patientId, nuformId: it.id, nuformDefId: nuformDefId])}">
                <i class="icon-pencil edit-action" title="Edit"></i>
            </a>
            <a href="${ui.actionLink("nuform", "nuformUtils", "toggleNuform", [nuformId: it.id])}">
                <i class="icon-remove delete-action" title="Delete"></i>
            </a>

            <% } else { %>
        <td>
            <a href="${ui.actionLink("nuform", "nuformUtils", "toggleNuform", [nuformId: it.id])}">
                <i class="icon-undo delete-action" title="UnDelete"></i>
            </a>
            <% } %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
