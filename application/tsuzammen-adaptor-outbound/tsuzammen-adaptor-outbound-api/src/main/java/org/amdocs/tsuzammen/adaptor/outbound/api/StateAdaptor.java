package org.amdocs.tsuzammen.adaptor.outbound.api;

import org.amdocs.tsuzammen.commons.datatypes.Id;
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
  void createItem(SessionContext context, Id itemId, Info itemInfo);

  void saveItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void validateItemVersionExistence(SessionContext context, Id itemId, Id versionId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId, Id versionId,
                         Info versionInfo);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId, Info versionInfo);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  void publishItemVersion(SessionContext context, Id itemId, Id versionId);

  void syncItemVersion(SessionContext context, Id itemId, Id versionId);

  void saveEntity(SessionContext context, Id itemId, Id versionId, Namespace entityNamespace,
                  Info entityInfo);

  void deleteEntity(SessionContext context, Id itemId, Id versionId, Namespace entityNamespace);

  Info getEntity(SessionContext context, Id itemId, Id versionId, Namespace entityNamespace);

  void deleteContent(SessionContext context, Id itemId, Id versionId, Namespace contentNamespace);

  Map<String, Info> getContent(SessionContext context, Id itemId, Id versionId, Namespace
      contentNamespace);

  void createWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  void saveWorkspace(SessionContext context, Id workspaceId, Info workspaceInfo);

  void deleteWorkspace(SessionContext context, Id workspaceId);

  List<WorkspaceInfo> listWorkspaces(SessionContext context);
}
