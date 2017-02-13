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

package org.amdocs.zusammen.core.api.types;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Relation;
import org.amdocs.zusammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CoreElement {
  private Action action = Action.IGNORE;
  private Id id;
  private Id parentId;
  private Namespace namespace;
  private Info info;
  private Collection<Relation> relations = Collections.EMPTY_LIST;
  private byte[] data;
  private byte[] searchableData;
  private byte[] visualization;
  private Collection<CoreElement> subElements = Collections.EMPTY_LIST;

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
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

  public InputStream getData() {
    return getInputStream(data);
  }

  public void setData(InputStream data) {
    this.data = getBytes(data);
  }

  public InputStream getSearchableData() {
    return getInputStream(searchableData);
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = getBytes(searchableData);
  }

  public InputStream getVisualization() {
    return getInputStream(visualization);
  }

  public void setVisualization(InputStream visualization) {
    this.visualization = getBytes(visualization);
  }

  private InputStream getInputStream(byte[] bytes) {
    return Objects.isNull(bytes) || bytes.length == 0 ? null : FileUtils.toInputStream(bytes);
  }

  private byte[] getBytes(InputStream inputStream) {
    return inputStream == null ? null : FileUtils.toByteArray(inputStream);
  }

  public Collection<CoreElement> getSubElements() {
    return subElements;
  }

  public void setSubElements(Collection<CoreElement> subElements) {
    this.subElements = subElements;
  }
}
