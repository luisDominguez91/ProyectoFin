package org.apache.fineract.portfolio.solicitud.data;

@SuppressWarnings("unused")
public class HistoricalCuvData {

    private final Long idSolicitud;
    private final String cuv;
    private final String domicilio;
    private final String tUbicacion;
    private final String vUbicacion;
    private final String cp;
    private final String colonia;
    private final String delegacion;
    private final String descEdo;
    private final String mTerreno;
    private final String mConstruccion;
    private final String vidaUtil;
    private final String tipoVivenda;
    private final String repLegal;
    private final String promotor;
    private final String observaciones;
    private final String idAclaracion;
    private final String pModifica;
    private final String fModifica;

    public HistoricalCuvData(Long idSolicitud, String cuv, String domicilio, String tUbicacion, String vUbicacion,
            String cp, String colonia, String delegacion, String descEdo, String mTerreno, String mConstruccion,
            String vidaUtil, String tipoVivenda, String repLegal, String promotor, String observaciones,
            String idAclaracion, String pModifica, String fModifica) {
        this.idSolicitud = idSolicitud;
        this.cuv = cuv;
        this.domicilio = domicilio;
        this.tUbicacion = tUbicacion;
        this.vUbicacion = vUbicacion;
        this.cp = cp;
        this.colonia = colonia;
        this.delegacion = delegacion;
        this.descEdo = descEdo;
        this.mTerreno = mTerreno;
        this.mConstruccion = mConstruccion;
        this.vidaUtil = vidaUtil;
        this.tipoVivenda = tipoVivenda;
        this.repLegal = repLegal;
        this.promotor = promotor;
        this.observaciones = observaciones;
        this.idAclaracion = idAclaracion;
        this.pModifica = pModifica;
        this.fModifica = fModifica;
    }

}