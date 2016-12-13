package org.amdocs.tsuzammen.core.api.workspace;


import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.util.List;

public interface WorkspaceManager {

  String create(SessionContext context, Info workspaceInfo);

  void save(SessionContext context, String workspaceId, Info workspaceInfo);

  void delete(SessionContext context, String workspaceId);

  List<WorkspaceInfo> list(SessionContext context);

  void addItem(SessionContext context, String workspaceId, String itemId, String versionId);

  void removeItem(SessionContext context, String workspaceId, String itemId, String versionId);

  List<ItemVersionKey> listItems(SessionContext context, String workspaceId);
}
