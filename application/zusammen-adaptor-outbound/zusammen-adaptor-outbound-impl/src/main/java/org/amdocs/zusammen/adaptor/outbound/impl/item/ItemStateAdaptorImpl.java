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

import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Item;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class ItemStateAdaptorImpl implements ItemStateAdaptor {
  @Override
  public Response<Collection<Item>> listItems(SessionContext context) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).listItems(context);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_LIST, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Boolean> isItemExist(SessionContext context, Id itemId) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).isItemExist(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_IS_EXIST, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_IS_EXIST, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_IS_EXIST, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Item> getItem(SessionContext context, Id itemId) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).getItem(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_GET, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_GET, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_GET, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> createItem(SessionContext context, Id itemId, Info itemInfo) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).createItem(context, itemId, itemInfo);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_CREATE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_CREATE, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_CREATE, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).updateItem(context, itemId, itemInfo);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_UPDATE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_UPDATE, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_UPDATE, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

  @Override
  public Response<Void> deleteItem(SessionContext context, Id itemId) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).deleteItem(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.SS_ITEM_DELETE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      ReturnCode returnCode = e.getReturnCode();

      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_DELETE, Module.MDW, e.getMessage(),
          returnCode));
    }catch (RuntimeException rte){
      response = new Response(new ReturnCode(ErrorCode.SS_ITEM_DELETE, Module.MDW, rte.getMessage(),
          null));
    }
    return response;
  }

}
