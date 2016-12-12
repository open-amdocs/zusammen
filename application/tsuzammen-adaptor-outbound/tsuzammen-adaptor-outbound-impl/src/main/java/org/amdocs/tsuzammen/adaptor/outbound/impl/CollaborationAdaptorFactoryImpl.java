package org.amdocs.tsuzammen.adaptor.outbound.impl;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public class CollaborationAdaptorFactoryImpl extends CollaborationAdaptorFactory {
  private static final CollaborationAdaptor INSTANCE = new CollaborationAdaptorImpl();

  @Override
  public CollaborationAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
