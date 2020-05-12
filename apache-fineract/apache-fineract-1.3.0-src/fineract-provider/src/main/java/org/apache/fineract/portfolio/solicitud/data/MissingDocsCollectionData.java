package org.apache.fineract.portfolio.solicitud.data;

import java.util.Collection;

@SuppressWarnings("unused")
public class MissingDocsCollectionData {

    private final Collection<MissingDocsData> missingDocs;

    public MissingDocsCollectionData(final Collection<MissingDocsData> missingDocs) {
        this.missingDocs = defaultSolicitudesIfEmpty(missingDocs);
    }

    private Collection<MissingDocsData> defaultSolicitudesIfEmpty(final Collection<MissingDocsData> collection) {
        Collection<MissingDocsData> returnCollection = null;
        if (collection != null && !collection.isEmpty()) {
            returnCollection = collection;
        }
        return returnCollection;
    }

}