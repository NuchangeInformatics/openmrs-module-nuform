<%
    ui.includeCss("nuform", "nuform.css")
%>
<script>
    var jq = jQuery;
    jq(document).ready(function () {
        jq("#tabs").tabs();

        //##### Add Visit note when the submit button is clicked #########
        jq(".add-visit-note").click(function (e) {

            e.preventDefault();

            var patientid = ${ patient.id };
            var conceptId = 162169;
            var obs = jq(this).data('consult');
            //obs = jq(obs).text();
            jq.post('${ ui.actionLink("addVisitNote") }', {
                        returnFormat: 'json',
                        PatientId: patientid,
                        conceptid: conceptId,
                        note: obs
                    },
                    function (data) {
                        if (data.indexOf("${NUFORM_CONSTANTS.SUCCESS}") >= 0) {
                            jq().toastmessage('showSuccessToast', "Visit Note Added");
                        } else {
                            jq().toastmessage('showErrorToast', "Error");
                        }

                    })
                    .error(function () {
                        jq().toastmessage('showErrorToast', "Error");
                    })

        });

    });
</script>

<!-- Title begins here -->
<div id="nuform-main" class="info-section nuform">
    <div class="info-header">
        <i class="icon-pencil"></i>

        <h3>NUFORM</h3>
    </div>


    <div id="tabs" class="ui-tabs">
        <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" role="tablist">
            <li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active">
                <a href="#nuform-tab" class="ui-tabs-anchor">
                    Nuorms
                </a>
            </li>
            <li class="ui-state-default ui-corner-top">
                <a href="#create-tab" class="ui-tabs-anchor">
                    Create
                </a>
            </li>
            <li class="ui-state-default ui-corner-top">
                <a href="#annotation-tab" class="ui-tabs-anchor">
                    Annotate
                </a>
            </li>
        </ul>

        <!-- Title Ends here -->

        <div id="nuform-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
            <div>
                <table class="nuformTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Edited</th>
                        <th>Created</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    <% nuforms.each { %>
                    <tr<% if (it.status != NUFORM_CONSTANTS.ACTIVE) { %> class="inactive" <% } %>>
                        <td>${it.id}</td>
                        <td>${it.last_edited_on}</td>
                        <td>${it.created_on}</td>
                        <% if (it.status == NUFORM_CONSTANTS.ACTIVE) { %>
                        <td>
                            <a href="${
                                    ui.pageLink("nuform", "nuform", [patientId: patient.id, nuformId: it.id, nuformDefId: it.nuformDef])}">
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

            </div>
        </div>


        <div id="create-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
            <table class="nuformTable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Form</th>
                    <th>Created</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>

                <% nuformdefs.each { %>
                <tr<% if (it.status != NUFORM_CONSTANTS.ACTIVE) { %> class="inactive" <% } %>>
                    <td>${it.id}</td>
                    <td>${it.backgroundImage} (${it.comments})</td>
                    <td>${it.created_on}</td>
                    <% if (it.status == NUFORM_CONSTANTS.ACTIVE) { %>
                    <td>
                        <a href="${ui.pageLink("nuform", "nuform", [patientId: patient.id, nuformDefId: it.id])}">
                            <i class="icon-pencil edit-action" title="Create"></i>
                        </a>
                        <a href="${ui.pageLink("nuform", "nuformListForDef", [nuformDefId: it.id])}">
                            <i class="icon-eye-open view-action" title="View"></i>
                        </a>
                        <a href="${ui.actionLink("nuform", "nuformUtils", "toggleDef", [nuformDefId: it.id])}">
                            <i class="icon-remove delete-action" title="Delete"></i>
                        </a>
                        <% } else { %>
                    <td>
                        <a href="${ui.actionLink("nuform", "nuformUtils", "toggleDef", [nuformDefId: it.id])}">
                            <i class="icon-undo delete-action" title="UnDelete"></i>
                        </a>

                        <% } %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <div id="annotation-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">

                Annotation

        </div>

    </div> <!--tabs-->
</div><!--nuclinic-main-->