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

package org.amdocs.zusammen.adaptor.inbound.impl.convertor;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ZusammenElement;
import org.amdocs.zusammen.core.api.types.CoreElement;


import java.util.Collection;
import java.util.stream.Collectors;

public class ConverterCoreElementElement {

  public static Element getElement(CoreElement coreElement) {

    ZusammenElement element = new ZusammenElement();
    element.setElementId(coreElement.getId());
    element.setInfo(coreElement.getInfo());
    element.setRelations(coreElement.getRelations());

    element.setData(coreElement.getData());
    element.setSearchableData(coreElement.getSearchableData());
    element.setVisualization(coreElement.getVisualization());

    element.setSubElements(getElements(coreElement.getSubElements()));
    return element;
  }


  public static Collection<Element> getElements(Collection<CoreElement> coreElements) {
      return coreElements == null
          ? null
          : coreElements.stream().map(coreElement ->
              getElement(coreElement)).collect(Collectors.toList());
  }
}
