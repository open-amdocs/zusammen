package org.amdocs.tsuzammen.sdk.impl;

import org.amdocs.tsuzammen.commons.configuration.ConfigurationManager;
import org.amdocs.tsuzammen.commons.configuration.ConfigurationManagerFactory;
import org.amdocs.tsuzammen.commons.configuration.datatypes.PluginInfo;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.sdk.StateStore;
import org.amdocs.tsuzammen.sdk.StateStoreFactory;
import org.amdocs.tsuzammen.sdk.utils.SdkConstants;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

public class StateStoreFactoryImpl extends StateStoreFactory {

  private static StateStore stateStore;

  private static void initStateStore(SessionContext context) {
    synchronized (StateStoreFactoryImpl.class) {
      ConfigurationManager configurationManager =
          ConfigurationManagerFactory.getInstance().createInterface();
      PluginInfo pluginInfo =
          configurationManager.getPluginInfo(SdkConstants.TSUZAMMEN_STATE_STORE);
      try {
        stateStore =
            CommonMethods.newInstance(pluginInfo.getImplementationClass(), StateStore.class);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
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
