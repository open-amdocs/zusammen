package org.amdocs.tsuzammen.sdk;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class CollaborationStoreFactory
    extends AbstractComponentFactory<CollaborationStore> {

  public static CollaborationStoreFactory getInstance() {
    return AbstractFactory.getInstance(CollaborationStoreFactory.class);
  }

  public abstract CollaborationStore createInterface(SessionContext context);
}
