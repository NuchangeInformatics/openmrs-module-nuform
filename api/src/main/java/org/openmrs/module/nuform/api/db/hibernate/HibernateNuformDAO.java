/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nuform.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.nuform.api.db.NuformDAO;

import java.util.List;

/**
 * It is a default implementation of  {@link NuformDAO}.
 */
@SuppressWarnings("JpaQlInspection")
public class HibernateNuformDAO implements NuformDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // REF: http://levelup.lishman.com/spring/hibernate-orm/quick-start.php
    @Override
    public List getAllDefWithDeleted() {
        return sessionFactory.getCurrentSession().createQuery("from nuformdef").list();
    }

    @Override
    public List getAllNuformsWithDeleted() {
        return sessionFactory.getCurrentSession().createQuery("from nuform").list();
    }

}