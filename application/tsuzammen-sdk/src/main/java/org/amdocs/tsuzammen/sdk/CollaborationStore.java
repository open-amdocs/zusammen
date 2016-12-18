package org.amdocs.tsuzammen.sdk;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityData;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.net.URI;

public interface CollaborationStore {

  void init(SessionContext context);

  void createItem(SessionContext context, String itemId, String initialVersion, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId, Info versionInfo);

  void saveItemVersion(SessionContext context, String itemId, String versionId,
                       ItemVersion itemVersion, String message);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId, String message);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  ItemVersion getItemVersion(SessionContext context, String itemId, String versionId,
                             ItemVersion itemVersion);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, String entityId, EntityData entityData);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                             URI namespace, String entityId, EntityData entityData);

  void deleteItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, String entityId);

  void commitItemVersionEntities(SessionContext context, String itemId, String versionId,
                                 String message);
}
