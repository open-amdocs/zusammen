package com.amdocs.zusammen.flow;

import com.amdocs.zusammen.element.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.element.CreateElement;
import com.amdocs.zusammen.element.ElementScenarios;
import com.amdocs.zusammen.utils.fileutils.FileUtils;

public class SimplePublishAndSyncElementWithConflictFlow
    extends Element {

  public static void main(String[] args) {
    SimplePublishAndSyncElementWithConflictFlow
        flow = new SimplePublishAndSyncElementWithConflictFlow();
    try {
      flow.execute();
    } catch (RuntimeException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.exit(0);
  }

  private void execute() {
    System.out.println("create Item");
    Id itemId = initItem(contextA);
    System.out.println("create Version");
    Id versionId = initVersion(contextA, itemId, null);
    System.out.println("create Element User A");
    Id elementId = initElement(contextA, itemId, versionId);
    System.out.println("Publish User A");
    publishItemVersion(contextA, itemId, versionId, "UaCiCitCeCsePivUbSivGedFlow");
    System.out.println("Sync User B");
    syncItemVersion(contextB, itemId, versionId);
    System.out.println("get Element User A");
    Element elementA =
        getElementData(contextA, itemId, versionId, elementId);
    printElementData(elementA);
    System.out.println("get Element User B");
    Element elementB =
        getElementData(contextB, itemId, versionId, elementId);
    printElementData(elementB);

    CreateElement updateElementUserA = new CreateElement();
    Info newInfoA = elementA.getInfo();
    newInfoA.getProperties().put("step", "first update User A");
    Element newElementA = ElementScenarios.prepareElement(elementId, Action.UPDATE,
        newInfoA,
        elementA.getRelations(),
        new String(FileUtils.toByteArray(elementA.getData())),
        new String(FileUtils.toByteArray(elementA.getSearchableData())),
        new String(FileUtils.toByteArray(elementA.getVisualization())),
        elementA.getSubElements());
    System.out.println("update Element User A item id:" + itemId + " version id:" + versionId);

    updateElementUserA
        .execute(contextA, itemId, versionId, newElementA, "first update User A",
            null);

    publishItemVersion(contextA, itemId, versionId,
        "execute User A after execute item id:" + itemId + " version id:" + versionId);

    CreateElement updateElementUserB = new CreateElement();


    Info newInfoB = elementB.getInfo();
    newInfoB.getProperties().put("step", "first update User B");
    Element newElementB = ElementScenarios.prepareElement(elementId, Action.UPDATE,
        newInfoB,
        elementB.getRelations(),
        new String(FileUtils.toByteArray(elementB.getData())),
        new String(FileUtils.toByteArray(elementB.getSearchableData())),
        new String(FileUtils.toByteArray(elementB.getVisualization())),
        elementB.getSubElements());

    System.out.println("update Element User B item id:" + itemId + " version id:" + versionId);
    updateElementUserB.execute(contextB, itemId, versionId, newElementB,
        "first update User B", null);
    System.out.println("execute User B");
    MergeResult syncResult = syncItemVersion(contextB, itemId, versionId);
    System.out
        .println("execute status:" + syncResult.isSuccess() + " number of conflicts:" + syncResult
            .getConflict().getElementConflicts().size());

  }

}
