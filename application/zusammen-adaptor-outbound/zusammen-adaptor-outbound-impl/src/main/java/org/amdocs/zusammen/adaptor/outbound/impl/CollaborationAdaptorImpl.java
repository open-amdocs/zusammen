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
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
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

public class CollaborationAdaptorImpl implements CollaborationAdaptor {


  private CollaborationStore getCollaborationStore(SessionContext context) {
    return CollaborationStoreFactory.getInstance().createInterface(context);
  }

  private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(CollaborationAdaptorImpl.class
          .getName());

  @Override
  public Response<Void> createItem(SessionContext context, Id itemId,
                                   Info itemInfo) {
    Response response;
    try {
      response = getCollaborationStore(context).createItem(context, itemId, itemInfo);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_CREATE, Module.MDW, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_CREATE, Module.MDW, e.getMessage(), null);
      logger.error(returnCode.toString());
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
    Response response;
    try {
      response = getCollaborationStore(context).deleteItem(context, itemId);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_DELETE, Module.MDW, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_DELETE, Module.MDW, e.getMessage(), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                          Id versionId, ItemVersionData data) {
    Response response;
    try {
      response = getCollaborationStore(context)
          .createItemVersion(context, itemId, baseVersionId, versionId, data);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_CREATE, Module.MDW, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_CREATE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateItemVersion(SessionContext context, Id itemId, Id versionId,
                                          ItemVersionData data) {
    Response response;
    try {
      response = getCollaborationStore(context).updateItemVersion(context, itemId, versionId, data);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_UPDATE, Module.MDW,
            null, response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_UPDATE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> deleteItemVersion(SessionContext context, Id itemId, Id versionId) {
    Response response;
    try {
      response = getCollaborationStore(context).deleteItemVersion(context, itemId, versionId);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_DELETE, Module.MDW,
            null, response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_DELETE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
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
      if (!collaborationResponse.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_PUBLISH, Module.MDW, null,
            collaborationResponse.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_PUBLISH, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    CorePublishResult corePublishResult = CollaborationPublishResultConvertor.convert
        (collaborationResponse.getValue());

    return new Response(corePublishResult);
  }

  @Override
  public Response<CoreMergeResult> syncItemVersion(SessionContext context, Id itemId, Id
      versionId) {
    Response<CollaborationMergeResult> collaborationResponse;
    try {
      collaborationResponse =
          getCollaborationStore(context).syncItemVersion(context, itemId, versionId);
      if (!collaborationResponse.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_SYNC, Module.MDW, null,
            collaborationResponse.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_SYNC, Module.MDW, e.getMessage(), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    CoreMergeResult coreMergeResult = CollaborationMergeResultConvertor.convert(
        (collaborationResponse.getValue()));

    return new Response(coreMergeResult);


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
      if (!collaborationResponse.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_MERGE, Module.MDW, null,
            collaborationResponse.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_MERGE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    CoreMergeResult coreMergeResult = CollaborationMergeResultConvertor.convert(
        (collaborationResponse.getValue()));

    return new Response(coreMergeResult);

  }

  @Override
  public Response<CoreElement> getElement(SessionContext context, ElementContext elementContext,
                                          Namespace namespace, Id elementId) {

    Response<CollaborationElement> collaborationResponse;
    try {
      collaborationResponse =
          getCollaborationStore(context)
              .getElement(context, elementContext, namespace, elementId);
      if (!collaborationResponse.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ELEMENT_GET, Module.MDW, null,
            collaborationResponse.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ELEMENT_GET, Module.MDW, e.getMessage(),
          null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    CoreElement coreElement = CollaborationElementConvertor.convertToCoreElement(
        collaborationResponse.getValue());

    return new Response(coreElement);
  }

  @Override
  public Response<Void> createElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {

    Response response;
    try {
      response = getCollaborationStore(context)
          .createElement(context,
              CollaborationElementConvertor.convertFromCoreElement(element, elementContext));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ELEMENT_CREATE, Module.MDW, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ELEMENT_CREATE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {
    Response response;
    try {
      response = getCollaborationStore(context)
          .updateElement(context,
              CollaborationElementConvertor.convertFromCoreElement(element, elementContext));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode =
            new ReturnCode(ErrorCode.CL_ELEMENT_UPDATE, Module.MDW, null, response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ELEMENT_UPDATE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> deleteElement(SessionContext context, ElementContext elementContext,
                                      CoreElement element) {

    Response response;
    try {
      response = getCollaborationStore(context)
          .deleteElement(context,
              CollaborationElementConvertor.convertFromCoreElement(element, elementContext));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode =
            new ReturnCode(ErrorCode.CL_ELEMENT_DELETE, Module.MDW, null, response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ELEMENT_DELETE, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
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
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_HISTORY, Module.MDW, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }

    } catch (RuntimeException e) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.CL_ITEM_VERSION_HISTORY, Module.MDW, e.getMessage(),
              null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response;
  }

  @Override
  public Response<CoreMergeChange> revertItemVersionHistory(SessionContext context, Id
      itemId, Id versionId, Id changeId) {

    Response<CollaborationMergeChange> collaborationResponse;
    try {

      collaborationResponse = getCollaborationStore(context)
          .revertItemVersionHistory(context, itemId, versionId, changeId);
      if (collaborationResponse.isSuccessful()) {
        return new Response(CollaborationMergeChangeConvertor
            .convertToCoreMergeChange(collaborationResponse.getValue()));
      } else {
        ReturnCode returnCode =
            new ReturnCode(ErrorCode.CL_ITEM_VERSION_REVERT_HISTORY, Module.MDW, null,
                collaborationResponse.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }

    } catch (RuntimeException e) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ITEM_VERSION_REVERT_HISTORY, Module.MDW, e
          .getMessage(),
          null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }


  }


}
