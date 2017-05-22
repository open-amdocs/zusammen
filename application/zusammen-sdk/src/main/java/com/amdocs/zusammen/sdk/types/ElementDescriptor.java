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

package com.amdocs.zusammen.sdk.types;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Relation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ElementDescriptor {
  private Space space = Space.PRIVATE;
  private Id itemId;
  private Id versionId;
  private Namespace namespace;
  private Id id;
  private Id parentId;
  private Info info;
  private Collection<Relation> relations;
  private Set<Id> subElements = new HashSet();

  public ElementDescriptor(Id itemId, Id versionId, Namespace namespace, Id id) {
    this.itemId = itemId;
    this.versionId = versionId;
    this.namespace = namespace;
    this.id = id;
  }

  public Space getSpace() {
    return space;
  }

  public void setSpace(Space space) {
    this.space = space;
  }

  public Id getItemId() {
    return itemId;
  }

  public Id getVersionId() {
    return versionId;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public Id getId() {
    return id;
  }

  public Id getParentId() {
    return parentId;
  }

  public void setParentId(Id parentId) {
    this.parentId = parentId;
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

  public Set<Id> getSubElements() {
    return subElements;
  }

  public void setSubElements(Set<Id> subElements) {
    this.subElements = subElements;
  }
}
