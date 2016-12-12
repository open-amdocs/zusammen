package org.amdocs.tsuzammen.adaptor.inbound.api.item;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

/**
 * Created by TALIG on 12/4/2016.
 */
public interface ItemVersionAdaptor {

  Id create(SessionContext context, Id itemId, Id baseVersionId, Info versionInfo);

  void save(SessionContext context, Id itemId, Id versionId, ItemVersion itemVersion, String message);

  void publish(SessionContext context, Id itemId, Id versionId, String message);

  void sync(SessionContext context, Id itemId, Id versionId, boolean overrideInd);
}
