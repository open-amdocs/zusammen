/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.tsuzammen.core.api.item;


import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.core.api.types.CoreSyncResult;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.ItemVersion;

import java.util.Collection;

public interface ItemVersionManager {

  Collection<ItemVersion> list(SessionContext context, Id itemId);

  ItemVersion get(SessionContext context, Id itemId, Id versionId);

  Id create(SessionContext context, Id itemId, Id baseVersionId, Info versionInfo);

  void save(SessionContext context, Id itemId, Id versionId, Info versionInfo);

  void delete(SessionContext context, Id itemId, Id versionId);

  void publish(SessionContext context, Id itemId, Id versionId, String message);

  CoreSyncResult sync(SessionContext context, Id itemId, Id versionId);

  void revert(SessionContext context, Id itemId, Id versionId, String targetRevisionId);

  CoreSyncResult merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);
}
