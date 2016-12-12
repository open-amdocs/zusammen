package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemAdaptor;
import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;


public class ItemAdaptorFactoryImpl extends ItemAdaptorFactory {
  private static final ItemAdaptor INSTANCE = new ItemAdaptorImpl();

  @Override
  public ItemAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
