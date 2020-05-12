package org.apache.fineract.portfolio.autos.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.configuration.service.ConfigurationReadPlatformService;
import org.springframework.stereotype.Component;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.portfolio.autos.data.AutosApiCollectionConstants;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.fineract.portfolio.autos.api.AutosApiConstants;
import org.joda.time.LocalDate;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;

@Component
public final class AutoDataValidator{

    private final FromJsonHelper fromApiJsonHelper;
    private final ConfigurationReadPlatformService configurationReadPlatformService;

    @Autowired
    public AutoDataValidator(final FromJsonHelper fromJsonHelper, final ConfigurationReadPlatformService configurationReadPlatformService){
        this.fromApiJsonHelper = fromJsonHelper;
        this.configurationReadPlatformService = configurationReadPlatformService;
    }

    public void validateForCreate(final String json){

        if(StringUtils.isBlank(json)){
            throw new InvalidJsonException();
        }

        final Type typeOfMap = new TypeToken<Map<String, Object>>() {
        }.getType();
        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, AutosApiCollectionConstants.AUTOS_CREATE_REQUEST_DATA_PARAMETERS);
        final JsonElement element = this.fromApiJsonHelper.parse(json);

        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(AutosApiCollectionConstants.AUTOS_RESOURCE_NAME);

        if(this.fromApiJsonHelper.parameterExists(AutosApiConstants.marca, element)){
            String marca = this.fromApiJsonHelper.extractStringNamed(AutosApiConstants.marca, element);
            baseDataValidator.reset().parameter(AutosApiConstants.marca).value(marca).ignoreIfNull().notExceedingLengthOf(30);
        }

        if(this.fromApiJsonHelper.parameterExists(AutosApiConstants.modelo, element)){
            String modelo = this.fromApiJsonHelper.extractStringNamed(AutosApiConstants.modelo, element);
            baseDataValidator.reset().parameter(AutosApiConstants.modelo).value(modelo).ignoreIfNull().notExceedingLengthOf(30);
        }

        if(this.fromApiJsonHelper.parameterExists(AutosApiConstants.anio, element)){
            String anio = this.fromApiJsonHelper.extractStringNamed(AutosApiConstants.anio, element);
            baseDataValidator.reset().parameter(AutosApiConstants.anio).value(anio).ignoreIfNull().notExceedingLengthOf(30);
        }

        if (this.fromApiJsonHelper.parameterExists(AutosApiConstants.fechaApertura, element)) {
            final LocalDate fechaApertura = this.fromApiJsonHelper.extractLocalDateNamed(AutosApiConstants.fechaApertura, element);
            baseDataValidator.reset().parameter(AutosApiConstants.fechaApertura).value(fechaApertura).notNull()
                    .validateDateBefore(DateUtils.getLocalDateOfTenant());
        }

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }
    }
}