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
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.zusammen.core.api.item.ItemManager;
import org.amdocs.zusammen.core.api.item.ItemManagerFactory;
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
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

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {

  @Override
  public Collection<ItemVersion> list(SessionContext context, Space space, Id itemId) {
    validateItemExistence(context, itemId);
    return getStateAdaptor(context).listItemVersions(context, space, itemId);
  }

  @Override
  public boolean isExist(SessionContext context, Space space, Id itemId, Id versionId) {
    validateItemExistence(context, itemId);
    return getStateAdaptor(context).isItemVersionExist(context, space, itemId, versionId);
  }

  @Override
  public ItemVersion get(SessionContext context, Space space, Id itemId, Id versionId) {
    validateItemExistence(context, itemId);
    return getStateAdaptor(context).getItemVersion(context, space, itemId, versionId);
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                   ItemVersionData data) {
    if(baseVersionId != null) {
      validateItemVersionExistence(context, Space.PRIVATE, itemId, baseVersionId);
    }
    Id versionId = new Id();
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);

    getStateAdaptor(context)
        .createItemVersion(context, Space.PRIVATE, itemId, baseVersionId, versionId, data);

    return versionId;
  }

  @Override
  public void update(SessionContext context, Id itemId, Id versionId, ItemVersionData data) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    getCollaborationAdaptor(context).updateItemVersion(context, itemId, versionId, data);
    getStateAdaptor(context).updateItemVersion(context, Space.PRIVATE, itemId, versionId, data);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, Space.PRIVATE, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    CorePublishResult publishResult =
        getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);

    saveMergeChange(context, Space.PUBLIC, itemId, versionId, publishResult.getChange());
  }


  @Override
  public CoreMergeResult sync(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, Space.PUBLIC, itemId, versionId);
    CoreMergeResult syncResult =
        getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);

    if (syncResult.isSuccess()) {
      saveMergeChange(context, Space.PRIVATE, itemId, versionId, syncResult.getChange());
    }

    return syncResult;
  }

  @Override
  public CoreMergeResult merge(SessionContext context, Id itemId, Id versionId,
                               Id sourceVersionId) {
    validateItemVersionExistence(context, Space.PRIVATE, itemId, versionId);
    validateItemVersionExistence(context, Space.PRIVATE, itemId, sourceVersionId);

    CoreMergeResult mergeResult = getCollaborationAdaptor(context)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);

    if (mergeResult.isSuccess()) {
      saveMergeChange(context, Space.PRIVATE, itemId, versionId, mergeResult.getChange());
    }

    return mergeResult;
  }

  private void saveMergeChange(SessionContext context, Space space, Id itemId, Id versionId,
                               CoreMergeChange mergeChange) {
    if(mergeChange.getChangedVersion()!= null)
      saveItemVersionChange(context, space, itemId, mergeChange.getChangedVersion());
    if(mergeChange.getChangedElements() != null)
      saveElementChanges(context, space, itemId, versionId, mergeChange.getChangedElements());
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

  private void saveElementChanges(SessionContext context, Space space, Id itemId, Id versionId,
                                  Collection<CoreElement> elements) {
    ElementContext elementContext = new ElementContext(itemId, versionId);
    elements.stream().forEach(element -> {
      switch (element.getAction()) {
        case CREATE:
          createElement(context, elementContext, space, element);
          break;
        case UPDATE:
          updateElement(context, elementContext, space, element);
          break;
        case DELETE:
          deleteElement(context, elementContext, space, element);
          break;
        default:
          throw new RuntimeException(String.format(Messages.UNSUPPORTED_ELEMENT_ACTION,
              elementContext.getItemId(), elementContext.getVersionId(), element.getId(),
              element.getAction()));
      }
    });
  }

  private void createElement(SessionContext context, ElementContext elementContext,
                             Space space, CoreElement element) {
    ElementStateAdaptorFactory.getInstance().createInterface(context)
        .create(context, elementContext, space, element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .createElement(context, elementContext, space, element);
  }

  private void updateElement(SessionContext context, ElementContext elementContext,
                             Space space, CoreElement element) {
    ElementStateAdaptorFactory.getInstance().createInterface(context)
        .update(context, elementContext, space, element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .updateElement(context, elementContext, space, element);
  }

  private void deleteElement(SessionContext context, ElementContext elementContext,
                             Space space, CoreElement element) {
    ElementStateAdaptorFactory.getInstance().createInterface(context)
        .delete(context, elementContext, space, element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .deleteElement(context, elementContext, space, element);
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
