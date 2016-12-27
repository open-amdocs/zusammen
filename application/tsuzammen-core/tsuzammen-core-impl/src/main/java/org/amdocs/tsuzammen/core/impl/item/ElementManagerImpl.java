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
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SearchCriteria;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Element;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementResponse;
import org.amdocs.tsuzammen.core.api.item.ElementManager;

import java.net.URI;
import java.net.URISyntaxException;

public class ElementManagerImpl implements ElementManager {

  private static final URI emptyNamespace = createNamespace("");

  @Override
  public ElementResponse get(SessionContext context, ElementContext elementContext,
                             Id elementId,
                             SearchCriteria searchCriteria) {
    return null;
  }

  @Override
  public ElementResponse save(SessionContext context, ElementContext elementContext,
                              Element element,
                              String message) {
    if (element.getElementId() == null) {
      create(context, elementContext, emptyNamespace, element);
    }
    return null;
  }

  private ElementResponse create(SessionContext context, ElementContext elementContext,
                                 URI namespace, Element element) {
    element.setElementId(new Id());
    getCollaborationAdaptor(context).createElement(context, elementContext, namespace, element);
    return null;
  }


  private static URI createNamespace(String namespace) {
    try {
      return new URI(namespace);
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
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
