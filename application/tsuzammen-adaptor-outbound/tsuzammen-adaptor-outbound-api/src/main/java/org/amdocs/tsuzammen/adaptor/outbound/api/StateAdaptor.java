package org.amdocs.tsuzammen.adaptor.outbound.api;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.Namespace;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.workspace.WorkspaceInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by TALIG on 11/30/2016.
 */
public interface StateAdaptor {
  void createItem(SessionContext context, String itemId, Info itemInfo);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void validateItemVersionExistence(SessionContext context, String itemId, String versionId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId, String versionId,
                         Info versionInfo);

  void saveItemVersion(SessionContext context, String itemId, String versionId, Info versionInfo);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void saveEntity(SessionContext context, String itemId, String versionId, Namespace entityNamespace,
                  Info entityInfo);

  void deleteEntity(SessionContext context, String itemId, String versionId, Namespace entityNamespace);

  Info getEntity(SessionContext context, String itemId, String versionId, Namespace entityNamespace);

  void deleteContent(SessionContext context, String itemId, String versionId, Namespace contentNamespace);

  Map<String, Info> getContent(SessionContext context, String itemId, String versionId, Namespace
      contentNamespace);

  void createWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, String workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, String workspaceId);

  List<WorkspaceInfo> listWorkspaces(SessionContext context);
}
