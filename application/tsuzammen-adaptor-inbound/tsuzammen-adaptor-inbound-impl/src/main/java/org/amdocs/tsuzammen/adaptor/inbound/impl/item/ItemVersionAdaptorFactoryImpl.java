package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemVersionAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;


public class ItemVersionAdaptorFactoryImpl extends ItemVersionAdaptorFactory {
  private static final ItemVersionAdaptor INSTANCE = new ItemVersionAdaptorImpl();

  @Override
  public ItemVersionAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
