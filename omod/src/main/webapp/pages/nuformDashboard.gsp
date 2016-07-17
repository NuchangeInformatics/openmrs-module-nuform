<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeFragment("appui", "standardEmrIncludes")
    ui.includeJavascript("nuform", "jquery.form.js")
    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

    ui.includeCss("nuform", "nuform.css")

    ui.includeFragment("referenceapplication", "infoAndErrorMessages")
%>
<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.escapeJs(ui.message("nuform.nuformDashboard.title")) }"}
    ]
</script>

<script>
    var jq = jQuery;
    var image_pointer = 0;
    var folder = "../moduleServlet/nuform/NuformImageServlet?image=";
    var filesList = "${listOfFiles}";
    var num_files = ${numberOfFiles};

    // File list
    filesList = filesList.slice(1, -1);
    filesList = filesList.split(",");


    jq(document).ready(function () {

        var confirmDeleteController = emr.setupConfirmationDialog({
            selector: '#confirmDeletePopup',
            actions: {
                cancel: function () {
                    confirmDeleteController.close();
                },
                confirm: function () {
                    confirmDeleteController.close();
                    jq.post("${ ui.actionLink("nuform","nuformUtils","deleteImage")}", {
                                returnFormat: 'json',
                                type: "data",
                                image: (filesList[image_pointer]).trim()
                            },
                            function (data) {
                                if (data.indexOf("${NUFORM_CONSTANTS.SUCCESS}") >= 0) {
                                    jq().toastmessage('showSuccessToast', "Image Deleted.");
                                    location.reload();
                                } else {
                                    jq().toastmessage('showErrorToast', "Error. Try again after page refresh");
                                }
                            })
                            .error(function () {
                                jq().toastmessage('showErrorToast', "Error. Try again after page refresh");
                            });
                }
            }
        });

        // Form Upload progressbar begin
        // Ref: simplecodestuffs file-upload-with-progress-bar-using-jquery-in-servlet
        var options = {
            beforeSend: function () {
                jq("#progressbox").show();
                // clear everything
                jq("#progressbar").width('0%');
                jq("#message").empty();
                jq("#percent").html("0%");
            },
            uploadProgress: function (event, position, total, percentComplete) {
                jq("#progressbar").width(percentComplete + '%');
                jq("#percent").html(percentComplete + '%');

                // change message text to red after 50%
                if (percentComplete > 50) {
                    jq("#message").html("<font color='red'>File Upload is in progress</font>");
                }
            },
            success: function () {
                jq("#progressbar").width('100%');
                jq("#percent").html('100%');
            },
            complete: function (response) {
                jq("#message").html("<font color='blue'>Your file has been uploaded!</font>");
                location.reload();
            },
            error: function () {
                jq("#message").html("<font color='red'> ERROR: unable to upload files</font>");
            }
        };
        jq("#UploadForm").ajaxForm(options);
        //Form Upload progressbar end


        jq("#but_left").click(function (e) {
            if (image_pointer > 0) image_pointer--;
            // Prevent showing blank image.
            if (filesList[image_pointer].trim().length > 0) {
                jq("#patientimg").attr('src', folder + (filesList[image_pointer]).trim());
                jq("#file_date").text(filesList[image_pointer]);
                jq("#but_delete").show();
            }
        });

        jq("#but_usethis").click(function (e) {
            if (filesList[image_pointer].trim().length > 0) {
                jq("#backgroundImage").val(filesList[image_pointer]);
            }
        });


        jq("#but_right").click(function (e) {
            if (image_pointer < num_files - 1) image_pointer++;
            if (filesList[image_pointer].trim().length > 0) {
                jq("#patientimg").attr('src', folder + (filesList[image_pointer]).trim());
                jq("#file_date").text(filesList[image_pointer]);
                jq("#but_delete").show();
            }
        });

        jq("#but_delete").click(function (e) {
            console.log("delete clicked!");
            confirmDeleteController.show();

        });

    });
</script>

<!-- img tag -->
<div id="file_date"></div>
<img alt="" id="patientimg" width="320" height="240"
     src="../ms/uiframework/resource/nuform/images/blank.png"/>

<br>
<!-- Buttons -->
<a class="button" id="but_left">
    <i class="icon-arrow-left"></i>
</a>
<a class="button" id="but_right">
    <i class="icon-arrow-right"></i>
</a>
<a class="button" id="but_usethis">
    <i class="icon-arrow-down"></i>
</a>
<a class="button" id="but_delete">
    <i class="icon-remove"></i>
</a>

<!-- Upload form -->
<div id="upload_form">
    <form id="UploadForm" method="post" action="../moduleServlet/nuform/NuformUploadServlet"
          enctype="multipart/form-data">
        Select Image to upload:
        <input type="file" size="60" id="myfile" name="myfile">
        <input type="submit" value="Upload"/>

        <div id="progressbox">
            <div id="progressbar"></div>

            <div id="percent">0%</div>
        </div>
        <br/>

        <div id="message"></div>
    </form>
</div>
<!-- Messages -->
<div id="responds"></div>

<hr> <!-- Form Upload Ends Here -->
<h2>Create NuForm</h2>

<form id="NuformCreate" method="post" action="${ui.pageLink("nuform", "nuformDashboard")}">
    <label for="formtype">Select Form Type. General forms are patient independent.</label>
    <select name="formtype" id="formtype">
        <option value="${NUFORM_CONSTANTS.GENERALFORM}">General</option>
        <option value="${NUFORM_CONSTANTS.PATIENTFORM}">Patient Specific</option>
    </select><br>
    <label for="backgroundImage">Choose / Upload Form above.</label>
    <input name="backgroundImage" id="backgroundImage" type="text"/>
    <label for="nuform-comments">Comments</label>
    <textarea name="comment" id="nuform-comments"></textarea><br>
    <button type="submit" id="nuform-create">Create NuForm</button>
</form>

<hr> <!-- Create NuForm Ends Here -->
<h2>List of General (Patient Independent) NuForms</h2>
<table class="nuformTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Form Image (Comments)</th>
        <th>Created On</th>
        <th>Type <br>(${NUFORM_CONSTANTS.GENERAL}=General,<br> ${NUFORM_CONSTANTS.PATIENT}=Patient)</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>

    <% nuformdefs.each { %>
    <tr<% if (it.status != NUFORM_CONSTANTS.ACTIVE) { %> class="inactive" <% } %>>
        <td>${it.id}</td>
        <td>${it.backgroundImage} (${it.comments})</td>
        <td>${it.created_on}</td>
        <td>${it.formtype}</td>
        <% if (it.status == NUFORM_CONSTANTS.ACTIVE) { %>
        <td>
            <a href="${ui.pageLink("nuform", "nuform", [nuformDefId: it.id])}">
                <i class="icon-file-alt edit-action" title="Create"></i>
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
            <a href="${ui.actionLink("nuform", "nuformUtils", "purgeDef", [nuformDefId: it.id])}">
                <i class="icon-remove delete-action" title="Purge"></i>
            </a>
            <% } %></td>
    </tr>
    <% } %>
    </tbody>
</table>

<div id="confirmDeletePopup" class="dialog" style="display: none">
    <div class="dialog-header">
        <i class="icon-remove"></i>

        <h3>${ui.message("nuform.delete.confirm")}</h3>
    </div>

    <div class="dialog-content">
        <p class="dialog-instructions">${ui.message("nuform.delete.explanation")}</p>

        <button class="confirm">${ui.message("referenceapplication.okay")}</button>
        <button class="cancel">${ui.message("nuform.delete.cancel")}</button>
    </div>
</div>