package org.amdocs.zusammen.itemversion;

import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;

public class ResetItemVersion extends ItemVersion{


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA,itemId,null);
    Id elementId = initElement(contextA,itemId,versionId);
    ListHistoryItemVersion listHistoryItemVersion = new ListHistoryItemVersion();
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key1","value1");
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key2","value2");
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key3","value3");
    ItemVersionHistory itemVersionHistory = listHistoryItemVersion.execute(contextA,
        itemId,versionId);

    listHistoryItemVersion.printHistory(itemVersionHistory);

    ResetItemVersion resetItemVersion = new ResetItemVersion();
    resetItemVersion.execute(contextA,itemId,versionId,itemVersionHistory.getItemVersionChanges()
        .get(3).getChangeId());
    itemVersionHistory = listHistoryItemVersion.execute(contextA,
        itemId,versionId);

    listHistoryItemVersion.printHistory(itemVersionHistory);
    System.exit(0);
  }

  public void execute(SessionContext context, Id itemId, Id versionId, Id changeId) {
    getItemVersionAdaptor(context).revertHistory(context,itemId,
        versionId,changeId);
  }
}
