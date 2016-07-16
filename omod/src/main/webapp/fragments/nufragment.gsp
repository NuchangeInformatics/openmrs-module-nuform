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
                        if (data.indexOf("${MESSAGE_SUCCESS}") >= 0) {
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
                    Patient Forms
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
            <div><a class="info-body">

            </div>
        </div>


        <!-- Footer Below-->

        <div id="annotation-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
            <p>
            </p>

            <p>
                Annotation
            </p>
        </small>

        </div>

    </div> <!--tabs-->
</div><!--nuclinic-main-->