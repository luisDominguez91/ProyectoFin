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

public class CURPData {

	private final String responseCode;

	private final String responseMessage;

	private final String curp;

	private final String firstname;

	private final String lastname;

	private final String motherlastname;

	private final String nss;


	public CURPData(String responseCode, String responseMessage,String curp,String firstname,String lastname,
					 String motherlastname,String nss) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.curp = curp;
		this.firstname = firstname;
		this.lastname = lastname;
		this.motherlastname = motherlastname;
		this.nss = nss;
	}

}
