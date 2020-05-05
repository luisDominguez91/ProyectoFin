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

package org.apache.fineract.portfolio.client.data;

import java.util.Collection;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.joda.time.LocalDate;

public class ClientFamilyMembersData {

	private final Long id;

	private final Long clientId;

	private final String firstname;

	private final String middlename;

	private final String lastname;

	private final String qualification;

	private final Long relationshipId;

	private final String relationship;

	private final Long maritalStatusId;

	private final String maritalStatus;

	private final Long genderId;

	private final String gender;

	private final LocalDate dateOfBirth;

	private final Long professionId;

	private final String profession;
	
	private final String mobileNo;
	
	private final Long age;
	
	private final Boolean isDependent;

	// Se agregan los nuevos campos {
	private final String motherlastname;
	private final String curp;
	private final String rfc;
	private final String createdBy;
	private final LocalDate createdOn;
	private final String updatedBy;
	private final LocalDate updatedOn;
	private final String emailAddress;
	private final String nss;
	private final String phoneNo;
	private final Boolean isActive;
	// }



	// template holder
	private Collection<CodeValueData> relationshipIdOptions;
	private Collection<CodeValueData> genderIdOptions;
	private Collection<CodeValueData> maritalStatusIdOptions;
	private Collection<CodeValueData> professionIdOptions;

	private ClientFamilyMembersData(final Long id, final Long clientId, final String firstname, final String middlename,
									final String lastname, final String qualification, final String mobileNo, final Long age, final Boolean isDependent, final String relationship, final Long relationshipId,
									final String maritalStatus, final Long maritalStatusId, final String gender, final Long genderId,
									final LocalDate dateOfBirth, final String profession, final Long professionId,
									final String motherlastname, final String curp, final String rfc, final String createdBy, final LocalDate createdOn,
									final String updatedBy, final LocalDate updatedOn, final String emailAddress, final String nss, final String phoneNo, final Boolean isActive,
									final Collection<CodeValueData> relationshipIdOptions, final Collection<CodeValueData> genderIdOptions, final Collection<CodeValueData> maritalStatusIdOptions,
									final Collection<CodeValueData> professionIdOptions) {
		this.id = id;
		this.clientId = clientId;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.qualification = qualification;
		this.relationship = relationship;
		this.relationshipId = relationshipId;
		this.maritalStatus = maritalStatus;
		this.maritalStatusId = maritalStatusId;
		this.gender = gender;
		this.genderId = genderId;
		this.dateOfBirth = dateOfBirth;
		this.profession = profession;
		this.professionId = professionId;
		this.mobileNo = mobileNo;
		this.age=age;
		this.isDependent=isDependent;

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
		this.isActive =isActive;
		// }
		this.relationshipIdOptions=relationshipIdOptions;
		this.genderIdOptions=genderIdOptions;
		this.maritalStatusIdOptions=maritalStatusIdOptions;
		this.professionIdOptions=professionIdOptions;
	}

	public static ClientFamilyMembersData instance(final Long id, final Long clientId, final String firstName,
			final String middleName, final String lastName, final String qualification,final String mobileNumber,final Long age,final Boolean isDependent, final String relationship,
			final Long relationshipId, final String maritalStatus, final Long maritalStatusId, final String gender,
			final Long genderId, final LocalDate dateOfBirth, final String profession, final Long professionId,
			final String motherlastname, final String curp, final String rfc, final String createdBy, final LocalDate createdOn,
			final String updatedBy, final LocalDate updatedOn, final String emailAddress, final String nss,	final String phoneNo,
			final Boolean isActive
			) {
		return new ClientFamilyMembersData(id, clientId, firstName, middleName, lastName, qualification,mobileNumber,age,isDependent, relationship,
				relationshipId, maritalStatus, maritalStatusId, gender, genderId, dateOfBirth, profession,
				professionId, motherlastname, curp, rfc, createdBy,  createdOn,
				updatedBy, updatedOn, emailAddress, nss,	phoneNo,  isActive,null,null,null,null);
	}
	
	
	public static ClientFamilyMembersData templateInstance(final Collection<CodeValueData> relationshipIdOptions,
			final Collection<CodeValueData> genderIdOptions,final Collection<CodeValueData> maritalStatusIdOptions,
			final Collection<CodeValueData> professionIdOptions) {
		
		
		return new ClientFamilyMembersData(null, null, null, null, null, null,null,
				null, null, null, null, null, null, null,
				null, null, null, null, null, null, null,
				null,null,null,null,null,null,null,null,relationshipIdOptions,genderIdOptions,maritalStatusIdOptions,professionIdOptions);
	}

	public Long getId() {
		return this.id;
	}

	public Long getClientId() {
		return this.clientId;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public String getLastname() {
		return this.lastname;
	}

	public String getQualification() {
		return this.qualification;
	}

	public Long getRelationshipId() {
		return this.relationshipId;
	}

	public String getRelationship() {
		return this.relationship;
	}

	public Long getMaritalStatusId() {
		return this.maritalStatusId;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public Long getGenderId() {
		return this.genderId;
	}

	public String getGender() {
		return this.gender;
	}

	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	public Long getProfessionId() {
		return this.professionId;
	}

	public String getProfession() {
		return this.profession;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public Long getAge() {
		return this.age;
	}

	public Boolean getIsDependent() {
		return this.isDependent;
	}

	public Boolean getDependent() {
		return isDependent;
	}

	public String getMotherlastname() {
		return motherlastname;
	}

	public String getCurp() {
		return curp;
	}

	public String getRfc() {
		return rfc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public LocalDate getUpdatedOn() {
		return updatedOn;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getNss() {
		return nss;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public Collection<CodeValueData> getRelationshipIdOptions() {
		return relationshipIdOptions;
	}

	public Collection<CodeValueData> getGenderIdOptions() {
		return genderIdOptions;
	}

	public Collection<CodeValueData> getMaritalStatusIdOptions() {
		return maritalStatusIdOptions;
	}

	public Collection<CodeValueData> getProfessionIdOptions() {
		return professionIdOptions;
	}

	public void setRelationshipIdOptions(Collection<CodeValueData> relationshipIdOptions) {
		this.relationshipIdOptions = relationshipIdOptions;
	}

	public void setGenderIdOptions(Collection<CodeValueData> genderIdOptions) {
		this.genderIdOptions = genderIdOptions;
	}

	public void setMaritalStatusIdOptions(Collection<CodeValueData> maritalStatusIdOptions) {
		this.maritalStatusIdOptions = maritalStatusIdOptions;
	}

	public void setProfessionIdOptions(Collection<CodeValueData> professionIdOptions) {
		this.professionIdOptions = professionIdOptions;
	}
}
