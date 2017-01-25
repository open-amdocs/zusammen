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

package org.amdocs.zusammen.adaptor.outbound.impl.convertor;

import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.sdk.types.ElementData;

import java.util.stream.Collectors;

public class ElementDataConvertor {

  public static ElementData convertTo(CoreElement coreElement, ElementContext elementContext) {
    ElementData elementData = new ElementData(elementContext.getItemId(), elementContext
        .getVersionId(), coreElement.getNamespace(), coreElement.getId());
    elementData.setParentId(coreElement.getParentId());
    elementData.setInfo(coreElement.getInfo());
    elementData.setRelations(coreElement.getRelations());

    elementData.setData(coreElement.getData());
    elementData.setSearchableData(coreElement.getSearchableData());
    elementData.setVisualization(coreElement.getVisualization());
    return elementData;
  }

  public static CoreElement convertFrom(ElementData elementData) {
    CoreElement coreElement = convertFrom(elementData.getId());
    coreElement.setNamespace(elementData.getNamespace());
    coreElement.setParentId(elementData.getParentId());
    coreElement.setInfo(elementData.getInfo());
    coreElement.setRelations(elementData.getRelations());

    coreElement.setData(elementData.getData());
    coreElement.setSearchableData(elementData.getSearchableData());
    coreElement.setVisualization(elementData.getVisualization());

    coreElement.setSubElements(elementData.getSubElements().stream()
        .map(subElementEntry -> convertFrom(subElementEntry))
        .collect(Collectors.toList()));
    return coreElement;
  }

  private static CoreElement convertFrom(Id elementId) {
    CoreElement coreElement = new CoreElement();
    coreElement.setId(elementId);
    return coreElement;
  }
}
