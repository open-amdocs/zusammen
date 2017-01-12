/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.tsuzammen.adaptor.outbound.api.item;

import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.ItemVersion;

import java.util.Collection;

public interface ItemVersionStateAdaptor {
  Collection<ItemVersion> listItemVersions(SessionContext context, Id itemId);

  boolean isItemVersionExist(SessionContext context, Id itemId, Id versionId);

  ItemVersion getItemVersion(SessionContext context, Id itemId, Id versionId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, Info versionInfo);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId, Info versionInfo);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  void publishItemVersion(SessionContext context, Id itemId, Id versionId);

  void syncItemVersion(SessionContext context, Id itemId, Id versionId);
}
