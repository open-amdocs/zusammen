package org.amdocs.tsuzammen.adaptor.outbound.impl;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public class StateAdaptorFactoryImpl extends StateAdaptorFactory {
  private static final StateAdaptor INSTANCE = new StateAdaptorImpl();

  @Override
  public StateAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
