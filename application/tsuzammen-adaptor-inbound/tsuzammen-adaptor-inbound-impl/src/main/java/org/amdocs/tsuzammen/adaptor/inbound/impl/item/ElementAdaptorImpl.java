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

package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ElementAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SearchCriteria;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Element;
import org.amdocs.tsuzammen.commons.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.core.api.item.ElementManager;
import org.amdocs.tsuzammen.core.api.item.ElementManagerFactory;

public class ElementAdaptorImpl implements ElementAdaptor {


  @Override
  public void update(SessionContext context, ElementContext elementContext, Element element,
                     String message) {
    getElementManager(context).save(context, elementContext, element, message);
  }

  @Override
  public void delete(SessionContext context, ElementContext elementContext, Id elementId,
                     String message) {

  }

  @Override
  public Element get(SessionContext context, ElementContext elementContext, Id elementId,
                     SearchCriteria searchCriteria) {
    return getElementManager(context)
        .get(context, elementContext, elementId, searchCriteria);
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
