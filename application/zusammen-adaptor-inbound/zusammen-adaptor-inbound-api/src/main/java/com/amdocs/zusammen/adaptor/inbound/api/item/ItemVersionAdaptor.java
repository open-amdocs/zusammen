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

package com.amdocs.zusammen.adaptor.inbound.api.item;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.ItemVersionConflict;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Tag;
import com.amdocs.zusammen.datatypes.response.Response;

import java.util.Collection;
import java.util.List;

public interface ItemVersionAdaptor {

  Response<Collection<ItemVersion>> list(SessionContext context, Space space, Id itemId);

  Response<ItemVersion> get(SessionContext context, Space space, Id itemId, Id versionId);

  Response<Id> create(SessionContext context, Id itemId, Id baseVersionId, ItemVersionData data);

  Response<Void> update(SessionContext context, Id itemId, Id versionId, ItemVersionData data);

  Response<Void> delete(SessionContext context, Id itemId, Id versionId);

  Response<ItemVersionStatus> getStatus(SessionContext context, Id itemId, Id versionId);

  Response<Void> tag(SessionContext context, Id itemId, Id versionId, Id changeId, Tag tag);

  Response<Void> publish(SessionContext context, Id itemId, Id versionId, String message);

  Response<MergeResult> sync(SessionContext context, Id itemId, Id versionId);

  Response<MergeResult> merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);

  Response<ItemVersionRevisions> listRevisions(SessionContext context, Id itemId, Id
      versionId);

  Response<Void> resetRevision(SessionContext context, Id itemId, Id versionId, String revisionId);

  Response<Void> revertRevision(SessionContext context, Id itemId, Id versionId, String revisionId);

  Response<ItemVersionConflict> getConflict(SessionContext context, Id itemId, Id versionId);
}
