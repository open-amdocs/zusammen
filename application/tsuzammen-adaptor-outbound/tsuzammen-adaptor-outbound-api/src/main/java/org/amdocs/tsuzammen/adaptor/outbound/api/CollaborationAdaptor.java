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

package org.amdocs.tsuzammen.adaptor.outbound.api;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;

import java.net.URI;
import java.util.Collection;

public interface CollaborationAdaptor {

  void createItem(SessionContext context, String itemId, Info info);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId, Info info);

  void saveItemVersion(SessionContext context, String itemId, String versionId,
                       Info versionInfo);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId, String message);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void revertItemVersion(SessionContext context, String itemId, String versionId,
                         String targetRevisionId);

  Collection listItemVersionRevisions(SessionContext context, String itemId, String versionId);

  Collection listItemVersionMissingRevisions(SessionContext context, String itemId,
                                             String versionId);

  Collection listItemVersionOverRevisions(SessionContext context, String itemId, String versionId);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, Entity entity, Format dataFormat);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                             URI namespace, Entity entity, Format dataFormat);

  void deleteItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, String entityId);

  void commitItemVersionEntities(SessionContext context, String itemId, String versionId,
                                 String message);
}
