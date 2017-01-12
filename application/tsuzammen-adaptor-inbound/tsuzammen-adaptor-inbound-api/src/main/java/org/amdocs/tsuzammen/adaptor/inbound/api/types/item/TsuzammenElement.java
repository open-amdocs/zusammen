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

package org.amdocs.tsuzammen.adaptor.inbound.api.types.item;

import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.Relation;
import org.amdocs.tsuzammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

public class TsuzammenElement implements Element {
  private ElementAction action;
  private Id elementId;
  private Info info;
  private Collection<Relation> relations;
  private byte[] data;
  private byte[] searchableData;
  private byte[] visualization;
  private Collection<Element> subElements = Collections.EMPTY_LIST;

  @Override
  public ElementAction getAction() {
    return this.action;
  }

  @Override
  public Id getElementId() {
    return this.elementId;
  }

  @Override
  public Info getInfo() {
    return this.info;
  }

  @Override
  public Collection<Relation> getRelations() {
    return this.relations;
  }

  @Override
  public InputStream getData() {
    return FileUtils.toInputStream(this.data);
  }

  @Override
  public InputStream getSearchableData() {
    return FileUtils.toInputStream(searchableData);
  }

  @Override
  public InputStream getVisualization() {
    return FileUtils.toInputStream(this.visualization);
  }


  @Override
  public Collection<Element> getSubElements() {
    return this.subElements;
  }


  public void setInfo(Info info) {
    this.info = info;
  }


  public void setElementId(Id elementId) {
    this.elementId = elementId;
  }

  public void setData(InputStream data) {
    this.data = FileUtils.toByteArray(data);
  }

  public void setRelations(Collection<Relation> relations) {
    this.relations = relations;
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = FileUtils.toByteArray(searchableData );
  }

  public void setVisualization(InputStream visualization) {
    this.visualization = FileUtils.toByteArray(visualization);
  }

  public void setSubElements(Collection<Element> subElements) {
    this.subElements = subElements;
  }

  public void setAction(ElementAction action) {
    this.action = action;
  }
}
