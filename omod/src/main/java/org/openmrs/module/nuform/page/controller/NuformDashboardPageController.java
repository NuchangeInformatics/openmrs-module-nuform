package org.openmrs.module.nuform.page.controller;

import org.apache.commons.io.FileUtils;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.expression.EvaluationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by beapen on 11/07/16.
 */
public class NuformDashboardPageController {

    public void controller(UiSessionContext sessionContext,
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
    }


}
