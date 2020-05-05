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
package org.apache.fineract.portfolio.address.service;

import java.math.BigDecimal;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepository;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.address.domain.Address;
import org.apache.fineract.portfolio.address.domain.AddressRepository;
import org.apache.fineract.portfolio.address.serialization.AddressCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientAddress;
import org.apache.fineract.portfolio.client.domain.ClientAddressRepository;
import org.apache.fineract.portfolio.client.domain.ClientAddressRepositoryWrapper;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.useradministration.domain.AppUser;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class AddressWritePlatformServiceImpl implements AddressWritePlatformService {
	private final PlatformSecurityContext context;
	private final CodeValueRepository codeValueRepository;
	private final ClientAddressRepository clientAddressRepository;
	private final ClientRepositoryWrapper clientRepositoryWrapper;
	private final AddressRepository addressRepository;
	private final ClientAddressRepositoryWrapper clientAddressRepositoryWrapper;
	private final AddressCommandFromApiJsonDeserializer fromApiJsonDeserializer;

	@Autowired
	public AddressWritePlatformServiceImpl(final PlatformSecurityContext context,
			final CodeValueRepository codeValueRepository, final ClientAddressRepository clientAddressRepository,
			final ClientRepositoryWrapper clientRepositoryWrapper, final AddressRepository addressRepository,
			final ClientAddressRepositoryWrapper clientAddressRepositoryWrapper,
			final AddressCommandFromApiJsonDeserializer fromApiJsonDeserializer) {
		this.context = context;
		this.codeValueRepository = codeValueRepository;
		this.clientAddressRepository = clientAddressRepository;
		this.clientRepositoryWrapper = clientRepositoryWrapper;
		this.addressRepository = addressRepository;
		this.clientAddressRepositoryWrapper = clientAddressRepositoryWrapper;
		this.fromApiJsonDeserializer = fromApiJsonDeserializer;

	}

	@Override
	public CommandProcessingResult addClientAddress(final Long clientId, final Long addressTypeId,
			final JsonCommand command) {

		AppUser user= this.context.authenticatedUser();
		this.fromApiJsonDeserializer.validateForCreate(clientId,command.json(), true);

		final CodeValue addressTypeIdObj = this.codeValueRepository.getOne(addressTypeId);

		final Address add = Address.fromJson(command);
		add.setCreatedOn(LocalDate.now().toDate());
		add.setCreatedBy(user.getUsername());
		this.addressRepository.save(add);
		final Long addressid = add.getId();
		final Address addobj = this.addressRepository.getOne(addressid);

		final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
		final boolean isActive = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.isActiveParamName);

		final ClientAddress clientAddressobj = ClientAddress.fromJson(isActive, client, addobj, addressTypeIdObj);
		this.clientAddressRepository.save(clientAddressobj);

		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientAddressobj.getId()).build();
	}

	// following method is used for adding multiple addresses while creating new
	// client

	@Override
	public CommandProcessingResult addNewClientAddress(final Client client, final JsonCommand command) {
		AppUser user= this.context.authenticatedUser();
		ClientAddress clientAddressobj = new ClientAddress();
		final JsonArray addressArray = command.arrayOfParameterNamed(ClientApiConstants.address);
		
		if(addressArray != null){
			//LADPthis.fromApiJsonDeserializer.validateMaxAddressInClient(addressArray.size());
			for (int i = 0; i < addressArray.size(); i++) {
				final JsonObject jsonObject = addressArray.get(i).getAsJsonObject();

				// validate every address
				//LADPthis.fromApiJsonDeserializer.validateForCreate(client.getId(), jsonObject.toString(), true);

				final long addressTypeId = jsonObject.get(ClientApiConstants.addressTypeIdParamName).getAsLong();
				final CodeValue addressTypeIdObj = this.codeValueRepository.getOne(addressTypeId);

				final Address add = Address.fromJsonObject(jsonObject);
				add.setCreatedOn(LocalDate.now().toDate());
				add.setCreatedBy(user.getUsername());
				this.addressRepository.save(add);
				final Long addressid = add.getId();
				final Address addobj = this.addressRepository.getOne(addressid);

				//final boolean isActive = jsonObject.get("isActive").getAsBoolean();
				boolean isActive=false;
				if(jsonObject.get(ClientApiConstants.isActiveParamName)!= null)
				{
					isActive= jsonObject.get(ClientApiConstants.isActiveParamName).getAsBoolean();
				}
				

				clientAddressobj = ClientAddress.fromJson(isActive, client, addobj, addressTypeIdObj);
				this.clientAddressRepository.save(clientAddressobj);
			}
		}

		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientAddressobj.getId()).build();
	}

	@Override
	public CommandProcessingResult updateClientAddress(final Long clientId, final JsonCommand command) {
		AppUser user= this.context.authenticatedUser();

		boolean is_address_update = false;

		this.fromApiJsonDeserializer.validateForUpdate(command.json());

		final long addressId = command.longValueOfParameterNamed(ClientApiConstants.addressIdParamName);

		final ClientAddress clientAddressObj = this.clientAddressRepositoryWrapper
				.findOneByClientIdAndAddressId(clientId, addressId);

		final Address addobj = this.addressRepository.getOne(addressId);

		if (command.parameterExists(ClientApiConstants.streetParamName)) {
			is_address_update = true;
			final String street = command.stringValueOfParameterNamed(ClientApiConstants.streetParamName);
			addobj.setStreet(street);
		}
		if (command.parameterExists(ClientApiConstants.betweenStreetsParamName)){
			is_address_update = true;
			final String betweenStreets = command.stringValueOfParameterNamed(ClientApiConstants.betweenStreetsParamName);
			addobj.setBetweenStreets(betweenStreets);
		}

		if (command.parameterExists(ClientApiConstants.extNoParamName)) {
			is_address_update = true;
			final String extNo = command.stringValueOfParameterNamed(ClientApiConstants.extNoParamName);
			addobj.setExtNo(extNo);
		}

		if (command.parameterExists(ClientApiConstants.intNoParamName)) {
			is_address_update = true;
			final String intNo = command.stringValueOfParameterNamed(ClientApiConstants.intNoParamName);
			addobj.setIntNo(intNo);
		}

		if (command.parameterExists(ClientApiConstants.addressLine1ParamName)) {
			is_address_update = true;
			final String addressLine1 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine1ParamName);
			addobj.setAddressLine1(addressLine1);
		}
		if (command.parameterExists(ClientApiConstants.buildingParamName)) {
			is_address_update = true;
			final String building = command.stringValueOfParameterNamed(ClientApiConstants.buildingParamName);
			addobj.setBuilding(building);
		}
		if (command.parameterExists(ClientApiConstants.departmentParamName)) {
			is_address_update = true;
			final String departament = command.stringValueOfParameterNamed(ClientApiConstants.departmentParamName);
			addobj.setDepartment(departament);
		}
		if (command.parameterExists(ClientApiConstants.addressLine2ParamName)) {
			is_address_update = true;
			final String addressLine2 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine2ParamName);
			addobj.setAddressLine2(addressLine2);
		}
		if (command.parameterExists(ClientApiConstants.lotParamName)) {
			is_address_update = true;
			final String lot = command.stringValueOfParameterNamed(ClientApiConstants.lotParamName);
			addobj.setLot(lot);
		}
		if (command.parameterExists(ClientApiConstants.blockParamName)) {
			is_address_update = true;
			final String block = command.stringValueOfParameterNamed(ClientApiConstants.blockParamName);
			addobj.setBlock(block);
		}
		if (command.parameterExists(ClientApiConstants.addressLine3ParamName)) {
			is_address_update = true;
			final String addressLine3 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine3ParamName);
			addobj.setAddressLine3(addressLine3);
		}
		if (command.parameterExists(ClientApiConstants.postalCodeParamName)) {
			is_address_update = true;
			final String postalCode = command.stringValueOfParameterNamed(ClientApiConstants.postalCodeParamName);
			addobj.setPostalCode(postalCode);
		}
		if (command.parameterExists(ClientApiConstants.suburbIdParamName)) {
			is_address_update = true;
			if ((command.longValueOfParameterNamed(ClientApiConstants.suburbIdParamName) != 0)) {
				Long suburbId = command.longValueOfParameterNamed(ClientApiConstants.suburbIdParamName);
				addobj.setSuburbId(suburbId);
			}else{
				addobj.setSuburbId(null);
			}
		}
		if (command.parameterExists(ClientApiConstants.townVillageParamName)) {
			is_address_update = true;
			final String townVillage = command.stringValueOfParameterNamed(ClientApiConstants.townVillageParamName);
			addobj.setTownVillage(townVillage);
		}
		if (command.parameterExists(ClientApiConstants.cityIdParamName)) {
			if ((command.longValueOfParameterNamed(ClientApiConstants.cityIdParamName) != 0)) {
				is_address_update = true;
				Long cityId = command.longValueOfParameterNamed(ClientApiConstants.cityIdParamName);
				addobj.setCityId(cityId);
			}
		}
		if (command.parameterExists(ClientApiConstants.cityParamName)) {
			is_address_update = true;
			final String city = command.stringValueOfParameterNamed(ClientApiConstants.cityParamName);
			addobj.setCity(city);
		}
		if (command.parameterExists(ClientApiConstants.municipalityIdParamName)) {
			if ((command.longValueOfParameterNamed(ClientApiConstants.municipalityIdParamName) != 0)) {
				is_address_update = true;
				Long municipalityId = command.longValueOfParameterNamed(ClientApiConstants.municipalityIdParamName);
				addobj.setMunicipalityId(municipalityId);
			}
		}
		if (command.parameterExists(ClientApiConstants.countyDistrictParamName)) {
			is_address_update = true;
			final String countryDistrict = command.stringValueOfParameterNamed(ClientApiConstants.countyDistrictParamName);
			addobj.setCountyDistrict(countryDistrict);
		}
		if (command.parameterExists(ClientApiConstants.stateIdParamName)) {
			if ((command.longValueOfParameterNamed(ClientApiConstants.stateIdParamName) != 0)) {
				is_address_update = true;
				Long stateId = command.longValueOfParameterNamed(ClientApiConstants.stateIdParamName);
				addobj.setStateId(stateId);
			}
		}
		if (command.parameterExists(ClientApiConstants.stateParamName)) {
			is_address_update = true;
			final String state = command.stringValueOfParameterNamed(ClientApiConstants.stateParamName);
			addobj.setState(state);
		}
		if (command.parameterExists(ClientApiConstants.countryIdParamName)) {
			is_address_update = true;
			if ((command.longValueOfParameterNamed(ClientApiConstants.countryIdParamName) != 0)) {
				Long countryId = command.longValueOfParameterNamed(ClientApiConstants.countryIdParamName);
				addobj.setCountryId(countryId);
			}else{
				addobj.setCountryId(null);
			}
		}
		if (command.parameterExists(ClientApiConstants.countryParamName)) {
			is_address_update = true;
			final String country = command.stringValueOfParameterNamed(ClientApiConstants.countryParamName);
			addobj.setCountry(country);
		}


		if (command.parameterExists(ClientApiConstants.latitudeParamName)) {
			is_address_update = true;
			final BigDecimal latitude = command.bigDecimalValueOfParameterNamed(ClientApiConstants.latitudeParamName);
			addobj.setLatitude(latitude);
		}
		if (command.parameterExists(ClientApiConstants.longitudeParamName)) {
			is_address_update = true;
			final BigDecimal longitude = command.bigDecimalValueOfParameterNamed(ClientApiConstants.longitudeParamName);
			addobj.setLongitude(longitude);
		}

		if (is_address_update) {
			addobj.setUpdatedOn(LocalDate.now().toDate());
			addobj.setUpdatedBy(user.getUsername());
			this.addressRepository.save(addobj);

		}

		final Boolean testActive = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.isActiveParamName);
		if (testActive != null) {

			final boolean active = command.booleanPrimitiveValueOfParameterNamed(ClientApiConstants.isActiveParamName);
			clientAddressObj.setIs_active(active);

		}

		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientAddressObj.getId()).build();
	}

	@Override
	public CommandProcessingResult deleteClientAddress(final Long clientId, final Long addressId, JsonCommand command) {
		AppUser user= this.context.authenticatedUser();

		this.fromApiJsonDeserializer.validateForDelete(addressId);

		final ClientAddress clientAddressObj = this.clientAddressRepositoryWrapper
				.findOneByClientIdAndAddressId(clientId, addressId);

		final Address addobj = this.addressRepository.getOne(addressId);

		addobj.setUpdatedOn(LocalDate.now().toDate());
		addobj.setUpdatedBy(user.getUsername());
		this.addressRepository.delete(addobj);
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId())
				.withEntityId(clientAddressObj.getId()).build();
	}
}