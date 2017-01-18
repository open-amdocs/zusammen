/*
 * Copyright © 2016 European Support Limited
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
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.core.impl.Messages;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersion;

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {

  @Override
  public Collection<ItemVersion> list(SessionContext context, Id itemId) {
    return getStateAdaptor(context).listItemVersions(context, itemId);
  }

  @Override
  public ItemVersion get(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    return getStateAdaptor(context).getItemVersion(context, itemId, versionId);
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                   Info versionInfo) {
    Id versionId = new Id();
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void save(SessionContext context, Id itemId, Id versionId, Info versionInfo) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
    getStateAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String
      message) {
    validateItemVersionExistence(context, itemId, versionId);
    CorePublishResult publishResult =
        getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    saveElements(context, itemId, versionId, Space.PUBLIC, publishResult.getChangedElements());
  }

  @Override
  public CoreMergeResult sync(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    CoreMergeResult syncResult =
        getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);

    if (syncResult.isSuccess()) {
      saveElements(context, itemId, versionId, Space.PRIVATE, syncResult.getChangedElements());
    }

    return syncResult;
  }

  @Override
  public CoreMergeResult merge(SessionContext context, Id itemId, Id versionId,
                               Id sourceVersionId) {
    validateItemVersionExistence(context, itemId, versionId);
    CoreMergeResult mergeResult = getCollaborationAdaptor(context)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);

    if (mergeResult.isSuccess()) {
      saveElements(context, itemId, versionId, Space.PRIVATE, mergeResult.getChangedElements());
    }

    return mergeResult;
  }

  @Override
  public void revert(SessionContext context, Id itemId, Id versionId,
                     String targetRevisionId) {
  }

  private void saveElements(SessionContext context, Id itemId, Id versionId, Space space,
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
        .create(context, elementContext, space, /* todo fix this! --> */new Namespace(), element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .createElement(context, elementContext, Space.PRIVATE, element);
  }

  private void updateElement(SessionContext context, ElementContext elementContext,
                             Space space, CoreElement element) {
    ElementStateAdaptorFactory.getInstance().createInterface(context)
        .update(context, elementContext, space, element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .updateElement(context, elementContext, Space.PRIVATE, element);
  }

  private void deleteElement(SessionContext context, ElementContext elementContext,
                             Space space, CoreElement element) {
    ElementStateAdaptorFactory.getInstance().createInterface(context)
        .delete(context, elementContext, space, element);
    SearchIndexAdaptorFactory.getInstance().createInterface(context)
        .deleteElement(context, elementContext, Space.PRIVATE, element);
  }

  private void validateItemVersionExistence(SessionContext context, Id itemId,
                                            Id versionId) {
    String space = context.getUser().getUserName();
    if (!getStateAdaptor(context).isItemVersionExist(context, itemId, versionId)) {
      String message = getItemStateAdaptor(context).isItemExist(context, itemId)
          ? String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);// TODO: 12/20/2016 space!
      throw new RuntimeException(message);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
