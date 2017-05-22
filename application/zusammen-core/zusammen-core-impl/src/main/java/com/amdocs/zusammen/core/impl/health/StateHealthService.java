package com.amdocs.zusammen.core.impl.health;

import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.commons.health.service.HealthCheckService;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

public class StateHealthService extends HealthCheckService<SessionContext>  implements HealthFactorySupplier {

    public StateHealthService() {
        super(StateHealthService.class.getSimpleName());
    }

    @Override
    protected HealthInfo checkHealth(SessionContext context) throws ZusammenException {
        return getHealthAdaptorFactory(context).getStateStatus(context);
    }
}