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

package org.amdocs.tsuzammen.core.api.item;

import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.datatypes.FetchCriteria;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;

public interface ElementManager {

  CoreElement get(SessionContext context, ElementContext elementContext, Id elementId,
                  FetchCriteria fetchCriteria);

  ElementInfo getInfo(SessionContext context, ElementContext elementContext, Id elementId,
                      FetchCriteria fetchCriteria);

  void save(SessionContext context, ElementContext elementContext, CoreElement element,
            String message);
}
