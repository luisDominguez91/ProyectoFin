package org.apache.fineract.portfolio.solicitud.data;

import java.util.Collection;
@SuppressWarnings("unused")
public class HistoricalCuvCollectionData {

    private final Collection<HistoricalCuvData> historicalCuv;

    
    public HistoricalCuvCollectionData(final Collection<HistoricalCuvData> historicalCuv) {
        this.historicalCuv = defaultSolicitudesIfEmpty(historicalCuv);
    }

    private Collection<HistoricalCuvData> defaultSolicitudesIfEmpty(final Collection<HistoricalCuvData> collection){
        Collection<HistoricalCuvData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }
}