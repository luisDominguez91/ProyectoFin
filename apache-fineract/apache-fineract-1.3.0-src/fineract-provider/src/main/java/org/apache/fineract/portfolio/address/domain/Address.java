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
package org.apache.fineract.portfolio.address.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.domain.ClientAddress;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonObject;

@Entity
@Table(name = "m_address")
public class Address extends AbstractPersistableCustom<Long> {

	/*
	 * @OneToMany(mappedBy = "address", cascade = CascadeType.ALL) private
	 * List<ClientAddress> clientaddress = new ArrayList<>();
	 */

	@OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
	private Set<ClientAddress> clientaddress;

	@Column(name = "street", length = 50, nullable = true)
	private String street;

	@Column(name = "ctm_between_streets", length = 100, nullable = true)
	private String betweenStreets;

	@Column(name = "ctm_ext_no", length = 10, nullable = true)
	private String extNo;

	@Column(name = "ctm_int_no", length = 10, nullable = true)
	private String intNo;

	@Column(name = "address_line_1", length = 100, nullable = true)
	private String addressLine1;

	@Column(name = "ctm_building", length = 50, nullable = true)
	private String building;

	@Column(name = "ctm_department", length = 50, nullable = true)
	private String department;

	@Column(name = "address_line_2", length = 100, nullable = true)
	private String addressLine2;

	@Column(name = "ctm_lot", length = 50, nullable = true)
	private String lot;

	@Column(name = "ctm_block", length = 50, nullable = true)
	private String block;

	@Column(name = "address_line_3", length = 100, nullable = true)
	private String addressLine3;

	@Column(name = "postal_code", length = 5, nullable = true)
	private String postalCode;

	@Column(name = "ctm_suburb_id", nullable = true)
	private Long suburbId;

	@Column(name = "town_village", length = 100, nullable = true)
	private String townVillage;

	@Column(name = "ctm_city_id", nullable = true)
	private Long cityId;

	@Column(name = "city", length = 100, nullable = true)
	private String city;

	@Column(name = "ctm_municipality_id", nullable = true)
	private Long municipalityId;

	@Column(name = "county_district", length = 100, nullable = true)
	private String countyDistrict;

	@Column(name = "state_province_id", nullable = true)
	private Long stateId;

	@Column(name = "ctm_state", length = 100, nullable = true)
	private String state;

	@Column(name = "country_id", nullable = true)
	private Long countryId;

	@Column(name = "ctm_country", length = 50, nullable = true)
	private String country;

	@Column(name = "latitude")
	private BigDecimal latitude;

