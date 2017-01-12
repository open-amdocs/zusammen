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

package org.amdocs.tsuzammen.adaptor.outbound.impl.convertor;

import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.sdk.types.ElementData;

import java.util.stream.Collectors;

public class ConverterCoreElementElementData {

  public static CoreElement getCoreElement(ElementData elementData) {
    CoreElement coreElement =
        getCoreElement(elementData.getId());
    coreElement.setInfo(elementData.getInfo());
    coreElement.setRelations(elementData.getRelations());

    coreElement.setData(elementData.getData());
    coreElement.setSearchableData(elementData.getSearchableData());
    coreElement.setVisualization(elementData.getVisualization());

    coreElement.setSubElements(elementData.getSubElements().stream()
        .map(subElementEntry ->
            getCoreElement(subElementEntry))
        .collect(Collectors.toList()));
    return coreElement;
  }

  public static CoreElement getCoreElement(Id elementId) {
    CoreElement coreElement = new CoreElement();
    coreElement.setId(elementId);
    return coreElement;
  }
}
