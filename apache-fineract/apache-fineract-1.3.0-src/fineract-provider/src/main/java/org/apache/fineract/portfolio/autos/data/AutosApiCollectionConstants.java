package org.apache.fineract.portfolio.autos.data;

import org.apache.fineract.portfolio.autos.api.AutosApiConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AutosApiCollectionConstants extends AutosApiConstants {
    protected static final Set<String> AUTOS_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(marca, modelo, anio, fechaApertura));
}