package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.Zusammen;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;

public class SyncItemVersion extends ItemVersion{


  public static void main(String[] args) {

    Id itemId = Zusammen.initItem(Zusammen.contextA);
    Id versionId = Zusammen.initVersion(Zusammen.contextA,itemId,null);
    Id elementId = Zusammen.initElement(Zusammen.contextA,itemId,versionId);

    PublishItemVersion publishItemVersion = new PublishItemVersion();
    publishItemVersion.execute(Zusammen.contextA,itemId,versionId,"execute befre execute to user_b");

    SyncItemVersion syncItemVersion = new SyncItemVersion();
    syncItemVersion.execute(Zusammen.contextB,itemId,versionId);
    System.exit(0);
  }

  public MergeResult execute(SessionContext context, Id itemId, Id versionId) {
    return getItemVersionAdaptor(context).sync(context,itemId,versionId).getValue();
  }
}
