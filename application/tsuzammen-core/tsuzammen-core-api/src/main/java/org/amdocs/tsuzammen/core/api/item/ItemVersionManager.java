package org.amdocs.tsuzammen.core.api.item;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.util.Collection;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface ItemVersionManager {

  Id create(SessionContext context, Id itemId, Id baseVersionId, Info versionInfo);

  void save(SessionContext context, Id itemId, Id versionId, ItemVersion itemVersion, String
      message);

  void delete(SessionContext context, Id itemId, Id versionId);

  void publish(SessionContext context, Id itemId, Id versionId, String message);

  void sync(SessionContext context, Id itemId, Id versionId);

  void revert(SessionContext context, Id itemId, Id versionId, String targetRevisionId);

  Collection<ItemVersion> get(SessionContext context, Id itemId, Id versionId);

  Collection<ItemVersion> getInfo(SessionContext context, Id itemId, Id versionId);
}
