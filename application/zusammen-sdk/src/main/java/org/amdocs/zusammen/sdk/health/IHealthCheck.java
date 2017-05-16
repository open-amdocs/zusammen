package org.amdocs.zusammen.sdk.health;


import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

public interface IHealthCheck {
    Response<HealthInfo> checkHealth(SessionContext sessionContext) throws ZusammenException;
}
