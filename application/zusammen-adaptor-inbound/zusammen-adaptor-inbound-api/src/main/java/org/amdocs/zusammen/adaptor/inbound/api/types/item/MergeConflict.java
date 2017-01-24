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

package org.amdocs.zusammen.adaptor.inbound.api.types.item;

import org.amdocs.zusammen.datatypes.item.ItemVersionDataConflict;

import java.util.Collection;

public class MergeConflict {
  private ItemVersionDataConflict versionDataConflict;
  private Collection<ElementConflict> elementConflicts;

  public ItemVersionDataConflict getVersionDataConflict() {
    return versionDataConflict;
  }

  public void setVersionDataConflict(
      ItemVersionDataConflict versionDataConflict) {
    this.versionDataConflict = versionDataConflict;
  }

  public Collection<ElementConflict> getElementConflicts() {
    return elementConflicts;
  }

  public void setElementConflicts(
      Collection<ElementConflict> elementConflicts) {
    this.elementConflicts = elementConflicts;
  }

  public boolean isSuccess() {
    return (this.elementConflicts == null || this.elementConflicts.size() == 0)
        && this.versionDataConflict == null;
  }
}
