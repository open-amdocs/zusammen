package org.amdocs.tsuzammen.adaptor.outbound.api;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.net.URI;
import java.util.List;

public interface StateAdaptor {
  void createItem(SessionContext context, String itemId, Info itemInfo);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void validateItemVersionExistence(SessionContext context, String itemId, String versionId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId,
                         Info versionInfo);

  void saveItemVersion(SessionContext context, String itemId, String versionId, Info versionInfo);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, Entity entity);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                             URI namespace, Entity entity);

  void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, String workspaceId);

  List<WorkspaceInfo> listWorkspaces(SessionContext context);
}
