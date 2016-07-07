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

import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.NuformService;

/**
 *  Database methods for {@link NuformService}.
 */
public interface NuformDAO {
	
	/*
	 * Add DAO methods here
	 */
    NuformDef[] getAllDef();

    Nuform[] getAllNuforms();

    Nuform getNuformById(int id);

    Nuform saveNuform(Nuform nuform);

    NuformDef getNuformDefById(int id);

    NuformDef saveNuformDef(NuformDef nuformDef);

    Nuform[] getAllNuformsByDef(NuformDef nuformDef);
}