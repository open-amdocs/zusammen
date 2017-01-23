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

package org.amdocs.zusammen.core.impl.workspace;

import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.workspace.WorkspaceStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.workspace.WorkspaceStateAdaptorFactory;
import org.amdocs.zusammen.core.api.workspace.WorkspaceManager;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.ItemVersionKey;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;

import java.util.Collection;

public class WorkspaceManagerImpl implements WorkspaceManager {


  @Override
  public Id create(SessionContext context, Info workspaceInfo) {
    Id workspaceId = new Id();
    getStateAdaptor(context).createWorkspace(context, workspaceId, workspaceInfo);
    return workspaceId;
  }

  @Override
  public void save(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getStateAdaptor(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void delete(SessionContext context, Id workspaceId) {
    getStateAdaptor(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public Collection<WorkspaceInfo> list(SessionContext context) {
    return getStateAdaptor(context).listWorkspaces(context);
  }

  @Override
  public void addItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {

    //get item from collaborative store
    //update item in state store
    //add item to workspace in the state store

  }

  @Override
  public void removeItem(SessionContext context, Id workspaceId, Id itemId,
                         Id versionId) {

  }

  @Override
  public Collection<ItemVersionKey> listItems(SessionContext context, Id workspaceId) {
    return null;
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private WorkspaceStateAdaptor getStateAdaptor(SessionContext context) {
    return WorkspaceStateAdaptorFactory.getInstance().createInterface(context);
  }


}
