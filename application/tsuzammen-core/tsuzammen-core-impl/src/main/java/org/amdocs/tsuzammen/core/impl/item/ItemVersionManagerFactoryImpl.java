package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManagerFactory;

public class ItemVersionManagerFactoryImpl extends ItemVersionManagerFactory {

  private static final ItemVersionManager INSTANCE = new ItemVersionManagerImpl();

  @Override
  public ItemVersionManager createInterface(SessionContext context) {
    return INSTANCE;
  }
}
