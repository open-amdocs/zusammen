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

package com.amdocs.zusammen.adaptor.outbound.impl.item;

import com.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Item;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;

import java.util.Collection;
import java.util.Date;

public class ItemStateAdaptorImpl implements ItemStateAdaptor {

  /*private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ItemStateAdaptorImpl.class
      .getName());*/

  @Override
  public Response<Collection<Item>> listItems(SessionContext context) {
    Response<Collection<Item>> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).listItems(context);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_LIST, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_LIST, Module.ZMDP, rte.getMessage(),
          null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_LIST, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Boolean> isItemExist(SessionContext context, Id itemId) {
    Response<Boolean> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).isItemExist(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_IS_EXIST, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_IS_EXIST, Module.ZMDP, rte
          .getMessage(), null);
      response =
          new Response<>(new ReturnCode(ErrorCode.MD_ITEM_IS_EXIST, Module.ZSTM, null,
              returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Item> getItem(SessionContext context, Id itemId) {
    Response<Item> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).getItem(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_GET, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_GET, Module.ZMDP, rte.getMessage(),
          null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_GET, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Void> createItem(SessionContext context, Id itemId, Info itemInfo,Date
      creationTime) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).createItem(context, itemId,
          itemInfo,creationTime);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_CREATE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_CREATE, Module.ZMDP,
          rte.getMessage(), null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_CREATE, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Void> updateItem(SessionContext context, Id itemId, Info itemInfo,
                                   Date modificationTime) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).updateItem(context, itemId,
          itemInfo,modificationTime);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_UPDATE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }

    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_UPDATE, Module.ZMDP, rte.getMessage()
          , null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_UPDATE, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Void> deleteItem(SessionContext context, Id itemId) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).deleteItem(context, itemId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_DELETE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_DELETE, Module.ZMDP, rte.getMessage()
          , null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_DELETE, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

  @Override
  public Response<Void> updateItemModificationTime(SessionContext context, Id itemId, Date modificationTime) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).updateItemModificationTime(context, itemId,
          modificationTime);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_UPDATE, Module.ZSTM, null,
            response.getReturnCode()));
        //logger.error(response.getReturnCode().toString());
      }

    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_UPDATE, Module.ZMDP, rte.getMessage()
          , null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_UPDATE, Module.ZSTM, null,
          returnCode));
      //logger.error(response.getReturnCode().toString(), rte);
    }
    return response;
  }

}
