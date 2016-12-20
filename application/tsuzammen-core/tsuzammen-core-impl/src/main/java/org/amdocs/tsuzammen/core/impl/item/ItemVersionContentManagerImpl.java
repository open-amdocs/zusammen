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

package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionContentStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionContentStateAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.ContentNamespace;
import org.amdocs.tsuzammen.commons.datatypes.EntityNamespace;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Content;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersionData;
import org.amdocs.tsuzammen.core.api.item.ItemVersionContentManager;
import org.amdocs.tsuzammen.core.impl.Messages;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class ItemVersionContentManagerImpl implements ItemVersionContentManager {

  @Override
  public void save(SessionContext context, String itemId, String versionId,
                   ItemVersionData versionData, List<ContentNamespace> contentsToDelete,
                   List<EntityNamespace> entitiesToDelete, String message) {
    validateItemVersionExistence(context, itemId, versionId);
    // TODO: 12/20/2016 instead of executing an action (create/save/delete) on an entity during
    // the recursion - collect the actions and do it all together at the end. will enable doing it in parallel.
    saveContents(context, itemId, versionId, null, null, versionData.getContents());
    deleteContents(context, itemId, versionId, contentsToDelete);
  }


  private void saveContents(SessionContext context, String itemId, String versionId,
                            URI parentNamespace, String parentEntityId,
                            Map<String, Content> contents) {
    contents.entrySet().forEach(contentEntry -> {
      saveContent(context, itemId, versionId, parentNamespace, parentEntityId,
          contentEntry.getKey(),
          contentEntry.getValue());
    });
  }

  private void deleteContents(SessionContext context, String itemId, String versionId,
                              List<ContentNamespace> contentsToDelete) {

  }

  private void saveContent(SessionContext context, String itemId, String versionId,
                           URI parentNamespace, String parentEntityId, String contentName,
                           Content content) {
    URI namespace = createNamespace(parentNamespace, parentEntityId, contentName);
    content.getEntities().forEach(entity ->
        actOnEntity(context, itemId, versionId, namespace, entity, content.getDataFormat()));
  }

  private void actOnEntity(SessionContext context, String itemId, String versionId, URI namespace,
                           Entity entity, Format dataFormat) {
    if (entity.getId() == null) {
      createEntity(context, itemId, versionId, namespace, entity, dataFormat);
    } else {
      saveEntity(context, itemId, versionId, namespace, entity, dataFormat);
    }

    saveContents(context, itemId, versionId, namespace, entity.getId(), entity.getContents());
  }

  private void createEntity(SessionContext context, String itemId, String versionId, URI namespace,
                            Entity entity, Format dataFormat) {
    entity.setId(new String(CommonMethods.nextUUID()));

    getCollaborationAdaptor(context).createItemVersionEntity(context, itemId, versionId,
        namespace, entity, dataFormat);
    getStateAdaptor(context).createItemVersionEntity(context, itemId, versionId,
        namespace, entity);
  }

  private void saveEntity(SessionContext context, String itemId, String versionId, URI namespace,
                          Entity entity, Format dataFormat) {
    validateEntityExistence(context, itemId, versionId, namespace, entity.getId());
    getCollaborationAdaptor(context).saveItemVersionEntity(context, itemId, versionId,
        namespace, entity, dataFormat);
    getStateAdaptor(context).saveItemVersionEntity(context, itemId, versionId,
        namespace, entity);
  }

  private URI createNamespace(URI parentNamespace, String entityId, String contentName) {
    String namespace = parentNamespace == null && entityId == null
        ? contentName
        : parentNamespace.toString() + "/" + entityId + "/" + contentName;
    try {
      return new URI(namespace);
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void validateItemVersionExistence(SessionContext context, String itemId,
                                            String versionId) {
    String space = context.getUser().getUserName();
    if (!getItemVersionStateAdaptor(context).isItemVersionExist(context, itemId, versionId)) {
      String message = getItemStateAdaptor(context).isItemExist(context, itemId)
          ? String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);// TODO: 12/20/2016 space!
      throw new RuntimeException(message);
    }
  }

  private void validateEntityExistence(SessionContext context, String itemId, String versionId,
                                       URI namespace, String entityId) {
    if (!getStateAdaptor(context)
        .isItemVersionEntityExist(context, itemId, versionId, namespace, entityId)) {
      throw new RuntimeException(String.format(
          Messages.ITEM_VERSION_ENTITY_NOT_EXIST, itemId, versionId, namespace, entityId, context
              .getUser().getUserName())); // TODO: 12/20/2016 space!
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionContentStateAdaptor getStateAdaptor(SessionContext context) {
    return ItemVersionContentStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getItemVersionStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
