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

package org.amdocs.tsuzammen.adaptor.inbound.api.types.item;

import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.Relation;

import java.io.InputStream;
import java.util.Collection;

public class TsuzammenElement implements Element {
  @Override
  public ElementAction getAction() {
    return null;
  }

  @Override
  public void setAction(ElementAction action) {

  }

  @Override
  public Id getElementId() {
    return null;
  }

  @Override
  public void setElementId(Id elementId) {

  }

  @Override
  public Info getInfo() {
    return null;
  }

  @Override
  public void setInfo(Info info) {

  }

  @Override
  public Collection<Relation> getRelations() {
    return null;
  }

  @Override
  public void setRelations(Collection<Relation> relations) {

  }

  @Override
  public void setData(InputStream data) {

  }

  @Override
  public void setSearchData(InputStream searchData) {

  }

  @Override
  public void setVisualization(InputStream visualization) {

  }

  @Override
  public InputStream getData() {
    return null;
  }

  @Override
  public InputStream getSearchData() {
    return null;
  }

  @Override
  public InputStream getVisualization() {
    return null;
  }

  @Override
  public Collection<Element> getSubElements() {
    return null;
  }

  @Override
  public void setSubElements(Collection<Element> subElements) {

  }
}
