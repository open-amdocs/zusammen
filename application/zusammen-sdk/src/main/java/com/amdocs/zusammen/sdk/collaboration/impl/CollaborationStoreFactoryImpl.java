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

package com.amdocs.zusammen.sdk.collaboration.impl;

import com.amdocs.zusammen.sdk.collaboration.CollaborationStoreFactory;
import com.amdocs.zusammen.commons.configuration.ConfigurationManager;
import com.amdocs.zusammen.commons.configuration.ConfigurationManagerFactory;
import com.amdocs.zusammen.commons.configuration.datatypes.PluginInfo;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.sdk.SdkConstants;
import com.amdocs.zusammen.sdk.collaboration.CollaborationStore;
import com.amdocs.zusammen.utils.common.CommonMethods;

public class CollaborationStoreFactoryImpl extends CollaborationStoreFactory {

  private static CollaborationStore collaborationStore;

  private static void intCollaborationStore(SessionContext context) {
    synchronized (CollaborationStoreFactoryImpl.class) {
      ConfigurationManager configurationManager =
          ConfigurationManagerFactory.getInstance().createInterface();
      PluginInfo pluginInfo = configurationManager.getPluginInfo(SdkConstants
          .ZUSAMMEN_COLLABORATIVE_STORE);
      try {
        collaborationStore = CommonMethods
            .newInstance(pluginInfo.getImplementationClass(), CollaborationStore.class);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }


  @Override
  public CollaborationStore createInterface(SessionContext context) {
    synchronized (CollaborationStoreFactoryImpl.class) {
      if (collaborationStore == null) {
        intCollaborationStore(context);
      }
    }
    return collaborationStore;
  }
}
