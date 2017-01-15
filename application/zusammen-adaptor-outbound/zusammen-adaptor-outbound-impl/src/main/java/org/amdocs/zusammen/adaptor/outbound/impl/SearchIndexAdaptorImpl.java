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

package org.amdocs.zusammen.adaptor.outbound.impl;


import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;
import org.amdocs.zusammen.sdk.SearchIndex;
import org.amdocs.zusammen.sdk.SearchIndexFactory;
import org.amdocs.zusammen.sdk.types.searchindex.ElementSearchableData;

public class SearchIndexAdaptorImpl implements SearchIndexAdaptor {


  @Override
  public void createElement(SessionContext context, ElementContext elementContext,
                            CoreElement element, Space space) {
    getSearchIndex(context)
        .createElement(context, getElementSearchableData(elementContext, element, space));
  }

  @Override
  public void updateElement(SessionContext context, ElementContext elementContext,
                            CoreElement element, Space space) {
    getSearchIndex(context)
        .updateElement(context, getElementSearchableData(elementContext, element, space));
  }

  @Override
  public void deleteElement(SessionContext context, ElementContext elementContext,
                            CoreElement element, Space space) {
    getSearchIndex(context)
        .deleteElement(context, getElementSearchableData(elementContext, element, space));
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    return getSearchIndex(context).search(context, searchCriteria);
  }

  private ElementSearchableData getElementSearchableData(ElementContext elementContext,
                                                         CoreElement element, Space space) {
    ElementSearchableData elementSearchableData = new ElementSearchableData();
    elementSearchableData.setSearchableData(element.getSearchableData());
    elementSearchableData.setElementId(element.getId());
    elementSearchableData.setVersionId(elementContext.getVersionId());
    elementSearchableData.setItemId(elementContext.getItemId());
    elementSearchableData.setSpace(space);
    return elementSearchableData;
  }

  private SearchIndex getSearchIndex(SessionContext context) {
    return SearchIndexFactory.getInstance().createInterface(context);
  }
}
