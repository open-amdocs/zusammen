package org.amdocs.zusammen.core.impl.health;

import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.commons.health.service.HealthCheckService;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class SearchHealthService extends HealthCheckService<SessionContext>  implements HealthFactorySupplier {

    public SearchHealthService() {
        super(SearchHealthService.class.getSimpleName());
    }

    @Override
    protected HealthInfo checkHealth(SessionContext context) throws ZusammenException {
        return getHealthAdaptorFactory(context).getSearchStatus(context);
    }
}