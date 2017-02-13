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
import org.amdocs.zusammen.core.api.types.CoreElementInfo;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.sdk.state.types.StateElement;

import java.util.stream.Collectors;

public class StateElementConvertor {

  public static StateElement convertFromCoreElement(ElementContext elementContext, Space space,
                                                    CoreElement element) {
    StateElement stateElement =
        new StateElement(elementContext.getItemId(), elementContext.getVersionId(),
            element.getNamespace(), element.getId());
    stateElement.setSpace(space);
    stateElement.setParentId(element.getParentId());
    stateElement.setInfo(element.getInfo());
    stateElement.setRelations(element.getRelations());
    return stateElement;
  }

  public static CoreElementInfo convertToCoreElementInfo(StateElement element) {
    CoreElementInfo elementInfo = convertToCoreElementInfo(element.getId());
    elementInfo.setParentId(element.getParentId());
    elementInfo.setNamespace(element.getNamespace());
    elementInfo.setRelations(element.getRelations());
    elementInfo.setInfo(element.getInfo()    );
    if (element.getSubElements() != null && !element.getSubElements().isEmpty()) {
      elementInfo.setSubElements(element.getSubElements().stream()
          .map(StateElementConvertor::convertToCoreElementInfo)
          .collect(Collectors.toList()));
    }
    return elementInfo;
  }

  private static CoreElementInfo convertToCoreElementInfo(Id elementId) {
    CoreElementInfo coreElementInfo = new CoreElementInfo();
    coreElementInfo.setId(elementId);
    return coreElementInfo;
  }
}
