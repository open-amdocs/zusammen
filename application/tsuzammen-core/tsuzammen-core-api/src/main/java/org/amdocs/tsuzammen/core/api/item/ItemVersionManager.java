package org.amdocs.tsuzammen.core.api.item;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.util.Collection;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface ItemVersionManager {

  String create(SessionContext context, String itemId, String baseVersionId, Info versionInfo);

  void save(SessionContext context, String itemId, String versionId, ItemVersion itemVersion, String
      message);

  void delete(SessionContext context, String itemId, String versionId);

  void publish(SessionContext context, String itemId, String versionId, String message);

  void sync(SessionContext context, String itemId, String versionId);

  void revert(SessionContext context, String itemId, String versionId, String targetRevisionId);

  Collection<ItemVersion> get(SessionContext context, String itemId, String versionId);

  Collection<ItemVersion> getInfo(SessionContext context, String itemId, String versionId);
}
