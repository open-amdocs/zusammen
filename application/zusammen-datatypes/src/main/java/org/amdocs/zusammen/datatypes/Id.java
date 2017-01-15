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

package org.amdocs.zusammen.datatypes;

import java.util.UUID;

public class Id {
  public static final Id ZERO = new Id("00000000000000000000000000000000");
  private String value;

  public Id() {
    value = UUID.randomUUID().toString().replace("-","");
  }

  public Id(String value) {
    this.value = value;
  }


  public String getValue() {
    return value;
  }

  // only for json transformation (in REST for example)
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value == null ? "" : value;
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
