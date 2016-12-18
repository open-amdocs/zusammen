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


import org.amdocs.tsuzammen.commons.datatypes.ContentNamespace;
import org.amdocs.tsuzammen.commons.datatypes.EntityNamespace;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersionData;

import java.util.Collection;
import java.util.List;

public interface ItemVersionManager {

  String create(SessionContext context, String itemId, String baseVersionId, Info versionInfo);

  void saveInfo(SessionContext context, String itemId, String versionId, Info versionInfo);

  void saveData(SessionContext context, String itemId, String versionId,
                ItemVersionData versionData,
                List<ContentNamespace> contentsToDelete, List<EntityNamespace> entitiesToDelete,
                String message);

  void delete(SessionContext context, String itemId, String versionId);

  void publish(SessionContext context, String itemId, String versionId, String message);

  void sync(SessionContext context, String itemId, String versionId);

  void revert(SessionContext context, String itemId, String versionId, String targetRevisionId);

  Collection<ItemVersion> get(SessionContext context, String itemId, String versionId);

  Collection<ItemVersion> getInfo(SessionContext context, String itemId, String versionId);
}
