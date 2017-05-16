package org.amdocs.zusammen.health;

import org.amdocs.zusammen.adaptor.inbound.api.health.HealthAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.health.HealthAdaptorFactory;
import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.UserInfo;

import java.util.Collection;

public class HealthCheckSample {

    private static String TENANT = "test";
    private static String USER = "user";

    public static void main(String[] args) {
        SessionContext context = createSessionContext(USER, TENANT);

        HealthCheckSample healthCheckSample = new HealthCheckSample();
//        Collection<HealthInfo> healthInfos = healthCheckSample.checkHealth(context);
//        healthInfos.stream().forEach(healthInfo -> System.out.println("Health :" + healthInfo.toString()));
        System.out.println( healthCheckSample.checkHealthStatus(context));
        System.exit(0);
    }

    public Collection<HealthInfo> checkHealth(SessionContext context) {
        String user = USER;
        String tenant = TENANT;

        return getHealthAdapter(context).getHealthStatus(context);
    }
    public String checkHealthStatus(SessionContext context) {
        String user = USER;
        String tenant = TENANT;

        return getHealthAdapter(context).getHealthStatusReport(context);
    }


    private HealthAdaptor getHealthAdapter(SessionContext context) {
        return HealthAdaptorFactory.getInstance().createInterface(context);
    }

    private static SessionContext createSessionContext(String user, String tenant) {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setTenant(tenant);
        sessionContext.setUser(new UserInfo(user));
        return sessionContext;
    }

}
