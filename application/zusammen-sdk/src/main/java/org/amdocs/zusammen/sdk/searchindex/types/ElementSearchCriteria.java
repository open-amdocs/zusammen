/*
 * Add Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.sdk.searchindex.types;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;

public class ElementSearchCriteria {
  private SearchCriteria searchCriteria;
  private Id elementId;

  public SearchCriteria getSearchCriteria() {
    return searchCriteria;
  }

  public void setSearchCriteria(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public Id getElementId() {
    return elementId;
  }

  public void setElementId(Id elementId) {
    this.elementId = elementId;
  }
}
