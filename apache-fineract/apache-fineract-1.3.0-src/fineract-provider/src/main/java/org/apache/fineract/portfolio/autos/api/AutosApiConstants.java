package org.apache.fineract.portfolio.autos.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.fineract.portfolio.autos.data.AutosData;

public class AutosApiConstants{

    public static final String AUTOS_RESOURCE_NAME = "autos";
    public static final String clientNonPersonDetailsParamName = "clientNonPersonDetails";

    public static final String marca = "marca";
    public static final String modelo = "modelo";
    public static final String anio = "anio";
    public static final String fechaApertura = "fechaApertura";

    protected static final Set<String> AUTO_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(marca, modelo, anio, fechaApertura));
}