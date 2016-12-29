package org.amdocs.tsuzammen.adaptor.inbound.impl.workspace;

import org.amdocs.tsuzammen.adaptor.inbound.api.workspace.WorkspaceAdaptor;
import org.amdocs.tsuzammen.adaptor.inbound.api.workspace.WorkspaceAdaptorFactory;
import org.amdocs.tsuzammen.datatypes.SessionContext;


public class WorkspaceAdaptorFactoryImpl extends WorkspaceAdaptorFactory {
  private static final WorkspaceAdaptor INSTANCE = new WorkspaceAdaptorImpl();

  @Override
  public WorkspaceAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
