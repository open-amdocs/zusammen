package org.amdocs.tsuzammen.adaptor.inbound.api.workspace;

import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.util.Collection;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface WorkspaceAdaptor {

  Collection<WorkspaceInfo> list(SessionContext context);

  String create(SessionContext context, Info workspaceInfo);

  void save(SessionContext context, String workspaceId, Info workspaceInfo);

  void delete(SessionContext context, String workspaceId);

  void addItem(SessionContext context, String workspaceId, String itemId, String versionId);

  void removeItem(SessionContext context, String workspaceId, String itemId, String versionId);

  Collection<ItemVersionKey> listItems(SessionContext context, String workspaceId);

}
