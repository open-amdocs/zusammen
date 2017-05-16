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
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationElementConvertor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationMergeChangeConvertor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationMergeResultConvertor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.CollaborationPublishResultConvertor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeChange;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.itemversion.Tag;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.sdk.collaboration.CollaborationStore;
import org.amdocs.zusammen.sdk.collaboration.CollaborationStoreFactory;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationElement;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeChange;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeResult;
import org.amdocs.zusammen.sdk.collaboration.types.CollaborationPublishResult;

import java.util.Collection;
import java.util.stream.Collectors;

public class CollaborationAdaptorImpl implements CollaborationAdaptor {


  private CollaborationStore getCollaborationStore(SessionContext context) {
    return CollaborationStoreFactory.getInstance().createInterface(context);
  }

  /*private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(CollaborationAdaptorImpl.class
          .getName());*/

  @Override
  public Response<Void> createItem(SessionContext context, Id itemId,
                                   Info itemInfo) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context).createItem(context, itemId, itemInfo);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_CREATE, Module.ZCSM, null,
                response.getReturnCode());
        //logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_CREATE, Module.ZCSM, null, new ReturnCode(ErrorCode
                      .CL_ITEM_CREATE, Module.ZCSP, e.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo) {
    return new Response(Void.TYPE);
    /* Response response;
    try {
      response = getCollaborationStore(context).updateItem(context, itemId, itemInfo);
      if (response.isSuccessful()) {
        return response;
      } else {
        throw new ZusammenException(ErrorCode.CL_ITEM_UPDATE, Module.MDW, null,
            response.getReturnCode());
      }
    } catch (RuntimeException e) {
      throw new ZusammenException(ErrorCode.CL_ITEM_UPDATE, Module.MDW, e.getMessage(), null);
    }*/
  }

  @Override
  public Response<Void> deleteItem(SessionContext context, Id itemId) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context).deleteItem(context, itemId);
    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_DELETE, Module.ZCSM, null, new ReturnCode(ErrorCode
                      .CL_ITEM_DELETE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_DELETE, Module.ZCSM, null,
              response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                          Id versionId, ItemVersionData data) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context)
              .createItemVersion(context, itemId, baseVersionId, versionId, data);
    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_CREATE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_CREATE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_CREATE, Module.ZCSM,
              null,
              response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateItemVersion(SessionContext context, Id itemId, Id versionId,
                                          ItemVersionData data) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context).updateItemVersion(context, itemId, versionId, data);
    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_UPDATE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_UPDATE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_UPDATE, Module.ZCSM,
              null, response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> deleteItemVersion(SessionContext context, Id itemId, Id versionId) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context).deleteItemVersion(context, itemId, versionId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_DELETE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_DELETE, Module.ZCSP, re.getMessage(), null));

      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_DELETE, Module.ZCSM,
              null, response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> tagItemVersion(SessionContext context, Id itemId, Id versionId, Id changeId,
                                       Tag tag) {
    Response<Void> response;
    try {
      response =
              getCollaborationStore(context).tagItemVersion(context, itemId, versionId, changeId, tag);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_TAG, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_TAG, Module.ZCSP, re.getMessage(), null));

      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_TAG, Module.ZCSM,
              null, response.getReturnCode());
      throw new ZusammenException(returnCode);
    }
  }


  @Override
  public Response<CorePublishResult> publishItemVersion(SessionContext context, Id itemId, Id
          versionId,
                                                        String message) {
    Response<CollaborationPublishResult> collaborationResponse;
    try {
      collaborationResponse =
              getCollaborationStore(context).publishItemVersion(context, itemId, versionId, message);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_PUBLISH, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_PUBLISH, Module.ZCSP, re.getMessage(),
                              null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!collaborationResponse.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_PUBLISH, Module.ZCSM,
              null,
              collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    CorePublishResult corePublishResult = CollaborationPublishResultConvertor.convert
            (collaborationResponse.getValue());

    return new Response<>(corePublishResult);
  }

  @Override
  public Response<CoreMergeResult> syncItemVersion(SessionContext context, Id itemId, Id
          versionId) {
    Response<CollaborationMergeResult> collaborationResponse;
    try {
      collaborationResponse =
              getCollaborationStore(context).syncItemVersion(context, itemId, versionId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_SYNC, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_SYNC, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!collaborationResponse.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_SYNC, Module.ZCSM, null,
              collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    CoreMergeResult coreMergeResult = CollaborationMergeResultConvertor.convert(
            (collaborationResponse.getValue()));

    return new Response<>(coreMergeResult);


  }

  @Override
  public Response<CoreMergeResult> mergeItemVersion(SessionContext context, Id itemId, Id
          versionId,
                                                    Id sourceVersionId) {
    Response<CollaborationMergeResult> collaborationResponse;
    try {
      collaborationResponse =
              getCollaborationStore(context)
                      .mergeItemVersion(context, itemId, versionId, sourceVersionId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_MERGE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_MERGE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!collaborationResponse.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_MERGE, Module.ZCSM, null,
              collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    CoreMergeResult coreMergeResult = CollaborationMergeResultConvertor.convert(
            (collaborationResponse.getValue()));

    return new Response<>(coreMergeResult);

  }

  @Override
  public Response<CoreElement> getElement(SessionContext context, ElementContext elementContext,
                                          Namespace namespace, Id elementId) {

    Response<CollaborationElement> collaborationResponse;
    try {
      collaborationResponse =
              getCollaborationStore(context)
                      .getElement(context, elementContext, namespace, elementId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ELEMENT_GET, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!collaborationResponse.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZCSM, null,
              collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    CoreElement coreElement = CollaborationElementConvertor.convertToCoreElement(
            collaborationResponse.getValue());

    return new Response<>(coreElement);
  }

  @Override
  public Response<Void> createElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {

    Response<Void> response;
    try {
      response = getCollaborationStore(context)
              .createElement(context,
                      CollaborationElementConvertor.convertFromCoreElement(element, elementContext));

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ELEMENT_CREATE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZCSM, null,
              response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {
    Response<Void> response;
    try {
      response = getCollaborationStore(context)
              .updateElement(context,
                      CollaborationElementConvertor.convertFromCoreElement(element, elementContext));

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ELEMENT_UPDATE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZCSM, null, response.getReturnCode
                      ());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> deleteElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {

    Response<Void> response;
    try {
      response = getCollaborationStore(context)
              .deleteElement(context,
                      CollaborationElementConvertor.convertFromCoreElement(element, elementContext));

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ELEMENT_DELETE, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (response.isSuccessful()) {
      return response;
    } else {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZCSM, null, response.getReturnCode
                      ());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> commitEntities(SessionContext context, ElementContext elementContext,
                                       String message) {
    return new Response(Void.TYPE);
    //getCollaborationStore(context).commitEntities(context, elementContext, message);
  }

  @Override
  public Response<ItemVersionHistory> listItemVersionHistory(SessionContext context, Id itemId,
                                                             Id versionId) {
    Response<ItemVersionHistory> response;
    try {
      response = getCollaborationStore(context).listItemVersionHistory(context, itemId, versionId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_HISTORY, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_HISTORY, Module.ZCSP, re.getMessage(),
                              null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ITEM_VERSION_HISTORY, Module.ZCSM, null,
              response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    return response;
  }

  @Override
  public Response<CoreMergeChange> resetItemVersionHistory(SessionContext context, Id
          itemId, Id versionId, String changeRef) {

    Response<CollaborationMergeChange> collaborationResponse;
    try {

      collaborationResponse = getCollaborationStore(context)
              .resetItemVersionHistory(context, itemId, versionId, changeRef);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_RESET_HISTORY, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ITEM_VERSION_RESET_HISTORY, Module.ZCSP, re.getMessage(),
                              null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (collaborationResponse.isSuccessful()) {
      return new Response<>(CollaborationMergeChangeConvertor
              .convertToCoreMergeChange(collaborationResponse.getValue()));
    } else {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ITEM_VERSION_RESET_HISTORY, Module.ZCSM, null,
                      collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }


  }

  @Override
  public Response<Void> commitElements(SessionContext context, ElementContext elementContext,
                                       String message) {
    Response<Void> response;
    response = getCollaborationStore(context).commitElements(context, elementContext.getItemId(),
            elementContext
                    .getVersionId(), message);
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_COMMIT, Module.ZCSM,
              null,
              response.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response;
  }

  @Override
  public Response<Collection<CoreElement>> listElements(SessionContext context,
                                                        ElementContext elementContext,
                                                        Namespace namespace, Id elementId) {
    Response<Collection<CollaborationElement>> collaborationResponse;
    try {
      collaborationResponse = getCollaborationStore(context)
              .listElements(context, elementContext, namespace, elementId);

    } catch (RuntimeException re) {
      ReturnCode returnCode =
              new ReturnCode(ErrorCode.MD_ELEMENT_GET_LIST, Module.ZCSM, null,
                      new ReturnCode(ErrorCode.CL_ELEMENT_GET_LIST, Module.ZCSP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    if (!collaborationResponse.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_GET_LIST, Module.ZCSM, null,
              collaborationResponse.getReturnCode());
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return new Response<>(collaborationResponse.getValue().stream()
            .map(CollaborationElementConvertor::convertToCoreElement)
            .collect(Collectors.toList()));
  }
}
