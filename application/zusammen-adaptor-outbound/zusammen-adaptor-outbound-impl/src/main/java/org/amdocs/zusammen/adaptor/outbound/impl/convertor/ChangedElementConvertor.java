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
import org.amdocs.zusammen.datatypes.item.ElementAction;
import org.amdocs.zusammen.sdk.types.ChangedElementData;

import java.util.Collection;
import java.util.stream.Collectors;

public class ChangedElementConvertor {

  private static final String UNSUPPORTED_CHANGE_TYPE_ERR_MSG =
      "Unsupported element change type %s.";

  public static Collection<CoreElement> convertChangedElements(
      Collection<ChangedElementData> changedElements) {
    return changedElements.stream()
        .map(ChangedElementConvertor::convertChangedCoreElement)
        .collect(Collectors.toList());
  }

  public static CoreElement convertChangedCoreElement(ChangedElementData changedElement) {
    CoreElement coreElement = ElementDataConvertor.getCoreElement(changedElement.getElementData());
    switch (changedElement.getChangeType()) {
      case ADD:
        coreElement.setAction(ElementAction.CREATE);
        break;
      case MODIFY:
        coreElement.setAction(ElementAction.UPDATE);
        break;
      case DELETE:
        coreElement.setAction(ElementAction.DELETE);
        break;
      default:
        throw new RuntimeException(
            String.format(UNSUPPORTED_CHANGE_TYPE_ERR_MSG, changedElement.getChangeType()));
    }
    return coreElement;
  }
}
