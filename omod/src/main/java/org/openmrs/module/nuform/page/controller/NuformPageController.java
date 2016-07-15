package org.openmrs.module.nuform.page.controller;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.nuform.Nuform;
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

    public void get(@RequestParam("nuformId") int nuformId,
                    @RequestParam("nuformDefId") int nuformDefId,
                    @RequestParam("patientId") int patientId,
                    @RequestParam(required = false, value = "lesionmap") String lesionmap,
                    PageModel model) {
        NuformService nuformService = Context.getService(NuformService.class);
        String backgroundImage = nuformService.getNuformDefById(nuformDefId).getBackgroundImage();
        if (lesionmap.isEmpty() && nuformId > 0)
            lesionmap = nuformService.getNuformById(nuformId).getLesionmap();
        model.addAttribute("nuformId", nuformId);
        model.addAttribute("nuformDefId", nuformDefId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("lesionmap", lesionmap);
        model.addAttribute("backgroundImage", backgroundImage);

    }

    public String post(@RequestParam("nuformId") int nuformId,
                       @RequestParam("nuformDefId") int nuformDefId,
                       @RequestParam("patientId") String patientId,
                       @RequestParam("lesionmap") String lesionmap,
                       Errors errors,
                       UiUtils ui) {

        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        NuformService nuformService = Context.getService(NuformService.class);
        NuformDef nuformDef = new NuformDef();
        Nuform nuform;
        if (nuformDefId > 0)
            nuformDef = nuformService.getNuformDefById(nuformDefId);
        if (nuformId > 0) {
            nuform = nuformService.getNuformById(nuformId);
            nuform.setLast_edited_by(user.toString());
            nuform.setLast_edited_on(cal.getTime());
        } else {
            nuform = new Nuform();
            nuform.setCreated_by(user.toString());
            nuform.setCreated_on(cal.getTime());
        }
        nuform.setPatientId(patientId);
        nuform.setLesionmap(lesionmap);
        Nuform saved = nuformService.saveNuform(nuform);

        SimpleObject redirectParams = new SimpleObject();
        if (saved.getId() != null)
            redirectParams.put("savedId", saved.getId());
        else
            redirectParams.put("savedId", 0);
        return "redirect:" + ui.pageLink("nuform", "nuformDashboard", redirectParams);
    }
}
