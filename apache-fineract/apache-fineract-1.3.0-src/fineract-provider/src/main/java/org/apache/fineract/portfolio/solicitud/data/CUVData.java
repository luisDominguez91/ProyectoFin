package org.apache.fineract.portfolio.solicitud.data;

@SuppressWarnings("unused")
public class CUVData {

    private final String cuv;
    private final Long ruv;
    private final Long ruvE;
    private final String dui;
    private final String domgeografico;
    private final String metrosterreno;
    private final String metrosconstruccion;
    private final String vidautil;
    private final String tipovivienda;
    private final String oferente;
    private final String propievivienda;
    private final String replegal;
    private final String telefono;
    private final String correo;
    private final Long montovivienda;
    private final Long avaluoelec;
    private final String fechavaluo;
    private final String feavaluoelec;
    private final boolean dtu;
    private final boolean viviendasus;
    private final String local;
    private final String observaciones;

    public CUVData(String cuv, Long ruv, Long ruvE, String dui, String domgeografico, String metrosterreno,
            String metrosconstruccion, String vidautil, String tipovivienda, String oferente, String propievivienda,
            String replegal, String telefono, String correo, Long montovivienda, Long avaluoelec, String fechavaluo,
            String feavaluoelec, boolean dtu, boolean viviendasus, String local, String observaciones) {
        this.cuv = cuv;
        this.ruv = ruv;
        this.ruvE = ruvE;
        this.dui = dui;
        this.domgeografico = domgeografico;
        this.metrosterreno = metrosterreno;
        this.metrosconstruccion = metrosconstruccion;
        this.vidautil = vidautil;
        this.tipovivienda = tipovivienda;
        this.oferente = oferente;
        this.propievivienda = propievivienda;
        this.replegal = replegal;
        this.telefono = telefono;
        this.correo = correo;
        this.montovivienda = montovivienda;
        this.avaluoelec = avaluoelec;
        this.fechavaluo = fechavaluo;
        this.feavaluoelec = feavaluoelec;
        this.dtu = dtu;
        this.viviendasus = viviendasus;
        this.local = local;
        this.observaciones = observaciones;
    }

    public String getCuv() {
        return this.cuv;
    }


    public Long getRuv() {
        return this.ruv;
    }


    public Long getRuvE() {
        return this.ruvE;
    }


    public String getDui() {
        return this.dui;
    }


    public String getDomgeografico() {
        return this.domgeografico;
    }


    public String getMetrosterreno() {
        return this.metrosterreno;
    }


    public String getMetrosconstruccion() {
        return this.metrosconstruccion;
    }


    public String getVidautil() {
        return this.vidautil;
    }


    public String getTipovivienda() {
        return this.tipovivienda;
    }


    public String getOferente() {
        return this.oferente;
    }


    public String getPropievivienda() {
        return this.propievivienda;
    }


    public String getReplegal() {
        return this.replegal;
    }


    public String getTelefono() {
        return this.telefono;
    }


    public String getCorreo() {
        return this.correo;
    }


    public Long getMontovivienda() {
        return this.montovivienda;
    }


    public Long getAvaluoelec() {
        return this.avaluoelec;
    }


    public String getFechavaluo() {
        return this.fechavaluo;
    }


    public String getFeavaluoelec() {
        return this.feavaluoelec;
    }


    public boolean isDtu() {
        return this.dtu;
    }

    public boolean getDtu() {
        return this.dtu;
    }


    public boolean isViviendasus() {
        return this.viviendasus;
    }

    public boolean getViviendasus() {
        return this.viviendasus;
    }


    public String getLocal() {
        return this.local;
    }


    public String getObservaciones() {
        return this.observaciones;
    }


}
