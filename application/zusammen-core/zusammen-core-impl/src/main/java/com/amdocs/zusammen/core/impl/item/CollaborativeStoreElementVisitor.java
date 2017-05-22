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

package com.amdocs.zusammen.core.impl.item;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;

public class CollaborativeStoreElementVisitor implements ElementVisitor {
  private ElementCommandAbstarctFactory elementCommandFactory;

  private CollaborativeStoreElementVisitor() {
    elementCommandFactory = CollaborativeElementCommandFactory.init();
  }

  private static final CollaborativeStoreElementVisitor INSTANCE =
      new CollaborativeStoreElementVisitor();

  public static CollaborativeStoreElementVisitor init() {
    return INSTANCE;
  }

  @Override
  public void visit(SessionContext context, ElementContext elementContext, Space space,
                    CoreElement element) {
    elementCommandFactory.executeCommand(context, elementContext, space, element);
    setSubElementsHierarchyPosition(element);
  }

  private void setSubElementsHierarchyPosition(CoreElement element) {
    if (element.getSubElements() == null || element.getSubElements().isEmpty()) {
      return;
    }
    Namespace subElementsNamespace = new Namespace(element.getNamespace(), element.getId());
    element.getSubElements().forEach(subElement -> {
      subElement.setParentId(element.getId());
      subElement.setNamespace(subElementsNamespace);
    });
  }
}
