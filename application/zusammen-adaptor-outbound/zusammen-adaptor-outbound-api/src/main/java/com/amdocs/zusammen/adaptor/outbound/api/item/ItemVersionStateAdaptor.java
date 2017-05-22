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

package com.amdocs.zusammen.adaptor.outbound.api.item;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.response.Response;

import java.util.Collection;
import java.util.Date;

public interface ItemVersionStateAdaptor {
  Response<Collection<ItemVersion>> listItemVersions(SessionContext context, Space space, Id
      itemId);

  Response<Boolean> isItemVersionExist(SessionContext context, Space space, Id itemId, Id
      versionId);

  Response<ItemVersion> getItemVersion(SessionContext context, Space space, Id itemId, Id
      versionId);

  Response<Void> createItemVersion(SessionContext context, Space space, Id itemId, Id baseVersionId,
                                   Id versionId, ItemVersionData data, Date creationTime);

  Response<Void> updateItemVersion(SessionContext context, Space space, Id itemId, Id versionId,
                                   ItemVersionData data, Date modificationTime);

  Response<Void> deleteItemVersion(SessionContext context, Space space, Id itemId, Id versionId);

  Response<Void> updateItemVersionModificationTime(SessionContext context, Space space, Id itemId,
                                         Id versionId, Date modificationTime);

}
