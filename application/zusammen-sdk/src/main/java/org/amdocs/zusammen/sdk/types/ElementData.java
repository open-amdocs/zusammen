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

package org.amdocs.zusammen.sdk.types;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Relation;
import org.amdocs.zusammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public class ElementData {
  private Id id;
  private Id itemId;
  private Id versionId;
  private Namespace namespace;
  private Space space;
  private Id parentId;
  private Info info;
  private Collection<Relation> relations;
  private byte[] data;
  private byte[] searchableData;
  private byte[] visualization;
  private Set<Id> subElements;

  public ElementData(Id itemId, Id versionId, Namespace namespace, Id id) {
    this.itemId = itemId;
    this.versionId = versionId;
    this.namespace = namespace;
    this.id = id;
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

  public Space getSpace() {
    return space;
  }

  public void setSpace(Space space) {
    this.space = space;
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

  public InputStream getData() {
    return FileUtils.toInputStream(data);
  }

  public void setData(InputStream data) {
    this.data = FileUtils.toByteArray(data);
  }

  public InputStream getSearchableData() {
    return FileUtils.toInputStream(searchableData);
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = FileUtils.toByteArray(searchableData);
  }

  public InputStream getVisualization() {
    return FileUtils.toInputStream(visualization);
  }

  public void setVisualization(InputStream visualization) {
    this.visualization = FileUtils.toByteArray(visualization);
  }

  public Set<Id> getSubElements() {
    return subElements;
  }

  public void setSubElements(Set<Id> subElements) {
    this.subElements = subElements;
  }

  public void addSubElement(Id key) {
    this.subElements.add(key);
  }
}
