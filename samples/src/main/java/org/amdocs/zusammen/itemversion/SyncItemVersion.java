package org.amdocs.zusammen.itemversion;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;

public class SyncItemVersion extends ItemVersion{


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA,itemId,null);
    Id elementId = initElement(contextA,itemId,versionId);

    PublishItemVersion publishItemVersion = new PublishItemVersion();
    publishItemVersion.execute(contextA,itemId,versionId,"execute befre execute to user_b");

    SyncItemVersion syncItemVersion = new SyncItemVersion();
    syncItemVersion.execute(contextB,itemId,versionId);
    System.exit(0);
  }

  public MergeResult execute(SessionContext context, Id itemId, Id versionId) {
    return getItemVersionAdaptor(context).sync(context,itemId,versionId).getValue();
  }
}
