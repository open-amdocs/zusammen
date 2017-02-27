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
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;
import org.amdocs.zusammen.sdk.searchindex.SearchIndex;
import org.amdocs.zusammen.sdk.searchindex.SearchIndexFactory;

public class SearchIndexAdaptorImpl implements SearchIndexAdaptor {

 /* private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(SearchIndexAdaptorImpl.class
          .getName());*/

  @Override
  public Response<Void> createElement(SessionContext context, ElementContext elementContext,
                                      Space space, CoreElement element) {
    Response<Void> response;
    try {
      response = getSearchIndex(context).createElement(context,
          SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZSIM, null,
            response.getReturnCode());
        //logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException re) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZSIM, null,
              new ReturnCode(ErrorCode.IN_ELEMENT_CREATE, Module.ZSIP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> updateElement(SessionContext context, ElementContext elementContext,
                                      Space space, CoreElement element) {
    Response<Void> response;
    try {
      response = getSearchIndex(context).updateElement(context,
          SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZSIM, null,
            response.getReturnCode());
        //logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException re) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZSIM, null,
              new ReturnCode(ErrorCode.IN_ELEMENT_UPDATE, Module.ZSIP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<Void> deleteElement(SessionContext context, ElementContext elementContext,
                                      Space space, CoreElement element) {

    Response<Void> response;
    try {
      response = getSearchIndex(context).deleteElement(context,
          SearchIndexElementConvertor.convertFromCoreElement(elementContext, element, space));
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZSIM, null,
            response.getReturnCode());
        //logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException re) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZSIM, null,
              new ReturnCode(ErrorCode.IN_ELEMENT_DELETE, Module.ZSIP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  @Override
  public Response<SearchResult> search(SessionContext context, SearchCriteria searchCriteria) {

    Response<SearchResult> response;
    try {
      response = getSearchIndex(context).search(context, searchCriteria);
      if (response.isSuccessful()) {
        return response;
      } else {
        ReturnCode returnCode = new ReturnCode(ErrorCode.MD_SEARCH, Module.ZSIM, null,
            response.getReturnCode());
        //logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);
      }
    } catch (RuntimeException re) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.MD_SEARCH, Module.ZSIM, null,
              new ReturnCode(ErrorCode.IN_SEARCH, Module.ZSIP, re.getMessage(), null));
      //logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  private SearchIndex getSearchIndex(SessionContext context) {
    return SearchIndexFactory.getInstance().createInterface(context);
  }
}
