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

package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreMergeChange;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElementChange;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeChange;

import java.util.stream.Collectors;

public class CollaborationMergeChangeConvertor {

  public static CoreMergeChange convertToCoreMergeChange(CollaborationMergeChange mergeChange) {
    if (mergeChange == null) {
      return null;
    }

    CoreMergeChange coreMergeChange = new CoreMergeChange();
    coreMergeChange.setChangedVersion(mergeChange.getChangedVersion());
    coreMergeChange.setChangedElements(mergeChange.getChangedElements().stream()
        .map(CollaborationMergeChangeConvertor::convertToCoreElement)
        .collect(Collectors.toList()));

    return coreMergeChange;
  }

  private static CoreElement convertToCoreElement(CollaborationElementChange elementChange) {
    CoreElement coreElement =
        CollaborationElementConvertor.convertToCoreElement(elementChange.getElement());
    coreElement.setAction(elementChange.getAction());
    return coreElement;
  }
}