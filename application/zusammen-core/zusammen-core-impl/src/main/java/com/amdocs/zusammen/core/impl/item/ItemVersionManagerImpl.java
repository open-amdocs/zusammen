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

package com.amdocs.zusammen.core.impl.item;


import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import com.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ItemManager;
import com.amdocs.zusammen.core.api.item.ItemManagerFactory;
import com.amdocs.zusammen.core.api.item.ItemVersionManager;
import com.amdocs.zusammen.core.api.types.CoreItemVersionConflict;
import com.amdocs.zusammen.core.api.types.CoreMergeChange;
import com.amdocs.zusammen.core.api.types.CoreMergeResult;
import com.amdocs.zusammen.core.api.types.CorePublishResult;
import com.amdocs.zusammen.core.impl.Messages;
import com.amdocs.zusammen.core.impl.ValidationUtil;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.ItemVersion;
import com.amdocs.zusammen.datatypes.item.ItemVersionChange;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.ItemVersionStatus;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Tag;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;
import java.util.Date;

import static com.amdocs.zusammen.datatypes.item.SynchronizationStatus.UP_TO_DATE;

public class ItemVersionManagerImpl implements ItemVersionManager {
  private ElementVisitor indexingElementVisitor = IndexingElementVisitor.init();
  private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(ItemVersionManagerImpl.class.getName());

  @Override
  public Collection<ItemVersion> list(SessionContext context, Space space, Id itemId) {
    validateItemExistence(context, itemId);
    Response<Collection<ItemVersion>> response =
        getStateAdaptor(context).listItemVersions(context, space, itemId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_LIST);
    return response.getValue();
  }

  @Override
  public boolean isExist(SessionContext context, Space space, Id itemId, Id versionId) {
    validateItemExistence(context, itemId);
    Response<Boolean> response =
        getStateAdaptor(context).isItemVersionExist(context, space, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_IS_EXIST);
    return response.getValue();
  }

  @Override
  public ItemVersion get(SessionContext context, Space space, Id itemId, Id versionId,
                         Id revisionId) {
    validateItemExistence(context, itemId);
    Response<ItemVersion> response;
    if(revisionId == null) {
      response =
          getStateAdaptor(context).getItemVersion(context, space, itemId, versionId);
    }else{
      response =
          getCollaborationAdaptor(context).getItemVersion(context, space, itemId, versionId, revisionId);
    }
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_GET);

