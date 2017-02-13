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

package org.amdocs.zusammen.core.impl.item;

import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Action;

public class CollaborativeElementCommandFactory extends ElementCommandAbstarctFactory {

  private CollaborativeElementCommandFactory() {
  }

  public static ElementCommandAbstarctFactory init() {
    final ElementCommandAbstarctFactory factory = new CollaborativeElementCommandFactory();

    factory.addCommand(Action.CREATE, (context, elementContext, space, element) -> {
      element.setId(new Id());
      getCollaborationAdaptor(context).createElement(context, elementContext, element);
    });
    factory.addCommand(Action.UPDATE, (context, elementContext, space, element) ->
        getCollaborationAdaptor(context).updateElement(context, elementContext, element)
    );
    factory.addCommand(Action.DELETE, (context, elementContext, space, element) ->
        getCollaborationAdaptor(context).deleteElement(context, elementContext, element)
    );
    return factory;
  }

  private static CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }
}
