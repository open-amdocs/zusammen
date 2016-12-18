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
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.ContentNamespace;
import org.amdocs.tsuzammen.commons.datatypes.EntityNamespace;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Content;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersionData;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ItemVersionManagerImpl implements ItemVersionManager {

  @Override
  public String create(SessionContext context, String itemId, String baseVersionId,
                       Info versionInfo) {
    String versionId = new String(CommonMethods.nextUUID());
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void saveInfo(SessionContext context, String itemId, String versionId, Info versionInfo) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
    getStateAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
  }

  @Override
  public void saveData(SessionContext context, String itemId, String versionId,
                       ItemVersionData versionData,
                       List<ContentNamespace> contentsToDelete,
                       List<EntityNamespace> entitiesToDelete,
                       String message) {
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

  @Override
  public void delete(SessionContext context, String itemId, String versionId) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, String itemId, String versionId, String message) {
    getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    getStateAdaptor(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void sync(SessionContext context, String itemId, String versionId) {
    getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    getStateAdaptor(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revert(SessionContext context, String itemId, String versionId,
                     String targetRevisionId) {

  }

  @Override
  public Collection<ItemVersion> get(SessionContext context, String itemId, String versionId) {
    // TODO: 12/4/2016 add filter
    return null;
  }

  @Override
  public Collection<ItemVersion> getInfo(SessionContext context, String itemId, String versionId) {
    return null;
  }

  private void validateExistence(SessionContext context, String itemId, String versionId) {
    getStateAdaptor(context).validateItemVersionExistence(context, itemId, versionId);
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
