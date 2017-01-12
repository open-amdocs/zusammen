/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.zusammen.sdk.impl;

import org.amdocs.zusammen.commons.configuration.ConfigurationManager;
import org.amdocs.zusammen.commons.configuration.ConfigurationManagerFactory;
import org.amdocs.zusammen.commons.configuration.datatypes.PluginInfo;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.sdk.SearchIndexFactory;
import org.amdocs.zusammen.sdk.SearchIndex;
import org.amdocs.zusammen.sdk.utils.SdkConstants;
import org.amdocs.zusammen.utils.common.CommonMethods;

public class SearchIndexFactoryImpl extends SearchIndexFactory {

  private static SearchIndex searchIndex;

  private static void initSearchIndex(SessionContext context) {
    synchronized (SearchIndexFactoryImpl.class) {
      ConfigurationManager configurationManager =
          ConfigurationManagerFactory.getInstance().createInterface();
      PluginInfo pluginInfo =
          configurationManager.getPluginInfo(SdkConstants.TSUZAMMEN_SEARCH_INDEX);
      try {
        searchIndex =
            CommonMethods.newInstance(pluginInfo.getImplementationClass(), SearchIndex.class);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  @Override
  public SearchIndex createInterface(SessionContext context) {
    synchronized (SearchIndexFactoryImpl.class) {
      if (searchIndex == null) {
        initSearchIndex(context);
      }
    }
    return searchIndex;
  }
}
