package com.amdocs.zusammen.sdk.health;


import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

public interface IHealthCheck {
    Response<HealthInfo> checkHealth(SessionContext sessionContext) throws ZusammenException;
}
