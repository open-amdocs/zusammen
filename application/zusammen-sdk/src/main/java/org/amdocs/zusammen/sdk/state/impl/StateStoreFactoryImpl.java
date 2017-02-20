/*
 * Add Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.sdk.state.impl;

import org.amdocs.zusammen.commons.configuration.ConfigurationManager;
import org.amdocs.zusammen.commons.configuration.ConfigurationManagerFactory;
import org.amdocs.zusammen.commons.configuration.datatypes.PluginInfo;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.sdk.SdkConstants;
import org.amdocs.zusammen.sdk.state.StateStore;
import org.amdocs.zusammen.sdk.state.StateStoreFactory;
import org.amdocs.zusammen.utils.common.CommonMethods;

public class StateStoreFactoryImpl extends StateStoreFactory {

  private static StateStore stateStore;
  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(StateStoreFactoryImpl
      .class.getName());
  private static void initStateStore(SessionContext context) {
    synchronized (StateStoreFactoryImpl.class) {
      ConfigurationManager configurationManager =
          ConfigurationManagerFactory.getInstance().createInterface();
      PluginInfo pluginInfo =
          configurationManager.getPluginInfo(SdkConstants.ZUSAMMEN_STATE_STORE);
      try {
        stateStore =
            CommonMethods.newInstance(pluginInfo.getImplementationClass(), StateStore.class);
      } catch (Exception ex) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_STATE_STORE_INIT, Module.ZUS,ex
            .getMessage(),null);
        logger.error(returnCode.toString(),ex);
        throw new ZusammenException(returnCode);
      }
    }
  }

  @Override
  public StateStore createInterface(SessionContext context) {
    synchronized (StateStoreFactoryImpl.class) {
      if (stateStore == null) {
        initStateStore(context);
      }
    }
    return stateStore;
  }
}
