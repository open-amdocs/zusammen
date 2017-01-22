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

package org.amdocs.zusammen.core.api.types;

import org.amdocs.zusammen.datatypes.item.Info;

import java.util.Collection;

public class CoreItemVersionChangedData {
  Collection<CoreElement> coreElements;
  Info itemVersionInfo;

  public Collection<CoreElement> getCoreElements() {
    return coreElements;
  }

  public void setCoreElements(
      Collection<CoreElement> coreElements) {
    this.coreElements = coreElements;
  }

  public Info getItemVersionInfo() {
    return itemVersionInfo;
  }

  public void setItemVersionInfo(Info itemVersionInfo) {
    this.itemVersionInfo = itemVersionInfo;
  }
}
