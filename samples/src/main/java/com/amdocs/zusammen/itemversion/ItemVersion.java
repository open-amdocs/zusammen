package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.Zusammen;
import com.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptorFactory;
import com.amdocs.zusammen.datatypes.SessionContext;

public abstract  class ItemVersion extends Zusammen {

  public ItemVersionAdaptor getItemVersionAdaptor(SessionContext context) {
    return ItemVersionAdaptorFactory.getInstance().createInterface(context);
  }


}
