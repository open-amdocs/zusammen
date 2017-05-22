package com.amdocs.zusammen.core.impl.health;

import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.commons.health.service.HealthCheckService;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.response.ZusammenException;


public class CollaborationHealthService extends HealthCheckService<SessionContext> implements HealthFactorySupplier {

    public CollaborationHealthService() {
        super(CollaborationHealthService.class.getSimpleName());
    }

    @Override
    protected HealthInfo checkHealth(SessionContext context)  throws ZusammenException {
        return getHealthAdaptorFactory(context).getCollaborationStatus(context);
    }

}