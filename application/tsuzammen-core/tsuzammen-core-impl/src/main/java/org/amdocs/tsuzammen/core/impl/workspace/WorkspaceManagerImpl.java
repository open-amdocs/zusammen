/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.tsuzammen.core.impl.workspace;

import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;
import org.amdocs.tsuzammen.core.api.workspace.WorkspaceManager;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;

public class WorkspaceManagerImpl implements WorkspaceManager {


  @Override
  public String create(SessionContext context, Info workspaceInfo) {
    String workspaceId = new String(CommonMethods.nextUUID());
    getStateAdaptor(context).createWorkspace(context, workspaceId, workspaceInfo);
    return workspaceId;
  }

  @Override
  public void save(SessionContext context, String workspaceId, Info workspaceInfo) {
    getStateAdaptor(context).saveWorkspace(context, workspaceId, workspaceInfo);
  }

  @Override
  public void delete(SessionContext context, String workspaceId) {
    getStateAdaptor(context).deleteWorkspace(context, workspaceId);
  }

  @Override
  public Collection<WorkspaceInfo> list(SessionContext context) {
    return getStateAdaptor(context).listWorkspaces(context);
  }

  @Override
  public void addItem(SessionContext context, String workspaceId, String itemId, String versionId) {

    //get item from collaborative store
    //save item in state store
    //add item to workspace in the state store

  }

  @Override
  public void removeItem(SessionContext context, String workspaceId, String itemId,
                         String versionId) {

  }

  @Override
  public Collection<ItemVersionKey> listItems(SessionContext context, String workspaceId) {
    return null;
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }


}
