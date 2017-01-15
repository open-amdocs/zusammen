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

package org.amdocs.tsuzammen.adaptor.outbound.impl.convertor;

import org.amdocs.tsuzammen.core.api.types.CoreElementConflict;
import org.amdocs.tsuzammen.core.api.types.CoreMergeResult;
import org.amdocs.tsuzammen.sdk.types.ElementDataConflict;
import org.amdocs.tsuzammen.sdk.types.ElementsMergeResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ElementsMergeResultConvertor {

  public static CoreMergeResult getCoreSyncResult(ElementsMergeResult mergeResult) {
    CoreMergeResult coreMergeResult = new CoreMergeResult();
    coreMergeResult.setConflicts(convertConflicts(mergeResult.getConflicts()));
    coreMergeResult.setChangedElements(
        ChangedElementConvertor.convertChangedElements(mergeResult.getChangedElements()));
    return coreMergeResult;
  }

  private static Collection<CoreElementConflict> convertConflicts(
      Collection<ElementDataConflict> conflicts) {
    Collection<CoreElementConflict> coreConflicts = new ArrayList<>();
    CoreElementConflict coreElementConflict;

    return conflicts.stream()
        .map(ElementsMergeResultConvertor::convertConflict)
        .collect(Collectors.toList());
  }

  private static CoreElementConflict convertConflict(ElementDataConflict conflict) {
    CoreElementConflict coreConflict = new CoreElementConflict();
    coreConflict.setLocalElement(
        CoreElementElementDataConvertor.getCoreElement(conflict.getLocalElement()));
    coreConflict.setRemoteElement(
        CoreElementElementDataConvertor.getCoreElement(conflict.getRemoteElement()));
    return coreConflict;
  }
}
