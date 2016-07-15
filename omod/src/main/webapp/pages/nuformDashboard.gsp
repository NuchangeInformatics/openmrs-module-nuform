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
        {label: "${ ui.escapeJs(ui.message("nuform.nuformDashboard.title")) }"}
    ]
</script>
<style type="text/css">
#progressbox {
    position: relative;
    width: 400px;
    border: 1px solid #ddd;
    padding: 1px;
    border-radius: 3px;
}

#progressbar {
    background-color: lightblue;
    width: 0%;
    height: 20px;
    border-radius: 4px;
}

#percent {
    position: absolute;
    display: inline-block;
    top: 3px;
    left: 48%;
}
</style>
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
            //TODO: Delete function in fragment
            jq.post("${ ui.actionLink("nuform","NuFormDashboardPageController","deleteImage")}", {
                        returnFormat: 'json',
                        type: "data",
                        image: (filesList[image_pointer]).trim()
                    },
                    function (data) {
                        if (data.indexOf("${MESSAGE_SUCCESS}") >= 0) {
                            jq().toastmessage('showSuccessToast', "Image Deleted.");
                            location.reload();
                        } else {
                            jq().toastmessage('showErrorToast', "Error. Try again after page refresh");
                        }
                    })
                    .error(function () {
                        jq().toastmessage('showErrorToast', "Error. Try again after page refresh");
                    });
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
        <option value="general">General</option>
        <option value="patient">Patient Specific</option>
    </select><br>
    <label for="backgroundImage">Choose / Upload Form above.</label>
    <input name="backgroundImage" id="backgroundImage" type="text"/>
    <label for="nuform-comments">Comments</label>
    <textarea name="comment" id="nuform-comments"></textarea><br>
    <button type="submit" id="nuform-create">Create NuForm</button>
</form>

<hr> <!-- Create NuForm Ends Here -->
<h2>List of General Active NuForms</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Form Image</th>
        <th>Created On</th>
        <th>Type <br>(1=General,<br> 2=Patient)</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>

    <% nuformdefs.each { %>
    <tr>
        <td>${it.id}</td>
        <td>${it.backgroundImage}</td>
        <td>${it.created_on}</td>
        <td>${it.formtype}</td>
        <td>
            <i class="icon-pencil edit-action" title="Create"></i>
            <i class="icon-eye-open view-action" title="View"></i>
            <i class="icon-remove delete-action" title="Delete"></i>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>