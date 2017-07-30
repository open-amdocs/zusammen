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

package com.amdocs.zusammen.adaptor.outbound.api;


import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementConflict;
import com.amdocs.zusammen.core.api.types.CoreItemVersionConflict;
import com.amdocs.zusammen.core.api.types.CoreMergeChange;
import com.amdocs.zusammen.core.api.types.CoreMergeResult;
import com.amdocs.zusammen.core.api.types.CorePublishResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.item.Resolution;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import com.amdocs.zusammen.datatypes.itemversion.Tag;
import com.amdocs.zusammen.datatypes.response.Response;

import java.util.Collection;

public interface CollaborationAdaptor {

  Response<Void> createItem(SessionContext context, Id itemId, Info info);

  Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> deleteItem(SessionContext context, Id itemId);

  Response<Void> createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                   Id versionId, ItemVersionData data);

  Response<Void> updateItemVersion(SessionContext context, Id itemId, Id versionId,
                                   ItemVersionData data);

  Response<Void> deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  Response<ItemVersionStatus> getItemVersionStatus(SessionContext context, Id itemId, Id versionId);

  Response<Void> tagItemVersion(SessionContext context, Id itemId, Id versionId, Id changeId,
                                Tag tag);

  Response<CorePublishResult> publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                                 String message);

  Response<CoreMergeResult> syncItemVersion(SessionContext context, Id itemId, Id versionId);

  Response<CoreMergeResult> mergeItemVersion(SessionContext context, Id itemId, Id versionId,
                                             Id sourceVersionId);

  Response<ItemVersionHistory> listItemVersionHistory(SessionContext context, Id itemId,
                                                      Id versionId);

  Response<CoreMergeChange> resetItemVersionHistory(SessionContext context, Id itemId, Id versionId,
                                                    String changeRef);

  Response<Void> commitElements(SessionContext context, ElementContext elementContext,
                                String message);

  Response<CoreItemVersionConflict> getItemVersionConflict(SessionContext context, Id itemId, Id
      versionId);

  Response<Collection<CoreElement>> listElements(SessionContext context,
                                                 ElementContext elementContext, Namespace namespace,
                                                 Id elementId);

  Response<CoreElement> getElement(SessionContext context, ElementContext elementContext,
                                   Namespace namespace, Id elementId);

  Response<Void> createElement(SessionContext context, ElementContext elementContext,
                               CoreElement element);

  Response<Void> updateElement(SessionContext context, ElementContext elementContext,
                               CoreElement element);

  Response<Void> deleteElement(SessionContext context, ElementContext elementContext,
                               CoreElement element);

  Response<Void> commitEntities(SessionContext context, ElementContext elementContext,
                                String message);

  CoreElementConflict getElementConflict(SessionContext context, ElementContext
      elementContext, Id elementId);

  void resolveConflict(SessionContext context, ElementContext elementContext, Id elementId,
                       Resolution resolution);
}
