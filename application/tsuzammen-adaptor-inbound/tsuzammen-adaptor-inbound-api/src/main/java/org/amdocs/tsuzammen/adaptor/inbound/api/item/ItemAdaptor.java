package org.amdocs.tsuzammen.adaptor.inbound.api.item;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;

/**
 * Created by TALIG on 11/27/2016.
 */
public interface ItemAdaptor {

  ItemVersionKey create(SessionContext context, Info itemInfo);

  void save(SessionContext context, Id itemId, Info itemInfo);

  void delete(SessionContext context, Id itemId);

}
