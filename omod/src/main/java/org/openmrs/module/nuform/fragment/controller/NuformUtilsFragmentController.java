package org.openmrs.module.nuform.fragment.controller;

import org.openmrs.User;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

/**
 * Created by beapen on 16/07/16.
 */
public class NuformUtilsFragmentController {

    public void controller(FragmentConfiguration config,
                           @SpringBean("patientService") PatientService patientService,
                           FragmentModel model) throws Exception {

    }

    public void deleteDef(@RequestParam(value = "nuformDefId", required = true) int nuformDefId,
                          Errors errors,
                          UiUtils ui) {

        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        NuformService nuformService = Context.getService(NuformService.class);
        NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
        nuformDef.setStatus(NuformConstants.DELETED);
        nuformDef.setDeleted_by(user.toString());
        nuformDef.setDeleted_on(cal.getTime());

        NuformDef saved = nuformService.saveNuformDef(nuformDef);
/*
        SimpleObject redirectParams = new SimpleObject();
        if (saved.getId() != null)
            redirectParams.put("savedId", saved.getId());
        else
            redirectParams.put("savedId", 0);
        return "redirect:" + ui.pageLink("nuform", "nuformDashboard", redirectParams);*/
    }
}
