package org.amdocs.tsuzammen.adaptor.outbound.api;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.net.URI;
import java.util.Collection;

public interface CollaborationAdaptor {

  void createItem(SessionContext context, String itemId, Info info);

  void saveItem(SessionContext context, String itemId, Info itemInfo);

  void deleteItem(SessionContext context, String itemId);

  void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                         String versionId, Info info);

  void saveItemVersion(SessionContext context, String itemId, String versionId,
                       ItemVersion itemVersion, String message);

  void deleteItemVersion(SessionContext context, String itemId, String versionId);

  void publishItemVersion(SessionContext context, String itemId, String versionId, String message);

  void syncItemVersion(SessionContext context, String itemId, String versionId);

  void revertItemVersion(SessionContext context, String itemId, String versionId,
                         String targetRevisionId);

  Collection listItemVersionRevisions(SessionContext context, String itemId, String versionId);

  Collection listItemVersionMissingRevisions(SessionContext context, String itemId,
                                             String versionId);

  Collection listItemVersionOverRevisions(SessionContext context, String itemId, String versionId);

  void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                               URI namespace, Entity entity, Format dataFormat);

  void saveItemVersionEntity(SessionContext context, String itemId, String versionId, URI namespace,
                             Entity entity, Format dataFormat);
}
