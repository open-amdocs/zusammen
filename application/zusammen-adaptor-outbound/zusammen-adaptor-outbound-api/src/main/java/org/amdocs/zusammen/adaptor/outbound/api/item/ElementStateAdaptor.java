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

package org.amdocs.zusammen.adaptor.outbound.api.item;

import org.amdocs.zusammen.datatypes.FetchCriteria;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;

import java.util.Collection;

public interface ElementStateAdaptor {

  Collection<ElementInfo> list(SessionContext context, ElementContext elementContext, Id elementId);

  boolean isExist(SessionContext context, ElementContext elementContext, Id elementId);

  ElementInfo get(SessionContext context, ElementContext elementContext, Id elementId,
                  FetchCriteria fetchCriteria);

  void create(SessionContext context,
              ElementInfo element);

  void update(SessionContext context, ElementInfo element);

  void delete(SessionContext context, ElementInfo element);
}
