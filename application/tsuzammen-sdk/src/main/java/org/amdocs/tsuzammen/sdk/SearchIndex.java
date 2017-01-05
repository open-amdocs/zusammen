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

package org.amdocs.tsuzammen.sdk;


import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.amdocs.tsuzammen.datatypes.item.ElementInfo;
import org.amdocs.tsuzammen.datatypes.item.ElementNamespace;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.Item;
import org.amdocs.tsuzammen.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchContext;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchResult;
import org.amdocs.tsuzammen.datatypes.searchindex.SearchableData;
import org.amdocs.tsuzammen.datatypes.workspace.WorkspaceInfo;

import java.util.Collection;

public interface SearchIndex {

  void create(SessionContext sessionContext, SearchContext searchContext,
              SearchableData searchableData, Id searchableDataId);

  void update(SessionContext sessionContext, SearchContext searchContext,
              SearchableData searchableData, Id searchableDataId);

  SearchResult search(SessionContext sessionContext, SearchContext searchContext,
                      SearchCriteria searchCriteria);

  void delete(SessionContext sessionContext, SearchContext searchContext,
              SearchCriteria searchCriteria, Id searchableDataId);


}
