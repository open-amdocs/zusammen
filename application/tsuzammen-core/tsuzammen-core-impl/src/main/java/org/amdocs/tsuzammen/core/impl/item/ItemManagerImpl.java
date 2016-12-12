package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.ItemVersionKey;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManagerFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

public class ItemManagerImpl implements ItemManager {

  private static final String CREATE_ITEM_PUBLISH_MSG = "Publish created item";
  private static final Id MAIN_VERSION = new Id("main");
  @Override
  public ItemVersionKey create(SessionContext context, Info itemInfo) {
    Id itemId = new Id(CommonMethods.nextUUID());
    getCollaborationAdaptor(context).createItem(context, itemId,MAIN_VERSION, itemInfo);
    getStateAdaptor(context).createItem(context, itemId, itemInfo);

    ItemVersionManager itemVersionManager = getItemVersionManager(context);
    //Id versionId = itemVersionManager.create(context, itemId, null);
    //itemVersionManager.publish(context, itemId, versionId, CREATE_ITEM_PUBLISH_MSG);

    return new ItemVersionKey(itemId, MAIN_VERSION);
  }

  @Override
  public void save(SessionContext context, Id itemId, Info itemInfo) {
    getCollaborationAdaptor(context).saveItem(context, itemId, itemInfo);
    getStateAdaptor(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId) {
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
