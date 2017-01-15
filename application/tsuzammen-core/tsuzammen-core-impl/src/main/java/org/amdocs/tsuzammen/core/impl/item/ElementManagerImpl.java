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
import org.amdocs.tsuzammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.tsuzammen.core.api.item.ElementManager;
import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.core.impl.Messages;
import org.amdocs.tsuzammen.datatypes.FetchCriteria;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.Namespace;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.Space;
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public class ElementManagerImpl implements ElementManager {
  @Override
  public Collection<ElementInfo> list(SessionContext context, ElementContext elementContext,
                                      Id elementId) {
    return getStateAdaptor(context).list(context, elementContext, elementId);
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext,
                             Id elementId, FetchCriteria fetchCriteria) {
    return getStateAdaptor(context).get(context, elementContext, elementId, fetchCriteria);
  }

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId, FetchCriteria fetchCriteria) {
    return null;
  }

  @Override
  public void save(SessionContext context, ElementContext elementContext,
                   CoreElement element, String message) {
    // TODO error handling
    validateItemVersionExistence(context, elementContext.getItemId(),
        elementContext.getVersionId());

    Namespace parentNamespace = isCreateRootElement(element)
        ? Namespace.EMPTY_NAMESPACE
        : null; // the namespace/parentId of the element itself will be retrieved later on
    traverse(context, elementContext, parentNamespace, element);
  }

  private boolean isCreateRootElement(CoreElement element) {
    return element.getAction() == ElementAction.CREATE;
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getSearchIndexAdaptor(context).search(context, searchCriteria);
  }

  private void traverse(SessionContext context, ElementContext elementContext,
                        Namespace parentNamespace, CoreElement element) {
    Namespace namespace;
    switch (element.getAction()) {
      case CREATE:
        namespace = create(context, elementContext, parentNamespace, element);
        break;
      case UPDATE:
        namespace = update(context, elementContext, parentNamespace, element);
        break;
      case DELETE:
        namespace = delete(context, elementContext, parentNamespace, element);
        break;
      case IGNORE:
        namespace = ignore(context, elementContext, parentNamespace, element);
        break;
      default:
        throw new RuntimeException(String.format(Messages.UNSUPPORTED_ELEMENT_ACTION,
            elementContext.getItemId(), elementContext.getVersionId(), element.getId(),
            element.getAction()));
    }

    element.getSubElements().forEach(subElement ->
        traverse(context, elementContext, namespace, subElement));
  }

  private Namespace create(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace,
                           CoreElement element) {
    element.setId(new Id());
    // todo consider refactoring the set of the element id as the parentId of the sub elements.
    // This create action should act only on the current elemnt and should not access any other
    // elements in the hierarchy.
    element.getSubElements().forEach(subElement -> subElement.setParentId(element.getId()));

    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).createElement(context, elementContext, namespace, element);
    getStateAdaptor(context).create(context, ElementInfoConvertor.getElementInfo(elementContext,
        element));
    getSearchIndexAdaptor(context).createElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace update(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).updateElement(context, elementContext, namespace, element);
    getStateAdaptor(context).update(context, ElementInfoConvertor.getElementInfo(elementContext,
        element));
    getSearchIndexAdaptor(context).updateElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace delete(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element);

    getCollaborationAdaptor(context).deleteElement(context, elementContext, namespace, element);
    getStateAdaptor(context).delete(context, ElementInfoConvertor.getElementInfo(elementContext,
        element));
    getSearchIndexAdaptor(context).deleteElement(context, elementContext, element, Space.PRIVATE);

    return namespace;
  }

  private Namespace ignore(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    return getNamespace(context, elementContext, parentNamespace, element);
  }

  private Namespace getNamespace(SessionContext context, ElementContext elementContext,
                                 Namespace parentNamespace, CoreElement element) {
    if (!isSaveRequestTopElement(parentNamespace)) {
      return new Namespace(parentNamespace, element.getId());
    }

    ElementInfo elementInfo =
        getStateAdaptor(context).get(context, elementContext, element.getId(), null);
    element.setParentId(elementInfo.getParentId());
    return elementInfo.getNamespace();
  }

  private boolean isSaveRequestTopElement(Namespace parentNamespace) {
    return parentNamespace == null;
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
