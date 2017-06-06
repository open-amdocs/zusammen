/*
 * Copyright © 2016-2017 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amdocs.zusammen.adaptor.inbound.impl.health;


import com.amdocs.zusammen.adaptor.inbound.api.health.HealthAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ZusammenElement;
import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.health.HealthManager;
import com.amdocs.zusammen.core.api.health.HealthManagerFactory;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.utils.fileutils.json.JsonUtil;

import java.util.Collection;

public class HealthAdaptorImpl implements HealthAdaptor {

    private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(HealthAdaptorImpl.class
            .getName());


    @Override
   public Collection<HealthInfo> getHealthStatus(SessionContext context) {
        return getHealthManager(context).getHealthStatus(context);
    }

    private HealthManager getHealthManager(SessionContext context) {
        return HealthManagerFactory.getInstance().createInterface(context);
    }

    @Override
    public String getVersion() {
        return this.getClass().getPackage().getImplementationVersion();
    }

    @Override
    public String getHealthStatusReport(SessionContext sessionContext) {
        Collection<HealthInfo> healthStatus = getHealthStatus(sessionContext);
        return  JsonUtil.object2Json(healthStatus);
    }
}
