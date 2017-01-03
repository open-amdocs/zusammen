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

package org.amdocs.tsuzammen.datatypes.collaboration;

import java.util.ArrayList;
import java.util.Collection;

public class SyncResult {


  private Collection<FileConflicts> conflicts = new ArrayList<>();

  public boolean isSuccesses(){
      return conflicts==null || conflicts.size()==0;
    }




  public Collection<FileConflicts> getConflicts() {
    return conflicts;
  }

  public void setConflicts(
      Collection<FileConflicts> conflicts) {
    this.conflicts = conflicts;
  }

  public void addConflict(FileConflicts conflict){
    conflicts.add(conflict);
  }
}


