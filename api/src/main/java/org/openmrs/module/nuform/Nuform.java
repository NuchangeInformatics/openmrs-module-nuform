/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nuform;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

import java.io.Serializable;
import java.util.Date;

import static org.openmrs.module.nuform.NuformConstants.ACTIVE;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Nuform extends BaseOpenmrsObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private NuformDef nuformDef;

    private String created_by;
    private String deleted_by;
    private String last_edited_by;

    private Date created_on;
    private Date deleted_on;
    private Date last_edited_on;

    private String patientId;
    private String lesionmap;
    private String status = ACTIVE;
    private String comments;

    @Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDeleted_by() {
        return deleted_by;
    }

    public void setDeleted_by(String deleted_by) {
        this.deleted_by = deleted_by;
    }

    public String getLast_edited_by() {
        return last_edited_by;
    }

    public void setLast_edited_by(String last_edited_by) {
        this.last_edited_by = last_edited_by;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public Date getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Date deleted_on) {
        this.deleted_on = deleted_on;
    }

    public Date getLast_edited_on() {
        return last_edited_on;
    }

    public void setLast_edited_on(Date last_edited_on) {
        this.last_edited_on = last_edited_on;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLesionmap() {
        return lesionmap;
    }

    public void setLesionmap(String lesionmap) {
        this.lesionmap = lesionmap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public NuformDef getNuformDef() {
        return nuformDef;
    }

    public void setNuformDef(NuformDef nuformDef) {
        this.nuformDef = nuformDef;
    }
}