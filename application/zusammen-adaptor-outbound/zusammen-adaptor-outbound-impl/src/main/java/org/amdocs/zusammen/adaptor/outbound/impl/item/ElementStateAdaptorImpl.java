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

package org.amdocs.zusammen.adaptor.outbound.impl.item;

import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.ElementInfoConvertor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;

import java.util.Collection;

public class ElementStateAdaptorImpl implements ElementStateAdaptor {

  @Override
  public Collection<ElementInfo> list(SessionContext context, ElementContext elementContext,
                                      Id elementId) {
    return OutboundAdaptorUtils.getStateStore(context)
        .listElements(context, elementContext, elementId);
  }

  @Override
  public boolean isExist(SessionContext context, ElementContext elementContext, Id elementId) {
    return OutboundAdaptorUtils.getStateStore(context)
        .isElementExist(context, elementContext, elementId);
  }

  @Override
  public ElementInfo get(SessionContext context, ElementContext elementContext, Id elementId) {
    return OutboundAdaptorUtils.getStateStore(context)
        .getElement(context, elementContext, elementId);
  }

  @Override
  public void create(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element) {
    ElementInfo elementInfo = ElementInfoConvertor.getElementInfo(elementContext, space, element);
    OutboundAdaptorUtils.getStateStore(context).createElement(context, elementInfo);
  }

  @Override
  public void update(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element) {
    OutboundAdaptorUtils.getStateStore(context)
        .updateElement(context,
            ElementInfoConvertor.getElementInfo(elementContext, space, element));
  }

  @Override
  public void delete(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element) {
    OutboundAdaptorUtils.getStateStore(context)
        .deleteElement(context,
            ElementInfoConvertor.getElementInfo(elementContext, space, element));
  }
}
