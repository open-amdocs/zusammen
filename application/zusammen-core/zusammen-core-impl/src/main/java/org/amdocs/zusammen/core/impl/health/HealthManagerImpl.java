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

package org.amdocs.zusammen.core.impl.health;

import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.core.api.health.HealthManager;

import java.util.Collection;

public class HealthManagerImpl implements HealthManager {
  private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(HealthManagerImpl.class.getName());

  @Override
  public Collection<HealthInfo> getHealthStatus() {
    return null;
  }

}
