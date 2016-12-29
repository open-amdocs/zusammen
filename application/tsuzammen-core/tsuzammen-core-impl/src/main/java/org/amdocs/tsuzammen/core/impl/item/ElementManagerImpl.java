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
import org.amdocs.tsuzammen.commons.datatypes.CollaborationNamespace;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.Namespace;
import org.amdocs.tsuzammen.commons.datatypes.SearchCriteria;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementNamespace;
import org.amdocs.tsuzammen.core.api.item.ElementManager;
import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.core.impl.Messages;

import java.net.URI;
import java.net.URISyntaxException;

public class ElementManagerImpl implements ElementManager {

  private static final URI emptyNamespace = createNamespace("");

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId, SearchCriteria searchCriteria) {
    return null;
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext,
                             Id elementId, SearchCriteria searchCriteria) {
    return getStateAdaptor(context).get(context, elementContext, elementId);
  }

  @Override
  public CoreElement update(SessionContext context, ElementContext elementContext,
                            CoreElement element, String message) {

    // TODO error handling
    validateItemVersionExistence(context, elementContext.getItemId(),
        elementContext.getVersionId());

    updateRecursively(context, elementContext, new Namespace(), element);
    return null;
  }

  private void updateRecursively(SessionContext context, ElementContext elementContext,
                                 Namespace parentNamespace, CoreElement element) {
    if (element.getElementId() == null) {
      createRecursively(context, elementContext, parentNamespace, element);
    } else {
      ElementNamespace elementNamespace =
          getStateAdaptor(context).getNamespace(context, elementContext, element.getElementId());
      if (elementNamespace == null) {
        //error - no such element
      }

      save(context, elementContext, elementNamespace.getCollaborationNamespace(), element);

      element.getSubElements().entrySet().forEach(subElementEntry ->
          updateRecursively(context, elementContext, elementNamespace.getNamespace(),
              subElementEntry.getValue()));
    }
  }

  private void createRecursively(SessionContext context, ElementContext elementContext,
                                 Namespace parentNamespace, CoreElement element) {
    element.setElementId(new Id());
    Namespace namespace = create(context, elementContext, parentNamespace, element);

    element.getSubElements().entrySet().forEach(subElementEntry ->
        createRecursively(context, elementContext, namespace, subElementEntry.getValue()));
  }

  private Namespace create(SessionContext context, ElementContext elementContext,
                           Namespace parentNamespace, CoreElement element) {
    CollaborationNamespace collaborationNamespace = getCollaborationAdaptor(context)
        .createElement(context, elementContext, parentNamespace, element);
    Namespace namespace = new Namespace(parentNamespace, element.getElementId());
    ElementNamespace elementNamespace = new ElementNamespace(namespace, collaborationNamespace);
    getStateAdaptor(context).create(context, elementContext, elementNamespace,
        getElementInfo(element));

    return namespace;
  }

  private void save(SessionContext context, ElementContext elementContext,
                    CollaborationNamespace collaborationNamespace, CoreElement element) {
    getCollaborationAdaptor(context)
        .saveElement(context, elementContext, collaborationNamespace, element);
    getStateAdaptor(context).save(context, elementContext, getElementInfo(element));
  }

  private ElementInfo getElementInfo(CoreElement coreElement) {
    ElementInfo elementInfo = new ElementInfo();
    elementInfo.setId(coreElement.getElementId());
    elementInfo.setInfo(coreElement.getInfo());
    elementInfo.setRelations(coreElement.getRelations());
    return elementInfo;
  }


  private static URI createNamespace(String namespace) {
    try {
      return new URI(namespace);
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
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
