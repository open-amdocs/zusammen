package org.amdocs.tsuzammen.core.impl.workspace;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManager;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManagerFactory;

public class WorkspaceManagerFactoryImpl extends WorkspaceManagerFactory {

  private static final WorkspaceManager INSTANCE = new WorkspaceManagerImpl();

  @Override
  public WorkspaceManager createInterface(SessionContext context) {
    return INSTANCE;
  }
}
