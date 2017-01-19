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

public class Namespace {
  private static final String INVALID_CTOR_ARGS_ERR_MSG =
      "Parent element namespace and element Id must be supplied";
  private static final String INVALID_VALUE_ERR_MSG =
      "Namespace string representaion must be supplied";
  private static final String ROOT_NAMESPACE_VALUE = "";

  public static final String NAMESPACE_DELIMITER = "/";
  public static final Namespace ROOT_NAMESPACE = new Namespace();

  private String value;

  public Namespace() {
    value = ROOT_NAMESPACE_VALUE;
  }

  public Namespace(Namespace parentNamespace, Id elementId) {
    if (parentNamespace == null || elementId == null) {
      throw new IllegalArgumentException(INVALID_CTOR_ARGS_ERR_MSG);
    }
    this.value = parentNamespace.getValue() + NAMESPACE_DELIMITER + elementId.toString();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException(INVALID_VALUE_ERR_MSG);
    }
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Namespace namespace = (Namespace) obj;

    if (value != null ? !value.equals(namespace.value) : namespace.value != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  public String toString() {
    return value;
  }
}
