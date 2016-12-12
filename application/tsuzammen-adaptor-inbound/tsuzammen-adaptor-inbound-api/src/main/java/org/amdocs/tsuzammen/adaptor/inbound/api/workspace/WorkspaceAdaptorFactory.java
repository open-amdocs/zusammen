package org.amdocs.tsuzammen.adaptor.inbound.api.workspace;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class WorkspaceAdaptorFactory
    extends AbstractComponentFactory<WorkspaceAdaptor> {

  public static WorkspaceAdaptorFactory getInstance() {
    return AbstractFactory.getInstance(WorkspaceAdaptorFactory.class);
  }

  public abstract WorkspaceAdaptor createInterface(SessionContext context);
}
