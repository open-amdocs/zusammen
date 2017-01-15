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

package org.amdocs.zusammen.adaptor.inbound.api.types.item;

import java.util.Collection;

public class MergeResult {
  private Collection<ElementConflict> conflicts;

  public boolean isSuccesses() {
    return conflicts == null || conflicts.size() == 0;
  }

  public void setConflicts(Collection<ElementConflict> conflicts) {
    this.conflicts = conflicts;
  }

  public Collection<ElementConflict> getConflicts() {
    return conflicts;
  }

}
