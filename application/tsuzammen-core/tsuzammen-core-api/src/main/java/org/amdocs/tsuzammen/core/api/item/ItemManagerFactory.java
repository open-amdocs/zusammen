package org.amdocs.tsuzammen.core.api.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class ItemManagerFactory extends AbstractComponentFactory<ItemManager> {

  public static ItemManagerFactory getInstance() {
    return AbstractFactory.getInstance(ItemManagerFactory.class);
  }

  public abstract ItemManager createInterface(SessionContext context);
}
