package org.amdocs.tsuzammen.adaptor.outbound.api;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class StateAdaptorFactory
    extends AbstractComponentFactory<StateAdaptor> {

  public static StateAdaptorFactory getInstance() {
    return AbstractFactory.getInstance(StateAdaptorFactory.class);
  }

  public abstract StateAdaptor createInterface(SessionContext context);
}
