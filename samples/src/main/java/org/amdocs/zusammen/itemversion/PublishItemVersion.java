package org.amdocs.zusammen.itemversion;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;

public class PublishItemVersion extends org.amdocs.zusammen.itemversion.ItemVersion{


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA,itemId,null);
    Id elementId = initElement(contextA,itemId,versionId);

    PublishItemVersion publishItemVersion = new PublishItemVersion();
    publishItemVersion.execute(contextA,itemId,versionId,"execute from User_a");

    System.exit(0);
  }

  public void execute(SessionContext context, Id itemId, Id versionId, String message) {
    getItemVersionAdaptor(context).publish(context,itemId,
        versionId,message);
  }
}
