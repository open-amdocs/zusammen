package org.amdocs.tsuzammen.core.api.workspace;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.utils.facade.api.AbstractComponentFactory;
import org.amdocs.tsuzammen.utils.facade.api.AbstractFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public abstract class WorkspaceManagerFactory extends AbstractComponentFactory<WorkspaceManager> {

  public static WorkspaceManagerFactory getInstance() {
    return AbstractFactory.getInstance(WorkspaceManagerFactory.class);
  }

  public abstract WorkspaceManager createInterface(SessionContext context);
}
