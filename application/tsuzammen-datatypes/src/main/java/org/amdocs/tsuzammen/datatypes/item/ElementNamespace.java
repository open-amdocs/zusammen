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

import org.amdocs.tsuzammen.datatypes.CollaborationNamespace;
import org.amdocs.tsuzammen.datatypes.Namespace;

public class ElementNamespace {

  private Namespace namespace;
  private CollaborationNamespace collaborationNamespace;

  public ElementNamespace() {
  }

  public ElementNamespace(Namespace namespace, CollaborationNamespace collaborationNamespace) {
    this.namespace = namespace;
    this.collaborationNamespace = collaborationNamespace;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public void setNamespace(Namespace namespace) {
    this.namespace = namespace;
  }

  public CollaborationNamespace getCollaborationNamespace() {
    return collaborationNamespace;
  }

  public void setCollaborationNamespace(
      CollaborationNamespace collaborationNamespace) {
    this.collaborationNamespace = collaborationNamespace;
  }
}
