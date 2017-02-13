/*
 * Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.sdk.collaboration;


import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationElement;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeChange;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeResult;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationPublishResult;

public interface CollaborationStore {

  void createItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, ItemVersionData data);

  void updateItemVersion(SessionContext context, Id itemId, Id versionId,
                         ItemVersionData data);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  CollaborationPublishResult publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                                String message);

  CollaborationMergeResult syncItemVersion(SessionContext context, Id itemId, Id versionId);


  CollaborationMergeResult mergeItemVersion(SessionContext context, Id itemId, Id versionId,
                                            Id sourceVersionId);

  CollaborationElement getElement(SessionContext context, ElementContext elementContext,
                                  Namespace namespace, Id elementId);

  void createElement(SessionContext context, CollaborationElement element);

  void updateElement(SessionContext context, CollaborationElement element);

  void deleteElement(SessionContext context, CollaborationElement element);

  ItemVersionHistory listItemVersionHistory(SessionContext context, Id itemId, Id versionId);

  CollaborationMergeChange revertItemVersionHistory(SessionContext context, Id itemId, Id versionId,
                                                    Id changeId);
}
