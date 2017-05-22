package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.Zusammen;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;

public class PublishItemVersion extends ItemVersion {


  public static void main(String[] args) {

    Id itemId = Zusammen.initItem(Zusammen.contextA);
    Id versionId = Zusammen.initVersion(Zusammen.contextA,itemId,null);
    Id elementId = Zusammen.initElement(Zusammen.contextA,itemId,versionId);

    PublishItemVersion publishItemVersioFn = new PublishItemVersion();
    publishItemVersion.execute(Zusammen.contextA,itemId,versionId,"execute from User_a");

    System.exit(0);
  }

  public void execute(SessionContext context, Id itemId, Id versionId, String message) {
    getItemVersionAdaptor(context).publish(context,itemId,
        versionId,message);
  }
}
