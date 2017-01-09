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

import java.util.Map;

public class Relation {
  private String id;
  private String type;
  private Info info;
  private RelationEdge endPoint1;
  private RelationEdge endPoint2;
  private RelationDirection direction;

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public RelationEdge getEndPoint1() {
    return endPoint1;
  }

  public void setEndPoint1(RelationEdge endPoint1) {
    this.endPoint1 = endPoint1;
  }

  public RelationEdge getEndPoint2() {
    return endPoint2;
  }

  public void setEndPoint2(RelationEdge endPoint2) {
    this.endPoint2 = endPoint2;
  }

  public RelationDirection getDirection() {
    return direction;
  }

  public void setDirection(RelationDirection direction) {
    this.direction = direction;
  }
}
