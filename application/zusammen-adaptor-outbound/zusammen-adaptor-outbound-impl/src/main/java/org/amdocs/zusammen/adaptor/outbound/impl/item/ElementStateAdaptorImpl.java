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
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.sdk.state.types.StateElement;

import java.util.Collection;
import java.util.stream.Collectors;

public class ElementStateAdaptorImpl implements ElementStateAdaptor {

  @Override
  public Response<Collection<CoreElementInfo>> list(SessionContext context, ElementContext
      elementContext,
                                                    Id elementId) {
    Response<Collection<StateElement>> plugintResponse;
    Response<Collection<CoreElementInfo>> response;
    try {

      plugintResponse = OutboundAdaptorUtils.getStateStore(context)
          .listElements(context, elementContext, elementId);
      if (plugintResponse.isSuccessful()) {
        response = new Response<Collection<CoreElementInfo>>(plugintResponse.getValue().stream()
            .map(StateElementConvertor::convertToCoreElementInfo)
            .collect(Collectors.toList()));
      } else {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, null,
            plugintResponse.getReturnCode()));
      }

    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, e.getMessage(),
          returnCode));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Boolean> isExist(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .isElementExist(context, elementContext, elementId);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, e.getMessage(),
          returnCode));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<CoreElementInfo> get(SessionContext context, ElementContext elementContext, Id
      elementId) {
    Response<StateElement> pluginResponse;
    Response<CoreElementInfo> response;
    try {
      pluginResponse = OutboundAdaptorUtils.getStateStore(context)
          .getElement(context, elementContext, elementId);
      if (pluginResponse.isSuccessful()) {
        response = new Response<CoreElementInfo>(StateElementConvertor.convertToCoreElementInfo
            (pluginResponse.getValue()));
      } else {
        response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_GET, Module.MDW, null,
            pluginResponse.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_GET, Module.MDW, e.getMessage(),
          returnCode));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_GET, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> create(SessionContext context, ElementContext elementContext, Space space,
                               CoreElement element) {
    StateElement elementInfo =
        StateElementConvertor.convertFromCoreElement(elementContext, space, element);
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).createElement(context, elementInfo);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_CREATE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_CREATE, Module.MDW, e.getMessage(),
          returnCode));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_CREATE, Module.MDW, rte
          .getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> update(SessionContext context, ElementContext elementContext, Space
      space,
                               CoreElement element) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).updateElement(context,
          StateElementConvertor.convertFromCoreElement(elementContext, space, element));
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_UPDATE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_UPDATE, Module.MDW, e.getMessage(),
          returnCode));

    }catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_UPDATE, Module.MDW, rte
          .getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> delete(SessionContext context, ElementContext elementContext, Space
      space,
                               CoreElement element) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).deleteElement(context,
          StateElementConvertor.convertFromCoreElement(elementContext, space, element));
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_DELETE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_DELETE, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.SS_ELEMENT_DELETE, Module.MDW, rte
          .getMessage(),
          null));
    }
    return response;
  }
}
