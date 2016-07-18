/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nuform.api.db;

import org.openmrs.Patient;
import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Database methods for {@link NuformService}.
 */
public interface NuformDAO {
	
	/*
	 * Add DAO methods here
	 */
    @Transactional(readOnly = true)
    List<NuformDef> getAllDef(String formtype);

    @Transactional(readOnly = true)
    List<Nuform> getAllNuforms(String status);

    @Transactional(readOnly = true)
    Nuform getNuformById(int id);

    Nuform saveNuform(Nuform nuform);

    void purgeNuform(Nuform nuform);

    @Transactional(readOnly = true)
    NuformDef getNuformDefById(int id);

    NuformDef saveNuformDef(NuformDef nuformDef);

    void purgeNuformDef(NuformDef nuformDef);

    @Transactional(readOnly = true)
    List<Nuform> getAllNuformsByDef(NuformDef nuformDef);

    @Transactional(readOnly = true)
    List<Nuform> getAllNuformsByPatient(Patient patient);

}