package org.amdocs.tsuzammen.core.impl.item;


import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManagerFactory;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

public class ItemManagerImpl implements ItemManager {


  @Override
  public ItemVersionKey create(SessionContext context, Info itemInfo) {
    String itemId = new String(CommonMethods.nextUUID());
    getCollaborationAdaptor(context).createItem(context, itemId,  itemInfo);
    getStateAdaptor(context).createItem(context, itemId, itemInfo);

    ItemVersionManager itemVersionManager = getItemVersionManager(context);

    return new ItemVersionKey(itemId, null);
  }

  @Override
  public void save(SessionContext context, String itemId, Info itemInfo) {
    getCollaborationAdaptor(context).saveItem(context, itemId, itemInfo);
    getStateAdaptor(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void delete(SessionContext context, String itemId) {
    getCollaborationAdaptor(context).deleteItem(context, itemId);
    getStateAdaptor(context).deleteItem(context, itemId);
  }


  private ItemVersionManager getItemVersionManager(SessionContext context) {
    return ItemVersionManagerFactory.getInstance().createInterface(context);
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
