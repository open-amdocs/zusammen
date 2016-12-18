package org.amdocs.tsuzammen.sdk;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.net.URI;
import java.util.List;

public interface StateStore {

  void createItem(SessionContext context, String itemId, Info itemInfo);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId, Info versionInfo);

  void saveItemVersion(SessionContext context, String itemId, String versionId, Info versionInfo);

  void publishItemVersion(SessionContext context, String itemId, String versionId);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, String entityId, EntityInfo entityInfo);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                             URI namespace, String entityId, EntityInfo entityInfo);

  void deleteItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, String entityId);

  void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, String workspaceId);

  List<WorkspaceInfo> listWorkspaces(SessionContext context);
}
