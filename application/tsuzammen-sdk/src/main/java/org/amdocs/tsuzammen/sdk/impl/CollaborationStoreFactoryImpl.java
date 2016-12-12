package org.amdocs.tsuzammen.sdk.impl;

import org.amdocs.tsuzammen.commons.configuration.ConfigurationManager;
import org.amdocs.tsuzammen.commons.configuration.ConfigurationManagerFactory;
import org.amdocs.tsuzammen.commons.configuration.datatypes.PluginInfo;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.sdk.CollaborationStore;
import org.amdocs.tsuzammen.sdk.CollaborationStoreFactory;
import org.amdocs.tsuzammen.sdk.utils.SdkConstants;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

public class CollaborationStoreFactoryImpl extends CollaborationStoreFactory {

  private static CollaborationStore collaborationStore;

  private static void intCollaborationStore(SessionContext context) {
    synchronized (CollaborationStoreFactoryImpl.class) {
      ConfigurationManager configurationManager =
          ConfigurationManagerFactory.getInstance().createInterface();
      PluginInfo pluginInfo = configurationManager.getPluginInfo(SdkConstants
          .TSUZAMMEN_COLLABORATIVE_STORE);
      try {
        collaborationStore = CommonMethods
            .newInstance(pluginInfo.getImplementationClass(), CollaborationStore.class);
        collaborationStore.init(context);
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
