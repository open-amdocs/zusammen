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
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;

import java.util.Collection;

public class WorkspaceStateAdaptorImpl implements WorkspaceStateAdaptor {

  @Override
  public void createWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo) {
    OutboundAdaptorUtils.getStateStore(context)
        .createWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo) {
    OutboundAdaptorUtils.getStateStore(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void deleteWorkspace(SessionContext context, Id workspaceId) {
    OutboundAdaptorUtils.getStateStore(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public Collection<WorkspaceInfo> listWorkspaces(SessionContext context) {
    return OutboundAdaptorUtils.getStateStore(context).listWorkspaces(context);
  }

}
