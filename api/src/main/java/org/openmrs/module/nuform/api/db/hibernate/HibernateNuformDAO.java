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
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.nuform.Nuform;
import org.openmrs.module.nuform.NuformDef;
import org.openmrs.module.nuform.api.db.NuformDAO;

import java.util.List;

import java.lang.reflect.Method; //Hibernate Fix

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
    public List getAllDef(String formtype) {
        if (formtype.isEmpty())
            return getCurrentSession()
                    .createCriteria(NuformDef.class)
                    .list();
        return getCurrentSession()
                .createCriteria(NuformDef.class)
                .add(Restrictions.eq("formtype", formtype))
                .list();

    }

    @Override
    public List getAllNuforms(String status) {
        if (status.isEmpty())
            return getCurrentSession()
                    .createCriteria(Nuform.class)
                    .list();
        return getCurrentSession()
                .createCriteria(Nuform.class)
                .add(Restrictions.eq("status", status))
                .list();
    }

    @Override
    public List getAllNuformsByDef(NuformDef nuformDef) {
        return getCurrentSession()
                .createCriteria(Nuform.class)
                .add(Restrictions.eq("nuformDef", nuformDef))
                .list();
    }

    @Override
    public List getAllNuformsByPatient(Patient patient) {
        return getCurrentSession()
                .createCriteria(Nuform.class)
                .add(Restrictions.eq("patient", patient))
                .list();
    }

    @Override
    public Nuform getNuformById(int id) {
        return (Nuform) getCurrentSession()
                .createCriteria(Nuform.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public NuformDef getNuformDefById(int id) {
        return (NuformDef) getCurrentSession()
                .createCriteria(NuformDef.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public Nuform saveNuform(Nuform nuform) {
        getCurrentSession().saveOrUpdate(nuform);
        return nuform;
    }

    @Override
    public void purgeNuform(Nuform nuform) {
        getCurrentSession().delete(nuform);
    }

    @Override
    public NuformDef saveNuformDef(NuformDef nuformDef) {
        getCurrentSession().saveOrUpdate(nuformDef);
        return nuformDef;
    }

    @Override
    public void purgeNuformDef(NuformDef nuformDef) {
        getCurrentSession().delete(nuformDef);
    }

    /**
     * Gets the current hibernate session while taking care of the hibernate 3 and 4 differences.
     * dkayiwa's fix: https://github.com/openmrs/openmrs-module-xforms/commit/5d79f327d27777a40ec755a90433e4dc69067e4a
     * @return the current hibernate session.
     */
    private org.hibernate.Session getCurrentSession() {
        try {
            return sessionFactory.getCurrentSession();
        }
        catch (NoSuchMethodError ex) {
            try {
                Method method = sessionFactory.getClass().getMethod("getCurrentSession", null);
                return (org.hibernate.Session)method.invoke(sessionFactory, null);
            }
            catch (Exception e) {
                log.error("Failed to get the hibernate session", e);
                throw new RuntimeException("Failed to get the current hibernate session", e);
            }
        }
        
        return null;
    }

}