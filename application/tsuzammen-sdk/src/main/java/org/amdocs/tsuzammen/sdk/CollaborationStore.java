package org.amdocs.tsuzammen.sdk;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface CollaborationStore {

  void init(SessionContext context);

  void createItem(SessionContext context, Id itemId,Id initialVersion, Info itemInfo);

  void deleteItem(SessionContext context, Id itemId);

  void createItemVersion(SessionContext context, Id itemId, Id baseVersionId, Id versionId,
                         Info versionInfo);

  void saveItemVersion(SessionContext context, Id itemId, Id versionId, ItemVersion itemVersion,
                       String message);

  void deleteItemVersion(SessionContext context, Id itemId, Id versionId);

  void publishItemVersion(SessionContext context, Id itemId, Id versionId, String message);

  void syncItemVersion(SessionContext context, Id itemId, Id versionId);

  ItemVersion getItemVersion(SessionContext context, Id itemId, Id versionId,
                             ItemVersion itemVersion);
}
