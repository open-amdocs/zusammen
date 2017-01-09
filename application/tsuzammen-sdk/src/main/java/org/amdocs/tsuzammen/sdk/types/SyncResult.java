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

package org.amdocs.tsuzammen.sdk.types;

import java.util.ArrayList;
import java.util.Collection;

public class SyncResult {

  private Collection<ElementConflicts> elementConflicts = new ArrayList<>();
  private Collection<ChangedElementData> changedElementDataCollection = new ArrayList<>();

  public void addElementConflicts(ElementConflicts elementConflicts){
    this.elementConflicts.add(elementConflicts);
  }

  public Collection<ElementConflicts> getElementConflicts() {
    return elementConflicts;
  }

  public void setElementConflicts(
      Collection<ElementConflicts> elementConflicts) {
    this.elementConflicts = elementConflicts;
  }

  public void addChangedElementInfo(ChangedElementData changedElementData){
    this.changedElementDataCollection.add(changedElementData);
  }

  public Collection<ChangedElementData> getChangedElementDataCollection() {
    return changedElementDataCollection;
  }

  public void setChangedElementDataCollection(
      Collection<ChangedElementData> changedElementDataCollection) {
    this.changedElementDataCollection = changedElementDataCollection;
  }
}
