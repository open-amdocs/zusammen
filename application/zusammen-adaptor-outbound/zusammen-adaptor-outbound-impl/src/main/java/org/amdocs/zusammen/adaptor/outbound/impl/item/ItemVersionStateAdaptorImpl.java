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

import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;

import java.util.Collection;

public class ItemVersionStateAdaptorImpl implements ItemVersionStateAdaptor {

  @Override
  public Response<Collection<ItemVersion>> listItemVersions(SessionContext context, Space space,
                                                            Id itemId) {
    Response<Collection<ItemVersion>> response;
    try {
      response =
          OutboundAdaptorUtils.getStateStore(context).listItemVersions(context, space, itemId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSIONS_LIST, Module.ZSTM, null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_VERSIONS_LIST, Module.ZMDP, rte
          .getMessage(),
          null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSIONS_LIST, Module.ZSTM, null,
          returnCode));
    }
    return response;
  }

  @Override
  public Response<Boolean> isItemVersionExist(SessionContext context, Space space, Id
      itemId, Id versionId) {
    Response<Boolean> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .isItemVersionExist(context, space, itemId, versionId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_IS_EXIST, Module.ZSTM,
            null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_VERSION_IS_EXIST, Module.ZMDP, rte
          .getMessage(),
          null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_IS_EXIST, Module.ZSTM,
          null,
          returnCode));
    }
    return response;
  }

  @Override
  public Response<ItemVersion> getItemVersion(SessionContext context, Space space, Id
      itemId, Id versionId) {
    Response<ItemVersion> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .getItemVersion(context, space, itemId, versionId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_GET, Module.ZSTM, null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_VERSION_GET, Module.ZMDP, rte
          .getMessage(),
          null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_GET, Module.ZSTM, null,
          returnCode));
    }
    return response;
  }

  @Override
  public Response<Void> createItemVersion(SessionContext context, Space space, Id itemId,
                                          Id baseVersionId,
                                          Id versionId, ItemVersionData data) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .createItemVersion(context, space, itemId, baseVersionId, versionId, data);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_CREATE, Module.ZSTM, null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ITEM_VERSION_CREATE, Module.ZMDP, rte
          .getMessage(), null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_CREATE, Module.ZSTM, null,
          returnCode));
    }
    return response;
  }

  @Override
  public Response<Void> updateItemVersion(SessionContext context, Space space, Id itemId,
                                          Id versionId,ItemVersionData data) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .updateItemVersion(context, space, itemId, versionId, data);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_UPDATE, Module.ZSTM, null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ST_ITEM_VERSION_UPDATE, Module.ZMDP, rte.getMessage(),
              null);
      response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_UPDATE, Module.ZSTM, null,
          returnCode));
    }
    return response;
  }

  @Override
  public Response<Void> deleteItemVersion(SessionContext context, Space space, Id itemId,
                                          Id versionId) {
    Response<Void> response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .deleteItemVersion(context, space, itemId, versionId);
      if (!response.isSuccessful()) {
        response = new Response<>(new ReturnCode(ErrorCode.MD_ITEM_VERSION_DELETE, Module.ZSTM, null,
            response.getReturnCode()));
      }
    } catch (RuntimeException rte) {
      ReturnCode returnCode =new ReturnCode(ErrorCode.ST_ITEM_VERSION_DELETE, Module.ZMDP, rte
          .getMessage(),
          null);
      response = new Response<>(
          new ReturnCode(ErrorCode.MD_ITEM_VERSION_DELETE, Module.ZSTM, null,
              returnCode));
    }
    return response;
  }
}
