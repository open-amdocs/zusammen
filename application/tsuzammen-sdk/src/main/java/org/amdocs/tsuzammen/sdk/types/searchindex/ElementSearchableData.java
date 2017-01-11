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

package org.amdocs.tsuzammen.sdk.types.searchindex;


import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.Space;
import org.amdocs.tsuzammen.utils.fileutils.FileUtils;

import java.io.InputStream;

public class ElementSearchableData {
  private byte[] searchableData;
  private Id elementId;
  private Id itemId;
  private Id versionId;
  private Space space;

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

  public Space getSpace() {
    return space;
  }

  public void setSpace(Space space) {
    this.space = space;
  }

  public InputStream getSearchableData() {
    return FileUtils.toInputStream(searchableData);
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = FileUtils.toByteArray(searchableData);
  }

  public Id getElementId() {
    return elementId;
  }

  public void setElementId(Id elementId) {
    this.elementId = elementId;
  }
}
