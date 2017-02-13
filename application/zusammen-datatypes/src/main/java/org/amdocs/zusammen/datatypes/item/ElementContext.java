/*
 * Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.datatypes.item;

import org.amdocs.zusammen.datatypes.Id;

public class ElementContext {
  private Id itemId;
  private Id versionId;

  public ElementContext() {
  }

  public ElementContext(String itemId, String versionId) {
    this(new Id(itemId), new Id(versionId));
  }

  public ElementContext(Id itemId, Id versionId) {
    this.itemId = itemId;
    this.versionId = versionId;
  }

  public Id getItemId() {
    return itemId;
  }

  public void setItemId(Id itemId) {
    this.itemId = itemId;
  }

  public Id getVersionId() {
    return versionId;
  }

  public void setVersionId(Id versionId) {
    this.versionId = versionId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    ElementContext that = (ElementContext) obj;

    if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) {
      return false;
    }
    if (versionId != null ? !versionId.equals(that.versionId) : that.versionId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = itemId != null ? itemId.hashCode() : 0;
    result = 31 * result + (versionId != null ? versionId.hashCode() : 0);
    return result;
  }
}
