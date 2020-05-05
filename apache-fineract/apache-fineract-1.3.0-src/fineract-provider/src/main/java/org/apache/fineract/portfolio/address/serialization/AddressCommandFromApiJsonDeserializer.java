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
package org.apache.fineract.portfolio.address.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.configuration.data.GlobalConfigurationPropertyData;
import org.apache.fineract.infrastructure.configuration.service.ConfigurationReadPlatformService;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.portfolio.address.data.AddressData;
import org.apache.fineract.portfolio.address.service.AddressReadPlatformServiceImpl;
import org.apache.fineract.portfolio.address.service.FieldConfigurationReadPlatformService;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public class AddressCommandFromApiJsonDeserializer {
	private final FromJsonHelper fromApiJsonHelper;
	private final FieldConfigurationReadPlatformService readservice;
	private final AddressReadPlatformServiceImpl readPlatformService;
	private final ConfigurationReadPlatformService confReadPlatformService;

	@Autowired
	public AddressCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper,
			final FieldConfigurationReadPlatformService readservice,
			final AddressReadPlatformServiceImpl readPlatformService,
			final ConfigurationReadPlatformService confReadPlatformService) {
		this.fromApiJsonHelper = fromApiJsonHelper;
		this.readservice = readservice;
		this.readPlatformService = readPlatformService;
		this.confReadPlatformService = confReadPlatformService;
	}

	public void validateForUpdate(final String json) {
		validate(null, json, false);
	}

	public void validateForCreate(final Long clientId, final String json, final boolean fromNewClient) {
		validate(clientId, json, fromNewClient);
	}

	public void validate(final Long clientId, final String json, final boolean fromNewClient) {
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("Address");

		final JsonElement element = this.fromApiJsonHelper.parse(json);


		Set<String> supportedParameters = new HashSet<>();
		final List<String> enabledFieldList = new ArrayList<>();

		enabledFieldList.add("locale");
		enabledFieldList.add("dateFormat");

		enabledFieldList.add("street");
		enabledFieldList.add("betweenStreets");
		enabledFieldList.add("extNo");
		enabledFieldList.add("intNo");
		enabledFieldList.add("addressLine1");
		enabledFieldList.add("building");
		enabledFieldList.add("department");
		enabledFieldList.add("addressLine2");
		enabledFieldList.add("lot");
		enabledFieldList.add("block");
		enabledFieldList.add("addressLine3");
		enabledFieldList.add("postalCode");
		enabledFieldList.add("suburbId");
		enabledFieldList.add("townVillage");
		enabledFieldList.add("cityId");
		enabledFieldList.add("city");
		enabledFieldList.add("municipalityId");
		enabledFieldList.add("countyDistrict");
		enabledFieldList.add("stateId");
		enabledFieldList.add("state");
		enabledFieldList.add("countryId");
		enabledFieldList.add("country");
		enabledFieldList.add("isActive");

		if (fromNewClient) {
			enabledFieldList.add("addressTypeId");
			supportedParameters = new HashSet<>(enabledFieldList);
			final Integer addressTypeId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.addressTypeIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.addressTypeIdParamName).value(addressTypeId).integerGreaterThanZero();
			
			final GlobalConfigurationPropertyData configurationData = this.confReadPlatformService.retrieveGlobalConfiguration("max-address-in-client");
			
			Collection<AddressData> address;
			address = this.readPlatformService.retrieveAllClientAddress(clientId);
			baseDataValidator.reset().parameter(ClientApiConstants.addressLimit).value(address.size()).maxAddressInClient(configurationData.getValue().intValue());
		
		}else{ // UpdateCliente
			enabledFieldList.add("addressId");
			supportedParameters = new HashSet<>(enabledFieldList);
			final Long addressId = this.fromApiJsonHelper.extractLongNamed(ClientApiConstants.addressIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.addressIdParamName).value(addressId).longGreaterThanZero();
		}

		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.streetParamName, element)) {
			final String street = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.streetParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.streetParamName).value(street).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.betweenStreetsParamName, element)) {
			final String betweenStreets = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.betweenStreetsParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.betweenStreetsParamName).value(betweenStreets).ignoreIfNull()
					.notExceedingLengthOf(100);
		}
		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.extNoParamName, element)) {
			final String extNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.extNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.extNoParamName).value(extNo).ignoreIfNull()
					.notExceedingLengthOf(10);
		}
		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.intNoParamName, element)) {
			final String intNo = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.intNoParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.intNoParamName).value(intNo).ignoreIfNull()
					.notExceedingLengthOf(10);
		}
		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.addressLine1ParamName, element)) {
			final String addressLine1 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.addressLine1ParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.addressLine1ParamName).value(addressLine1).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.buildingParamName, element)) {
			final String building = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.buildingParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.buildingParamName).value(building).ignoreIfNull()
					.notExceedingLengthOf(50);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.departmentParamName, element)) {
			final String department = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.departmentParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.departmentParamName).value(department).ignoreIfNull()
					.notExceedingLengthOf(50);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.addressLine2ParamName, element)) {
			final String addressLine2 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.addressLine2ParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.addressLine2ParamName).value(addressLine2).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.lotParamName, element)) {
			final String lot = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.lotParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.lotParamName).value(lot).ignoreIfNull()
					.notExceedingLengthOf(50);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.blockParamName, element)) {
			final String block = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.blockParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.blockParamName).value(block).ignoreIfNull()
					.notExceedingLengthOf(50);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.addressLine3ParamName, element)) {
			final String addressLine3 = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.addressLine3ParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.addressLine3ParamName).value(addressLine3).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.postalCodeParamName, element)) {
			final String postalCode = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.postalCodeParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.postalCodeParamName).value(postalCode).ignoreIfNull()
					.notExceedingLengthOf(5);

			if(!this.fromApiJsonHelper.parameterExists(ClientApiConstants.suburbIdParamName, element)) {
				baseDataValidator.reset().parameter(ClientApiConstants.suburbIdParamName).value(0).integerGreaterThanZero();
			} else {
				final Integer suburbId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.suburbIdParamName, element);
				baseDataValidator.reset().parameter(ClientApiConstants.suburbIdParamName).value(suburbId).integerGreaterThanZero();
			}
		}


		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.townVillageParamName, element)) {
			final String townVillage = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.townVillageParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.townVillageParamName).value(townVillage).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cityIdParamName, element)) {
			final Integer cityId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.cityIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.cityIdParamName).value(cityId).integerGreaterThanZero();
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.cityParamName, element)) {
			final String city = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.cityParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.cityParamName).value(city).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.municipalityIdParamName, element)) {
			final Integer municipalityId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.municipalityIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.municipalityIdParamName).value(municipalityId).integerGreaterThanZero();
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.countyDistrictParamName, element)) {
			final String countyDistrict = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.countyDistrictParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.countyDistrictParamName).value(countyDistrict).ignoreIfNull()
					.notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.stateIdParamName, element)) {
			final Integer municipalityId = this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.municipalityIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.stateIdParamName).value(municipalityId).integerGreaterThanZero();
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.stateParamName, element)) {
			final String state = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.stateParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.stateParamName).value(state).ignoreIfNull()
					.notExceedingLengthOf(100);
		}
		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.countryIdParamName, element)) {
			final Integer countryId= this.fromApiJsonHelper.extractIntegerSansLocaleNamed(ClientApiConstants.countryIdParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.countryIdParamName).value(countryId).integerZeroOrGreater();
		}

		if (this.fromApiJsonHelper.parameterExists(ClientApiConstants.countryParamName, element)) {
			final String country = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.countryParamName, element);
			baseDataValidator.reset().parameter(ClientApiConstants.countryParamName).value(country).ignoreIfNull()
					.notExceedingLengthOf(50);
		}
		throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}

	public void validateMaxAddressInClient(final Integer arraySize) {
		
		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("Address");

		final GlobalConfigurationPropertyData configurationData = this.confReadPlatformService.retrieveGlobalConfiguration("max-address-in-client");
		baseDataValidator.reset().parameter(ClientApiConstants.addressLimit).value(arraySize).maxArrayAddressInClient(configurationData.getValue().intValue());
		
		throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}

	public void validateForDelete(final long addressId) {

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("Address");

		baseDataValidator.reset().value(addressId).notBlank().integerGreaterThanZero();

		throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}
	
	private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
		if (!dataValidationErrors.isEmpty()) {
			throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
					"Validation errors exist.", dataValidationErrors);
		}
	}
}
