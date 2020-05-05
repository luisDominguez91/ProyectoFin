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
package org.apache.fineract.portfolio.address.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.joda.time.LocalDate;

@SuppressWarnings("unused")
public class AddressData {
	
	private final Long client_id;

	private final String addressType;

	private final Long addressId;

	private final Long addressTypeId;

	private final Boolean isActive;

	private final String street;

	private final String addressLine1;

	private final String addressLine2;

	private final String addressLine3;

	private final String townVillage;

	private final String city;

	private final String countyDistrict;

	private final Long stateId;

	private final String country;

	private final String state;

	private final Long countryId;

	private final String postalCode;

	private final BigDecimal latitude;

	private final BigDecimal longitude;

	private final String createdBy;

	private final LocalDate createdOn;

	private final String updatedBy;

	private final LocalDate updatedOn;

	// Se agregan nuevos campos {

	private final String betweenStreets;
	private final String extNo;
	private final String intNo;
	private final String building;
	private final String department;
	private final String lot;
	private final String block;
	private final Long suburbId;
	private final Long cityId;
	private final Long municipalityId;
	// }

	// template holder
	private final Collection<CodeValueData> countryIdOptions;
	private final Collection<CodeValueData> stateIdOptions;
	private final Collection<CodeValueData> addressTypeIdOptions;

	public AddressData(Long addressTypeId,String street, String addressLine1, String addressLine2, String addressLine3,
			String city,String postalCode, Boolean isActive,Long stateId,Long countryId) {

		this.addressTypeId = addressTypeId;
		this.isActive = isActive;
		this.street = street;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.countryId = countryId;
		this.postalCode = postalCode;
		this.stateId = stateId;
		this.city = city;
		this.townVillage = null;
		this.client_id = null;
		this.addressType = null;
		this.addressId = null;
		this.countyDistrict = null;
		this.country = null;
		this.state = null;
		this.latitude = null;
		this.longitude = null;
		this.createdBy = null;
		this.createdOn = null;
		this.updatedBy = null;
		this.updatedOn = null;
		this.countryIdOptions = null;
		this.stateIdOptions = null;
		this.addressTypeIdOptions = null;
		// Se agregan campos {
		this.betweenStreets=null;
		this.extNo=null;
		this.intNo=null;
		this.building=null;
		this.department=null;
		this.lot=null;
		this.block=null;
		this.suburbId=null;
		this.cityId=null;
		this.municipalityId=null;
		// }
	}


	private AddressData(final String addressType, final Long client_id, final Long addressId, final Long addressTypeId,
			final Boolean is_active, final String street, final String addressLine1, final String addressLine2,
			final String addressLine3, final String townVillage, final String city, final String countyDistrict,
			final Long stateId, final Long countryId, final String state, final String country,
			final String postalCode, final BigDecimal latitude, final BigDecimal longitude, final String createdBy,
			final LocalDate createdOn, final String updatedBy, final LocalDate updatedOn,
			final String betweenStreets,final String extNo, final String intNo, final String building, final String department,
			final String lot, final String block, final Long suburbId, final Long cityId,final Long municipalityId,
			final Collection<CodeValueData> countryIdOptions, final Collection<CodeValueData> stateIdOptions,
			final Collection<CodeValueData> addressTypeIdOptions) {
		this.addressType = addressType;
		this.client_id = client_id;
		this.addressId = addressId;
		this.addressTypeId = addressTypeId;
		this.isActive = is_active;
		this.street = street;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.townVillage = townVillage;
		this.city = city;
		this.countyDistrict = countyDistrict;
		this.stateId = stateId;
		this.countryId = countryId;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.countryIdOptions = countryIdOptions;
		this.stateIdOptions = stateIdOptions;
		this.addressTypeIdOptions = addressTypeIdOptions;
		this.betweenStreets=betweenStreets;
		this.extNo=extNo;
		this.intNo=intNo;
		this.building=building;
		this.department=department;
		this.lot=lot;
		this.block=block;
		this.suburbId=suburbId;
		this.cityId=cityId;
		this.municipalityId=municipalityId;
	}

