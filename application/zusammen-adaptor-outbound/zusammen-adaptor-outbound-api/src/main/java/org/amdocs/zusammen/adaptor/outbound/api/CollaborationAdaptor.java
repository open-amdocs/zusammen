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

package org.amdocs.zusammen.adaptor.outbound.api;


import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeChange;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.response.Response;

public interface CollaborationAdaptor {

  Response<Void> createItem(SessionContext context, Id itemId, Info info);

  Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo);

  Response<Void> deleteItem(SessionContext context, Id itemId);

  Response<Void> createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, ItemVersionData data);

  Response<Void> updateItemVersion(SessionContext context, Id itemId, Id versionId,
                         ItemVersionData data);

  Response<Void> deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  Response<CorePublishResult> publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                       String message);

  Response<CoreMergeResult> syncItemVersion(SessionContext context, Id itemId, Id versionId);

  Response<CoreMergeResult> mergeItemVersion(SessionContext context, Id itemId, Id versionId,
                                   Id sourceVersionId);

  Response<CoreElement> getElement(SessionContext context, ElementContext elementContext,
                         Namespace namespace, Id elementId);

  Response<Void> createElement(SessionContext context, ElementContext elementContext, CoreElement element);

  Response<Void> updateElement(SessionContext context, ElementContext elementContext, CoreElement element);

  Response<Void> deleteElement(SessionContext context, ElementContext elementContext, CoreElement element);

  Response<Void> commitEntities(SessionContext context, ElementContext elementContext, String message);

  Response<ItemVersionHistory> listItemVersionHistory(SessionContext context, Id itemId, Id
      versionId);

  Response<CoreMergeChange> revertItemVersionHistory(SessionContext context, Id itemId, Id
      versionId, Id changeId);
}
