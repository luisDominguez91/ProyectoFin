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

import org.apache.fineract.portfolio.client.api.ClientApiConstants;

import java.util.Collection;

@SuppressWarnings("unused")
public class PostalCodeData {

	private final String postalCode;

	private final Long cityId;

	private final String cityName;

	private final Long stateId;

	private final String stateName;

	private final Long municipalityId;

	private final String municipalityName;

	private final Long countryId;

	private final String countryName;

	private Collection<SuburbData> suburbOptions;


	private PostalCodeData(String postalCode, Long cityId, String cityName, Long stateId, String stateName,
						   Long municipalityId, String municipalityName, Long countryId, String countryName,
                           Collection<SuburbData> suburbOptions) {
		this.postalCode = postalCode;
		this.cityId = cityId;
		this.cityName = cityName;
		this.stateId = stateId;
		this.stateName = stateName;
		this.municipalityId = municipalityId;
		this.municipalityName = municipalityName;
		this.countryId = countryId;
		this.countryName = countryName;
		this.suburbOptions = suburbOptions;
	}

	public static PostalCodeData instance(final String postalCode, final Long cityId, final String cityName, final Long stateId, final String stateName,
										  Long municipalityId, String municipalityName, final Long countryId, final String countryName,
										  final Collection<SuburbData> suburbOptions) {
		return new PostalCodeData(postalCode, cityId, cityName, stateId, stateName, municipalityId, municipalityName, countryId, countryName,
				suburbOptions);
	}

	public void setSuburbOptions(Collection<SuburbData> suburbOptions){
		this.suburbOptions = suburbOptions;
	}
}
