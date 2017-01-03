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

package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ElementAdaptor;
import org.amdocs.tsuzammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.tsuzammen.core.api.item.ElementManager;
import org.amdocs.tsuzammen.core.api.item.ElementManagerFactory;
import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SearchCriteria;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;
import java.util.stream.Collectors;

public class ElementAdaptorImpl implements ElementAdaptor {


  @Override
  public Element get(SessionContext context, ElementContext elementContext, Id elementId,
                     SearchCriteria searchCriteria) {
    return getElement(
        getElementManager(context).get(context, elementContext, elementId, searchCriteria));
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext, Id elementId,
                             SearchCriteria searchCriteria) {
    return getElementManager(context).getInfo(context, elementContext, elementId, searchCriteria);
  }

  @Override
  public Element update(SessionContext context, ElementContext elementContext,
                        Element element, String message) {
    return getElement(
        getElementManager(context).update(context, elementContext, getCoreElement(element),
            message));
  }

  @Override
  public void delete(SessionContext context, ElementContext elementContext, Id elementId,
                     String message) {
  }

  private Element getElement(CoreElement coreElement) {
    Element element = (Element) CommonMethods.newInstance(coreElement.getElementImplClass());
    element.setElementId(coreElement.getElementId());
    element.setInfo(coreElement.getInfo());
    element.setRelations(coreElement.getRelations());

    element.setData(coreElement.getData());
    element.setSearchData(coreElement.getSearchData());
    element.setVisualization(coreElement.getVisualization());

    element.setSubElements(getElements(coreElement.getSubElements()));
    return element;
  }

  private Collection<Element> getElements(Collection<CoreElement> coreElements) {
    return coreElements == null
        ? null
        : coreElements.stream().map(coreElement ->
            getElement(coreElement)).collect(Collectors.toList());
  }

  private CoreElement getCoreElement(Element element) {
    CoreElement coreElement = new CoreElement();
    coreElement.setElementId(element.getElementId());
    coreElement.setInfo(element.getInfo());
    coreElement.setRelations(element.getRelations());

    coreElement.setData(element.getData());
    coreElement.setSearchData(element.getSearchData());
    coreElement.setVisualization(element.getVisualization());

    coreElement.setSubElements(getCoreElements(element.getSubElements()));

    return coreElement;
  }

  private Collection<CoreElement> getCoreElements(Collection<Element> elements) {
    return elements == null
        ? null
        : elements.stream().map(element -> getCoreElement(element)).collect(Collectors.toList());
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
