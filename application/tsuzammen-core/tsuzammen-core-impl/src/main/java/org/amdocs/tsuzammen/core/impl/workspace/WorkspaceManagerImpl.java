package org.amdocs.tsuzammen.core.impl.workspace;

import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManager;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.List;

public class WorkspaceManagerImpl implements WorkspaceManager {


  @Override
  public Id create(SessionContext context, Info workspaceInfo) {
    Id workspaceId = new Id(CommonMethods.nextUUID());
    getStateAdaptor(context).createWorkspace(context, workspaceId, workspaceInfo);
    return workspaceId;
  }

  @Override
  public void save(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getStateAdaptor(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void delete(SessionContext context, Id workspaceId) {
    getStateAdaptor(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public List<WorkspaceInfo> list(SessionContext context) {
    return getStateAdaptor(context).listWorkspaces(context);
  }

  @Override
  public void addItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {

    //get item from collaborative store
    //save item in state store
    //add item to workspace in the state store

  }

  @Override
  public void removeItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {

  }

  @Override
  public List<ItemVersionKey> listItems(SessionContext context, Id workspaceId) {
    return null;
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }


}
