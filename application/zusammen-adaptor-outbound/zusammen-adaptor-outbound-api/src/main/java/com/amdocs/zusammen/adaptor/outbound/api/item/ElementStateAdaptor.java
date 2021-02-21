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

package com.amdocs.zusammen.adaptor.outbound.api.item;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.response.Response;

import java.util.Collection;

public interface ElementStateAdaptor {

  Response<Collection<CoreElementInfo>> list(SessionContext context, ElementContext elementContext,
                                             Id elementId);

  Response<Boolean> isExist(SessionContext context, ElementContext elementContext, Id elementId);

  Response<Namespace> getNamespace(SessionContext context, Id itemId, Id elementId);

  Response<CoreElementInfo> get(SessionContext context, ElementContext elementContext,
                                Id elementId);

  Response<CoreElementInfo> get(SessionContext context, Space space, ElementContext elementContext,
                                Id elementId);

  Response<Void> create(SessionContext context, ElementContext elementContext, Space space,
                        CoreElement element);

  Response<Void> update(SessionContext context, ElementContext elementContext, Space space,
                        CoreElement element);

  Response<Void> delete(SessionContext context, ElementContext elementContext, Space space,
                        CoreElement element);
}
