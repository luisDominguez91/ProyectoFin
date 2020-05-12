package org.apache.fineract.portfolio.client.service.impl;

import javax.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;

import org.apache.fineract.portfolio.autos.data.AutoDataValidator;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.configuration.service.ConfigurationReadPlatformService;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.infrastructure.configuration.data.GlobalConfigurationPropertyData;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.portfolio.autos.service.AutosWritePlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.fineract.portfolio.autos.domain.Autos;
import org.apache.fineract.portfolio.autos.domain.impl.AutosRepositoryImpl;
import java.lang.*;

@Service
public class AutosWritePlatformServiceImpl implements AutosWritePlatformService{

    private final static Logger logger = LoggerFactory.getLogger(AutosWritePlatformServiceImpl.class);

    private final PlatformSecurityContext context;
    private final AutoDataValidator fromApiJsonDeserializer;
    private final ConfigurationReadPlatformService configurationReadPlatformService;
    private final AutosRepositoryImpl autosRepository;

    @Autowired
    public AutosWritePlatformServiceImpl(final PlatformSecurityContext context, final AutoDataValidator fromApiJsonDeserializer,
                                         final ConfigurationReadPlatformService configurationReadPlatformService,
                                         final AutosRepositoryImpl autosRepository){
        this.context = context;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
        this.configurationReadPlatformService = configurationReadPlatformService;
        this.autosRepository = autosRepository;
    }

    @Transactional
    @Override
    public CommandProcessingResult createAuto(final JsonCommand command) {
        logger.error(command.json());
        try {
            final AppUser currentUser = this.context.authenticatedUser();

            this.fromApiJsonDeserializer.validateForCreate(command.json());


            final GlobalConfigurationPropertyData configuration = this.configurationReadPlatformService
                    .retrieveGlobalConfiguration("Enable-Address");

            final Boolean isAddressEnabled = configuration.isEnabled();

            final Autos newAutos = Autos.createNew(currentUser, command);

            this.autosRepository.save(newAutos);

            return new CommandProcessingResultBuilder().withCommandId(command.commandId())
                    .withEntityId(Long.parseLong(newAutos.getCtmId())).build();
        } catch (final DataIntegrityViolationException dve) {
            return CommandProcessingResult.empty();
        }catch(final PersistenceException dve) {
            return CommandProcessingResult.empty();
        }
    }
}