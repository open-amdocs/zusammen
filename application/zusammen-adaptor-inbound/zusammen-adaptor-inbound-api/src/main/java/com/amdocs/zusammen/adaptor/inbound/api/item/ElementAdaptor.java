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

package com.amdocs.zusammen.adaptor.inbound.api.item;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementConflict;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Resolution;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public interface ElementAdaptor {

  Response<Collection<ElementInfo>> list(SessionContext context, ElementContext elementContext,
                                         Id elementId);

  Response<ElementInfo> getInfo(SessionContext context, ElementContext elementContext,
                                Id elementId);

  Response<Element> get(SessionContext context, ElementContext elementContext, Id elementId);

  Response<Element> save(SessionContext context, ElementContext elementContext, Element element,
                         String message);

  Response<SearchResult> search(SessionContext context, SearchCriteria searchCriteria);

  Response<ElementConflict> getConflict(SessionContext context, ElementContext elementContext,
                                        Id elementId);

  Response<Void> resolveConflict(SessionContext context, ElementContext elementContext,
                                 Id elementId, Resolution resolution);

}
