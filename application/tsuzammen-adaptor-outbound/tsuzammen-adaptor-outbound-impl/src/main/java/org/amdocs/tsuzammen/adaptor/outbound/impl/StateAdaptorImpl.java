/*
 * Copyright © 2016 Amdocs Software Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.amdocs.tsuzammen.adaptor.outbound.impl;

import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.Item;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;
import org.amdocs.tsuzammen.sdk.StateStore;
import org.amdocs.tsuzammen.sdk.StateStoreFactory;

import java.net.URI;
import java.util.Collection;

public class StateAdaptorImpl implements StateAdaptor {
  @Override
  public Collection<Item> listItems(SessionContext context) {
    return getStateStore(context).listItems(context);
  }

  @Override
  public Item getItem(SessionContext context, String itemId) {
    return getStateStore(context).getItem(context, itemId);
  }

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
  public Collection<ItemVersion> listItemVersions(SessionContext context, String itemId) {
    return getStateStore(context).listItemVersions(context, itemId);
  }

  @Override
  public ItemVersion getItemVersion(SessionContext context, String itemId, String versionId) {
    return getStateStore(context).getItemVersion(context, itemId, versionId);
  }

  @Override
  public void validateItemVersionExistence(SessionContext context, String itemId,
                                           String versionId) {

  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                                String versionId, Info versionInfo) {
    getStateStore(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);
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
    getStateStore(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void syncItemVersion(SessionContext context, String itemId, String versionId) {
    getStateStore(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                                      URI namespace, Entity entity) {
    getStateStore(context).createItemVersionEntity(context, itemId, versionId, namespace,
        entity.getId(), createEntityInfo(entity));
  }

  @Override
  public void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                                    URI namespace, Entity entity) {
    getStateStore(context).saveItemVersionEntity(context, itemId, versionId, namespace,
        entity.getId(), createEntityInfo(entity));
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
  public Collection<WorkspaceInfo> listWorkspaces(SessionContext context) {
    return getStateStore(context).listWorkspaces(context);
  }

  private EntityInfo createEntityInfo(Entity entity) {
    EntityInfo entityInfo = new EntityInfo();
    entityInfo.setInfo(entity.getInfo());
    entityInfo.setRelations(entity.getRelations());
    return entityInfo;
  }

  private StateStore getStateStore(SessionContext context) {
    return StateStoreFactory.getInstance().createInterface(context);
  }
}
