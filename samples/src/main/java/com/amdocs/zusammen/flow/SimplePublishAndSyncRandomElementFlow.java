package com.amdocs.zusammen.flow;

import com.amdocs.zusammen.element.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.element.CreateElement;
import com.amdocs.zusammen.element.ElementScenarios;

import java.util.Collection;
import java.util.UUID;

public class SimplePublishAndSyncRandomElementFlow extends Element {


  private static Element elementTree;

  public static void main(String[] args) {
    SimplePublishAndSyncRandomElementFlow flow = new SimplePublishAndSyncRandomElementFlow();
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

    Id elementId = initRandomElement(contextA, itemId, versionId);
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

  private Id initRandomElement(SessionContext context, Id itemId, Id versionId) {
    Element element = ElementScenarios.getInstance().getRandomTreeElement(2, 4, 10);
    CreateElement createElement = new CreateElement();
    String identifier = UUID.randomUUID().toString();
    this.elementTree = element;
    return createElement.execute(context, itemId, versionId, element, "random element",
        identifier);


  }

}
