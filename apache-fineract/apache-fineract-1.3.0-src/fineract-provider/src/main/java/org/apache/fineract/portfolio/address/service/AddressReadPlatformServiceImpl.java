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

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.LocalDate;
import java.util.*;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.portfolio.address.data.AddressData;
import org.apache.fineract.portfolio.address.data.CURPData;
import org.apache.fineract.portfolio.address.data.PostalCodeData;
import org.apache.fineract.portfolio.address.data.StateData;
import org.apache.fineract.portfolio.address.data.SuburbData;
import org.apache.fineract.portfolio.address.exception.PostalCodeNotFoundException;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class AddressReadPlatformServiceImpl implements AddressReadPlatformService {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;
	private final CodeValueReadPlatformService readService;

	@Autowired
	public AddressReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource,
			final CodeValueReadPlatformService readService) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.readService = readService;
	}

	private static final class AddFieldsMapper implements RowMapper<AddressData> {
		public String schema() {
			return "addr.id as id,client.id as client_id,addr.street as street,addr.address_line_1 as address_line_1,addr.address_line_2 as address_line_2,"
					+ "addr.address_line_3 as address_line_3,addr.town_village as town_village, addr.city as city,addr.county_district as county_district,"
					+ "addr.state_province_id as state_province_id, addr.country_id as country_id,addr.postal_code as postal_code,addr.latitude as latitude,"
					+ "addr.longitude as longitude,addr.created_by as created_by,addr.created_on as created_on,addr.updated_by as updated_by,"
					+ "addr.updated_on as updated_on from m_address as addr,m_client client,addr.ctm_between_streets as ctm_between_streets,"
					+ "addr.ctm_department as ctm_department, addr.ctm_lot as ctm_lot, addr.ctm_block as ctm_block,addr.ctm_state as state_name, "
					+ "addr.ctm_suburb_id as ctm_suburb_id, ctm_city_id, addr.ctm_municipality_id as ctm_municipality_id,addr.ctm_country as country_name";
		}

		@Override
		public AddressData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			final long addressId = rs.getLong("id");

			final long clientId = rs.getLong("client_id");

			final String street = rs.getString("street");

			final String address_line_1 = rs.getString("address_line_1");

			final String address_line_2 = rs.getString("address_line_2");

			final String address_line_3 = rs.getString("address_line_3");

			final String town_village = rs.getString("town_village");

			final String city = rs.getString("city");

			final String county_district = rs.getString("county_district");

			final long state_province_id = rs.getLong("state_province_id");

			final long country_id = rs.getLong("country_id");

			final String postal_code = rs.getString("postal_code");

			final BigDecimal latitude = rs.getBigDecimal("latitude");

			final BigDecimal longitude = rs.getBigDecimal("longitude");

			final String created_by = rs.getString("created_by");

			final LocalDate created_on = JdbcSupport.getLocalDate(rs, "created_on");

			final String updated_by = rs.getString("updated_by");

			final LocalDate updated_on = JdbcSupport.getLocalDate(rs, "updated_on");

			// Se agregan campos {
			final String betweenStreets = rs.getString("ctm_between_streets");
			final String extNo = rs.getString("ctm_ext_no");
			final String intNo = rs.getString("ctm_int_no");
			final String building = rs.getString("ctm_building");
			final String department = rs.getString("ctm_department");
			final String lot = rs.getString("ctm_lot");
			final String block = rs.getString("ctm_block");
			final Long suburbId = rs.getLong("ctm_suburb_id");
			final Long cityId = rs.getLong("ctm_city_id");
			final Long municipalityId = rs.getLong("ctm_municipality_id");
			final String country = rs.getString("country_name");
			final String state = rs.getString("state_name");
			// }

			return AddressData.instance1(addressId, street, address_line_1, address_line_2, address_line_3,
					town_village, city, county_district, state_province_id, country_id, state, country, postal_code,
					latitude, longitude, created_by, created_on, updated_by, updated_on, betweenStreets, extNo, intNo,
					building, department, lot, block, suburbId, cityId, municipalityId);

		}
	}

	private static final class AddMapper implements RowMapper<AddressData> {
		public String schema() {
			return "cv2.code_value as addressType,ca.client_id as client_id,addr.id as id,ca.address_type_id as addresstyp,ca.is_active as is_active,addr.street as street,addr.address_line_1 as address_line_1,addr.address_line_2 as address_line_2,"
					+ "addr.address_line_3 as address_line_3,addr.town_village as town_village, addr.city as city,addr.county_district as county_district,"
					+ "addr.state_province_id as state_province_id,addr.ctm_state as state_name, addr.country_id as country_id,addr.ctm_country as country_name,addr.postal_code as postal_code,addr.latitude as latitude,"
					+ "addr.longitude as longitude,addr.created_by as created_by,addr.created_on as created_on,addr.updated_by as updated_by,"
					+ "addr.updated_on as updated_on, addr.updated_on as updated_on,addr.ctm_between_streets as ctm_between_streets,"
					+ "addr.ctm_ext_no as ctm_ext_no, addr.ctm_int_no as ctm_int_no,addr.ctm_building as ctm_building,"
					+ "addr.ctm_department as ctm_department, addr.ctm_lot as ctm_lot, addr.ctm_block as ctm_block,"
					+ "addr.ctm_suburb_id as ctm_suburb_id, ctm_city_id, addr.ctm_municipality_id as ctm_municipality_id"
					+ " from m_address addr" + " join m_client_address ca on addr.id= ca.address_id"
					+ " join m_code_value cv2 on ca.address_type_id=cv2.id";
		}

		@Override
		public AddressData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			final String addressType = rs.getString("addressType");
			final long addressId = rs.getLong("id");

			final long client_id = rs.getLong("client_id");

			final String street = rs.getString("street");

			final long address_type_id = rs.getLong("addresstyp");

			final boolean is_active = rs.getBoolean("is_active");

			final String address_line_1 = rs.getString("address_line_1");

			final String address_line_2 = rs.getString("address_line_2");

			final String address_line_3 = rs.getString("address_line_3");

			final String town_village = rs.getString("town_village");

			final String city = rs.getString("city");

			final String county_district = rs.getString("county_district");

			final long state_province_id = rs.getLong("state_province_id");

			final long country_id = rs.getLong("country_id");

			final String country = rs.getString("country_name");

			final String state = rs.getString("state_name");

			final String postal_code = rs.getString("postal_code");

			final BigDecimal latitude = rs.getBigDecimal("latitude");

			final BigDecimal longitude = rs.getBigDecimal("longitude");

			final String created_by = rs.getString("created_by");

			final LocalDate created_on = JdbcSupport.getLocalDate(rs, "created_on");

			final String updated_by = rs.getString("updated_by");

			final LocalDate updated_on = JdbcSupport.getLocalDate(rs, "updated_on");

			// Se agregan campos {
			final String betweenStreets = rs.getString("ctm_between_streets");
			final String extNo = rs.getString("ctm_ext_no");
			final String intNo = rs.getString("ctm_int_no");
			final String building = rs.getString("ctm_building");
			final String department = rs.getString("ctm_department");
			final String lot = rs.getString("ctm_lot");
			final String block = rs.getString("ctm_block");
			final Long suburbId = rs.getLong("ctm_suburb_id");
			final Long cityId = rs.getLong("ctm_city_id");
			final Long municipalityId = rs.getLong("ctm_municipality_id");
			// }

			return AddressData.instance(addressType, client_id, addressId, address_type_id, is_active, street,
					address_line_1, address_line_2, address_line_3, town_village, city, county_district,
					state_province_id, country_id, state, country, postal_code, latitude, longitude, created_by,
					created_on, updated_by, updated_on, betweenStreets, extNo, intNo, building, department, lot, block,
					suburbId, cityId, municipalityId);

		}
	}

	private static final class PostalCodeMapper implements RowMapper<PostalCodeData> {
		public String schema() {
			return "distinct ctm_postal_code as postalCode, m.ctm_municipality_id as munucipalityId, m.ctm_name as municipalityName,"
					+ "       c.ctm_city_id as cityId, c.ctm_name as cityName, e.ctm_state_id as stateId, e.ctm_name as stateName"
					+ " from ctm_cat_suburb s"
					+ " left join ctm_cat_municipality m on m.ctm_municipality_id= s.ctm_municipality_id"
					+ " left join ctm_cat_city c on c.ctm_city_id= m.ctm_city_id"
					+ " left join ctm_cat_state e on e.ctm_state_id= m.ctm_state_id";
		}

		@Override
		public PostalCodeData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			final String postalCode = rs.getString("postalCode");

			final long cityId = rs.getLong("cityId");
			final String cityName = rs.getString("cityName");

			final long stateId = rs.getLong("stateId");
			final String stateName = rs.getString("stateName");

			final long municipalityId = rs.getLong("munucipalityId");
			final String municipalityName = rs.getString("municipalityName");

			return PostalCodeData.instance(postalCode, cityId, cityName, stateId, stateName, municipalityId,
					municipalityName, ClientApiConstants.countryIdParamValue, ClientApiConstants.countryNameParamValue,
					null);

		}
	}

	private static final class SuburbMaper implements RowMapper<SuburbData> {
		public String schema() {
			return "ctm_id_suburb as suburbId, ctm_name as suburbName" + " from ctm_cat_suburb s";
		}

		@Override
		public SuburbData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

			final long suburbId = rs.getLong("suburbId");
			final String suburbName = rs.getString("suburbName");

			return SuburbData.instance(suburbId, suburbName);

		}
	}

	private static final class StateMapper implements RowMapper<StateData> {
		public String schema() {
			return "ctm_state_id as stateId, ctm_name as state";
		}

		@Override
		public StateData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			final long stateId = rs.getLong("stateId");
			final String stateName = rs.getString("state");
			return StateData.instance(stateId, stateName);

		}
	}

	@Override
	public Collection<AddressData> retrieveAddressFields(final long clientid) {
		this.context.authenticatedUser();

		final AddFieldsMapper rm = new AddFieldsMapper();
		final String sql = "select " + rm.schema() + " where client.id=?";

		return this.jdbcTemplate.query(sql, rm, new Object[] { clientid });
	}

	@Override
	public Collection<AddressData> retrieveAllClientAddress(final long clientid) {
		this.context.authenticatedUser();
		final AddMapper rm = new AddMapper();
		final String sql = "select " + rm.schema() + " and ca.client_id=? and addr.ctm_is_delete is null";
		return this.jdbcTemplate.query(sql, rm, new Object[] { clientid });
	}

	@Override
	public Collection<AddressData> retrieveAddressbyType(final long clientid, final long typeid) {
		this.context.authenticatedUser();

		final AddMapper rm = new AddMapper();
		final String sql = "select " + rm.schema() + " and ca.client_id=? and ca.address_type_id=?";

		return this.jdbcTemplate.query(sql, rm, new Object[] { clientid, typeid });
	}

	@Override
	public Collection<AddressData> retrieveAddressbyTypeAndStatus(final long clientid, final long typeid,
			final String status) {
		this.context.authenticatedUser();
		Boolean temp = false;
		temp = Boolean.parseBoolean(status);

		final AddMapper rm = new AddMapper();
		final String sql = "select " + rm.schema() + " and ca.client_id=? and ca.address_type_id=? and ca.is_active=?";

		return this.jdbcTemplate.query(sql, rm, new Object[] { clientid, typeid, temp });
	}

	@Override
	public Collection<AddressData> retrieveAddressbyStatus(final long clientid, final String status) {
		this.context.authenticatedUser();
		Boolean temp = false;
		temp = Boolean.parseBoolean(status);

		final AddMapper rm = new AddMapper();
		final String sql = "select " + rm.schema() + " and ca.client_id=? and ca.is_active=?";

		return this.jdbcTemplate.query(sql, rm, new Object[] { clientid, temp });
	}

	@Override
	public AddressData retrieveTemplate() {
		final List<CodeValueData> countryoptions = new ArrayList<>(
				this.readService.retrieveCodeValuesByCode("COUNTRY"));

		final List<CodeValueData> StateOptions = new ArrayList<>(this.readService.retrieveCodeValuesByCode("STATE"));

		final List<CodeValueData> addressTypeOptions = new ArrayList<>(
				this.readService.retrieveCodeValuesByCode("ADDRESS_TYPE"));

		return AddressData.template(countryoptions, StateOptions, addressTypeOptions);
	}

	@Override
	public PostalCodeData retrievePostalCode(final String postalCode) {
		try {
			this.context.authenticatedUser();

			final PostalCodeMapper pcm = new PostalCodeMapper();
			final String sqlPostalCodes = "select " + pcm.schema() + " where ctm_postal_code=?";
			final List<PostalCodeData> pcs = this.jdbcTemplate.query(sqlPostalCodes, pcm, new Object[] { postalCode });
			if (pcs.isEmpty()) {
				throw new EmptyResultDataAccessException(0);
			}
			PostalCodeData temp = pcs.get(0);

			final SuburbMaper sm = new SuburbMaper();
			final String sqlSuburbs = "select " + sm.schema() + " where ctm_postal_code=?";
			final List<SuburbData> suburbs = this.jdbcTemplate.query(sqlSuburbs, sm, new Object[] { postalCode });

			temp.setSuburbOptions(suburbs);

			return temp;
		} catch (final EmptyResultDataAccessException e) {
			throw new PostalCodeNotFoundException(postalCode);
		}

	}

	@Override
	public CURPData retrieveCURP(final String curp) {
		String responseCode = "";
		String responseMessage = "";
		String firstname = "";
		String lastname = "";
		String motherlastname = "";
		String nss = "";
		try {

			String requestCURP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
					+ "xmlns:con=\"http://www.procesar.com.mx/TraspasosInfonavitFovissste/Fovissste/SolicitudCredito/consultarSaldoFovissste/\">"
					+ "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <con:consultarSaldoFovisssteRequest>"
					+ "         <cuerpo>" + "            <curp>" + curp + "</curp>" + "         </cuerpo>"
					+ "      </con:consultarSaldoFovisssteRequest>" + "   </soapenv:Body>" + "</soapenv:Envelope>";

			// Code to make a webservice HTTP request
			String wsURL = "http://192.168.1.237/TraspasosFovissste/SolicitudCredito";
			URL url = new URL(wsURL);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) connection;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			String xmlInput = requestCURP;

			byte[] buffer = new byte[xmlInput.length()];
			buffer = xmlInput.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			// Llevar a properties:
			String SOAPAction = "http://www.procesar.com.mx/TraspasosInfonavitFovissste/Fovissste/SolicitudCredito/consultarSaldoFovissste/";
			// Set the appropriate HTTP parameters.
			// Llevar a properties y encriptar:
			String userpass = "x" + ":" + "x";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
			httpConn.setRequestProperty("Authorization", basicAuth);
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			// Write the content of the request to the outputstream of the HTTP Connection.
			out.write(b);
			out.close();
			// Ready with sending the request.

			// Read the response.
			Document doc = null;
			InputStreamReader isr = null;
			if (httpConn.getResponseCode() == 200) {
				InputStream input = new BufferedInputStream(httpConn.getInputStream());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(input);
				NodeList nodeLst = doc.getElementsByTagName("codigoResultadoOperacion");
				responseCode = nodeLst.item(0).getTextContent();
				if (responseCode.compareTo("01") == 0) {
					responseMessage = "curp.exits";
					NodeList nodeLastname = doc.getElementsByTagName("apellidoPaterno");
					NodeList nodeMotherlastname = doc.getElementsByTagName("apellidoMaterno");
					NodeList nodeFistname = doc.getElementsByTagName("nombreTrabajador");
					NodeList nodeNSS = doc.getElementsByTagName("nssISSSTE");
					lastname = nodeLastname.item(0).getTextContent();
					motherlastname = nodeMotherlastname.item(0).getTextContent();
					firstname = nodeFistname.item(0).getTextContent();
					nss = nodeNSS.item(0).getTextContent();
				} else {
					responseMessage = "curp.does.not.exits";
				}
			} else {
				InputStream input = new BufferedInputStream(httpConn.getErrorStream());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(input);
				responseCode = "03";
				responseMessage = "curp.error.pattern";
			}
			System.out.println(responseCode);
			System.out.println(responseMessage);
			System.out.println(lastname);
			System.out.println(motherlastname);
			System.out.println(firstname);
			System.out.println(nss);
		} catch (Exception e) {
			responseCode = "04";
			responseMessage = "curp.service.error";

		} finally {
			return new CURPData(responseCode, responseMessage, curp, firstname, lastname, motherlastname, nss);
		}
	}

	@Override
	public List<StateData> catalogoEstados() {
		try{
			this.context.authenticatedUser();
			final StateMapper stm = new StateMapper();
			final String sqlState = "SELECT "+stm.schema()+" FROM ctm_cat_state";
			final List<StateData> listaEstados = this.jdbcTemplate.query(sqlState, stm);

			if (listaEstados.isEmpty()) {
				throw new EmptyResultDataAccessException(0);
			}

			return listaEstados;

		}catch(final EmptyResultDataAccessException e){
			throw new EmptyResultDataAccessException(0);
		}
	}

}
