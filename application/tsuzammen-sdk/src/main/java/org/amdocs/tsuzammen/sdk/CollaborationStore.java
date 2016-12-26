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

package org.amdocs.tsuzammen.sdk;


import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityData;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.net.URI;
import java.util.Collection;

public interface CollaborationStore {


  void createItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, Info versionInfo);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId,
                       Info versionInfo);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  void publishItemVersion(SessionContext context, Id itemId, Id versionId, String message);

  void syncItemVersion(SessionContext context, Id itemId, Id versionId);

  ItemVersion getItemVersion(SessionContext context, Id itemId, Id versionId,
                             ItemVersion itemVersion);

  Collection<EntityData> getContentEntities(SessionContext context, Id itemId, Id versionId,
                                            URI namespace); //namespace end = content name

  EntityData getEntity(SessionContext context, Id itemId, Id versionId,
                       URI namespace, String entityId);

  void createEntity(SessionContext context, Id itemId, Id versionId,
                    URI namespace, EntityData entityData);

  void saveEntity(SessionContext context, Id itemId, Id versionId,
                  URI namespace, EntityData entityData);

  void deleteContentEntities(SessionContext context, Id itemId, Id versionId,
                             URI namespace);

  void deleteEntity(SessionContext context, Id itemId, Id versionId,
                    URI namespace, String entityId);

  void commitEntities(SessionContext context, Id itemId, Id versionId,
                      String message);
}
