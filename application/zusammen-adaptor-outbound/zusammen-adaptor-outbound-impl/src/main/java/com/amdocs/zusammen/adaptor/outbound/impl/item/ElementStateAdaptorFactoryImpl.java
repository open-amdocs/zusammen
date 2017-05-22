/*
 * Copyright © 2016-2017 European Support Limited
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

package com.amdocs.zusammen.adaptor.outbound.impl.item;

import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import com.amdocs.zusammen.datatypes.SessionContext;

public class ElementStateAdaptorFactoryImpl extends ElementStateAdaptorFactory {
  private static final ElementStateAdaptor INSTANCE = new ElementStateAdaptorImpl();

  @Override
  public ElementStateAdaptor createInterface(SessionContext context) {
    return INSTANCE;
  }
}
