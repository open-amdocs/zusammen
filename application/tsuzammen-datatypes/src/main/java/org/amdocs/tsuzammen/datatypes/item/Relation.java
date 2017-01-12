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

package org.amdocs.tsuzammen.datatypes.item;

import org.amdocs.tsuzammen.datatypes.Id;

public class Relation {
  private Id id;
  private String type;
  private Info info;
  private RelationEdge edge1;
  private RelationEdge edge2;
  private RelationDirection direction;

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Info getInfo() {
    return info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }

  public RelationEdge getEdge1() {
    return edge1;
  }

  public void setEdge1(RelationEdge edge1) {
    this.edge1 = edge1;
  }

  public RelationEdge getEdge2() {
    return edge2;
  }

  public void setEdge2(RelationEdge edge2) {
    this.edge2 = edge2;
  }

  public RelationDirection getDirection() {
    return direction;
  }

  public void setDirection(RelationDirection direction) {
    this.direction = direction;
  }
}
