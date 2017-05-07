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

package org.amdocs.zusammen.adaptor.inbound.impl.health;

import org.amdocs.zusammen.adaptor.inbound.api.health.HealthAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementConvertor;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementInfoConvertor;
import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.item.ElementManagerFactory;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.response.*;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;
import java.util.stream.Collectors;

public class HealthAdaptorImpl implements HealthAdaptor {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(HealthAdaptorImpl.class
      .getName());


  @Override
  public Collection<HealthInfo> getHealthStatus() {
    return null;
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
