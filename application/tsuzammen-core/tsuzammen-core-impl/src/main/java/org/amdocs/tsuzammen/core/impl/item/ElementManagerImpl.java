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
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;

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

    Namespace parentNamespace =
        element.getAction() == ElementAction.CREATE ? new Namespace() : null;
    traverse(context, elementContext, parentNamespace, element);
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
        throw new RuntimeException(
            String.format("Action %s is not supported", element.getAction()));
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

    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element.getId());

    getCollaborationAdaptor(context)
        .createElement(context, elementContext, namespace, element);

    getStateAdaptor(context)
        .create(context, elementContext, namespace, getElementInfo(element));

    return namespace;
  }

  private Namespace update(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element.getId());

    getCollaborationAdaptor(context)
        .saveElement(context, elementContext, namespace, element);

    getStateAdaptor(context).save(context, elementContext, getElementInfo(element));

    return namespace;
  }

  private Namespace delete(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    Namespace namespace = getNamespace(context, elementContext, parentNamespace, element.getId());

    getCollaborationAdaptor(context)
        .deleteElement(context, elementContext, namespace, element);

    getStateAdaptor(context).delete(context, elementContext, getElementInfo(element));

    return namespace;
  }

  private Namespace ignore(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    return getNamespace(context, elementContext, parentNamespace, element.getId());
  }

  private Namespace getNamespace(SessionContext context, ElementContext elementContext,
                                 Namespace parentNamespace, Id elementId) {
    return parentNamespace == null
        ? getStateAdaptor(context).getNamespace(context, elementContext, elementId)
        : new Namespace(parentNamespace, elementId);
  }

  private ElementInfo getElementInfo(CoreElement coreElement) {
    ElementInfo elementInfo = new ElementInfo(coreElement.getId());
    elementInfo.setParentId(coreElement.getParentId());
    elementInfo.setInfo(coreElement.getInfo());
    elementInfo.setRelations(coreElement.getRelations());
    return elementInfo;
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

  protected ItemVersionStateAdaptor getItemVersionStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
