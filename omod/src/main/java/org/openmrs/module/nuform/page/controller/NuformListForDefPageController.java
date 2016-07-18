package org.openmrs.module.nuform.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by beapen on 16/07/16.
 */
public class NuformListForDefPageController {
    public void get(@RequestParam("nuformDefId") int nuformDefId,
                    PageModel model) {
        NuformService nuformService = Context.getService(NuformService.class);
        NuformDef nuformDef = nuformService.getNuformDefById(nuformDefId);
        List<Nuform> nuforms = nuformService.getAllNuformsByDef(nuformDef);
        model.addAttribute("nuforms", nuforms);
        model.addAttribute("nuformDefId", nuformDefId);
        model.addAttribute("NUFORM_CONSTANTS", NuformConstants.NUFORM_CONSTANTS());
    }
}
