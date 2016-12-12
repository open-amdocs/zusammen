package org.amdocs.tsuzammen.core.api.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class ItemVersionManagerFactory
    extends AbstractComponentFactory<ItemVersionManager> {

  public static ItemVersionManagerFactory getInstance() {
    return AbstractFactory.getInstance(ItemVersionManagerFactory.class);
  }

  public abstract ItemVersionManager createInterface(SessionContext context);
}
