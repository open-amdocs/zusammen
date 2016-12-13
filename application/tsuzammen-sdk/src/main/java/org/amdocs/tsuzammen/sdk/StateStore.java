package org.amdocs.tsuzammen.sdk;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.util.List;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface StateStore {

  void createItem(SessionContext context, String itemId, Info itemInfo);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId, String versionId,
                         Info versionInfo);

  void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, String workspaceId);

  List<WorkspaceInfo> listWorkspaces(SessionContext context);
}