    return response.getValue();
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                   ItemVersionData data) {
    if (baseVersionId != null) {
      validateItemVersionExistence(context, Space.PRIVATE, itemId, baseVersionId);
    }
    Id versionId = new Id();
    Response<Void> response = getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_CREATE);

    Date creationTime = new Date();
    response = getStateAdaptor(context)
        .createItemVersion(context, Space.PRIVATE, itemId, baseVersionId, versionId, data,
            creationTime);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_CREATE);

    getItemManager(context).updateModificationTime(context, itemId, creationTime);
    return versionId;
  }

  @Override
  public void update(SessionContext context, Id itemId, Id versionId, ItemVersionData data) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<Void> response =
        getCollaborationAdaptor(context).updateItemVersion(context, itemId, versionId, data);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_UPDATE);

    Date modificationTime = new Date();
    response = getStateAdaptor(context)
        .updateItemVersion(context, Space.PRIVATE, itemId, versionId, data, modificationTime);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_UPDATE);

    getItemManager(context).updateModificationTime(context, itemId, modificationTime);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<Void> response =
        getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_DELETE);

    response =
        getStateAdaptor(context).deleteItemVersion(context, Space.PRIVATE, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_DELETE);
  }

  @Override
  public ItemVersionStatus getStatus(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<ItemVersionStatus> response =
        getCollaborationAdaptor(context).getItemVersionStatus(context, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_GET_STATUS);
    return response.getValue();
  }

  @Override
  public void tag(SessionContext context, Id itemId, Id versionId, Id changeId, Tag tag) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<Void> response =
        getCollaborationAdaptor(context).tagItemVersion(context, itemId, versionId, changeId, tag);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_TAG);

    response = getStateAdaptor(context)
        .updateItemVersionModificationTime(context, Space.PRIVATE, itemId, versionId, new Date());
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_TAG);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    ItemVersionStatus status = getStatus(context, itemId, versionId);
    if (status.getSynchronizationStatus() != UP_TO_DATE) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_PUBLISH_NOT_ALLOWED, Module.ZDB, String.format
              (Messages.ITEM_VERSION_PUBLISH_NOT_ALLOWED, itemId, versionId,
                  status.getSynchronizationStatus()), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }

    Response<CorePublishResult> response =
        getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_PUBLISH);

    Response<Void> saveChangeResponse =
        saveMergeChange(context, Space.PUBLIC, itemId, versionId, response.getValue().getChange());
    ValidationUtil.validateResponse(saveChangeResponse, logger, ErrorCode.ZU_ITEM_VERSION_PUBLISH);
  }

  @Override
  public CoreMergeResult sync(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PUBLIC, itemId, versionId);

    Response<CoreMergeResult> response =
        getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_SYNC);

    if (response.getValue().isSuccess()) {
      Response<Void> saveChangeResponse =
          saveMergeChange(context, Space.PRIVATE, itemId, versionId,
              response.getValue().getChange());
      ValidationUtil.validateResponse(saveChangeResponse, logger, ErrorCode.ZU_ITEM_VERSION_SYNC);
    }
    return response.getValue();
  }

  @Override
  public CoreMergeResult merge(SessionContext context, Id itemId, Id versionId,
                               Id sourceVersionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    validateItemVersionExistence(context, Space.PRIVATE, itemId, sourceVersionId);

    Response<CoreMergeResult> response = getCollaborationAdaptor(context)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_MERGE);

    if (response.getValue().isSuccess()) {
      Response<Void> saveChangeResponse =
          saveMergeChange(context, Space.PRIVATE, itemId, versionId,
              response.getValue().getChange());
      ValidationUtil.validateResponse(saveChangeResponse, logger, ErrorCode.ZU_ITEM_VERSION_MERGE);
    }

    return response.getValue();
  }

  @Override
  public ItemVersionRevisions listRevision(SessionContext context, Id itemId,
                                                 Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<ItemVersionRevisions> response =
        getCollaborationAdaptor(context).listItemVersionRevisions(context, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_REVISION);
    return response.getValue();
  }

  @Override
  public void resetRevision(SessionContext context, Id itemId,
                            Id versionId, Id revisionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);

    Response<CoreMergeChange> response = getCollaborationAdaptor(context)
        .resetItemVersionRevision(context, itemId, versionId, revisionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_RESET_REVISION);

    Response<Void> saveChangeResponse =
        saveMergeChange(context, Space.PRIVATE, itemId, versionId, response.getValue());
    ValidationUtil
        .validateResponse(saveChangeResponse, logger, ErrorCode.ZU_ITEM_VERSION_RESET_REVISION);
  }

  @Override
  public void revertRevision(SessionContext context, Id itemId,
                            Id versionId, Id revisionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);

    Response<CoreMergeChange> response = getCollaborationAdaptor(context)
        .revertItemVersionRevision(context, itemId, versionId, revisionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_REVERT_REVISION);

    Response<Void> saveChangeResponse =
        saveMergeChange(context, Space.PRIVATE, itemId, versionId, response.getValue());
    ValidationUtil
        .validateResponse(saveChangeResponse, logger, ErrorCode.ZU_ITEM_VERSION_REVERT_REVISION);
  }

  @Override
  public void updateModificationTime(SessionContext context, Space space, Id itemId,
                                     Id versionId, Date modificationTime) {
    getStateAdaptor(context)
        .updateItemVersionModificationTime(context, space, itemId, versionId, modificationTime);
    getItemManager(context).updateModificationTime(context, itemId, modificationTime);
  }

  @Override
  public CoreItemVersionConflict getConflict(SessionContext context, Id itemId,
                                             Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);

    Response<CoreItemVersionConflict> response =
        getCollaborationAdaptor(context).getItemVersionConflict(context, itemId, versionId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ITEM_VERSION_GET_CONFLICT);
    return response.getValue();

  }

  private Response<Void> saveMergeChange(SessionContext context, Space space, Id itemId,
                                         Id versionId,
                                         CoreMergeChange mergeChange) {
    if (mergeChange.getChangedVersion() != null) {
      Response<Void> response =
          saveItemVersionChange(context, space, itemId, mergeChange.getChangedVersion());
      if (!response.isSuccessful()) {
        return response;
      }
    }
    if (mergeChange.getChangedElements() != null) {
      ElementContext elementContext = new ElementContext(itemId, versionId);
      mergeChange.getChangedElements().forEach(element ->
          indexingElementVisitor.visit(context, elementContext, space, element));
    }
    return new Response(Void.TYPE);
  }

  private Response<Void> saveItemVersionChange(SessionContext context, Space space, Id itemId,
                                               ItemVersionChange itemVersionChange) {
    ItemVersion itemVersion = itemVersionChange.getItemVersion();
    Date currentTime = new Date();

    switch (itemVersionChange.getAction()) {
      case CREATE:
        return getStateAdaptor(context)
            .createItemVersion(context, space, itemId, itemVersion.getBaseId(),
                itemVersion.getId(), itemVersion.getData(), currentTime);
      case UPDATE:
        return getStateAdaptor(context)
            .updateItemVersion(context, space, itemId, itemVersion.getId(), itemVersion.getData(),
                currentTime);
      default:
        throw new RuntimeException(String.format(Messages.UNSUPPORTED_VERSION_ACTION,
            itemId, itemVersion.getId(), itemVersionChange.getAction()));
    }
  }

  private void validateItemVersionExistence(SessionContext context, Space space, Id itemId,
                                            Id versionId) {
    if (!isExist(context, space, itemId, versionId)) {
      validateItemExistence(context, itemId);
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_DOES_NOT_EXIST, Module.ZDB, String.format(Messages
              .ITEM_VERSION_NOT_EXIST, itemId, versionId, space), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  private void validateItemExistence(SessionContext context, Id itemId) {
    if (!getItemManager(context).isExist(context, itemId)) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_DOES_NOT_EXIST, Module.ZDB,
          String.format(Messages.ITEM_NOT_EXIST, itemId), null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
