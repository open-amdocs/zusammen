package org.amdocs.tsuzammen.adaptor.inbound.api.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class ItemVersionAdaptorFactory
    extends AbstractComponentFactory<ItemVersionAdaptor> {

  public static ItemVersionAdaptorFactory getInstance() {
    return AbstractFactory.getInstance(ItemVersionAdaptorFactory.class);
  }

  public abstract ItemVersionAdaptor createInterface(SessionContext context);
}
