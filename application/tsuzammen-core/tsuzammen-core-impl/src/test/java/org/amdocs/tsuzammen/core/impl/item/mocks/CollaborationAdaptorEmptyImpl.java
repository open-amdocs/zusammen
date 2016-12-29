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

package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.CollaborationNamespace;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.Namespace;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.core.api.types.CoreElement;

import java.util.Collection;

public class CollaborationAdaptorEmptyImpl implements CollaborationAdaptor {
  @Override
  public void createItem(SessionContext context, Id itemId, Info info) {

  }

  @Override
  public void saveItem(SessionContext context, Id itemId, Info itemInfo) {

  }

  @Override
  public void deleteItem(SessionContext context, Id itemId) {

  }

  @Override
  public void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                Id versionId, Info info) {

  }

  @Override
  public void saveItemVersion(SessionContext context, Id itemId, Id versionId,
                              Info versionInfo) {

  }

  @Override
  public void deleteItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                 String message) {

  }

  @Override
  public void syncItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void revertItemVersion(SessionContext context, Id itemId, Id versionId,
                                String targetRevisionId) {

  }

  @Override
  public Collection listItemVersionRevisions(SessionContext context, Id itemId,
                                             Id versionId) {
    return null;
  }

  @Override
  public Collection listItemVersionMissingRevisions(SessionContext context, Id itemId,
                                                    Id versionId) {
    return null;
  }

  @Override
  public Collection listItemVersionOverRevisions(SessionContext context, Id itemId,
                                                 Id versionId) {
    return null;
  }

  @Override
  public CoreElement getElement(SessionContext context, ElementContext elementContext,
                                CollaborationNamespace namespace, Id elementId) {
    return null;
  }

  @Override
  public CollaborationNamespace createElement(SessionContext context, ElementContext elementContext,
                                              Namespace parentNamespace,
                                              CoreElement element) {
    return null;
  }

  @Override
  public void saveElement(SessionContext context, ElementContext elementContext,
                          CollaborationNamespace collaborationNamespace,
                          CoreElement element) {

  }

  @Override
  public void deleteElement(SessionContext context, ElementContext elementContext,
                            CollaborationNamespace collaborationNamespace,
                            Id elementId) {

  }

  @Override
  public void commitEntities(SessionContext context, ElementContext elementContext,
                             String message) {

  }
}
