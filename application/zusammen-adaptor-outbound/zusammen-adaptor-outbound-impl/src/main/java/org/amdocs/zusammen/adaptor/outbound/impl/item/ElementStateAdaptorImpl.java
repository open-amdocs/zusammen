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

package org.amdocs.zusammen.adaptor.outbound.impl.item;

import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.zusammen.adaptor.outbound.impl.convertor.StateElementConvertor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreElementInfo;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.sdk.state.types.StateElement;

import java.util.Collection;
import java.util.stream.Collectors;

public class ElementStateAdaptorImpl implements ElementStateAdaptor {

  /*private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(ElementStateAdaptorImpl.class
          .getName());*/
  @Override
  public Response<Collection<CoreElementInfo>> list(SessionContext context,
                                                    ElementContext elementContext, Id elementId) {
    Response<Collection<StateElement>> plugintResponse;
    Response<Collection<CoreElementInfo>> response;
    try {

      plugintResponse = OutboundAdaptorUtils.getStateStore(context)
          .listElements(context, elementContext, elementId);
      if (plugintResponse.isSuccessful()) {
        response = new Response<>(plugintResponse.getValue().stream()
            .map(StateElementConvertor::convertToCoreElementInfo)
            .collect(Collectors.toList()));
      } else {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_LIST, Module.ZSTM, null,
            plugintResponse.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }

    } catch (RuntimeException rte) {

      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_LIST, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_LIST, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }

  @Override
  public Response<Boolean> isExist(SessionContext context, ElementContext elementContext,
                                   Id elementId) {
    Response<Boolean> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .isElementExist(context, elementContext, elementId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_IS_EXIST, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_IS_EXIST, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_IS_EXIST, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }

  @Override
  public Response<Namespace> getNamespace(SessionContext context, Id itemId, Id elementId) {
    Response<Namespace> pluginResponse;
    Response<Namespace> response;
    try {
      pluginResponse = OutboundAdaptorUtils.getStateStore(context)
          .getElementNamespace(context, itemId, elementId);
      if (pluginResponse.isSuccessful()) {
        response = pluginResponse;
      } else {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZSTM, null,
            pluginResponse.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_GET, Module.ZMDP, rte.getMessage(), null)));
    }
    return response;
  }

  @Override
  public Response<CoreElementInfo> get(SessionContext context, ElementContext elementContext,
                                       Id elementId) {
    Response<StateElement> pluginResponse;
    Response<CoreElementInfo> response;
    try {
      pluginResponse = OutboundAdaptorUtils.getStateStore(context)
          .getElement(context, elementContext, elementId);
      if (pluginResponse.isSuccessful()) {
        response = new Response<>(StateElementConvertor.convertToCoreElementInfo
            (pluginResponse.getValue()));
      } else {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZSTM, null,
            pluginResponse.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {

      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_GET, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_GET, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }

  @Override
  public Response<Void> create(SessionContext context, ElementContext elementContext, Space space,
                               CoreElement element) {
    StateElement elementInfo =
        StateElementConvertor.convertFromCoreElement(elementContext, space, element);
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).createElement(context, elementInfo);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {

      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_CREATE, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_CREATE, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }

  @Override
  public Response<Void> update(SessionContext context, ElementContext elementContext, Space space,
                               CoreElement element) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).updateElement(context,
          StateElementConvertor.convertFromCoreElement(elementContext, space, element));
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {

      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_UPDATE, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_UPDATE, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }

  @Override
  public Response<Void> delete(SessionContext context, ElementContext elementContext, Space space,
                               CoreElement element) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).deleteElement(context,
          StateElementConvertor.convertFromCoreElement(elementContext, space, element));
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {

      response = new Response<>(new ReturnCode(ErrorCode.MD_ELEMENT_DELETE, Module.ZSTM, null,
          new ReturnCode(ErrorCode.ST_ELEMENT_DELETE, Module.ZMDP, rte.getMessage(), null)));
      //logger.error(response.getReturnCode().toString());
    }
    return response;
  }
}
