package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.core.api.item.ItemManagerFactory;

public class ItemManagerFactoryImpl extends ItemManagerFactory {

  private static final ItemManager INSTANCE = new ItemManagerImpl();

  @Override
  public ItemManager createInterface(SessionContext context) {
    return INSTANCE;
  }
}
