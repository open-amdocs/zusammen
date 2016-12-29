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

package org.amdocs.tsuzammen.commons.datatypes.item;

import java.util.Map;

public class Relation {
  private String id;
  private String type;
  private Map<String, String> tags;
  private RelationEdge source;
  private RelationEdge target;

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

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public RelationEdge getSource() {
    return source;
  }

  public void setSource(RelationEdge source) {
    this.source = source;
  }

  public RelationEdge getTarget() {
    return target;
  }

  public void setTarget(RelationEdge target) {
    this.target = target;
  }
}
