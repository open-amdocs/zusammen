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

package org.amdocs.zusammen.adaptor.inbound.impl.item;

import org.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementConvertor;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.item.ElementManagerFactory;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.datatypes.FetchCriteria;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

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
        .save(context, elementContext, getCoreElement(element), message);
  }


  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getElementManager(context).search(context, searchCriteria);
  }

  private CoreElement getCoreElement(Element element) {
    CoreElement coreElement = new CoreElement();
    coreElement.setAction(element.getAction());

    coreElement.setId(element.getElementId());
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
        : elements.stream().map(element -> getCoreElement(element))
            .collect(Collectors.toList());
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
