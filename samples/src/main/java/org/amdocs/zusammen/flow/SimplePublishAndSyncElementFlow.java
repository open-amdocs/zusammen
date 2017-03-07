package org.amdocs.zusammen.flow;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.datatypes.Id;

import java.util.Collection;


public class SimplePublishAndSyncElementFlow extends org.amdocs.zusammen.element.Element {

  public static void main(String[] args) {
    SimplePublishAndSyncElementFlow flow = new SimplePublishAndSyncElementFlow();
    try {
      flow.execute();
    } catch (RuntimeException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.exit(0);


  }

  private void execute() {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA, itemId, null);
    Id elementId = initElement(contextA, itemId, versionId);
    publishItemVersion(contextA, itemId, versionId, "UaCiCitCeCsePivUbSivGedFlow");
    syncItemVersion(contextB, itemId, versionId);


    Collection<ElementInfo> list =
        getElementList(contextB, itemId, versionId, null);
    System.out.println("======================print Element List=================================");
    list.forEach(elementInfo -> printElement(elementInfo));
    ElementInfo info = getElementInfo(contextB, itemId, versionId, elementId);
    System.out.println("======================print Element Info=================================");
    printElement(info);
    Element data =
        getElementData(contextB, itemId, versionId, elementId);
    System.out.println("======================print Element Data=================================");
    printElementData(data);


  }

}
