package org.amdocs.tsuzammen.adaptor.outbound.impl;

import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;

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
  public void createItem(SessionContext context, String itemId, Info itemInfo) {
    getStateStore(context).createItem(context, itemId, itemInfo);
  }

  @Override
  public void saveItem(SessionContext context, String itemId, Info itemInfo) {
    getStateStore(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void deleteItem(SessionContext context, String itemId) {
    getStateStore(context).deleteItem(context, itemId);
  }

  @Override
  public void validateItemVersionExistence(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId, String versionId,
                                Info versionInfo) {
    getStateStore(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);
  }

  @Override
  public void saveItemVersion(SessionContext context, String itemId, String versionId, Info versionInfo) {

  }

  @Override
  public void deleteItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void syncItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public Map<String, Info> getContent(SessionContext context, String itemId, String versionId,
                                      Namespace contentNamespace) {
    return null;
  }

  @Override
  public void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo) {
    getStateStore(context).createWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo) {
    getStateStore(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void deleteWorkspace(SessionContext context, String workspaceId) {
    getStateStore(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public List<WorkspaceInfo> listWorkspaces(SessionContext context) {
    return getStateStore(context).listWorkspaces(context);
  }


  @Override
  public void saveEntity(SessionContext context, String itemId, String versionId, Namespace entityNamespace,
                         Info entityInfo) {

  }

  @Override
  public void deleteEntity(SessionContext context, String itemId, String versionId,
                           Namespace entityNamespace) {

  }

  @Override
  public Info getEntity(SessionContext context, String itemId, String versionId,
                        Namespace entityNamespace) {
    return null;
  }

  @Override
  public void deleteContent(SessionContext context, String itemId, String versionId,
                            Namespace contentNamespace) {

  }

  private StateStore getStateStore(SessionContext context) {
    return StateStoreFactory.getInstance().createInterface(context);
  }
}
