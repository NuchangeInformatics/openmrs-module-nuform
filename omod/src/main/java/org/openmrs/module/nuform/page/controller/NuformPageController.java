package org.openmrs.module.nuform.page.controller;

import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by beapen on 11/07/16.
 */
public class NuformPageController {

    public void get(@RequestParam("nuformId") int nuformId,
                    @RequestParam("nuformDefId") int nuformDefId,
                    @RequestParam("patientId") int patientId,
                    @RequestParam("lesionmap") String lesionmap,
                    @RequestParam("backgroundImage") String backgroundImage,
                    PageModel model) {
        model.addAttribute("nuformId", nuformId);
        model.addAttribute("nuformDefId", nuformDefId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("lesionmap", lesionmap);
        model.addAttribute("backgroundImage", backgroundImage);

    }
}
