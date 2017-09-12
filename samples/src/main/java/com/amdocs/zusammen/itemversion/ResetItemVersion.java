package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;

public class ResetItemVersion extends ItemVersion {


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA, itemId, null);
    Id elementId = initElement(contextA, itemId, versionId);
    ListRevisionItemVersion listRevisionItemVersion = new ListRevisionItemVersion();
    listRevisionItemVersion.updateElement(contextA, itemId, versionId, elementId, "key1", "value1");
    listRevisionItemVersion.updateElement(contextA, itemId, versionId, elementId, "key2", "value2");
    listRevisionItemVersion.updateElement(contextA, itemId, versionId, elementId, "key3", "value3");
    ItemVersionRevisions itemVersionRevisions = listRevisionItemVersion.execute(contextA,
        itemId, versionId);

    listRevisionItemVersion.printHistory(itemVersionRevisions);

    ResetItemVersion resetItemVersion = new ResetItemVersion();
    resetItemVersion.execute(contextA, itemId, versionId,
        itemVersionRevisions.getItemVersionRevisions().get(3).getRevisionId().toString());
    itemVersionRevisions = listRevisionItemVersion.execute(contextA,
        itemId, versionId);

    listRevisionItemVersion.printHistory(itemVersionRevisions);
    System.exit(0);
  }

  public void execute(SessionContext context, Id itemId, Id versionId, String changeRef) {
    getItemVersionAdaptor(context).resetRevision(context, itemId, versionId, new Id(changeRef));
  }
}
