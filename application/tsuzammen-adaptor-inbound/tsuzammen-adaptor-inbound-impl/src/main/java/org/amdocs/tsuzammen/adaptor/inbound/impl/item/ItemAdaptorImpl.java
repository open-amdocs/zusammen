package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.core.api.item.ItemManagerFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public class ItemAdaptorImpl implements ItemAdaptor {


  @Override
  public ItemVersionKey create(SessionContext context, Info itemInfo) {

    return getItemManager(context).create(context, itemInfo);
  }

  @Override
  public void save(SessionContext context, Id itemId, Info itemInfo) {

    getItemManager(context).save(context, itemId, itemInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId) {

    getItemManager(context).delete(context, itemId);
  }



  private ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
