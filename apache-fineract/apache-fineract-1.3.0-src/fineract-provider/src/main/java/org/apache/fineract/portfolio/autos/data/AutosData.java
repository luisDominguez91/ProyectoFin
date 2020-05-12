package org.apache.fineract.portfolio.autos.data;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@SuppressWarnings("unused")
final public class AutosData implements Comparable<AutosData>{

    private final Long id;
    private String ctmMarca;
    private String ctmModelo;
    private String ctmAnio;
    private Date ctmFechaApertura;

    public AutosData(Long id, String ctmMarca, String ctmModelo, String ctmAnio, Date ctmFechaApertura) {
        this.id = id;
        this.ctmMarca = ctmMarca;
        this.ctmModelo = ctmModelo;
        this.ctmAnio = ctmAnio;
        this.ctmFechaApertura = ctmFechaApertura;
    }

    public Long getId() {
        return id;
    }

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

    @Override
    public int compareTo(final AutosData obj) {
        if (obj == null) { return -1; }
        return new CompareToBuilder() //
                .append(this.id, obj.id) //
                .append(this.ctmMarca, obj.ctmMarca) //
                .append(this.ctmModelo, obj.ctmModelo) //
                .append(this.ctmAnio, obj.ctmAnio) //
                .append(this.ctmFechaApertura, obj.ctmFechaApertura) //
                .toComparison();
    }
}