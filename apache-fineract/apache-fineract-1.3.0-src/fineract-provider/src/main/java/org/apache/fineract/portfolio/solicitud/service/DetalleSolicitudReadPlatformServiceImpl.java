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
package org.apache.fineract.portfolio.solicitud.service;

import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.security.utils.ColumnValidator;
import org.apache.fineract.portfolio.solicitud.data.ExpedienteData;
import org.apache.fineract.portfolio.client.service.ClientReadPlatformService;
import org.apache.fineract.portfolio.solicitud.data.SolicitudCollectionData;
import org.apache.fineract.portfolio.solicitud.data.SolicitudData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class DetalleSolicitudReadPlatformServiceImpl implements DetalleSolicitudReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final ClientReadPlatformService clientReadPlatformService;
    private final ColumnValidator columnValidator;

    @Autowired
    public DetalleSolicitudReadPlatformServiceImpl(final RoutingDataSource dataSource,
            final ClientReadPlatformService clientReadPlatformService, final ColumnValidator columnValidator) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.clientReadPlatformService = clientReadPlatformService;
        this.columnValidator = columnValidator;
    }

    @Override
    public SolicitudCollectionData recuperarDetalleSolicitudCliente(final Long clientId) {
        // Check if client exists
        this.clientReadPlatformService.retrieveOne(clientId);
        final String solicitudwhereClause = " where sol.ctm_id_cliente_titular = ?";

        // datos dummi de solicitudes {
        // final SolicitudData solicitudData = new
        // SolicitudData(Long.valueOf(202002325076L), Long.valueOf(clientId), null,
        // Long.valueOf(23), "TRAD", "TRADICIONAL", 32, "Zacatecas", 3, "GENERACIÓN DE
        // EXPEDIENTE", 3, "Vivienda Nueva", 1, "Adquisicion", "INDIVIDUAL", 1,
        // "ACTIVO");
        // final List<SolicitudData> solicitudes = new ArrayList<>();
        // solicitudes.add(solicitudData);
        // }

        final List<SolicitudData> solicitudes = recuperarDetalleSolicitud(solicitudwhereClause,
                new Object[] { clientId });
        return new SolicitudCollectionData(solicitudes);
    }

    private List<SolicitudData> recuperarDetalleSolicitud(final String loanwhereClause, final Object[] inputs) {
        final SolicitudDataMapper rm = new SolicitudDataMapper();
        final String sql = "select " + rm.solicitudSchema() + loanwhereClause;
        this.columnValidator.validateSqlInjection(rm.solicitudSchema(), loanwhereClause);
        return this.jdbcTemplate.query(sql, rm, inputs);
    }

    private static final class SolicitudDataMapper implements RowMapper<SolicitudData> {

        public String solicitudSchema() {

            final StringBuilder solicitudes = new StringBuilder("sol.ctm_id_solicitud as idSolicitud");
            solicitudes.append(",sol.ctm_id_cliente_titular as idClienteTitular")
                    .append(",sol.ctm_id_cliente_conyuge as idClienteconyuge")
                    .append(",sol.ctm_id_producto as idProducto").append(",prdct.short_name as nombreCortoProducto")
                    .append(",prdct.name as nombreProducto").append(",sol.ctm_id_estado as idEstado")
                    .append(",std.ctm_name as nombreEstado").append(",sol.ctm_id_fase as idFase")
                    .append(",fs.ctm_descripcion as descripcionFase").append(",sol.ctm_id_modalidad as idModalidad")
                    .append(",mdldd.ctm_descripcion as descripcionModalidad")
                    .append(",sol.ctm_id_linea_credito as idLineaCredito")
                    .append(",lncrdt.ctm_descripcion as descripcionLineaCredito")
                    .append(",case sol.ctm_id_tipo_credito when 'I' then 'INDIVIDUAL' when 'M' then 'MANCOMUNADO' end as tipoCredito")
                    .append(",sol.ctm_id_status as status").append(",stt.ctm_descripcion as descripcionStatus")
                    .append(",stt.ctm_color as colorStatus").append(" from ctm_ori_solicitud sol")
                    .append(" left join m_product_loan prdct on sol.ctm_id_producto = prdct.id")
                    .append(" left join ctm_cat_state std on sol.ctm_id_estado = std.ctm_state_id")
                    .append(" left join ctm_ori_cat_fase fs on sol.ctm_id_fase = fs.ctm_id_fase")
                    .append(" left join ctm_ori_cat_modalidad mdldd on sol.ctm_id_modalidad = mdldd.ctm_id_modalidad")
                    .append(" left join ctm_ori_cat_linea_credito lncrdt on sol.ctm_id_linea_credito = lncrdt.ctm_id_linea_credito")
                    .append(" left join ctm_ori_cat_status stt on sol.ctm_id_status = stt.ctm_id_status");
            return solicitudes.toString();
        }

        @Override
        public SolicitudData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
                throws SQLException {

            final Long idSolicitud = JdbcSupport.getLong(rs, "idSolicitud");
            final Long idClienteTitular = JdbcSupport.getLong(rs, "idClienteTitular");
            final Long idClienteconyuge = JdbcSupport.getLong(rs, "idClienteconyuge");
            final Long idProducto = JdbcSupport.getLong(rs, "idProducto");
            final String nombreCortoProducto = rs.getString("nombreCortoProducto");
            final String nombreProducto = rs.getString("nombreProducto");
            final Integer idEstado = JdbcSupport.getInteger(rs, "idEstado");
            final String nombreEstado = rs.getString("nombreEstado");
            final Integer idFase = JdbcSupport.getInteger(rs, "idFase");
            final String descripcionFase = rs.getString("descripcionFase");
            final Integer idModalidad = JdbcSupport.getInteger(rs, "idModalidad");
            final String descripcionModalidad = rs.getString("descripcionModalidad");
            final Integer idLineaCredito = JdbcSupport.getInteger(rs, "idLineaCredito");
            final String descripcionLineaCredito = rs.getString("descripcionLineaCredito");
            final String tipoCredito = rs.getString("tipoCredito");
            final Integer status = JdbcSupport.getInteger(rs, "status");
            final String descripcionStatus = rs.getString("descripcionStatus");
            final String colorStatus = rs.getString("colorStatus");

            return new SolicitudData(idSolicitud, idClienteTitular, idClienteconyuge, idProducto, nombreCortoProducto,
                    nombreProducto, idEstado, nombreEstado, idFase, descripcionFase, idModalidad, descripcionModalidad,
                    idLineaCredito, descripcionLineaCredito, tipoCredito, status, descripcionStatus, colorStatus);
        }
    }

    @Override
    public ExpedienteData consultaExpediente(Long solicitudId) {
        // TODO Auto-generated method stub
        final ExpedienteDataMapper edm = new ExpedienteDataMapper();
        final String sqlString = "SELECT "+edm.expedienteSchema()+" sol.ctm_id_solicitud = ? AND sol.ctm_id_modalidad = modal.ctm_id_modalidad "+
        "AND sol.ctm_id_linea_credito = credito.ctm_id_linea_credito";
        return this.jdbcTemplate.queryForObject(sqlString, edm, new Object[] { solicitudId });
    }

    private static final class ExpedienteDataMapper implements RowMapper<ExpedienteData> {

        public String expedienteSchema(){
            final StringBuilder expendienteString = new StringBuilder("sol.ctm_id_cliente_titular AS titular, ");
            expendienteString.append("ifnull(sol.ctm_id_cliente_conyuge,0) AS conyuge, sol.ctm_id_estado AS estado, ");
            expendienteString.append("modal.ctm_descripcion AS modalidad, ");
            expendienteString.append("credito.ctm_descripcion AS credito, ");
            expendienteString.append("CASE sol.ctm_id_tipo_credito WHEN 'I' THEN 'INDIVIDUAL' WHEN 'M' THEN 'MANCOMUNADO' END AS tipoCredito, ");
            expendienteString.append("sol.ctm_id_fase AS fase ");
            expendienteString.append("FROM ctm_ori_solicitud sol, ctm_ori_cat_modalidad modal, ctm_ori_cat_linea_credito credito WHERE");
            return expendienteString.toString();
        }
        @Override
        public ExpedienteData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
                throws SQLException {

            final Long titular = JdbcSupport.getLong(rs, "titular");
            final Long conyuge = JdbcSupport.getLong(rs, "conyuge");
            final Long estado = JdbcSupport.getLong(rs, "estado");
            final String modalidad = rs.getString("modalidad");
            final String credito = rs.getString("credito");
            final String tipoCredito = rs.getString("tipoCredito");
            final Long fase = JdbcSupport.getLong(rs, "fase");

            return new ExpedienteData(titular, conyuge, estado, modalidad, credito, tipoCredito,fase);
        }
    }
}
