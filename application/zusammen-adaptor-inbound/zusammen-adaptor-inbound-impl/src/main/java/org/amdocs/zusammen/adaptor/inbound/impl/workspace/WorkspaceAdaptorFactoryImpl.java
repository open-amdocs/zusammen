package org.amdocs.zusammen.adaptor.inbound.impl.workspace;

import org.amdocs.zusammen.adaptor.inbound.api.workspace.WorkspaceAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.workspace.WorkspaceAdaptorFactory;
import org.amdocs.zusammen.datatypes.SessionContext;


public class WorkspaceAdaptorFactoryImpl extends WorkspaceAdaptorFactory {
  private static final WorkspaceAdaptor INSTANCE = new WorkspaceAdaptorImpl();

  @Override
  public WorkspaceAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
