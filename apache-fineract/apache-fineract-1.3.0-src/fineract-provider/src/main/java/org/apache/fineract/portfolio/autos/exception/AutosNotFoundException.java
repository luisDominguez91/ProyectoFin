package org.apache.fineract.portfolio.autos.exception;

import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;

public class AutosNotFoundException extends AbstractPlatformResourceNotFoundException {

    public AutosNotFoundException(String ctmId){
        super("error.msg.auto.not.found.with.ctmId", "Client not found with ctmId "+ctmId+".");
    }
}