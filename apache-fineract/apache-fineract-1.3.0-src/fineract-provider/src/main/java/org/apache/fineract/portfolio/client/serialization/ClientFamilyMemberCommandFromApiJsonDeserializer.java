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

package org.apache.fineract.portfolio.client.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public class ClientFamilyMemberCommandFromApiJsonDeserializer 
{
	private final FromJsonHelper fromApiJsonHelper;
	 private final Set<String> supportedParameters = new HashSet<>(Arrays.asList(ClientApiConstants.idParamName,ClientApiConstants.clientIdParamName,
			 ClientApiConstants.firstnameParamName,ClientApiConstants.middlenameParamName,ClientApiConstants.lastnameParamName,ClientApiConstants.qualificationParamName,
			 ClientApiConstants.mobileNoParamName,ClientApiConstants.ageParamName, ClientApiConstants.isDependentParamName,
			 ClientApiConstants.relationshipIdParamName,ClientApiConstants.maritalStatusIdParamName, ClientApiConstants.genderIdParamName,
			 ClientApiConstants.dateOfBirthParamName,ClientApiConstants.professionIdParamName,ClientApiConstants.localeParamName,ClientApiConstants.dateFormatParamName,
			 ClientApiConstants.familyMembers,ClientApiConstants.curpParamName,ClientApiConstants.rfcParamName,ClientApiConstants.motherlastnameParamName,
			 ClientApiConstants.nssParamName,ClientApiConstants.phoneNoParamName, ClientApiConstants.emailAddressParamName, ClientApiConstants.isActiveParamName));
	
	@Autowired
	private ClientFamilyMemberCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper)
	{
			this.fromApiJsonHelper=fromApiJsonHelper;
	}
	
	
	public void validateForCreate(String json)
	{
		
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

		validateForCreate(1,json);
			
	}
		
	
	
	public void validateForCreate(final long clientId,String json)
	{
		
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("FamilyMembers");

		final JsonElement element = this.fromApiJsonHelper.parse(json);
		
		baseDataValidator.reset().value(clientId).notBlank().integerGreaterThanZero();
		
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element)!=null)
		{
			final String firstName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(firstName).notNull().notBlank().notExceedingLengthOf(50);
		}
		else
		{
			baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element)).notNull().notBlank().notExceedingLengthOf(50);
		}
		
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element)!=null)
		{
			final String lastName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(lastName).notNull().notBlank().notExceedingLengthOf(50);
		}
		
		
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element)!=null)
		{
			final String middleName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(middleName).notNull().notBlank().notExceedingLengthOf(50);
		}
		
		
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.qualificationParamName, element)!=null)
		{
			final String qualification = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.qualificationParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.qualificationParamName).value(qualification).notNull().notBlank().notExceedingLengthOf(100);
		}
		
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element)!=null)
		{
			final String mobileNumber = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.mobileNoParamName).value(mobileNumber).notNull().notBlank().notExceedingLengthOf(50);
		}
		
		if(this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.isDependentParamName, element)!=null)
		{
			final Boolean isDependent = this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.isDependentParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.isDependentParamName).value(isDependent).notNull().notBlank();
		}
		
		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.relationshipIdParamName, element)!=null)
		{
			final long relationShipId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.relationshipIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.relationshipIdParamName).value(relationShipId).notBlank().longGreaterThanZero();
		}
		else
		{
			baseDataValidator.reset().parameter(ClientApiConstants.relationshipIdParamName).value(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.relationshipIdParamName, element)).notBlank().longGreaterThanZero();
		}
		
		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.maritalStatusIdParamName, element)!=null)
		{
			final long maritalStatusId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.maritalStatusIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.maritalStatusIdParamName).value(maritalStatusId).notBlank().longGreaterThanZero();
		}
		
		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.genderIdParamName, element)!=null)
		{
			final long genderId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.genderIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.genderIdParamName).value(genderId).notBlank().longGreaterThanZero();
		}
		
		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.ageParamName, element)!=null)
		{
			final long age=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.ageParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.ageParamName).value(age).notBlank().longGreaterThanZero();
		}
		
		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.professionIdParamName, element)!=null)
		{
			final long professionId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.professionIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.professionIdParamName).value(professionId).notBlank().longGreaterThanZero();
		}
		
		
		if(this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element)!=null)
		{
			final LocalDate dateOfBirth=this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.dateOfBirthParamName).value(dateOfBirth).value(dateOfBirth).notNull()
            .validateDateBefore(DateUtils.getLocalDateOfTenant());
		}
		// Nuevos campos {
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.motherlastnameParamName, element)!=null)
		{
			final String motherlastname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.motherlastnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.motherlastnameParamName).value(motherlastname).ignoreIfNull().notExceedingLengthOf(50);
		}

		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.curpParamName, element)!=null)
		{
			final String curp = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.curpParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.curpParamName).value(curp).ignoreIfNull().notExceedingLengthOf(18);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.rfcParamName, element)!=null)
		{
			final String rfc = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.rfcParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.rfcParamName).value(rfc).ignoreIfNull().notExceedingLengthOf(13);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.emailAddressParamName, element)!=null)
		{
			final String emailAddress = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.emailAddressParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.emailAddressParamName).value(emailAddress).ignoreIfNull().notExceedingLengthOf(150);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.nssParamName, element)!=null)
		{
			final String nss = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.nssParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.nssParamName).value(nss).ignoreIfNull().notExceedingLengthOf(11);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.phoneNoParamName, element)!=null)
		{
			final String phoneNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.phoneNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.phoneNoParamName).value(phoneNo).ignoreIfNull().notExceedingLengthOf(20);
		}
		// }
		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}
	
	
	public void validateForUpdate(final long familyMemberId,String json)
	{
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("FamilyMembers");

		final JsonElement element = this.fromApiJsonHelper.parse(json);
		
		baseDataValidator.reset().value(familyMemberId).notBlank().integerGreaterThanZero();


		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element)!=null)
		{
			final String firstName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.firstnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.firstnameParamName).value(firstName).notNull().notBlank().notExceedingLengthOf(50);
		}

		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element)!=null)
		{
			final String lastName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lastnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.lastnameParamName).value(lastName).notNull().notBlank().notExceedingLengthOf(50);
		}


		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element)!=null)
		{
			final String middleName = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(middleName).notNull().notBlank().notExceedingLengthOf(50);
		}


		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.qualificationParamName, element)!=null)
		{
			final String qualification = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.qualificationParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.qualificationParamName).value(qualification).notNull().notBlank().notExceedingLengthOf(100);
		}

		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element)!=null)
		{
			final String mobileNumber = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.mobileNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.mobileNoParamName).value(mobileNumber).notNull().notBlank().notExceedingLengthOf(50);
		}

		if(this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.isDependentParamName, element)!=null)
		{
			final Boolean isDependent = this.fromApiJsonHelper.extractBooleanNamed(ClientApiConstants.isDependentParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.isDependentParamName).value(isDependent).notNull().notBlank();
		}

		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.relationshipIdParamName, element)!=null)
		{
			final long relationShipId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.relationshipIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.relationshipIdParamName).value(relationShipId).notBlank().longGreaterThanZero();
		}

		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.maritalStatusIdParamName, element)!=null)
		{
			final long maritalStatusId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.maritalStatusIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.maritalStatusIdParamName).value(maritalStatusId).notBlank().longGreaterThanZero();
		}

		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.genderIdParamName, element)!=null)
		{
			final long genderId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.genderIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.genderIdParamName).value(genderId).notBlank().longZeroOrGreater();
		}

		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.ageParamName, element)!=null)
		{
			final long age=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.ageParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.ageParamName).value(age).notBlank().longGreaterThanZero();
		}

		if(this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.professionIdParamName, element)!=null)
		{
			final long professionId=this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.professionIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.professionIdParamName).value(professionId).notBlank().longGreaterThanZero();
		}

		if(this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element)!=null)
		{
			final LocalDate dateOfBirth=this.fromApiJsonHelper.extractLocalDateNamed(ClientApiConstants.dateOfBirthParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.dateOfBirthParamName).value(dateOfBirth).value(dateOfBirth).notNull()
					.validateDateBefore(DateUtils.getLocalDateOfTenant());
		}

		// Nuevos campos {
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.motherlastnameParamName, element)!=null)
		{
			final String motherlastname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.motherlastnameParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.motherlastnameParamName).value(motherlastname).ignoreIfNull().notExceedingLengthOf(50);
		}

		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.curpParamName, element)!=null)
		{
			final String curp = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.curpParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.curpParamName).value(curp).ignoreIfNull().notExceedingLengthOf(18);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.rfcParamName, element)!=null)
		{
			final String rfc = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.rfcParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.rfcParamName).value(rfc).ignoreIfNull().notExceedingLengthOf(13);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.emailAddressParamName, element)!=null)
		{
			final String emailAddress = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.emailAddressParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.emailAddressParamName).value(emailAddress).ignoreIfNull().notExceedingLengthOf(150);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.nssParamName, element)!=null)
		{
			final String nss = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.nssParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.nssParamName).value(nss).ignoreIfNull().notExceedingLengthOf(11);
		}
		if(this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.phoneNoParamName, element)!=null)
		{
			final String phoneNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.phoneNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.phoneNoParamName).value(phoneNo).ignoreIfNull().notExceedingLengthOf(20);
		}
		// }
		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}
	
	public void  validateForDelete(final long familyMemberId)
	{

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("FamilyMembers");

		baseDataValidator.reset().value(familyMemberId).notBlank().integerGreaterThanZero();

		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}

	private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
		if (!dataValidationErrors.isEmpty()) {
			throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
					"Validation errors exist.", dataValidationErrors);
		}
	}
}