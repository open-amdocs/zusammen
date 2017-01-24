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

import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ItemVersion;

import java.util.Collection;

public class MergeChange {
  private ItemVersion changedVersion;
  private Action versionAction;
  private Collection<Element> changedElements;

  public ItemVersion getChangedVersion() {
    return changedVersion;
  }

  public void setChangedVersion(ItemVersion changedVersion) {
    this.changedVersion = changedVersion;
  }

  public Action getVersionAction() {
    return versionAction;
  }

  public void setVersionAction(Action versionAction) {
    this.versionAction = versionAction;
  }

  public Collection<Element> getChangedElements() {
    return changedElements;
  }

  public void setChangedElements(Collection<Element> changedElements) {
    this.changedElements = changedElements;
  }
}
