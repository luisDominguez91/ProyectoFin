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

package org.apache.fineract.portfolio.client.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepository;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientFamilyMembers;
import org.apache.fineract.portfolio.client.domain.ClientFamilyMembersRepository;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.client.serialization.ClientFamilyMemberCommandFromApiJsonDeserializer;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class ClientFamilyMembersWritePlatformServiceImpl implements ClientFamilyMembersWritePlatformService 
{
	
	private final PlatformSecurityContext context;
	private final CodeValueRepository codeValueRepository;
	private final ClientFamilyMembersRepository clientFamilyRepository;
	private final ClientRepositoryWrapper clientRepositoryWrapper;
	private final ClientFamilyMemberCommandFromApiJsonDeserializer  apiJsonDeserializer;
	
	
	@Autowired
	public ClientFamilyMembersWritePlatformServiceImpl(final PlatformSecurityContext context,final CodeValueRepository codeValueRepository,
			final ClientFamilyMembersRepository clientFamilyRepository,final ClientRepositoryWrapper clientRepositoryWrapper,final ClientFamilyMemberCommandFromApiJsonDeserializer  apiJsonDeserializer
			)
	{
		this.context=context;
		this.codeValueRepository=codeValueRepository;
		this.clientFamilyRepository=clientFamilyRepository;
		this.clientRepositoryWrapper=clientRepositoryWrapper;
		this.apiJsonDeserializer=apiJsonDeserializer;
		
	}
	
	

	@Override
	public CommandProcessingResult addFamilyMember(final long clientId,final JsonCommand command) 
	{
		
		Long relationshipId=null;
		CodeValue relationship=null;
		CodeValue maritalStatus=null;
		Long maritalStatusId=null;
		Long genderId=null;
		CodeValue gender=null;
		Long professionId=null;
		CodeValue profession=null;
		String firstName="";
		String middleName="";
		String lastName="";
		String qualification="";
		String mobileNumber="";
		Long age=null;
		Boolean isDependent=false;
		Date dateOfBirth=null;
		// Se agregan los nuevos campos:
		String motherlastName="";
		String curp="";
		String rfc="";
		Date createdOn=null;
		Long createdBy=null;
		Date updatedOn=null;
		Long updatedBy=null;
		String emailAddress="";
		String nss="";
		String phoneNo="";
		boolean active = false;
		// }
		
		AppUser user=this.context.authenticatedUser();
		apiJsonDeserializer.validateForCreate(clientId, command.json());
		
		
		Client client=clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
		
		if (command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName) != null) {
			firstName = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
			}
		
		if (command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName) != null) {
			middleName = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
			}
		
		if (command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName) != null) {
			lastName = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
			}
		
		
		if (command.stringValueOfParameterNamed(ClientApiConstants.qualificationParamName) != null) {
			qualification = command.stringValueOfParameterNamed(ClientApiConstants.qualificationParamName);
			}
		
		if (command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName) != null) {
			mobileNumber = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);
			}

		if (command.longValueOfParameterNamed(ClientApiConstants.ageParamName) != null) {
			age = command.longValueOfParameterNamed(ClientApiConstants.ageParamName);
			}
		
		if (command.booleanObjectValueOfParameterNamed(ClientApiConstants.isDependentParamName) != null) {
			isDependent = command.booleanObjectValueOfParameterNamed(ClientApiConstants.isDependentParamName);
			}
		
		if (command.longValueOfParameterNamed(ClientApiConstants.relationshipIdParamName) != null) {
			relationshipId = command.longValueOfParameterNamed(ClientApiConstants.relationshipIdParamName);
			relationship = this.codeValueRepository.getOne(relationshipId);
		}
		
		if (command.longValueOfParameterNamed(ClientApiConstants.maritalStatusIdParamName) != null) {
			maritalStatusId = command.longValueOfParameterNamed(ClientApiConstants.maritalStatusIdParamName);
			maritalStatus = this.codeValueRepository.getOne(maritalStatusId);
		}

		if (command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName) != null) {
			genderId = command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName);
			gender = this.codeValueRepository.getOne(genderId);
		}
		
		if (command.longValueOfParameterNamed(ClientApiConstants.professionIdParamName) != null) {
			professionId = command.longValueOfParameterNamed(ClientApiConstants.professionIdParamName);
			profession = this.codeValueRepository.getOne(professionId);
		}
		
		if(command.DateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName)!=null)
		{
			dateOfBirth=command.DateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
					
		}

		// Se agregan nuevos campos {
		if (command.stringValueOfParameterNamed(ClientApiConstants.motherlastnameParamName) != null) {
			motherlastName = command.stringValueOfParameterNamed(ClientApiConstants.motherlastnameParamName);
		}
		if (command.stringValueOfParameterNamed(ClientApiConstants.curpParamName) != null) {
			curp = command.stringValueOfParameterNamed(ClientApiConstants.curpParamName);
		}
		if (command.stringValueOfParameterNamed(ClientApiConstants.rfcParamName) != null) {
			rfc = command.stringValueOfParameterNamed(ClientApiConstants.rfcParamName);
		}
		if (command.stringValueOfParameterNamed(ClientApiConstants.emailAddressParamName) != null) {
			emailAddress = command.stringValueOfParameterNamed(ClientApiConstants.emailAddressParamName);
		}
		if (command.stringValueOfParameterNamed(ClientApiConstants.nssParamName) != null) {
			nss = command.stringValueOfParameterNamed(ClientApiConstants.nssParamName);
		}
		if (command.stringValueOfParameterNamed(ClientApiConstants.phoneNoParamName) != null) {
			phoneNo = command.stringValueOfParameterNamed(ClientApiConstants.phoneNoParamName);
		}
		if (command.booleanObjectValueOfParameterNamed(ClientApiConstants.isActiveParamName) != null) {
			active = command.booleanObjectValueOfParameterNamed(ClientApiConstants.isActiveParamName);
		}

		createdOn= new Date();
		createdBy = user.getId();
		// }

		ClientFamilyMembers clientFamilyMembers=ClientFamilyMembers.fromJson(client, firstName, middleName, lastName, qualification,mobileNumber,age,isDependent, relationship, maritalStatus, gender, dateOfBirth, profession,
				motherlastName, curp, rfc, createdBy, createdOn,updatedBy, updatedOn, emailAddress, nss, phoneNo, active, null, null, false);
		
		this.clientFamilyRepository.save(clientFamilyMembers);
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientFamilyMembers.getId()).build();
		
		
	
	}
	
	@Override
	public CommandProcessingResult addClientFamilyMember(final Client client,final JsonCommand command)
	{

		Long relationshipId=null;
		CodeValue relationship=null;
		CodeValue maritalStatus=null;
		Long maritalStatusId=null;
		Long genderId=null;
		CodeValue gender=null;
		Long professionId=null;
		CodeValue profession=null;
		String firstName="";
		String middleName="";
		String lastName="";
		String qualification="";
		Date dateOfBirth=null;
		String mobileNumber="";
		Long age=null;
		Boolean isDependent=false;
		// Se agregan los nuevos campos:
		String motherlastName="";
		String curp="";
		String rfc="";
		Date createdOn=null;
		Long createdBy=null;
		Date updatedOn=null;
		Long updatedBy=null;
		String emailAddress="";
		String nss="";
		String phoneNo="";
		boolean active = false;
		// }


		AppUser user= this.context.authenticatedUser();

		ClientFamilyMembers familyMember=new ClientFamilyMembers();

		JsonArray familyMembers=command.arrayOfParameterNamed(ClientApiConstants.familyMembers);
		
		for(JsonElement members :familyMembers)
		{
			
			apiJsonDeserializer.validateForCreate(members.toString());
			
			JsonObject member=members.getAsJsonObject();
			
			
			if (member.get(ClientApiConstants.firstnameParamName) != null) {
				firstName = member.get(ClientApiConstants.firstnameParamName).getAsString();
				}
			
			if (member.get(ClientApiConstants.middlenameParamName) != null) {
				middleName = member.get(ClientApiConstants.middlenameParamName).getAsString();
				}
			
			if (member.get(ClientApiConstants.lastnameParamName) != null) {
				lastName = member.get(ClientApiConstants.lastnameParamName).getAsString();
				}
			
			
			if (member.get(ClientApiConstants.qualificationParamName) != null) {
				qualification = member.get(ClientApiConstants.qualificationParamName).getAsString();
				}
			
			if (member.get(ClientApiConstants.mobileNoParamName) != null) {
				mobileNumber = member.get(ClientApiConstants.mobileNoParamName).getAsString();
				}
			
			
			if (member.get(ClientApiConstants.ageParamName) != null) {
				age = member.get(ClientApiConstants.ageParamName).getAsLong();
				}
			
			if (member.get(ClientApiConstants.isDependentParamName) != null) {
				isDependent = member.get(ClientApiConstants.isDependentParamName).getAsBoolean();
				}
			
			if (member.get(ClientApiConstants.relationshipIdParamName) != null) {
				relationshipId = member.get(ClientApiConstants.relationshipIdParamName).getAsLong();
				relationship = this.codeValueRepository.getOne(relationshipId);
			}
			
			if (member.get(ClientApiConstants.maritalStatusIdParamName) != null) {
				maritalStatusId = member.get(ClientApiConstants.maritalStatusIdParamName).getAsLong();
				maritalStatus = this.codeValueRepository.getOne(maritalStatusId);
			}

			if (member.get(ClientApiConstants.genderIdParamName) != null) {
				genderId = member.get(ClientApiConstants.genderIdParamName).getAsLong();
				gender = this.codeValueRepository.getOne(genderId);
			}
			
			if (member.get(ClientApiConstants.professionIdParamName) != null) {
				professionId = member.get(ClientApiConstants.professionIdParamName).getAsLong();
				profession = this.codeValueRepository.getOne(professionId);
			}
			
			if(member.get(ClientApiConstants.dateOfBirthParamName)!=null)
			{
				DateFormat format = new SimpleDateFormat(member.get(ClientApiConstants.dateFormatParamName).getAsString());
				Date date;
				try {
					date = format.parse(member.get(ClientApiConstants.dateOfBirthParamName).getAsString());
					dateOfBirth=date;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Se agregan nuevos campos {
			if (member.get(ClientApiConstants.motherlastnameParamName) != null) {
				motherlastName = member.get(ClientApiConstants.motherlastnameParamName).getAsString();
			}
			if (member.get(ClientApiConstants.curpParamName) != null) {
				curp = member.get(ClientApiConstants.curpParamName).getAsString();
			}
			if (member.get(ClientApiConstants.rfcParamName) != null) {
				rfc = member.get(ClientApiConstants.rfcParamName).getAsString();
			}

			if (member.get(ClientApiConstants.emailAddressParamName) != null) {
				emailAddress = member.get(ClientApiConstants.emailAddressParamName).getAsString();
			}

			if (member.get(ClientApiConstants.nssParamName) != null) {
				nss = member.get(ClientApiConstants.nssParamName).getAsString();
			}

			if (member.get(ClientApiConstants.phoneNoParamName) != null) {
				phoneNo = member.get(ClientApiConstants.phoneNoParamName).getAsString();
			}
			if (member.get(ClientApiConstants.isActiveParamName) != null) {
				active = member.get(ClientApiConstants.isActiveParamName).getAsBoolean();
			}

			createdOn= new Date();
			createdBy = user.getId();
			// }
			
			familyMember=ClientFamilyMembers.fromJson(client, firstName, middleName, lastName, qualification,mobileNumber,age,isDependent, relationship, maritalStatus, gender, dateOfBirth, profession,
					motherlastName, curp, rfc, createdBy, createdOn,updatedBy, updatedOn, emailAddress, nss, phoneNo, active, null, null, false);
			
			this.clientFamilyRepository.save(familyMember);	
			
		}
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(familyMember.getId()).build();
		
		
	}



	@Override
	public CommandProcessingResult updateFamilyMember(Long familyMemberId, JsonCommand command) {
		
		
		Long relationshipId=null;
		CodeValue relationship=null;
		CodeValue maritalStatus=null;
		Long maritalStatusId=null;
		Long genderId=null;
		CodeValue gender=null;
		Long professionId=null;
		CodeValue profession=null;
		String firstName="";
		String middleName="";
		String lastName="";
		String qualification="";
		Date dateOfBirth=null;
		String mobileNumber="";
		Long age=null;
		Boolean isDependent=false;
		// Se agregan los nuevos campos:
		String motherlastName="";
		String curp="";
		String rfc="";
		Date createdOn=null;
		Long createdBy=null;
		Date updatedOn=null;
		Long updatedBy=null;
		String emailAddress="";
		String nss="";
		String phoneNo="";
		boolean isActive = false;
		// }
		
		
		AppUser user=this.context.authenticatedUser();
		
		apiJsonDeserializer.validateForUpdate(familyMemberId, command.json());

		ClientFamilyMembers clientFamilyMember=clientFamilyRepository.getOne(familyMemberId);

		if (command.parameterExists(ClientApiConstants.firstnameParamName)) {
			firstName = command.stringValueOfParameterNamed(ClientApiConstants.firstnameParamName);
			clientFamilyMember.setFirstname(firstName);
			}
		
		if (command.parameterExists(ClientApiConstants.middlenameParamName)) {
			middleName = command.stringValueOfParameterNamed(ClientApiConstants.middlenameParamName);
			clientFamilyMember.setMiddlename(middleName);
			}

		if (command.parameterExists(ClientApiConstants.lastnameParamName)) {
			lastName = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);
			clientFamilyMember.setLastname(lastName);
			}


		if (command.parameterExists(ClientApiConstants.qualificationParamName)) {
			qualification = command.stringValueOfParameterNamed(ClientApiConstants.qualificationParamName);
			clientFamilyMember.setQualification(qualification);
			}


		if (command.parameterExists(ClientApiConstants.mobileNoParamName)) {
			mobileNumber = command.stringValueOfParameterNamed(ClientApiConstants.mobileNoParamName);
			clientFamilyMember.setMobileNumber(mobileNumber);
			}

		if (command.parameterExists(ClientApiConstants.ageParamName)) {
			age = command.longValueOfParameterNamed(ClientApiConstants.ageParamName);
			clientFamilyMember.setAge(age);
			}

		if (command.parameterExists(ClientApiConstants.isDependentParamName)) {
			isDependent = command.booleanObjectValueOfParameterNamed(ClientApiConstants.isDependentParamName);
			clientFamilyMember.setDependent(isDependent);
			}

		if (command.parameterExists(ClientApiConstants.relationshipIdParamName)) {
			relationshipId = command.longValueOfParameterNamed(ClientApiConstants.relationshipIdParamName);
			relationship = this.codeValueRepository.getOne(relationshipId);
			clientFamilyMember.setRelationship(relationship);
		}

		if (command.parameterExists(ClientApiConstants.maritalStatusIdParamName)) {
			maritalStatusId = command.longValueOfParameterNamed(ClientApiConstants.maritalStatusIdParamName);
			maritalStatus = this.codeValueRepository.getOne(maritalStatusId);
			clientFamilyMember.setMaritalStatus(maritalStatus);
		}

		if (command.parameterExists(ClientApiConstants.genderIdParamName)) {
			genderId = command.longValueOfParameterNamed(ClientApiConstants.genderIdParamName);
			if(genderId == null || genderId == 0 ) {
				clientFamilyMember.setGender(null);
			}else{
				gender = this.codeValueRepository.getOne(genderId);
				clientFamilyMember.setGender(gender);
			}
		}

		if (command.parameterExists(ClientApiConstants.professionIdParamName)) {
			professionId = command.longValueOfParameterNamed(ClientApiConstants.professionIdParamName);
			profession = this.codeValueRepository.getOne(professionId);
			clientFamilyMember.setProfession(profession);
		}

		if (command.parameterExists(ClientApiConstants.dateOfBirthParamName)) {
			dateOfBirth=command.DateValueOfParameterNamed(ClientApiConstants.dateOfBirthParamName);
			clientFamilyMember.setDateOfBirth(dateOfBirth);
		}

		// Se agregan los nuevos campos:
		if (command.parameterExists(ClientApiConstants.motherlastnameParamName)) {
			motherlastName = command.stringValueOfParameterNamed(ClientApiConstants.motherlastnameParamName);
			clientFamilyMember.setMotherlastname(motherlastName);
		}
		if (command.parameterExists(ClientApiConstants.curpParamName)) {
			curp = command.stringValueOfParameterNamed(ClientApiConstants.curpParamName);
			clientFamilyMember.setCurp(curp);
		}
		if (command.parameterExists(ClientApiConstants.rfcParamName)) {
			rfc = command.stringValueOfParameterNamed(ClientApiConstants.rfcParamName);
			clientFamilyMember.setRfc(rfc);
		}

		if (command.parameterExists(ClientApiConstants.emailAddressParamName)) {
			emailAddress = command.stringValueOfParameterNamed(ClientApiConstants.emailAddressParamName);
			clientFamilyMember.setEmailAddress(emailAddress);
		}
		if (command.parameterExists(ClientApiConstants.nssParamName)) {
			nss = command.stringValueOfParameterNamed(ClientApiConstants.nssParamName);
			clientFamilyMember.setNss(nss);
		}
		if (command.parameterExists(ClientApiConstants.phoneNoParamName)) {
			phoneNo = command.stringValueOfParameterNamed(ClientApiConstants.phoneNoParamName);
			clientFamilyMember.setPhoneNo(phoneNo);
		}
		if (command.parameterExists(ClientApiConstants.isActiveParamName)) {
			isActive = command.booleanObjectValueOfParameterNamed(ClientApiConstants.isActiveParamName);
			clientFamilyMember.setActive(isActive);
		}

		updatedOn=new Date();
		clientFamilyMember.setUpdatedOn(updatedOn);
		updatedBy=user.getId();
		clientFamilyMember.setUpdatedBy(updatedBy);
		// }


		this.clientFamilyRepository.save(clientFamilyMember);
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientFamilyMember.getId()).build();
	}



	@Override
	public CommandProcessingResult deleteFamilyMember(Long clientFamilyMemberId, JsonCommand command) {
		// TODO Auto-generated method stub

		AppUser user = this.context.authenticatedUser();
		
		apiJsonDeserializer.validateForDelete(clientFamilyMemberId);
		
		ClientFamilyMembers clientFamilyMember=null;

		if(clientFamilyMemberId!=null)
		{
            clientFamilyMember=clientFamilyRepository.getOne(clientFamilyMemberId);
            clientFamilyMember.delete(user.getId());
			clientFamilyRepository.save(clientFamilyMember);
		}

		if(clientFamilyMember!=null)
		{
			return new CommandProcessingResultBuilder().withCommandId(command.commandId())
					.withEntityId(clientFamilyMember.getId()).build();	
		}
		else
		{
			return new CommandProcessingResultBuilder().withCommandId(command.commandId())
					.withEntityId(Long.valueOf(clientFamilyMemberId)).build();	
		}
	}
}
