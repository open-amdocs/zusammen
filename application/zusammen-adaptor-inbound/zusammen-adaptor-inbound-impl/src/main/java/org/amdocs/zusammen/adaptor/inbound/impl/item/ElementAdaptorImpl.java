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

package org.amdocs.zusammen.adaptor.inbound.impl.item;

import org.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementConvertor;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ElementInfoConvertor;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.item.ElementManagerFactory;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;
import java.util.stream.Collectors;

public class ElementAdaptorImpl implements ElementAdaptor {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ElementAdaptorImpl.class
      .getName());
  @Override
  public Response<Collection<ElementInfo>> list(SessionContext context, ElementContext
      elementContext, Id elementId) {
    Response response;
    try {
      Collection<ElementInfo> elementInfoCollection = getElementManager(context).list(context,
          elementContext, elementId)
          .stream()
          .map(ElementInfoConvertor::convert)
          .collect(Collectors.toList());
      response = new Response(elementInfoCollection);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<ElementInfo> getInfo(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response response;
    try {
      ElementInfo elementInfo = ElementInfoConvertor
          .convert(getElementManager(context).getInfo(context, elementContext, elementId));
      response = new Response(elementInfo);
    } catch (ZusammenException ze) {
      logger.error(ze.getReturnCode().toString(),ze);
      response = new Response(ze.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<Element> get(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response response;
    try {
      Element element = ElementConvertor
          .convert(getElementManager(context).get(context, elementContext, elementId));
      response = new Response(element);
    } catch (ZusammenException ze) {
      logger.error(ze.getReturnCode().toString(),ze);
      response = new Response(ze.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<Void> save(SessionContext context, ElementContext elementContext,
                             Element element, String message) {
    Response response;
    try {
      getElementManager(context)
          .save(context, elementContext, ElementConvertor.convertFrom(element), message);
      response = new Response(Void.TYPE);
    } catch (ZusammenException ze) {
      logger.error(ze.getReturnCode().toString(),ze);
      response = new Response(ze.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<SearchResult> search(SessionContext context, SearchCriteria searchCriteria) {
    Response response;
    try {
      SearchResult searchResult = getElementManager(context).search(context, searchCriteria);
      response = new Response(searchResult);
    } catch (ZusammenException ze) {
      logger.error(ze.getReturnCode().toString(),ze);
      response = new Response(ze.getReturnCode());
    }
    return response;
  }

  private ElementManager getElementManager(SessionContext context) {
    return ElementManagerFactory.getInstance().createInterface(context);
  }
}
