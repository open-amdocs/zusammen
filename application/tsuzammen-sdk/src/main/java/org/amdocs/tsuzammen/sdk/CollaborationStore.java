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


import org.amdocs.tsuzammen.datatypes.CollaborationNamespace;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.Namespace;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.collaboration.MergeResponse;
import org.amdocs.tsuzammen.datatypes.collaboration.PublishResult;
import org.amdocs.tsuzammen.datatypes.collaboration.SyncResponse;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.sdk.types.ElementData;

public interface CollaborationStore {


  void createItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                         Id versionId, Info versionInfo);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId,
                       Info versionInfo);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  PublishResult publishItemVersion(SessionContext context, Id itemId, Id versionId, String message);

  SyncResponse syncItemVersion(SessionContext context, Id itemId, Id versionId);

  ItemVersion getItemVersion(SessionContext context, Id itemId, Id versionId,
                             ItemVersion itemVersion);

  ElementData getElement(SessionContext context, ElementContext elementContext,
                         CollaborationNamespace namespace, Id elementId);

  CollaborationNamespace createElement(SessionContext context, ElementContext elementContext,
                                       Namespace parentNamespace, ElementData elementData);

  void saveElement(SessionContext context, ElementContext elementContext,
                   CollaborationNamespace namespace, ElementData elementData);

  void deleteElement(SessionContext context, ElementContext elementContext,
                     CollaborationNamespace namespace, Id elementId);

  MergeResponse mergeItemVersion(SessionContext context, Id itemId, Id versionId, Id sourceVersionId);
}