	public static AddressData instance(final String addressType, final Long client_id, final Long addressId,
			final Long addressTypeId, final Boolean is_active, final String street, final String addressLine1,
			final String addressLine2, final String addressLine3, final String townVillage, final String city,
			final String countyDistrict, final Long stateId, final Long countryId, final String state,
			final String country, final String postalCode, final BigDecimal latitude, final BigDecimal longitude,
			final String createdBy, final LocalDate createdOn, final String updatedBy, final LocalDate updatedOn,
			final String betweenStreets,final String extNo, final String intNo, final String building, final String department,
			final String lot, final String block, final Long suburbId, final Long cityId,final Long municipalityId) {

		return new AddressData(addressType, client_id, addressId, addressTypeId, is_active, street, addressLine1,
				addressLine2, addressLine3, townVillage, city, countyDistrict, stateId, countryId,
				state, country, postalCode, latitude, longitude, createdBy, createdOn, updatedBy,
				updatedOn, betweenStreets, extNo, intNo, building, department, lot, block, suburbId, cityId,municipalityId,
				null, null, null);
	}

	public static AddressData instance1(final Long addressId, final String street, final String addressLine1,
			final String addressLine2, final String addressLine3, final String townVillage, final String city,
			final String countyDistrict, final Long stateId, final Long countryId, final String state, final String country,final String postalCode,
			final BigDecimal latitude, final BigDecimal longitude, final String createdBy, final LocalDate createdOn,
			final String updatedBy, final LocalDate updatedOn, final String betweenStreets,final String extNo,
			final String intNo, final String building, final String department,
			final String lot, final String block, final Long suburbId, final Long cityId,final Long municipalityId) {
		return new AddressData(null, null, addressId, null, false, street, addressLine1, addressLine2,
				addressLine3, townVillage, city, countyDistrict, stateId, countryId, state, country,
				postalCode, latitude, longitude, createdBy, createdOn, updatedBy, updatedOn,
				betweenStreets, extNo, intNo, building, department, lot, block, suburbId, cityId,municipalityId,
				null, null, null);
	}

	public static AddressData template(final Collection<CodeValueData> countryIdOptions,
			final Collection<CodeValueData> stateIdOptions,
			final Collection<CodeValueData> addressTypeIdOptions) {
		final Long client_idtemp = null;

		final Long addressIdtemp = null;

		final Long addressTypeIdtemp = null;

		final Boolean is_activetemp = null;

		final String streettemp = null;

		final String addressLine1temp = null;

		final String addressLine2temp = null;

		final String addressLine3temp = null;

		final String townVillagetemp = null;

		final String citytemp = null;

		final String countyDistricttemp = null;

		final Long stateIdtemp = null;

		final Long countryIdtemp = null;

		final String postalCodetemp = null;

		final BigDecimal latitudetemp = null;

		final BigDecimal longitudetemp = null;

		final String createdBytemp = null;

		final LocalDate createdOntemp = null;

		final String updatedBytemp = null;

		final LocalDate updatedOntemp = null;

		// Se agregan nuevos campos {
		final String betweenStreetstemp=null;
		final String extNotemp=null;
		final String intNotemp=null;
		final String buildingtemp=null;
		final String departmenttemp=null;
		final String lottemp=null;
		final String blocktemp=null;
		final Long suburbIdtemp=null;
		final Long cityIdtemp=null;
		final Long municipalityIdtemp=null;
		// }
		return new AddressData(null, client_idtemp, addressIdtemp, addressTypeIdtemp, is_activetemp, streettemp,
				addressLine1temp, addressLine2temp, addressLine3temp, townVillagetemp, citytemp,
				countyDistricttemp, stateIdtemp, countryIdtemp, null, null, postalCodetemp, latitudetemp,
				longitudetemp, createdBytemp, createdOntemp, updatedBytemp, updatedOntemp,
				betweenStreetstemp, extNotemp, intNotemp, buildingtemp, departmenttemp, lottemp, blocktemp, suburbIdtemp, cityIdtemp,municipalityIdtemp,
				countryIdOptions, stateIdOptions, addressTypeIdOptions);
	}

}
