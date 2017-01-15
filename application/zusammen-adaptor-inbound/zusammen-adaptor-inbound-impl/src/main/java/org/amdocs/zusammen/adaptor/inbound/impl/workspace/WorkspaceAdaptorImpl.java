/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.zusammen.adaptor.inbound.impl.workspace;

import org.amdocs.zusammen.adaptor.inbound.api.workspace.WorkspaceAdaptor;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.ItemVersionKey;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.workspace.WorkspaceInfo;
import org.amdocs.zusammen.core.api.workspace.WorkspaceManager;
import org.amdocs.zusammen.core.api.workspace.WorkspaceManagerFactory;

import java.util.Collection;

public class WorkspaceAdaptorImpl implements WorkspaceAdaptor {

  @Override
  public Collection<WorkspaceInfo> list(SessionContext context) {
    return getWorkspaceManager(context).list(context);
  }

  @Override
  public Id create(SessionContext context, Info workspaceInfo) {
    return getWorkspaceManager(context).create(context, workspaceInfo);
  }

  @Override
  public void save(SessionContext context, Id workspaceId, Info workspaceInfo) {
    getWorkspaceManager(context).save(context, workspaceId, workspaceInfo);
  }

  @Override
  public void delete(SessionContext context, Id workspaceId) {
    getWorkspaceManager(context).delete(context, workspaceId);
  }

  @Override
  public void addItem(SessionContext context, Id workspaceId, Id itemId, Id versionId) {
    getWorkspaceManager(context).addItem(context, workspaceId, itemId, versionId);
  }

  @Override
  public void removeItem(SessionContext context, Id workspaceId, Id itemId,
                         Id versionId) {
    getWorkspaceManager(context).removeItem(context, workspaceId, itemId, versionId);
  }

  @Override
  public Collection<ItemVersionKey> listItems(SessionContext context, Id workspaceId) {
    return getWorkspaceManager(context).listItems(context, workspaceId);
  }

  private WorkspaceManager getWorkspaceManager(SessionContext context) {
    return WorkspaceManagerFactory.getInstance().createInterface(context);
  }

}
