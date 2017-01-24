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
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.impl.Messages;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public class ElementManagerImpl implements ElementManager {
  @Override
  public Collection<ElementInfo> list(SessionContext context, ElementContext elementContext,
                                      Id elementId) {
    return getStateAdaptor(context).list(context, elementContext, elementId);
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext,
                             Id elementId) {
    return getStateAdaptor(context).get(context, elementContext, elementId);
  }

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId) {
    Namespace namespace = getInfo(context, elementContext, elementId).getNamespace();
    return getCollaborationAdaptor(context)
        .getElement(context, elementContext, namespace, elementId);
  }

  @Override
  public void save(SessionContext context, ElementContext elementContext,
                   CoreElement element, String message) {
    // TODO error handling
    validateItemVersionExistence(context, elementContext.getItemId(),
        elementContext.getVersionId());

    if (element.getAction() == Action.CREATE) {
      setElementHierarchyPosition(element, Namespace.ROOT_NAMESPACE, null);
    } else {
      ElementInfo elementInfo = getInfo(context, elementContext, element.getId());
      setElementHierarchyPosition(element, elementInfo.getNamespace(), elementInfo.getParentId());
    }

    saveRecursively(context, elementContext, element);
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getSearchIndexAdaptor(context).search(context, searchCriteria);
  }

  private void setElementHierarchyPosition(CoreElement element, Namespace namespace, Id parentId) {
    element.setParentId(parentId);
    element.setNamespace(namespace);
  }

  private void saveRecursively(SessionContext context, ElementContext elementContext,
                               CoreElement element) {
    switch (element.getAction()) {
      case CREATE:
        create(context, elementContext, element);
        break;
      case UPDATE:
        update(context, elementContext, element);
        break;
      case DELETE:
        delete(context, elementContext, element);
        break;
      case IGNORE:
        break;
      default:
        throw new RuntimeException(String.format(Messages.UNSUPPORTED_ELEMENT_ACTION,
            elementContext.getItemId(), elementContext.getVersionId(), element.getId(),
            element.getAction()));
    }

    Namespace subElementsNamespace = new Namespace(element.getNamespace(), element.getId());
    element.getSubElements().forEach(subElement -> {
      setElementHierarchyPosition(subElement, subElementsNamespace, element.getId());
      saveRecursively(context, elementContext, subElement);
    });
  }

  private void create(SessionContext context, ElementContext elementContext,
                      CoreElement element) {
    element.setId(new Id());
    getCollaborationAdaptor(context).createElement(context, elementContext, element);
    createDescriptor(context, elementContext, Space.PRIVATE, element);
  }

  private void createDescriptor(SessionContext context, ElementContext elementContext, Space space,
                                CoreElement element) {
    getStateAdaptor(context).create(context, elementContext, space, element);
    getSearchIndexAdaptor(context).createElement(context, elementContext, space, element);
  }

  private void update(SessionContext context, ElementContext elementContext,
                      CoreElement element) {
    getCollaborationAdaptor(context).updateElement(context, elementContext, element);
    updateDescriptor(context, elementContext, Space.PRIVATE, element);
  }

  private void updateDescriptor(SessionContext context, ElementContext elementContext, Space space,
                                CoreElement element) {
    getStateAdaptor(context).update(context, elementContext, space, element);
    getSearchIndexAdaptor(context).updateElement(context, elementContext, space, element);
  }

  private void delete(SessionContext context, ElementContext elementContext,
                      CoreElement element) {
    getCollaborationAdaptor(context).deleteElement(context, elementContext, element);
    deleteDescriptor(context, elementContext, Space.PRIVATE, element);
  }

  private void deleteDescriptor(SessionContext context, ElementContext elementContext, Space space,
                                CoreElement element) {
    getStateAdaptor(context).delete(context, elementContext, space, element);
    getSearchIndexAdaptor(context).deleteElement(context, elementContext, space, element);
  }

  private void validateItemVersionExistence(SessionContext context, Id itemId, Id versionId) {
    String space = context.getUser().getUserName();
    if (!getItemVersionStateAdaptor(context).isItemVersionExist(context, itemId, versionId)) {
      String message = getItemStateAdaptor(context).isItemExist(context, itemId)
          ? String
          .format(Messages.ITEM_VERSION_NOT_EXIST, itemId.toString(), versionId.toString(), space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);
      throw new RuntimeException(message);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ElementStateAdaptor getStateAdaptor(SessionContext context) {
    return ElementStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected SearchIndexAdaptor getSearchIndexAdaptor(SessionContext context) {
    return SearchIndexAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getItemVersionStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
