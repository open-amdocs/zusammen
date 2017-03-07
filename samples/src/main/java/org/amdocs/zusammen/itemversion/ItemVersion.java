package org.amdocs.zusammen.itemversion;

import org.amdocs.zusammen.Zusammen;
import org.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import org.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptorFactory;
import org.amdocs.zusammen.datatypes.SessionContext;

public abstract  class ItemVersion extends Zusammen{

  public ItemVersionAdaptor getItemVersionAdaptor(SessionContext context) {
    return ItemVersionAdaptorFactory.getInstance().createInterface(context);
  }


}
