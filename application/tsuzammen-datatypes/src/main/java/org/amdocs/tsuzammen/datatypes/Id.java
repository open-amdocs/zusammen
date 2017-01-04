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

import java.util.UUID;

public class Id {
  private UUID value;

  public Id() {
    value = UUID.randomUUID();
  }

  public Id(UUID value) {
    this.value = value;
  }

  public Id(String value) {
    this.value = UUID.fromString(value);
  }

  public UUID getValue() {
    return value;
  }

  // only for json transformation (in REST for example)
  public void setValue(UUID value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value == null ? "" : value.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Id id = (Id) obj;

    return value != null ? value.equals(id.value) : id.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
