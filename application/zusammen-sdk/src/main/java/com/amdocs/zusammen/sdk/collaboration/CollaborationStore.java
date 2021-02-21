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

package com.amdocs.zusammen.sdk.collaboration;


import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.item.Resolution;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Revision;
import com.amdocs.zusammen.datatypes.itemversion.Tag;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElement;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElementConflict;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationItemVersionConflict;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeChange;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeResult;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationPublishResult;
import com.amdocs.zusammen.sdk.health.IHealthCheck;

import java.util.Collection;

public interface CollaborationStore extends IHealthCheck {

  Response<Void> createItem(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> deleteItem(SessionContext context, Id itemId);

  Response<Void> createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                   Id versionId, ItemVersionData data);

  Response<Void> updateItemVersion(SessionContext context, Id itemId, Id versionId,
                                   ItemVersionData data);

  Response<Void> deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  Response<ItemVersionStatus> getItemVersionStatus(SessionContext context, Id itemId, Id versionId);

  Response<Void> tagItemVersion(SessionContext context, Id itemId, Id versionId, Id changeId,
                                Tag tag);

  Response<CollaborationPublishResult> publishItemVersion(SessionContext context, Id itemId,
                                                          Id versionId, String message);

  Response<CollaborationMergeResult> syncItemVersion(SessionContext context, Id itemId,
                                                     Id versionId);

  Response<CollaborationMergeResult> forceSyncItemVersion(SessionContext context, Id itemId,
                                                          Id versionId);

  Response<CollaborationMergeResult> mergeItemVersion(SessionContext context, Id itemId,
                                                      Id versionId, Id sourceVersionId);

  Response<CollaborationItemVersionConflict> getItemVersionConflict(SessionContext context,
                                                                    Id itemId, Id versionId);

  Response<ItemVersionRevisions> listItemVersionRevisions(SessionContext context, Id itemId,
                                                          Id versionId);

  Response<Revision> getItemVersionRevision(SessionContext context, Id itemId, Id versionId,
                                            Id revisionId);

  Response<CollaborationMergeChange> resetItemVersionRevision(SessionContext context, Id itemId,
                                                              Id versionId, Id revisionId);

  Response<CollaborationMergeChange> revertItemVersionRevision(SessionContext context, Id itemId,
                                                               Id versionId, Id revisionId);

  Response<Void> commitElements(SessionContext context, Id itemId, Id versionId, String message);

  // TODO: 7/2/2017 return ElementDescriptor
  Response<Collection<CollaborationElement>> listElements(SessionContext context,
                                                          ElementContext elementContext,
                                                          Namespace namespace, Id elementId);

  Response<CollaborationElement> getElement(SessionContext context, ElementContext elementContext,
                                            Namespace namespace, Id elementId);

  Response<CollaborationElement> getElement(SessionContext context, Space space, ElementContext elementContext,
                                            Namespace namespace, Id elementId);

  Response<CollaborationElementConflict> getElementConflict(SessionContext context,
                                                            ElementContext elementContext,
                                                            Namespace namespace, Id elementId);

  Response<Void> createElement(SessionContext context, CollaborationElement element);

  Response<Void> updateElement(SessionContext context, CollaborationElement element);

  Response<Void> deleteElement(SessionContext context, CollaborationElement element);

  Response<CollaborationMergeResult> resolveElementConflict(SessionContext context,
                                                            CollaborationElement element,
                                                            Resolution resolution);

  Response<ItemVersion> getItemVersion(SessionContext context, Space space, Id itemId,
                                       Id versionId, Id revisionId);
}
