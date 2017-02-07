/*
 * Add Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.sdk.state;


import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Item;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;
import org.amdocs.zusammen.sdk.state.types.StateElement;

import java.util.Collection;

public interface StateStore {

  Collection<Item> listItems(SessionContext context);

  boolean isItemExist(SessionContext context, Id itemId);

  Item getItem(SessionContext context, Id itemId);

  void createItem(SessionContext context, Id itemId, Info itemInfo);

  void updateItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  Collection<ItemVersion> listItemVersions(SessionContext context, Space space, Id itemId);

  boolean isItemVersionExist(SessionContext context, Space space, Id itemId, Id versionId);

  ItemVersion getItemVersion(SessionContext context, Space space, Id itemId, Id versionId);

  void createItemVersion(SessionContext context, Space space, Id itemId, Id baseVersionId,
                         Id versionId, ItemVersionData data);

  void updateItemVersion(SessionContext context, Space itemId, Id versionId, Id space,
                         ItemVersionData data);

  void deleteItemVersion(SessionContext context, Space itemId, Id versionId, Id space);

  Collection<StateElement> listElements(SessionContext context, ElementContext elementContext,
                                        Id elementId);

  boolean isElementExist(SessionContext context, ElementContext elementContext, Id elementId);

  StateElement getElement(SessionContext context, ElementContext elementContext, Id elementId);

  void createElement(SessionContext context, StateElement element);

  void updateElement(SessionContext context, StateElement element);

  void deleteElement(SessionContext context, StateElement element);

  void createWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, Id workspaceId);

  Collection<WorkspaceInfo> listWorkspaces(SessionContext context);
}
