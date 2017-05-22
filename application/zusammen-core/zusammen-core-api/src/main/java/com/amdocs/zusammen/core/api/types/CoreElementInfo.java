/*
 * Add Copyright © 2016-2017 European Support Limited
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

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Relation;

import java.util.Collection;
import java.util.Collections;

public class CoreElementInfo {
  private Id id;
  private Id parentId;
  private Namespace namespace;
  private Info info;
  private Collection<Relation> relations = Collections.EMPTY_LIST;
  private Collection<CoreElementInfo> subElements = Collections.EMPTY_LIST;

  public Id getId() {
    return id;
  }

  public void setId(Id elementId) {
    id = elementId;
  }

  public Id getParentId() {
    return parentId;
  }

  public void setParentId(Id parentId) {
    this.parentId = parentId;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public void setNamespace(Namespace namespace) {
    this.namespace = namespace;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public Collection<Relation> getRelations() {
    return relations;
  }

  public void setRelations(Collection<Relation> relations) {
    this.relations = relations;
  }

  public Collection<CoreElementInfo> getSubElements() {
    return subElements;
  }

  public void setSubElements(Collection<CoreElementInfo> subElements) {
    this.subElements = subElements;
  }
}
