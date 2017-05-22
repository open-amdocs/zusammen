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

package com.amdocs.zusammen.core.impl.health;

import com.amdocs.zusammen.commons.health.data.HealthInfo;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.health.HealthManager;
import com.amdocs.zusammen.datatypes.SessionContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HealthManagerImpl implements HealthManager {
  private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(HealthManagerImpl.class.getName());

  private CollaborationHealthService collaborationHealthService;
  private SearchHealthService searchHealthService;
  private StateHealthService stateHealthService;

  public HealthManagerImpl() {
    collaborationHealthService = new CollaborationHealthService();
    searchHealthService = new SearchHealthService();
    stateHealthService = new StateHealthService();
  }

  @Override
  public Collection<HealthInfo> getHealthStatus(SessionContext context) {
    List<HealthInfo> retVal = new ArrayList<>();
    retVal.addAll(stateHealthService.getHealthStatus(context));
    retVal.addAll(collaborationHealthService.getHealthStatus(context));
    retVal.addAll(searchHealthService.getHealthStatus(context));
    return retVal;
  }


}
