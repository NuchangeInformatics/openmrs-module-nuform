<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>

<p>My physician colleague once told me that he would use an EMR only if he can write on it like plain paper. Here it is!
    A onenote like experience for forms, as close to paper as it can get.<br/><br/><strong>Instructions to Use:</strong><br/><br/>1.
    Click on NuForm Icon on the dashboard.<br/>2. Upload your form image and click the 'down' arrow to transfer the name
    of the image below. You can also use an online image URL here.<br/>3. Create a NuForm definition. A general NuForm
    is patient independent while patient specific NuForms can be edited from the patient dashboard. A research data
    collection form might be general while a surgical checklist is patient specific.<br/>4. Fill/Write Nuforms by
    clicking on the Create Icon. General NuForms can be filled from the dashboard while patient specific ones can be
    filled from the patient dashboard.<br/><br/><strong>Annotation:</strong><br/><br/>If you have installed my 'Clinical
    Images' module, you can annotate your clinical images using nuform. A typical use case would be marking malignant
    cells on a histopathology slide image. Click on the 'Annotate' tab to create a patient-specific form with the image.<br/><br/><strong>Development:</strong><br/><br/>The
    inking engine is an angular2 project on GitHub. The NuForm is also available as an eForm for OSCAR EMR.</p>

<p><a href="http://nuchange.ca" target="_blank">Visit my blog.</a></p>

<%@ include file="/WEB-INF/template/footer.jsp"%>