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

package com.amdocs.zusammen.adaptor.inbound.impl.item;

import com.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementConvertor;
import com.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementInfoConvertor;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ElementManager;
import com.amdocs.zusammen.core.api.item.ElementManagerFactory;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;
import java.util.stream.Collectors;

public class ElementAdaptorImpl implements ElementAdaptor {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ElementAdaptorImpl.class
      .getName());

  @Override
  public Response<Collection<ElementInfo>> list(SessionContext context, ElementContext
      elementContext, Id elementId) {
    Response<Collection<ElementInfo>> response;
    try {
      Collection<ElementInfo> elementInfoCollection =
          getElementManager(context).list(context, elementContext, elementId).stream()
              .map(ElementInfoConvertor::convert)
              .collect(Collectors.toList());
      response = new Response<>(elementInfoCollection);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ELEMENT_LIST, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);

    }
    return response;
  }

  @Override
  public Response<ElementInfo> getInfo(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response<ElementInfo> response;
    try {
      ElementInfo elementInfo = ElementInfoConvertor
          .convert(getElementManager(context).getInfo(context, elementContext, elementId));
      response = new Response<>(elementInfo);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ELEMENT_GET_INFO, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Element> get(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response<Element> response;
    try {
      Element element = ElementConvertor
          .convert(getElementManager(context).get(context, elementContext, elementId));
      response = new Response<>(element);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ELEMENT_GET, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<Element> save(SessionContext context, ElementContext elementContext,
                                Element element, String message) {
    Response response;
    try {
      element = ElementConvertor.convert(getElementManager(context)
          .save(context, elementContext, ElementConvertor.convertFrom(element), message));
      response = new Response<>(element);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ELEMENT_SAVE, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  @Override
  public Response<SearchResult> search(SessionContext context, SearchCriteria searchCriteria) {
    Response<SearchResult> response;
    try {
      SearchResult searchResult = getElementManager(context).search(context, searchCriteria);
      response = new Response<>(searchResult);
    } catch (ZusammenException ze) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ELEMENT_SEARCH, Module.ZDB, null, ze.getReturnCode());
      logger.error(returnCode.toString(), ze);
      response = new Response<>(returnCode);
    }
    return response;
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
