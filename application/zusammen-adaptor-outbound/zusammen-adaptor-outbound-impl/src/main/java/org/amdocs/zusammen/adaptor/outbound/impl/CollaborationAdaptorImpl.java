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

package org.amdocs.zusammen.adaptor.outbound.impl;


import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationMergeResultConvertor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationPublishResultConvertor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.ElementDataConvertor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.sdk.CollaborationStore;
import org.amdocs.zusammen.sdk.CollaborationStoreFactory;
import org.amdocs.zusammen.sdk.types.CollaborationMergeResult;
import org.amdocs.zusammen.sdk.types.CollaborationPublishResult;

public class CollaborationAdaptorImpl implements CollaborationAdaptor {

  @Override
  public void createItem(SessionContext context, Id itemId,
                         Info itemInfo) {
    getCollaborationStore(context).createItem(context, itemId, itemInfo);
  }

  @Override
  public void saveItem(SessionContext context, Id itemId, Info itemInfo) {
    //getCollaborationStore(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void deleteItem(SessionContext context, Id itemId) {
    getCollaborationStore(context).deleteItem(context, itemId);
  }

  @Override
  public void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                Id versionId, ItemVersionData data) {
    getCollaborationStore(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);
  }

  @Override
  public void updateItemVersion(SessionContext context, Id itemId, Id versionId,
                                ItemVersionData data) {
    getCollaborationStore(context).saveItemVersion(context, itemId, versionId, data);
  }

  @Override
  public void deleteItemVersion(SessionContext context, Id itemId, Id versionId) {
    getCollaborationStore(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public CorePublishResult publishItemVersion(SessionContext context, Id itemId, Id versionId,
                                              String message) {
    CollaborationPublishResult publishResult =
        getCollaborationStore(context).publishItemVersion(context, itemId, versionId, message);
    return CollaborationPublishResultConvertor.convert(publishResult);
  }

  @Override
  public CoreMergeResult syncItemVersion(SessionContext context, Id itemId, Id versionId) {

    CollaborationMergeResult mergeResult =
        getCollaborationStore(context).syncItemVersion(context, itemId, versionId);
    return CollaborationMergeResultConvertor.convert(mergeResult);
  }

  @Override
  public CoreMergeResult mergeItemVersion(SessionContext context, Id itemId, Id versionId,
                                          Id sourceVersionId) {

    CollaborationMergeResult mergeResult = getCollaborationStore(context)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);
    return CollaborationMergeResultConvertor.convert(mergeResult);
  }

  @Override
  public CoreElement getElement(SessionContext context, ElementContext elementContext,
                                Namespace namespace, Id elementId) {
    return ElementDataConvertor.convertFrom(
        getCollaborationStore(context).getElement(context, elementContext, namespace, elementId));
  }

  @Override
  public void createElement(SessionContext context, ElementContext elementContext,
                            CoreElement element) {
    getCollaborationStore(context)
        .createElement(context, ElementDataConvertor.convertTo(element, elementContext));
  }

  @Override
  public void updateElement(SessionContext context, ElementContext elementContext,
                            CoreElement element) {
    getCollaborationStore(context)
        .updateElement(context, ElementDataConvertor.convertTo(element, elementContext));
  }

  @Override
  public void deleteElement(SessionContext context, ElementContext elementContext,
                            CoreElement element) {
    getCollaborationStore(context)
        .deleteElement(context, ElementDataConvertor.convertTo(element, elementContext));
  }

  @Override
  public void commitEntities(SessionContext context, ElementContext elementContext,
                             String message) {
    //getCollaborationStore(context).commitEntities(context, elementContext, message);
  }

  private CollaborationStore getCollaborationStore(SessionContext context) {
    return CollaborationStoreFactory.getInstance().createInterface(context);
  }
}
