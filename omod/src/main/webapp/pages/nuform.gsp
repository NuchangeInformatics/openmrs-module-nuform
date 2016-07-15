<%
    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("nuform", "polyfills.bundle.js")
    ui.includeJavascript("nuform", "vendor.bundle.js")
    //Main has to be loaded after the other two
    //Otherwise it will lead to webpackJsonp is not defined
    ui.includeJavascript("nuform", "main.bundle.js")

    ui.includeJavascript("uicommons", "angular.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

%>
<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.escapeJs(ui.message("nuform.nuformDrawboard.title")) }"}
    ]
</script>

<script>
    var NUFORM = {
        'image': '../moduleServlet/nuform/NuformImageServlet?image=${backgroundImage}',
        'width': 640,
        'height': 830,
        'nuform_in': '${lesionmap}',
        'nuform_out': ''
    };

    function saveNuform() {
        var imagemap = NUFORM.nuform_out;
        imagemap = imagemap.replace(/\"/g, '!');
        jQuery("#lesionmap").val(imagemap);
        if (imagemap.length < 3)
            return confirm("Form not saved. Submit empty form?");
        console.log(imagemap);
        return true;
    }
</script>

<nuform-app>
    Loading...
</nuform-app>

<form onsubmit="return saveNuform()" method="post" action="${ui.pageLink("nuform", "nuform")}" name="FormName"
      id="FormName">

    <input name="lesionmap" id="lesionmap" type="hidden" value="">
    <input name="nuformid" id="nuformId" type="hidden" value="${nuformId}">
    <input name="patientId" id="patientId" type="hidden" value="${patientId}">
    <input name="nuformDefId" id="nuformDefId" type="hidden" value="${NuformDefId}">

    <input value="Submit" name="SubmitButton" id="SubmitButton" type="submit">

</form>