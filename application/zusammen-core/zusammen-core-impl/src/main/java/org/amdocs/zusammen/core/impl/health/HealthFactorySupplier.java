package org.amdocs.zusammen.core.impl.health;

import org.amdocs.zusammen.adaptor.outbound.api.health.HealthAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.health.HealthAdaptorFactory;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

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
