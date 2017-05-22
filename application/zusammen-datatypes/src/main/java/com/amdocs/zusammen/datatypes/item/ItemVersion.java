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

package com.amdocs.zusammen.datatypes.item;

import com.amdocs.zusammen.datatypes.Id;

import java.util.Date;

public class ItemVersion {
  private Id id;
  private Id baseId;
  private Date creationTime;
  private Date modificationTime;
  private ItemVersionData data;

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public Id getBaseId() {
    return baseId;
  }

  public void setBaseId(Id baseId) {
    this.baseId = baseId;
  }

  public ItemVersionData getData() {
    return data;
  }

  public void setData(ItemVersionData data) {
    this.data = data;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getModificationTime() {
    return modificationTime;
  }

  public void setModificationTime(Date modificationTime) {
    this.modificationTime = modificationTime;
  }
}
