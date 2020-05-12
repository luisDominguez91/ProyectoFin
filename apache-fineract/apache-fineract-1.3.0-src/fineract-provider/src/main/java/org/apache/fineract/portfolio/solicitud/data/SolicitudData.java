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
package org.apache.fineract.portfolio.solicitud.data;

@SuppressWarnings("unused")
public class SolicitudData {

    private final Long idSolicitud;
    private final Long idClienteTitular;
    private final Long idClienteconyuge;
    private final Long idProducto;
    private final String nombreCortoProducto;
    private final String nombreProducto;
    private final Integer idEstado;
    private final String nombreEstado;
    private final Integer idFase;
    private final String descripcionFase;
    private final Integer idModalidad;
    private final String descripcionModalidad;
    private final Integer idLineaCredito;
    private final String descripcionLineaCredito;
    private final String tipoCredito;
    private final Integer status;
    private final String descripcionStatus;
    private final String colorStatus;

    public SolicitudData(final Long idSolicitud,
                         final Long idClienteTitular,
                         final Long idClienteconyuge,
                         final Long idProducto,
                         final String nombreCortoProducto,
                         final String nombreProducto,
                         final Integer idEstado,
                         final String nombreEstado,
                         final Integer idFase,
                         final String descripcionFase,
                         final Integer idModalidad,
                         final String descripcionModalidad,
                         final Integer idLineaCredito,
                         final String descripcionLineaCredito,
                         final String tipoCredito,
                         final Integer status,
                         final String descripcionStatus,
                         final String colorStatus) {
        this.idSolicitud = idSolicitud;
        this.idClienteTitular = idClienteTitular;
        this.idClienteconyuge = idClienteconyuge;
        this.idProducto = idProducto;
        this.nombreCortoProducto = nombreCortoProducto;
        this.nombreProducto = nombreProducto;
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.idFase = idFase;
        this.descripcionFase = descripcionFase;
        this.idModalidad = idModalidad;
        this.descripcionModalidad = descripcionModalidad;
        this.idLineaCredito = idLineaCredito;
        this.descripcionLineaCredito = descripcionLineaCredito;
        this.tipoCredito = tipoCredito;
        this.status = status;
        this.descripcionStatus = descripcionStatus;
        this.colorStatus = colorStatus;
    }
}
