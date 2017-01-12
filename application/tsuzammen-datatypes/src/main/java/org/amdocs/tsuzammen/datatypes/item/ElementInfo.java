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

package org.amdocs.tsuzammen.datatypes.item;

import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.Namespace;

import java.util.ArrayList;
import java.util.Collection;

public class ElementInfo {
  private Namespace namespace;
  private Id itemId;
  private Id versionId;
  private Id id;
  private Id parentId; //core uses for save
  private Info info;
  private Collection<Relation> relations;
  private Collection<ElementInfo> subElements = new ArrayList<>(); //core uses for get

  public ElementInfo(Id itemId, Id versionId, Id id, Id parentId) {
    this.itemId = itemId;
    this.versionId = versionId;
    this.id = id;
    this.parentId = parentId;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public void setNamespace(Namespace namespace) {
    this.namespace = namespace;
  }

  public Id getItemId() {
    return itemId;
  }

  public Id getVersionId() {
    return versionId;
  }

  public Id getId() {
    return id;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public Id getParentId() {
    return parentId;
  }

  public Collection<Relation> getRelations() {
    return relations;
  }

  public void setRelations(Collection<Relation> relations) {
    this.relations = relations;
  }

  public Collection<ElementInfo> getSubElements() {
    return subElements;
  }

  public void setSubElements(Collection<ElementInfo> subElements) {
    this.subElements = subElements;
  }

  public void addSubelement(ElementInfo elementInfo) {
    this.subElements.add(elementInfo);
  }
}
