package org.amdocs.tsuzammen.adaptor.outbound.impl;

import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.Namespace;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;
import org.amdocs.tsuzammen.sdk.StateStore;
import org.amdocs.tsuzammen.sdk.StateStoreFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by TALIG on 12/4/2016.
 */
public class StateAdaptorImpl implements StateAdaptor {
  @Override
  public void createItem(SessionContext context, Id itemId, Info itemInfo) {
    getStateStore(context).createItem(context, itemId, itemInfo);
  }

  @Override
  public void saveItem(SessionContext context, Id itemId, Info itemInfo) {
    getStateStore(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void deleteItem(SessionContext context, Id itemId) {
    getStateStore(context).deleteItem(context, itemId);
  }

  @Override
  public void validateItemVersionExistence(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void createItemVersion(SessionContext context, Id itemId, Id baseVersionId, Id versionId,
                                Info versionInfo) {
    getStateStore(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);
  }

  @Override
  public void saveItemVersion(SessionContext context, Id itemId, Id versionId, Info versionInfo) {

  }

  @Override
  public void deleteItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void syncItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public Map<String, Info> getContent(SessionContext context, Id itemId, Id versionId,
                                      Namespace contentNamespace) {
    return null;
  }

  @Override
  public void createWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getStateStore(context).createWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getStateStore(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void deleteWorkspace(SessionContext context, Id workspaceId) {
    getStateStore(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public List<WorkspaceInfo> listWorkspaces(SessionContext context) {
    return getStateStore(context).listWorkspaces(context);
  }


  @Override
  public void saveEntity(SessionContext context, Id itemId, Id versionId, Namespace entityNamespace,
                         Info entityInfo) {

  }

  @Override
  public void deleteEntity(SessionContext context, Id itemId, Id versionId,
                           Namespace entityNamespace) {

  }

  @Override
  public Info getEntity(SessionContext context, Id itemId, Id versionId,
                        Namespace entityNamespace) {
    return null;
  }

  @Override
  public void deleteContent(SessionContext context, Id itemId, Id versionId,
                            Namespace contentNamespace) {

  }

  private StateStore getStateStore(SessionContext context) {
    return StateStoreFactory.getInstance().createInterface(context);
  }
}
