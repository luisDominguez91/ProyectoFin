/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.portfolio.client.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;

@Entity
@Table(name = "m_family_members")
public class ClientFamilyMembers extends AbstractPersistableCustom<Long> {
	
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="middlename")
	private String middlename;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="qualification")
	private String qualification;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="age")
	private Long age;
	
	@Column(name="is_dependent")
	private Boolean isDependent;
	
	
	@ManyToOne
	@JoinColumn(name = "relationship_cv_id")
	private CodeValue relationship;
	
	@ManyToOne
	@JoinColumn(name = "marital_status_cv_id")
	private CodeValue maritalStatus;
	
	
	@ManyToOne
	@JoinColumn(name = "gender_cv_id")
	private CodeValue gender;
	
	@ManyToOne
	@JoinColumn(name = "profession_cv_id")
	private CodeValue profession;
	
	@Column(name = "date_of_birth", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	// Se agregan los campos nuevos {
	@Column(name="ctm_motherlastname")
	private String motherlastname;

	@Column(name="ctm_curp")
	private String curp;

	@Column(name="ctm_rfc")
	private String rfc;

	@Column(name = "ctm_created_by")
	private String createdBy;

	@Column(name = "ctm_created_on")
	private Date createdOn;

	@Column(name = "ctm_updated_by")
	private String updatedBy;

	@Column(name = "ctm_updated_on")
	private Date updatedOn;

	@Column(name = "ctm_emailAddress")
	private String emailAddress;

	@Column(name = "ctm_nss", nullable = false)
	private String nss;

	@Column(name = "ctm_phone_no", nullable = false)
	private String phoneNo;

	@Column(name = "ctm_is_active", nullable = false)
	private boolean active;
	// }



	private ClientFamilyMembers(final Client client,final String firstname,
				final String middlename,final String lastName,final String qualification,
				final String mobileNumber,final Long age,final Boolean isDependent,
				final CodeValue relationship,final CodeValue maritalStatus,final CodeValue gender,
				final Date dateOfBirth,final CodeValue profession ,final String motherlastname,
				final String curp, final String rfc, final String createdBy, final Date createdOn,
				final String updatedBy, final Date updatedOn, final String emailAddress, final String nss,
				final String phoneNo, final boolean active)
		{
			
			this.client=client;
			this.firstname =firstname;
			this.middlename =middlename;
			this.lastname =lastName;
			this.qualification=qualification;
			this.age=age;
			this.mobileNumber=mobileNumber;
			this.isDependent=isDependent;
			this.relationship=relationship;
			this.maritalStatus=maritalStatus;
			this.gender=gender;
			this.dateOfBirth=dateOfBirth;
			this.profession=profession;
			// Se agregan los nuevos campos {
			this.motherlastname = motherlastname;
			this.curp=curp;
			this.rfc=rfc;
			this.createdBy=createdBy;
			this.createdOn=createdOn;
			this.updatedBy=updatedBy;
			this.updatedOn=updatedOn;
			this.emailAddress=emailAddress;
			this.nss=nss;
			this.phoneNo=phoneNo;
			this.active=active;
			// }
		}
		
		
		public ClientFamilyMembers()
		{
			
		}
		
		public static ClientFamilyMembers fromJson(final Client client,final String firstName,
				final String middleName,final String lastName,final String qualification,
				final String mobileNumber,final Long age,final Boolean isDependent,
				final CodeValue relationship,final CodeValue maritalStatus,final CodeValue gender,
				final Date dateOfBirth,final CodeValue profession,final String motherlastName,
				final String curp, final String rfc, final String createdBy, final Date createdOn,
				final String updatedBy, final Date updatedOn, final String emailAddress, final String nss,
				final String phoneNo, final boolean active)
		{
			return new ClientFamilyMembers(client,firstName,middleName,lastName,qualification,
					mobileNumber,age,isDependent,relationship,maritalStatus,gender,
					dateOfBirth,profession, motherlastName, curp, rfc, createdBy, createdOn,
					updatedBy, updatedOn, emailAddress, nss, phoneNo, active);
		}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Boolean getDependent() {
		return isDependent;
	}

	public void setDependent(Boolean dependent) {
		isDependent = dependent;
	}

	public CodeValue getRelationship() {
		return relationship;
	}

	public void setRelationship(CodeValue relationship) {
		this.relationship = relationship;
	}

	public CodeValue getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(CodeValue maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public CodeValue getGender() {
		return gender;
	}

	public void setGender(CodeValue gender) {
		this.gender = gender;
	}

	public CodeValue getProfession() {
		return profession;
	}

	public void setProfession(CodeValue profession) {
		this.profession = profession;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMotherlastname() {
		return motherlastname;
	}

	public void setMotherlastname(String motherlastname) {
		this.motherlastname = motherlastname;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
