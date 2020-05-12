package org.apache.fineract.portfolio.autos.domain.impl;

import org.apache.fineract.portfolio.autos.domain.AutosRepository;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.fineract.portfolio.autos.domain.AutosRepository;
import org.apache.fineract.portfolio.autos.domain.Autos;
import org.apache.fineract.portfolio.autos.exception.AutosNotFoundException;

@Service
public class AutosRepositoryImpl{

    private final AutosRepository autosRepository;
    private final PlatformSecurityContext context;

    @Autowired
    public AutosRepositoryImpl(final AutosRepository repository, final PlatformSecurityContext context){
        this.autosRepository = repository;
        this.context = context;
    }

    public void save(final Autos autos){
        this.autosRepository.save(autos);
    }

    public Autos getAutosById(String ctmId){
        Autos autos = this.autosRepository.getAutosById(ctmId);
        if(autos==null){
            throw new AutosNotFoundException(ctmId);
        }
        return autos;
    }
}