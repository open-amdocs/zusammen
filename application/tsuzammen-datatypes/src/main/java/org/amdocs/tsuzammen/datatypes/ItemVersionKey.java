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

package org.amdocs.tsuzammen.datatypes;

public class ItemVersionKey {

  private String itemId;
  private String versionId;

  public ItemVersionKey() {
  }


  public ItemVersionKey(String itemId, String versionId) {
    this.itemId = itemId;
    this.versionId = versionId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getVersionId() {
    return versionId;
  }

  public void setVersionId(String versionId) {
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

    ItemVersionKey that = (ItemVersionKey) obj;

    return (itemId != null ? itemId.equals(that.itemId) : that.itemId == null)
        && (versionId != null ? versionId.equals(that.versionId) : that.versionId == null);

  }

  @Override
  public int hashCode() {
    int result = itemId != null ? itemId.hashCode() : 0;
    result = 31 * result + (versionId != null ? versionId.hashCode() : 0);
    return result;
  }
}
