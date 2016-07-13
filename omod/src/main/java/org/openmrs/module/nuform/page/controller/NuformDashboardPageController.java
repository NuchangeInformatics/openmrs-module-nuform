package org.openmrs.module.nuform.page.controller;

import org.apache.commons.io.FileUtils;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.expression.EvaluationException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by beapen on 11/07/16.
 */
public class NuformDashboardPageController {

    public void controller(UiSessionContext sessionContext,
                           //@SpringBean NuformService nuformService,
                           PageModel model) throws EvaluationException, IOException {
        String sep = File.separator;
        // Folder in which images are saved
        File imgDir = new File(OpenmrsUtil.getApplicationDataDirectory() +
                sep + "nuform" + sep);

        if (!imgDir.exists()) {
            FileUtils.forceMkdir(imgDir);
        }
        ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(imgDir.list()));
        model.addAttribute("folder", imgDir);
        model.addAttribute("listOfFiles", fileNames);
        model.addAttribute("numberOfFiles", fileNames.size());
        model.addAttribute("MESSAGE_SUCCESS", NuformConstants.SUCCESS);
        model.addAttribute("MESSAGE_ERROR", NuformConstants.ERROR);
        //model.addAttribute("nuforms", nuformService.getAllNuforms(NuformConstants.ACTIVE));
    }

    public String post(@RequestParam("formtype") String formtype,
                       @RequestParam("backgroundImage") String backgroundImage,
                       @RequestParam("comment") String comment,
                       @SpringBean NuformService nuformService,
                       Errors errors,
                       UiSessionContext sessionContext,
                       UiUtils ui) {

        NuformDef nuformDef = new NuformDef();
        User user = Context.getAuthenticatedUser();
        Calendar cal = Calendar.getInstance();
        nuformDef.setCreated_by(user.toString());
        nuformDef.setCreated_on(cal.getTime());
        nuformDef.setFormtype(formtype);
        nuformDef.setBackgroundImage(backgroundImage);
        nuformDef.setStatus(NuformConstants.ACTIVE);
        nuformDef.setComments(comment);
        //NuformService nuformService = Context.getService(NuformService.class);
        NuformDef saved = nuformService.saveNuformDef(nuformDef);
        SimpleObject redirectParams = new SimpleObject();
        if (saved.getId() != null)
            redirectParams.put("savedId", saved.getId());
        else
            redirectParams.put("savedId", 0);
        return "redirect:" + ui.pageLink("nuform", "nuformDashboard", redirectParams);
    }
}
