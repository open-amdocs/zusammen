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

package org.amdocs.zusammen.core.impl.item;


import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.zusammen.core.api.item.ItemManager;
import org.amdocs.zusammen.core.api.item.ItemManagerFactory;
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.types.CoreMergeChange;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.core.impl.Messages;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionChange;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {
  private ElementVisitor indexingElementVisitor = IndexingElementVisitor.init();

  @Override
  public Collection<ItemVersion> list(SessionContext context, Space space, Id itemId) {
    validateItemExistence(context, itemId);
    Response<Collection<ItemVersion>> response;
    response = getStateAdaptor(context).listItemVersions(context, space, itemId);
    if (response.isSuccessful()) {
      return response.getValue();
    } else {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_LIST, Module.ZUS, null, response
          .getReturnCode());
    }
  }

  @Override
  public boolean isExist(SessionContext context, Space space, Id itemId, Id versionId) {
    validateItemExistence(context, itemId);
    Response<Boolean> response;
    response = getStateAdaptor(context).isItemVersionExist(context, space, itemId, versionId);
    if (response.isSuccessful()) {
      return response.getValue();
    } else {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_IS_EXIST, Module.ZUS, null, response
          .getReturnCode());
    }
  }

  @Override
  public ItemVersion get(SessionContext context, Space space, Id itemId, Id versionId) {
    validateItemExistence(context, itemId);
    Response<ItemVersion> response;
    response = getStateAdaptor(context).getItemVersion(context, space, itemId, versionId);
    if (response.isSuccessful()) {
      return response.getValue();
    } else {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_GET, Module.ZUS, null, response
          .getReturnCode());
    }
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                   ItemVersionData data) {
    if (baseVersionId != null) {
      validateItemVersionExistence(context, Space.PRIVATE, itemId, baseVersionId);
    }
    Id versionId = new Id();
    Response response;

    response = getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_CREATE, Module.ZUS, null, response
          .getReturnCode());
    }

    response = getStateAdaptor(context)
        .createItemVersion(context, Space.PRIVATE, itemId, baseVersionId, versionId, data);

    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_CREATE, Module.ZUS, null, response
          .getReturnCode());
    }
    return versionId;
  }

  @Override
  public void update(SessionContext context, Id itemId, Id versionId, ItemVersionData data) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response response;
    response = getCollaborationAdaptor(context).updateItemVersion(context, itemId, versionId, data);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_UPDATE, Module.ZUS, null, response
          .getReturnCode());
    }
    response = getStateAdaptor(context).updateItemVersion(context, Space.PRIVATE, itemId,
        versionId, data);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_UPDATE, Module.ZUS, null, response
          .getReturnCode());
    }
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response response = getCollaborationAdaptor(context).deleteItemVersion(context, itemId,
        versionId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_DELETE, Module.ZUS, null, response
          .getReturnCode());
    }
    response = getStateAdaptor(context).deleteItemVersion(context, Space.PRIVATE, itemId,
        versionId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_DELETE, Module.ZUS, null, response
          .getReturnCode());
    }
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<CorePublishResult> response;
    response =
        getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_PUBLISH, Module.ZUS, null, response
          .getReturnCode());
    }

    saveMergeChange(context, Space.PUBLIC, itemId, versionId, response.getValue().getChange());
  }


  @Override
  public CoreMergeResult sync(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PUBLIC, itemId, versionId);


    Response<CoreMergeResult> response;
    response = getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_SYNC, Module.ZUS, null, response
          .getReturnCode());
    }

    if (response.getValue().isSuccess()) {
      saveMergeChange(context, Space.PRIVATE, itemId, versionId, response.getValue().getChange());
    }

    return response.getValue();
  }

  @Override
  public CoreMergeResult merge(SessionContext context, Id itemId, Id versionId,
                               Id sourceVersionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    validateItemVersionExistence(context, Space.PRIVATE, itemId, sourceVersionId);


    Response<CoreMergeResult> response;
    response = getCollaborationAdaptor(context)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_MERGE, Module.ZUS, null, response
          .getReturnCode());
    }

    if (response.getValue().isSuccess()) {
      saveMergeChange(context, Space.PRIVATE, itemId, versionId, response.getValue().getChange());
    }

    return response.getValue();


  }

  @Override
  public ItemVersionHistory listHistory(SessionContext context, Id itemId,
                                        Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    Response<ItemVersionHistory> response;

    response = getCollaborationAdaptor(context).listItemVersionHistory(context, itemId, versionId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_HISTORY, Module.ZUS, null, response
          .getReturnCode());
    }
    return response.getValue();
  }

  @Override
  public void revertHistory(SessionContext context, Id itemId,
                            Id versionId, Id changeId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);

    Response<CoreMergeChange> response;
    response = getCollaborationAdaptor(context).revertItemVersionHistory(context, itemId, versionId,
        changeId);
    if (!response.isSuccessful()) {
      throw new ZusammenException(ErrorCode.ZU_ITEM_VERSION_REVERT_HISTORY, Module.ZUS, null,
          response
              .getReturnCode());
    }
    saveMergeChange(context, Space.PRIVATE, itemId, versionId, response.getValue());
  }

  private void saveMergeChange(SessionContext context, Space space, Id itemId, Id versionId,
                               CoreMergeChange mergeChange) {
    if (mergeChange.getChangedVersion() != null) {
      saveItemVersionChange(context, space, itemId, mergeChange.getChangedVersion());
    }
    if (mergeChange.getChangedElements() != null) {
      ElementContext elementContext = new ElementContext(itemId, versionId);
      mergeChange.getChangedElements().stream().forEach(element ->
          indexingElementVisitor.visit(context, elementContext, space, element));
    }
  }

  private void saveItemVersionChange(SessionContext context, Space space, Id itemId,
                                     ItemVersionChange itemVersionChange) {
    ItemVersion itemVersion = itemVersionChange.getItemVersion();
    switch (itemVersionChange.getAction()) {
      case CREATE:
        getStateAdaptor(context)
            .createItemVersion(context, space, itemId, itemVersion.getBaseId(),
                itemVersion.getId(), itemVersion.getData());
        break;
      case UPDATE:
        getStateAdaptor(context)
            .updateItemVersion(context, space, itemId, itemVersion.getId(), itemVersion.getData());
        break;
      default:
        throw new RuntimeException(String.format(Messages.UNSUPPORTED_VERSION_ACTION,
            itemId, itemVersion.getId(), itemVersionChange.getAction()));
    }
  }

  private void validateItemVersionExistence(SessionContext context, Space space, Id itemId,
                                            Id versionId) {
    if (!isExist(context, space, itemId, versionId)) {
      validateItemExistence(context, itemId);
      throw new RuntimeException(
          String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space));
    }
  }

  private void validateItemExistence(SessionContext context, Id itemId) {
    if (!getItemManager(context).isExist(context, itemId)) {
      throw new RuntimeException(String.format(Messages.ITEM_NOT_EXIST, itemId));
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
