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

package org.amdocs.tsuzammen.adaptor.inbound.impl.convertor;

import org.amdocs.tsuzammen.adaptor.inbound.api.types.item.ChangedElement;
import org.amdocs.tsuzammen.adaptor.inbound.api.types.item.ElementConflict;
import org.amdocs.tsuzammen.core.api.types.ChangedCoreElement;
import org.amdocs.tsuzammen.core.api.types.CoreElementConflict;
import org.amdocs.tsuzammen.core.api.types.CoreSyncResult;
import org.amdocs.tsuzammen.adaptor.inbound.api.types.item.SyncResult;


import java.util.ArrayList;
import java.util.Collection;

public class ConverterSyncResultCoreSynResult {
  public static SyncResult getSyncResult(CoreSyncResult coreSyncResult) {
    SyncResult syncResult = new SyncResult();
    syncResult.setElementConflicts(convertElementConflicts(coreSyncResult.
        getCoreElementConflicts()));
    /*syncResult.setChangedElementCollection(convertChangedCoreElement(coreSyncResult
        .getChangedCoreElementCollection()));*/
    return syncResult;
  }

  private static Collection<ChangedElement> convertChangedCoreElement(
      Collection<ChangedCoreElement> changedCoreElementCollection) {

    Collection<ChangedElement> changedElements = new ArrayList<>();
    ChangedElement changedElement;
    for(ChangedCoreElement changedCoreElementData : changedCoreElementCollection){
      changedElement = ConverterChangedCoreElementCoreChangedElementData.getChangedElement(
          changedCoreElementData);
      changedElements.add(changedElement);
    }
    return changedElements;
  }

  private static Collection<ElementConflict> convertElementConflicts(
      Collection<CoreElementConflict> coreElementDataConflicts) {
    Collection<ElementConflict> elementConflicts = new ArrayList<>();
    ElementConflict elementConflict;

    for (CoreElementConflict conflict : coreElementDataConflicts) {
      elementConflict = new ElementConflict();
      elementConflict.
          setLocalElement(ConverterCoreElementElement.getElement(conflict
              .getLocalCoreElement()));
      elementConflict.setRemoteElement(ConverterCoreElementElement.getElement
          (conflict.getRemoteCoreElement()));
      elementConflicts.add(elementConflict);
    }
    return elementConflicts;
  }
}
