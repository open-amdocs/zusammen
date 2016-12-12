package org.amdocs.tsuzammen.adaptor.inbound.impl.workspace;

import org.amdocs.tsuzammen.adaptor.inbound.api.workspace.WorkspaceAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManager;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManagerFactory;

import java.util.List;

public class WorkspaceAdaptorImpl implements WorkspaceAdaptor {

  @Override
  public List<WorkspaceInfo> list(SessionContext context) {
    return getWorkspaceManager(context).list(context);
  }

  @Override
  public Id create(SessionContext context, Info workspaceInfo) {
    return getWorkspaceManager(context).create(context, workspaceInfo);
  }

  @Override
  public void save(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getWorkspaceManager(context).save(context, workspaceId, workspaceInfo);
  }

  @Override
  public void delete(SessionContext context, Id workspaceId) {
    getWorkspaceManager(context).delete(context, workspaceId);
  }

  @Override
  public void addItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {
    getWorkspaceManager(context).addItem(context, workspaceId, itemId, versionId);
  }

  @Override
  public void removeItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {
    getWorkspaceManager(context).removeItem(context, workspaceId, itemId, versionId);
  }

  @Override
  public List<ItemVersionKey> listItems(SessionContext context, Id workspaceId) {
    return getWorkspaceManager(context).listItems(context, workspaceId);
  }

  private WorkspaceManager getWorkspaceManager(SessionContext context) {
    return WorkspaceManagerFactory.getInstance().createInterface(context);
  }

}
