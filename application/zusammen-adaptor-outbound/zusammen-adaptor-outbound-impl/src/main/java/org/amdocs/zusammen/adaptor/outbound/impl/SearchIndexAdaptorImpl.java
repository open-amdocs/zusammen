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

package org.amdocs.zusammen.adaptor.outbound.impl;


import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.SearchIndexElementConvertor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;
import org.amdocs.zusammen.sdk.searchindex.SearchIndex;
import org.amdocs.zusammen.sdk.searchindex.SearchIndexFactory;

public class SearchIndexAdaptorImpl implements SearchIndexAdaptor {

  @Override
  public void createElement(SessionContext context, ElementContext elementContext,
                            Space space, CoreElement element) {
    getSearchIndex(context).createElement(context,
        SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
  }

  @Override
  public void updateElement(SessionContext context, ElementContext elementContext,
                            Space space, CoreElement element) {
    getSearchIndex(context).updateElement(context,
        SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
  }

  @Override
  public void deleteElement(SessionContext context, ElementContext elementContext,
                            Space space, CoreElement element) {
    getSearchIndex(context).deleteElement(context,
        SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getSearchIndex(context).search(context, searchCriteria);
  }

  private SearchIndex getSearchIndex(SessionContext context) {
    return SearchIndexFactory.getInstance().createInterface(context);
  }
}
