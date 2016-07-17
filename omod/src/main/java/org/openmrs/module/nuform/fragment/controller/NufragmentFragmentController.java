package org.openmrs.module.nuform.fragment.controller;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformConstants;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by beapen on 11/07/16.
 */
public class NufragmentFragmentController {

    public void controller(FragmentConfiguration config,
                           @SpringBean("patientService") PatientService patientService,
                           FragmentModel model) throws Exception {
        // unfortunately in OpenMRS 2.1 the coreapps patient page only gives us a patientId for this extension point
        // (not a patient) but I assume we'll fix this to pass patient, so I'll code defensively
        Patient patient;
        config.require("patient|patientId");
        Object pt = config.getAttribute("patient");
        if (pt == null)
            patient = patientService.getPatient((Integer) config.getAttribute("patientId"));
        else
            patient = (Patient) (pt instanceof Patient ? pt : PropertyUtils.getProperty(pt, "patient"));
        NuformService nuformService = Context.getService(NuformService.class);
        List<Nuform> nuforms = nuformService.getAllNuformsByPatient(patient);
        String dermimageStarted;
        ArrayList<String> fileNames = new ArrayList<String>();

        if (ModuleFactory.isModuleStarted("dermimage")) {
            dermimageStarted = NuformConstants.SUCCESS;
            String sep = File.separator;
            // Folder in which images are saved
            File imgDir = new File(OpenmrsUtil.getApplicationDataDirectory() +
                    sep + "patient_images" + sep +
                    patient.getPatientId().toString().trim() + sep);

            if (!imgDir.exists()) {
                FileUtils.forceMkdir(imgDir);
            }
            fileNames = new ArrayList<String>(Arrays.asList(imgDir.list()));
        }
        else
            dermimageStarted = NuformConstants.ERROR;

        List<NuformDef> nuformDefs = nuformService.getAllDef(NuformConstants.PATIENTFORM);
        nuformDefs.addAll(nuformService.getAllDef(NuformConstants.PERSONALFORM));
        model.addAttribute("dermimageStarted", dermimageStarted);
        model.addAttribute("NUFORM_CONSTANTS", NuformConstants.NUFORM_CONSTANTS());
        model.addAttribute("nuforms", nuforms);
        model.addAttribute("patient", patient);
        model.addAttribute("listOfFiles", fileNames);
        model.addAttribute("nuformdefs", nuformDefs);
    }
}
