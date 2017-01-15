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
import org.amdocs.tsuzammen.adaptor.inbound.impl.convertor.ElementConvertor;
import org.amdocs.tsuzammen.core.api.item.ElementManager;
import org.amdocs.tsuzammen.core.api.item.ElementManagerFactory;
import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.datatypes.FetchCriteria;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ElementAdaptorImpl implements ElementAdaptor {

  @Override
  public Collection<ElementInfo> list(SessionContext context, ElementContext elementContext,
                                      Id elementId) {
    return getElementManager(context).list(context, elementContext, elementId);
  }

  @Override
  public ElementInfo getInfo(SessionContext context, ElementContext elementContext, Id elementId,
                             FetchCriteria fetchCriteria) {
    return getElementManager(context).getInfo(context, elementContext, elementId, fetchCriteria);
  }

  @Override
  public Element get(SessionContext context, ElementContext elementContext, Id elementId,
                     FetchCriteria fetchCriteria) {
    return ElementConvertor.getElement(
        getElementManager(context).get(context, elementContext, elementId, fetchCriteria));
  }

  @Override
  public void save(SessionContext context, ElementContext elementContext,
                   Element element, String message) {
    getElementManager(context)
        .save(context, elementContext, getCoreElement(element, null), message);
  }


  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getElementManager(context).search(context, searchCriteria);
  }

  private CoreElement getCoreElement(Element element, Id parentId) {
    CoreElement coreElement = new CoreElement();
    coreElement.setAction(element.getAction());

    coreElement.setId(element.getElementId());
    coreElement.setParentId(parentId);
    coreElement.setInfo(element.getInfo());
    coreElement.setRelations(element.getRelations());

    coreElement.setData(element.getData());
    coreElement.setSearchableData(element.getSearchableData());
    coreElement.setVisualization(element.getVisualization());

    coreElement.setSubElements(getCoreElements(element.getSubElements(), element.getElementId()));

    return coreElement;
  }

  private Collection<CoreElement> getCoreElements(Collection<Element> elements, Id parentId) {
    return elements == null
        ? new ArrayList<>()
        : elements.stream().map(element -> getCoreElement(element, parentId))
            .collect(Collectors.toList());
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
