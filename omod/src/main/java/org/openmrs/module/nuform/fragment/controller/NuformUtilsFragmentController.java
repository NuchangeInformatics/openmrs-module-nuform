package org.openmrs.module.nuform.fragment.controller;

import org.openmrs.User;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nuform.Nuform;
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

    public void toggleDef(@RequestParam(value = "nuformDefId", required = true) int nuformDefId,
                          Errors errors,
                          UiUtils ui) {

        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        NuformService nuformService = Context.getService(NuformService.class);
        NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
        if (nuformDef.getStatus().equals(NuformConstants.ACTIVE)) {
            nuformDef.setStatus(NuformConstants.DELETED);
            nuformDef.setDeleted_by(user.toString());
            nuformDef.setDeleted_on(cal.getTime());
        } else
            nuformDef.setStatus(NuformConstants.ACTIVE);

        NuformDef saved = nuformService.saveNuformDef(nuformDef);
    }

    public void toggleNuform(@RequestParam(value = "nuformId", required = true) int nuformId,
                             Errors errors,
                             UiUtils ui) {

        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        NuformService nuformService = Context.getService(NuformService.class);
        Nuform nuform = nuformService.getNuformById(nuformId);
        if (nuform.getStatus().equals(NuformConstants.ACTIVE)) {
            nuform.setStatus(NuformConstants.DELETED);
            nuform.setDeleted_by(user.toString());
            nuform.setDeleted_on(cal.getTime());
        }
        else
            nuform.setStatus(NuformConstants.ACTIVE);

        Nuform saved = nuformService.saveNuform(nuform);
    }


    public void purgeDef(@RequestParam(value = "nuformDefId", required = true) int nuformDefId,
                         Errors errors,
                         UiUtils ui) {
        NuformService nuformService = Context.getService(NuformService.class);
        NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
        nuformService.purgeNuformDef(nuformDef);
    }

    public void purgeNuform(@RequestParam(value = "nuformId", required = true) int nuformId,
                            Errors errors,
                            UiUtils ui) {
        NuformService nuformService = Context.getService(NuformService.class);
        Nuform nuform = nuformService.getNuformById(nuformId);
        nuformService.purgeNuform(nuform);
    }
}
