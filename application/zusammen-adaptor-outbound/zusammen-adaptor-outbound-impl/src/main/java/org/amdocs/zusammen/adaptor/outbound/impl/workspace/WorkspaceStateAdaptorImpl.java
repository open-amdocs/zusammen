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

package org.amdocs.zusammen.adaptor.outbound.impl.workspace;

import org.amdocs.zusammen.adaptor.outbound.api.workspace.WorkspaceStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;

import java.util.Collection;

public class WorkspaceStateAdaptorImpl implements WorkspaceStateAdaptor {

  @Override
  public Response<Void> createWorkspace(SessionContext context, Id workspaceId, Info
      workspaceInfo) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context)
          .createWorkspace(context, workspaceId, workspaceInfo);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_CREATE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_CREATE, Module.MDW, e.getMessage(),
          e.getReturnCode()));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_CREATE, Module.MDW, rte.getMessage(),
          null));
    }

    return response;
  }

  @Override
  public Response<Void> saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).saveWorkspace(context, workspaceId, workspaceInfo);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_SAVE, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_SAVE, Module.MDW, e.getMessage(),
          e.getReturnCode()));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_SAVE, Module.MDW, rte.getMessage(),
          null));
    }

    return response;
  }

  @Override
  public Response<Void> deleteWorkspace(SessionContext context, Id workspaceId) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).deleteWorkspace(context, workspaceId);
    if (!response.isSuccessful()) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_DELETE, Module.MDW, null,
          response.getReturnCode()));
    }
  } catch (ZusammenException e) {
    response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_DELETE, Module.MDW, e
        .getMessage(),
        e.getReturnCode()));
  } catch (RuntimeException rte) {
    response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_DELETE, Module.MDW, rte
        .getMessage(),
        null));
  }
    return response;

  }

  @Override
  public Response<Collection<WorkspaceInfo>> listWorkspaces(SessionContext context) {
    Response response;
    try {
      response = OutboundAdaptorUtils.getStateStore(context).listWorkspaces(context);
      if (!response.isSuccessful()) {
        response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_LIST, Module.MDW, null,
            response.getReturnCode()));
      }
    } catch (ZusammenException e) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_LIST, Module.MDW, e
          .getMessage(),
          e.getReturnCode()));
    } catch (RuntimeException rte) {
      response = new Response(new ReturnCode(ErrorCode.ST_WORKSPACE_LIST, Module.MDW, rte
          .getMessage(),
          null));
    }
    return response;
  }

}
