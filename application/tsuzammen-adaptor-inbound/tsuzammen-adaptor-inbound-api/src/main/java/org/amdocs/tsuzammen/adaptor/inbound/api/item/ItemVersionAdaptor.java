package org.amdocs.tsuzammen.adaptor.inbound.api.item;


import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

/**
 * Created by TALIG on 12/4/2016.
 */
public interface ItemVersionAdaptor {

  String create(SessionContext context, String itemId, String baseVersionId, Info versionInfo);

  void save(SessionContext context, String itemId, String versionId, ItemVersion itemVersion, String message);

  void publish(SessionContext context, String itemId, String versionId, String message);

  void sync(SessionContext context, String itemId, String versionId, boolean overrideInd);
}
