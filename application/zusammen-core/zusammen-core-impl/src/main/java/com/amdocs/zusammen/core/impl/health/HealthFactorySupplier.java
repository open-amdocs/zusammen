package com.amdocs.zusammen.core.impl.health;

import com.amdocs.zusammen.adaptor.outbound.api.health.HealthAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.health.HealthAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

public interface HealthFactorySupplier {
    ZusammenLogger logger = ZusammenLoggerFactory.getLogger(HealthFactorySupplier.class.getName());
    default HealthAdaptor getHealthAdaptorFactory(SessionContext sessionContext) throws ZusammenException{
        try {
            return HealthAdaptorFactory.getInstance().createInterface(sessionContext);
        } catch ( Throwable t){
            logger.error("Failed to generate adapter ",t);
            ReturnCode returnCode = new ReturnCode(ErrorCode.HC_MISSING_PLUGIN, Module.ZHC,null,null);
            throw new ZusammenException(returnCode);
        }
    }
}