	@Column(name = "longitude")
	private BigDecimal longitude;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "ctm_is_delete")
	private Boolean ctmIsDelete;

	@Column(name = "ctm_delete_by")
	private Long ctmDeleteBy;

	private Address(final String street,final String betweenStreets,final String extNo, final String intNo, final String addressLine1,
					final String building, final String department, final String addressLine2, final String lot, final String block,
					final String addressLine3, final String postalCode, final Long suburbId, final String townVillage, final Long cityId,
					final String city, final Long municipalityId, final String countyDistrict, final Long stateId, final String state,
					final Long countryId, final String country, final BigDecimal latitude, final BigDecimal longitude,
					final String createdBy, final LocalDate createdOn, final String updatedBy,	final LocalDate updatedOn) {
		this.street = street;
		this.betweenStreets= betweenStreets;
		this.extNo= extNo;
		this.intNo= intNo;
		this.addressLine1 = addressLine1;
		this.building = building;
		this.department = department;
		this.addressLine2 = addressLine2;
		this.lot = lot;
		this.block = block;
		this.addressLine3 = addressLine3;
		this.postalCode = postalCode;
		this.suburbId = suburbId;
		this.townVillage = townVillage;
		this.cityId=cityId;
		this.city = city;
		this.municipalityId = municipalityId;
		this.countyDistrict = countyDistrict;
		this.stateId = stateId;
		this.state = state;
		this.countryId= countryId;
		this.country = country;

		this.latitude = latitude;
		this.longitude = longitude;
		this.createdBy = createdBy;
		if (createdOn != null) {
			this.createdOn = createdOn.toDate();

		}
		this.updatedBy = updatedBy;
		if (updatedOn != null) {
			this.updatedOn = updatedOn.toDate();
		}
	}

	public Address() {

	}

	public static Address fromJson(final JsonCommand command) {

		final String street = command.stringValueOfParameterNamed(ClientApiConstants.streetParamName);

		final String betweenStreets = command.stringValueOfParameterNamed(ClientApiConstants.betweenStreetsParamName);

		final String extNo = command.stringValueOfParameterNamed(ClientApiConstants.extNoParamName);

		final String intNo = command.stringValueOfParameterNamed(ClientApiConstants.intNoParamName);

		final String addressLine1 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine1ParamName);

		final String building = command.stringValueOfParameterNamed(ClientApiConstants.buildingParamName);

		final String department = command.stringValueOfParameterNamed(ClientApiConstants.departmentParamName);

		final String addressLine2 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine2ParamName);

		final String lot = command.stringValueOfParameterNamed(ClientApiConstants.lotParamName);

		final String block = command.stringValueOfParameterNamed(ClientApiConstants.blockParamName);

		final String addressLine3 = command.stringValueOfParameterNamed(ClientApiConstants.addressLine3ParamName);

		final String postalCode = command.stringValueOfParameterNamed(ClientApiConstants.postalCodeParamName);

		final Long suburbId = command.longValueOfParameterNamed(ClientApiConstants.suburbIdParamName);

		final String townVillage = command.stringValueOfParameterNamed(ClientApiConstants.townVillageParamName);

		final Long cityId = command.longValueOfParameterNamed(ClientApiConstants.cityIdParamName);

		final String city = command.stringValueOfParameterNamed(ClientApiConstants.cityParamName);

		final Long municipalityId = command.longValueOfParameterNamed(ClientApiConstants.municipalityIdParamName);

		final String countyDistrict = command.stringValueOfParameterNamed(ClientApiConstants.countyDistrictParamName);

		final Long stateId = command.longValueOfParameterNamed(ClientApiConstants.stateIdParamName);

		final String state = command.stringValueOfParameterNamed(ClientApiConstants.stateParamName);

		final Long countryId = command.longValueOfParameterNamed(ClientApiConstants.countryIdParamName);

		final String country = command.stringValueOfParameterNamed(ClientApiConstants.countryParamName);

		final BigDecimal latitude = command.bigDecimalValueOfParameterNamed("latitude");

		final BigDecimal longitude = command.bigDecimalValueOfParameterNamed("longitude");

		final String createdBy = command.stringValueOfParameterNamed("createdBy");

		final LocalDate createdOn = command.localDateValueOfParameterNamed("createdOn");

		final String updatedBy = command.stringValueOfParameterNamed("updatedBy");

		final LocalDate updatedOn = command.localDateValueOfParameterNamed("updatedOn");

		return new Address(street, betweenStreets,extNo,intNo, addressLine1, building,department, addressLine2, lot, block,
				addressLine3, postalCode,suburbId, townVillage, cityId, city, municipalityId, countyDistrict,
				stateId, state , countryId, country, latitude, longitude, createdBy, createdOn, updatedBy, updatedOn);
	}

	public static Address fromJsonObject(final JsonObject jsonObject) {
		String street = "";
		String betweenStreets = "";
		String extNo = "";
		String intNo = "";
		String addressLine1 = "";
		String building = "";
		String department = "";
		String addressLine2 = "";
		String lot = "";
		String block = "";
		String addressLine3 = "";
		String postalCode = "";
		Long suburbId = null;
		String townVillage = "";
		Long cityId = null;
		String city = "";
		Long municipalityId = null;
		String countyDistrict = "";
		Long stateId = null;
		String state = "";
		Long countryId = null;
		String country = "";
		BigDecimal latitude = BigDecimal.ZERO;
		BigDecimal longitude = BigDecimal.ZERO;
		String createdBy = "";
		Locale locale = Locale.ENGLISH;
		String updatedBy = "";
		LocalDate updatedOnDate = null;
		LocalDate createdOnDate = null;

		if (jsonObject.has(ClientApiConstants.streetParamName)) {
			street = jsonObject.get(ClientApiConstants.streetParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.betweenStreetsParamName)) {
			betweenStreets = jsonObject.get(ClientApiConstants.betweenStreetsParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.extNoParamName)) {
			extNo = jsonObject.get(ClientApiConstants.extNoParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.intNoParamName)) {
			intNo = jsonObject.get(ClientApiConstants.intNoParamName).getAsString();
		}

		if (jsonObject.has(ClientApiConstants.addressLine1ParamName)) {
			addressLine1 = jsonObject.get(ClientApiConstants.addressLine1ParamName).getAsString();
		}

		if (jsonObject.has(ClientApiConstants.buildingParamName)) {
			building = jsonObject.get(ClientApiConstants.buildingParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.departmentParamName)) {
			department = jsonObject.get(ClientApiConstants.departmentParamName).getAsString();
		}

		if (jsonObject.has(ClientApiConstants.addressLine2ParamName)) {
			addressLine2 = jsonObject.get(ClientApiConstants.addressLine2ParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.lotParamName)) {
			lot = jsonObject.get(ClientApiConstants.lotParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.blockParamName)) {
			block = jsonObject.get(ClientApiConstants.blockParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.addressLine3ParamName)) {
			addressLine3 = jsonObject.get(ClientApiConstants.addressLine3ParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.postalCodeParamName)) {
			postalCode = jsonObject.get(ClientApiConstants.postalCodeParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.suburbIdParamName)) {
			suburbId = jsonObject.get(ClientApiConstants.suburbIdParamName).getAsLong();
		}
		if (jsonObject.has(ClientApiConstants.townVillageParamName)) {
			townVillage = jsonObject.get(ClientApiConstants.townVillageParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.cityIdParamName)) {
			cityId = jsonObject.get(ClientApiConstants.cityIdParamName).getAsLong();
		}
		if (jsonObject.has(ClientApiConstants.cityParamName)) {
			city = jsonObject.get(ClientApiConstants.cityParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.municipalityIdParamName)) {
			municipalityId = jsonObject.get(ClientApiConstants.municipalityIdParamName).getAsLong();
		}
		if (jsonObject.has(ClientApiConstants.countyDistrictParamName)) {
			countyDistrict = jsonObject.get(ClientApiConstants.countyDistrictParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.stateIdParamName)) {
			stateId = jsonObject.get(ClientApiConstants.stateIdParamName).getAsLong();
		}
		if (jsonObject.has(ClientApiConstants.stateParamName)) {
			state = jsonObject.get(ClientApiConstants.stateParamName).getAsString();
		}
		if (jsonObject.has(ClientApiConstants.countryIdParamName)) {
			countryId = jsonObject.get(ClientApiConstants.countryIdParamName).getAsLong();
		}
		if (jsonObject.has(ClientApiConstants.countryParamName)) {
			country = jsonObject.get(ClientApiConstants.countryParamName).getAsString();
		}

		if (jsonObject.has("latitude")) {

			latitude = jsonObject.get("latitude").getAsBigDecimal();
		}
		if (jsonObject.has("longitude")) {

			longitude = jsonObject.get("longitude").getAsBigDecimal();
		}

		if (jsonObject.has("createdBy")) {
			createdBy = jsonObject.get("createdBy").getAsString();
		}
		if (jsonObject.has("createdOn")) {
			String createdOn = jsonObject.get("createdOn").getAsString();
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
			createdOnDate = LocalDate.parse(createdOn, formatter);

		}
		if (jsonObject.has("updatedBy")) {
			updatedBy = jsonObject.get("updatedBy").getAsString();
		}
		if (jsonObject.has("updatedOn")) {
			String updatedOn = jsonObject.get("updatedOn").getAsString();
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
			updatedOnDate = LocalDate.parse(updatedOn, formatter);
		}

		return new Address(street, betweenStreets,extNo,intNo, addressLine1, building,department, addressLine2, lot, block,
				addressLine3, postalCode,suburbId, townVillage, cityId, city, municipalityId, countyDistrict,
				stateId, state , countryId, country, latitude, longitude, createdBy, createdOnDate, updatedBy, updatedOnDate);

	}

	public Set<ClientAddress> getClientaddress() {
		return this.clientaddress;
	}

	public void setClientaddress(Set<ClientAddress> clientaddress) {
		this.clientaddress = clientaddress;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return this.addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getTownVillage() {
		return this.townVillage;
	}

	public void setTownVillage(String townVillage) {
		this.townVillage = townVillage;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountyDistrict() {
		return this.countyDistrict;
	}

	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}


	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn.toDate();
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn.toDate();;
	}

	public String getBetweenStreets() {
		return betweenStreets;
	}

	public void setBetweenStreets(String betweenStreets) {
		this.betweenStreets = betweenStreets;
	}

	public String getExtNo() {
		return extNo;
	}

	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}

	public String getIntNo() {
		return intNo;
	}

	public void setIntNo(String intNo) {
		this.intNo = intNo;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public Long getSuburbId() {
		return suburbId;
	}

	public void setSuburbId(Long suburbId) {
		this.suburbId = suburbId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getMunicipalityId() {
		return municipalityId;
	}

	public void setMunicipalityId(Long municipalityId) {
		this.municipalityId = municipalityId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void delete(){
		this.ctmIsDelete = true;
	}

	public boolean ctmIsDelete(){return this.ctmIsDelete;}

	public Long getCtmDeleteBy() { return ctmDeleteBy; }

	public void setCtmDeleteBy(Long ctmDeleteBy) { this.ctmDeleteBy = ctmDeleteBy; }
}
