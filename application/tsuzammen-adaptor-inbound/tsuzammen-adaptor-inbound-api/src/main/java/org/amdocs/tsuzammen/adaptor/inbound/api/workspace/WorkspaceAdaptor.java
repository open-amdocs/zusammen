package org.amdocs.tsuzammen.adaptor.inbound.api.workspace;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.util.List;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface WorkspaceAdaptor {

  List<WorkspaceInfo> list(SessionContext context);

  Id create(SessionContext context, Info workspaceInfo);

  void save(SessionContext context, Id workspaceId, Info workspaceInfo);

  void delete(SessionContext context, Id workspaceId);

  void addItem(SessionContext context, Id workspaceId, Id itemId, Id versionId);

  void removeItem(SessionContext context, Id workspaceId, Id itemId, Id versionId);

  List<ItemVersionKey> listItems(SessionContext context, Id workspaceId);

}
