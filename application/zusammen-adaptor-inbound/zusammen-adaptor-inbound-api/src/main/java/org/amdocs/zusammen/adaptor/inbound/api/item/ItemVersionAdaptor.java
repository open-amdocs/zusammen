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

package org.amdocs.zusammen.adaptor.inbound.api.item;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.response.Response;

import java.util.Collection;

public interface ItemVersionAdaptor {

  Response<Collection<ItemVersion>> list(SessionContext context, Space space, Id itemId);

  Response<ItemVersion> get(SessionContext context, Space space, Id itemId, Id versionId);

  Response<Id> create(SessionContext context, Id itemId, Id baseVersionId, ItemVersionData data);

  Response<Void> update(SessionContext context, Id itemId, Id versionId, ItemVersionData data);

  Response<Void> delete(SessionContext context, Id itemId, Id versionId);

  Response<Void> publish(SessionContext context, Id itemId, Id versionId, String message);

  Response<MergeResult> sync(SessionContext context, Id itemId, Id versionId);

  Response<MergeResult> merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);

  Response<ItemVersionHistory> listHistory(SessionContext context, Id itemId, Id versionId);

  Response<Void> revertHistory(SessionContext context, Id itemId, Id versionId, Id changeId);
}
