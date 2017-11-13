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

package com.amdocs.zusammen.core.api.item;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementConflict;
import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.core.api.types.CoreMergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Resolution;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public interface ElementManager {

  Collection<CoreElementInfo> list(SessionContext context, ElementContext elementContext,
                                   Id elementId);

  CoreElementInfo getInfo(SessionContext context, ElementContext elementContext, Id elementId);

  CoreElement get(SessionContext context, ElementContext elementContext, Id elementId);

  CoreElement save(SessionContext context, ElementContext elementContext, CoreElement element,
                   String message);

  CoreElementConflict getConflict(SessionContext context, ElementContext elementContext,
                                  Id elementId);

  CoreMergeResult resolveConflict(SessionContext context, ElementContext elementContext,
                                  CoreElement element, Resolution resolution);

  SearchResult search(SessionContext context, SearchCriteria searchCriteria);

  void saveMergeChange(SessionContext context, Space space, ElementContext elementContext,
                       Collection<CoreElement> elements);
}
