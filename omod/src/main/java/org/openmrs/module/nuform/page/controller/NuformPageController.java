package org.openmrs.module.nuform.page.controller;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

/**
 * Created by beapen on 11/07/16.
 */
public class NuformPageController {

    public void get(@RequestParam(required = false, value = "nuformId", defaultValue = "0") int nuformId,
                    @RequestParam(required = false, value = "nuformDefId", defaultValue = "0") int nuformDefId,
                    @RequestParam(required = false, value = "patientId", defaultValue = "0") int patientId,
                    @RequestParam(required = false, value = "backgroundImage", defaultValue = "") String backgroundImage,
                    @RequestParam(required = false, value = "lesionmap", defaultValue = "") String lesionmap,
                    PageModel model) {
        NuformService nuformService = Context.getService(NuformService.class);
        String formtype = "";
        if (nuformDefId > 0) {
            NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
            backgroundImage = nuformDef.getBackgroundImage();
            formtype = nuformDef.getFormtype();
        }
        if (nuformId > 0)
            lesionmap = nuformService.getNuformById(nuformId).getLesionmap();
        else
            lesionmap = "";
        model.addAttribute("nuformId", nuformId);
        model.addAttribute("nuformDefId", nuformDefId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("lesionmap", lesionmap);
        model.addAttribute("backgroundImage", backgroundImage);
        model.addAttribute("formtype", formtype);
        model.addAttribute("NUFORM_CONSTANTS", NuformConstants.NUFORM_CONSTANTS());

    }

    public String post(@RequestParam(required = false, value = "nuformId", defaultValue = "0") int nuformId,
                       @RequestParam("nuformDefId") int nuformDefId,
                       @RequestParam(required = false, value = "patientId", defaultValue = "0") int patientId,
                       @RequestParam(required = false, value = "lesionmap", defaultValue = "") String lesionmap,
                       Errors errors,
                       UiUtils ui) {

        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        NuformService nuformService = Context.getService(NuformService.class);
        PatientService patientService = Context.getPatientService();
        Patient patient;
        if (patientId > 0)
            patient = patientService.getPatient(patientId);
        else
            patient = null;
        NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
        Nuform nuform;
        if (nuformId > 0) {
            nuform = nuformService.getNuformById(nuformId);
            nuform.setLast_edited_by(user.toString());
            nuform.setLast_edited_on(cal.getTime());
        } else {
            nuform = new Nuform();
            nuform.setCreated_by(user.toString());
            nuform.setCreated_on(cal.getTime());
        }
        nuform.setNuformDef(nuformDef);

        nuform.setPatient(patient);
        nuform.setLesionmap(lesionmap);
        Nuform saved = nuformService.saveNuform(nuform);

        SimpleObject redirectParams = new SimpleObject();
        if (saved.getId() != null)
            redirectParams.put("savedId", saved.getId());
        else
            redirectParams.put("savedId", 0);
        if (patientId > 0) {
            // If a patient specific form return to patient dashboard
            redirectParams.put("patientId", patientId);
            redirectParams.put("app", "pih.app.clinicianDashboard");
            return "redirect:" + ui.pageLink("coreapps", "clinicianfacing/patient", redirectParams);
        }
        // Else return to Nuform dashboard
        return "redirect:" + ui.pageLink("nuform", "nuformDashboard", redirectParams);
    }
}
