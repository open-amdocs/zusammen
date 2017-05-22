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

package com.amdocs.zusammen.adaptor.outbound.api;


import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;

public interface SearchIndexAdaptor {


  Response<Void> createElement(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element);

  Response<Void> updateElement(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element);

  Response<Void> deleteElement(SessionContext context, ElementContext elementContext, Space space,
                     CoreElement element);

  Response<SearchResult> search(SessionContext context, SearchCriteria searchCriteria);


}
