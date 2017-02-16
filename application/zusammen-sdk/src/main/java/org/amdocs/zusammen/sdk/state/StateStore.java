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
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;
import org.amdocs.zusammen.sdk.state.types.StateElement;

import java.util.Collection;

public interface StateStore {

  Response<Collection<Item>> listItems(SessionContext context);

  Response<Boolean> isItemExist(SessionContext context, Id itemId);

  Response<Item> getItem(SessionContext context, Id itemId);

  Response<Void> createItem(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> deleteItem(SessionContext context, Id itemId);

  Response<Collection<ItemVersion>> listItemVersions(SessionContext context, Space space, Id
      itemId);

  Response<Boolean> isItemVersionExist(SessionContext context, Space space, Id itemId, Id versionId);

  Response<ItemVersion> getItemVersion(SessionContext context, Space space, Id itemId, Id
      versionId);

  Response<Void> createItemVersion(SessionContext context, Space space, Id itemId, Id baseVersionId,
                         Id versionId, ItemVersionData data);

  Response<Void> updateItemVersion(SessionContext context, Space space, Id itemId, Id versionId,
                         ItemVersionData data);

  Response<Void> deleteItemVersion(SessionContext context, Space space, Id itemId, Id versionId);

  Response<Collection<StateElement>> listElements(SessionContext context, ElementContext
      elementContext,
                                        Id elementId);

  Response<Boolean> isElementExist(SessionContext context, ElementContext elementContext, Id
      elementId);

  Response<StateElement> getElement(SessionContext context, ElementContext elementContext, Id
      elementId);

  Response<Void> createElement(SessionContext context, StateElement element);

  Response<Void> updateElement(SessionContext context, StateElement element);

  Response<Void> deleteElement(SessionContext context, StateElement element);

  Response<Void> createWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  Response<Void> saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  Response<Void> deleteWorkspace(SessionContext context, Id workspaceId);

  Response<Collection<WorkspaceInfo>> listWorkspaces(SessionContext context);
}
