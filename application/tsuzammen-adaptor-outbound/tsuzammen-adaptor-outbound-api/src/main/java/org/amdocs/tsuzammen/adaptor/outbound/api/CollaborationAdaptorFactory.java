package org.amdocs.tsuzammen.adaptor.outbound.api;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class CollaborationAdaptorFactory
    extends AbstractComponentFactory<CollaborationAdaptor> {

  public static CollaborationAdaptorFactory getInstance() {
    return AbstractFactory.getInstance(CollaborationAdaptorFactory.class);
  }

  public abstract CollaborationAdaptor createInterface(SessionContext context);
}
