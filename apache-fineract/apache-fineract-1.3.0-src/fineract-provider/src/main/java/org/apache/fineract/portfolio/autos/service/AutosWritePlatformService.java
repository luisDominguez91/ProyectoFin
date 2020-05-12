package org.apache.fineract.portfolio.autos.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.springframework.transaction.annotation.Transactional;

public interface AutosWritePlatformService {

    CommandProcessingResult createAuto(JsonCommand command);
}