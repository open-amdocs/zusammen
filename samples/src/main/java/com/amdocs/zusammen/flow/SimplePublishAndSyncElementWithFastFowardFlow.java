package com.amdocs.zusammen.flow;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.element.CreateElement;
import com.amdocs.zusammen.element.ElementScenarios;
import com.amdocs.zusammen.utils.fileutils.FileUtils;

import java.util.Collection;

public class SimplePublishAndSyncElementWithFastFowardFlow
    extends com.amdocs.zusammen.element.Element {

  public static void main(String[] args) {
    SimplePublishAndSyncElementWithFastFowardFlow
        flow = new SimplePublishAndSyncElementWithFastFowardFlow();
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
    Info newInfo = elementA.getInfo();
    newInfo.getProperties().put("step", "first update User A");
    Element newElement = ElementScenarios.prepareElement(elementId, Action.UPDATE, newInfo, elementA
            .getRelations(),
        new String(FileUtils.toByteArray(elementA.getData())),
        new String(FileUtils.toByteArray(elementA.getSearchableData())),
        new String(FileUtils.toByteArray(elementA.getVisualization())),
        elementA.getSubElements());
    System.out.println("update Element User A item id:" + itemId + " version id:" + versionId);

    updateElementUserA.execute(contextA, itemId, versionId, newElement, "first update User A",
        null);

    publishItemVersion(contextA, itemId, versionId,
        "execute User A after execute item id:" + itemId + " version id:" + versionId);

    CreateElement updateElementUserB = new CreateElement();

    newElement = ElementScenarios.prepareElement(elementId, Action.UPDATE,
        elementA.getInfo(),
        elementA.getRelations(),
        "first update User B",
        new String(FileUtils.toByteArray(elementA.getSearchableData())),
        new String(FileUtils.toByteArray(elementA.getVisualization())),
        elementA.getSubElements());

    System.out.println("update Element User B item id:" + itemId + " version id:" + versionId);
    updateElementUserB.execute(contextB, itemId, versionId, newElement,
        "first update User B", null);
    System.out.println("execute User B");
    syncItemVersion(contextB, itemId, versionId);
    System.out.println("execute User B");
    publishItemVersion(contextB, itemId, versionId,
        "execute User B after second execute item id:" + itemId + " version id:" + versionId);
    System.out.println("execute User A");
    syncItemVersion(contextA, itemId, versionId);


    Collection<ElementInfo> list =
        getElementList(contextA, itemId, versionId, null);
    System.out.println("======================print A Element " +
        "List=================================");
    list.forEach(com.amdocs.zusammen.element.Element::printElement);
    ElementInfo info = getElementInfo(contextA, itemId, versionId, elementId);
    System.out.println("======================print A Element " +
        "Info=================================");
    printElement(info);

    System.out.println("======================print Element Data A" +
        "=================================");
    Element data = getElementData(contextA, itemId, versionId, elementId);
    printElementData(data);
    System.out.println("======================print Element Data B" +
        "=================================");
    data = getElementData(contextB, itemId, versionId, elementId);
    printElementData(data);


  }

}
