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

package com.amdocs.zusammen.core.api.types;

public class CoreMergeResult {
  private CoreMergeChange change;
  private CoreMergeConflict conflict;

  public CoreMergeChange getChange() {
    return change;
  }

  public void setChange(CoreMergeChange change) {
    this.change = change;
  }

  public CoreMergeConflict getConflict() {
    return conflict;
  }

  public void setConflict(CoreMergeConflict conflict) {
    this.conflict = conflict;
  }

  public boolean isCompleted() {
    return this.conflict == null || conflict.isSuccess();
  }
}


