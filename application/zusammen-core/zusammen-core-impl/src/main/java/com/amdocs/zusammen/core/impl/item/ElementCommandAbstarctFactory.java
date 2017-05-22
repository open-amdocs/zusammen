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
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.ElementContext;

import java.util.HashMap;
import java.util.Map;

public abstract class ElementCommandAbstarctFactory {
  private final Map<Action, ElementCommand> commands = new HashMap<Action, ElementCommand>();

  public void addCommand(final Action action, final ElementCommand command) {
    commands.put(action, command);
  }

  public void executeCommand(SessionContext context, ElementContext elementContext, Space space,
                             CoreElement element) {
    if (commands.containsKey(element.getAction())) {
      commands.get(element.getAction()).apply(context, elementContext, space, element);
    }
  }

}
