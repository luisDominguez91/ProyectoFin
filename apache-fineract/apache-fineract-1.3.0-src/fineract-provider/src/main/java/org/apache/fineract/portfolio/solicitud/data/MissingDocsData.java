package org.apache.fineract.portfolio.solicitud.data;

@SuppressWarnings("unused")
public class MissingDocsData {
    private final String documento;
    private final String observaciones;
    private final String originales;
    private final String copias;
    private final boolean recibido;
    private final String fecharecepcion;

    public MissingDocsData(String documento, String observaciones, String originales, String copias, boolean recibido,
            String fecharecepcion) {
        this.documento = documento;
        this.observaciones = observaciones;
        this.originales = originales;
        this.copias = copias;
        this.recibido = recibido;
        this.fecharecepcion = fecharecepcion;
    }

}