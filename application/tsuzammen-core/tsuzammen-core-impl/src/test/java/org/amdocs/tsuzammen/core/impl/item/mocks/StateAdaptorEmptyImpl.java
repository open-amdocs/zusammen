package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.Item;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.net.URI;
import java.util.Collection;
import java.util.List;

public class StateAdaptorEmptyImpl implements StateAdaptor {
  @Override
  public Collection<Item> listItems(SessionContext context) {
    return null;
  }

  @Override
  public Item getItem(SessionContext context, String itemId) {
    return null;
  }

  @Override
  public void createItem(SessionContext context, String itemId, Info itemInfo) {

  }

  @Override
  public void saveItem(SessionContext context, String itemId, Info itemInfo) {

  }

  @Override
  public void deleteItem(SessionContext context, String itemId) {

  }

  @Override
  public Collection<ItemVersion> listItemVersions(SessionContext context, String itemId) {
    return null;
  }

  @Override
  public ItemVersion getItemVersion(SessionContext context, String itemId, String versionId) {
    return null;
  }

  @Override
  public void validateItemVersionExistence(SessionContext context, String itemId,
                                           String versionId) {

  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                                String versionId, Info versionInfo) {

  }

  @Override
  public void saveItemVersion(SessionContext context, String itemId, String versionId,
                              Info versionInfo) {

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
  public void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                                      URI namespace, Entity entity) {

  }

  @Override
  public void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                                    URI namespace, Entity entity) {

  }

  @Override
  public void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo) {

  }

  @Override
  public void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo) {

  }

  @Override
  public void deleteWorkspace(SessionContext context, String workspaceId) {

  }

  @Override
  public List<WorkspaceInfo> listWorkspaces(SessionContext context) {
    return null;
  }
}
