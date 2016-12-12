package org.amdocs.tsuzammen.adaptor.outbound.api;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.util.Collection;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface CollaborationAdaptor {

  void createItem(SessionContext context, Id itemId,Id initialBranch, Info info);

  void saveItem(SessionContext context, Id itemId, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId, Id versionId,
                         Info info);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId, ItemVersion itemVersion,
                       String message);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  void publishItemVersion(SessionContext context, Id itemId, Id versionId, String message);

  void syncItemVersion(SessionContext context, Id itemId, Id versionId);

  void revertItemVersion(SessionContext context, Id itemId, Id versionId, String targetRevisionId);

  Collection listItemVersionRevisions(SessionContext context, Id itemId, Id versionId);

  Collection listItemVersionMissingRevisions(SessionContext context, Id itemId, Id versionId);

  Collection listItemVersionOverRevisions(SessionContext context, Id itemId, Id versionId);
}
