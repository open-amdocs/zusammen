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

package org.amdocs.tsuzammen.adaptor.outbound.api;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.Item;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.net.URI;
import java.util.Collection;

public interface StateAdaptor {

  Collection<Item> listItems(SessionContext context);

  Item getItem(SessionContext context, String itemId);

  void createItem(SessionContext context, String itemId, Info itemInfo);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  Collection<ItemVersion> listItemVersions(SessionContext context, String itemId);

  ItemVersion getItemVersion(SessionContext context, String itemId, String versionId);

  void validateItemVersionExistence(SessionContext context, String itemId, String versionId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId,
                         Info versionInfo);

  void saveItemVersion(SessionContext context, String itemId, String versionId, Info versionInfo);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, Entity entity);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                             URI namespace, Entity entity);

  void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, String workspaceId);

  Collection<WorkspaceInfo> listWorkspaces(SessionContext context);
}
