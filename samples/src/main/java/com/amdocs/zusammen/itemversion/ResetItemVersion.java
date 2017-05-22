package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;

public class ResetItemVersion extends ItemVersion {


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA, itemId, null);
    Id elementId = initElement(contextA, itemId, versionId);
    ListHistoryItemVersion listHistoryItemVersion = new ListHistoryItemVersion();
    listHistoryItemVersion.updateElement(contextA, itemId, versionId, elementId, "key1", "value1");
    listHistoryItemVersion.updateElement(contextA, itemId, versionId, elementId, "key2", "value2");
    listHistoryItemVersion.updateElement(contextA, itemId, versionId, elementId, "key3", "value3");
    ItemVersionHistory itemVersionHistory = listHistoryItemVersion.execute(contextA,
        itemId, versionId);

    listHistoryItemVersion.printHistory(itemVersionHistory);

    ResetItemVersion resetItemVersion = new ResetItemVersion();
    resetItemVersion.execute(contextA, itemId, versionId,
        itemVersionHistory.getItemVersionChanges().get(3).getChangeId().toString());
    itemVersionHistory = listHistoryItemVersion.execute(contextA,
        itemId, versionId);

    listHistoryItemVersion.printHistory(itemVersionHistory);
    System.exit(0);
  }

  public void execute(SessionContext context, Id itemId, Id versionId, String changeRef) {
    getItemVersionAdaptor(context).resetHistory(context, itemId, versionId, changeRef);
  }
}
