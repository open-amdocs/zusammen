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

package org.amdocs.tsuzammen.core.api.types;

import java.util.Collection;

public class CoreSyncResult {
  private Collection<CoreElementConflict> coreElementConflicts;
  private Collection<ChangedCoreElement> changedCoreElementCollection;

  public void setCoreElementConflicts(Collection<CoreElementConflict> coreElementConflicts) {
    this.coreElementConflicts = coreElementConflicts;
  }

  public void setChangedCoreElementCollection(
      Collection<ChangedCoreElement> changedCoreElementCollection) {
    this.changedCoreElementCollection = changedCoreElementCollection;
  }

  public Collection<CoreElementConflict> getCoreElementConflicts() {
    return coreElementConflicts;
  }

  public Collection<ChangedCoreElement> getChangedCoreElementCollection() {
    return changedCoreElementCollection;
  }
}


