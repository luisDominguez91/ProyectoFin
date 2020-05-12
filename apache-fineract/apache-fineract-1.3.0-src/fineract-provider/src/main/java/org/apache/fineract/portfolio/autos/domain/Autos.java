
package org.apache.fineract.portfolio.autos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.autos.api.AutosApiConstants;
import org.apache.fineract.useradministration.domain.AppUser;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

@Entity
@Table(name = "ctm_autos")
public final class Autos extends AbstractPersistableCustom<Long> {

    @Column(name = "ctm_id", length = 20, unique = true, nullable = false)
    private String ctmId;

    @Column(name = "ctm_marca", length = 30, nullable = true)
    private String ctmMarca;

    @Column(name = "ctm_modelo", length = 30, nullable = true)
    private String ctmModelo;

    @Column(name = "ctm_anio", length = 30, nullable = true)
    private String ctmAnio;

    @Column(name = "ctm_fecha_apertura", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date ctmFechaApertura;

    @ManyToOne(optional = true, fetch=FetchType.LAZY)
    @JoinColumn(name = "ctm_created_by", nullable = true)
    private AppUser ctmCreatedBy;

    @Column(name = "ctm_created_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date ctmCreatedFecha;

    @ManyToOne(optional = true, fetch=FetchType.LAZY)
    @JoinColumn(name = "ctm_update_by", nullable = true)
    private AppUser ctmUpdateBy;

    @Column(name = "ctm_update_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date ctmUpdateFecha;

    @Column(name = "ctm_is_delete")
    private Boolean ctmIsDelete;

    public static Autos createNew(final AppUser currentUser, final JsonCommand command){
        final String ctmMarca = command.stringValueOfParameterNamed(AutosApiConstants.marca);
        final String ctmModelo = command.stringValueOfParameterNamed(AutosApiConstants.modelo);
        final String ctmAnio = command.stringValueOfParameterNamed(AutosApiConstants.anio);
        final LocalDate ctmFechaApertura = command.localDateValueOfParameterNamed(AutosApiConstants.fechaApertura);

        return new Autos(currentUser, ctmMarca, ctmModelo, ctmAnio, ctmFechaApertura);
    }

    private Autos(final AppUser currentUser, String ctmMarca, String ctmModelo, String ctmAnio, final LocalDate ctmFechaApertura) {
        if (StringUtils.isNotBlank(ctmMarca)) {
            this.ctmMarca = ctmMarca.trim();
        } else {
            this.ctmMarca = null;
        }

        if (StringUtils.isNotBlank(ctmModelo)) {
            this.ctmModelo = ctmModelo.trim();
        } else {
            this.ctmModelo = null;
        }

        if (StringUtils.isNotBlank(ctmAnio)) {
            this.ctmAnio = ctmAnio.trim();
        } else {
            this.ctmAnio = null;
        }
        if (ctmFechaApertura != null) {
            this.ctmFechaApertura = ctmFechaApertura.toDateTimeAtStartOfDay().toDate();
        }

        this.ctmCreatedBy = currentUser;
        this.ctmCreatedFecha = new LocalDate().toDate();
    }

    public String getCtmId() { return ctmId;    }

    public void setCtmId(String ctmId) { this.ctmId = ctmId;}

    public String getCtmMarca() {
        return ctmMarca;
    }

    public void setCtmMarca(String ctmMarca) {
        this.ctmMarca = ctmMarca;
    }

    public String getCtmModelo() {
        return ctmModelo;
    }

    public void setCtmModelo(String ctmModelo) {
        this.ctmModelo = ctmModelo;
    }

    public String getCtmAnio() {
        return ctmAnio;
    }

    public void setCtmAnio(String ctmAnio) {
        this.ctmAnio = ctmAnio;
    }

    public Date getCtmFechaApertura() {
        return ctmFechaApertura;
    }

    public void setCtmFechaApertura(Date ctmFechaApertura) {
        this.ctmFechaApertura = ctmFechaApertura;
    }

    public AppUser getCtmCreatedBy() {
        return ctmCreatedBy;
    }

    public void setCtmCreatedBy(AppUser ctmCreatedBy) {
        this.ctmCreatedBy = ctmCreatedBy;
    }

    public Date getCtmCreatedFecha() {
        return ctmCreatedFecha;
    }

    public void setCtmCreatedFecha(Date ctmCreatedFecha) {
        this.ctmCreatedFecha = ctmCreatedFecha;
    }

    public AppUser getCtmUpdateBy() {
        return ctmUpdateBy;
    }

    public void setCtmUpdateBy(AppUser ctmUpdateBy) {
        this.ctmUpdateBy = ctmUpdateBy;
    }

    public Date getCtmUpdateFecha() {
        return ctmUpdateFecha;
    }

    public void setCtmUpdateFecha(Date ctmUpdateFecha) {
        this.ctmUpdateFecha = ctmUpdateFecha;
    }

    public Boolean getCtmIsDelete() {
        return ctmIsDelete;
    }

    public void setCtmIsDelete(Boolean ctmIsDelete) {
        this.ctmIsDelete = ctmIsDelete;
    }
}