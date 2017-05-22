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

package com.amdocs.zusammen.adaptor.inbound.impl.convertor;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ZusammenElement;
import com.amdocs.zusammen.core.api.types.CoreElement;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ElementConvertor {

  public static Element convert(CoreElement coreElement) {
    if (coreElement == null) {
      return null;
    }
    ZusammenElement element = new ZusammenElement();
    element.setAction(coreElement.getAction());
    element.setElementId(coreElement.getId());
    element.setInfo(coreElement.getInfo());
    element.setRelations(coreElement.getRelations());

    element.setData(coreElement.getData());
    element.setSearchableData(coreElement.getSearchableData());
    element.setVisualization(coreElement.getVisualization());

    element.setSubElements(coreElement.getSubElements() == null
        ? null
        : coreElement.getSubElements().stream()
            .map(ElementConvertor::convert)
            .collect(Collectors.toList()));
    return element;
  }

  public static CoreElement convertFrom(Element element) {
    CoreElement coreElement = new CoreElement();
    coreElement.setAction(element.getAction());

    coreElement.setId(element.getElementId());
    coreElement.setInfo(element.getInfo());
    coreElement.setRelations(element.getRelations());

    coreElement.setData(element.getData());
    coreElement.setSearchableData(element.getSearchableData());
    coreElement.setVisualization(element.getVisualization());

    coreElement.setSubElements(element.getSubElements() == null
        ? new ArrayList<>()
        : element.getSubElements().stream()
            .map(ElementConvertor::convertFrom)
            .collect(Collectors.toList()));
    return coreElement;
  }
}
