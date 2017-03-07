package org.amdocs.zusammen.flow;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.element.CreateElement;
import org.amdocs.zusammen.element.ElementScenarios;

import java.util.Collection;
import java.util.UUID;

public class SimplePublishAndSyncRandomElementWithUpdateFlow
    extends org.amdocs.zusammen.element.Element {


  private static Element elementTree;

  public static void main(String[] args) {
    SimplePublishAndSyncRandomElementWithUpdateFlow
        flow = new SimplePublishAndSyncRandomElementWithUpdateFlow();
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

    Element newElement = loadElementTree(contextB, itemId, versionId);
    ElementScenarios.getInstance().updateElementTree(newElement);
    CreateElement createElement = new CreateElement();
    createElement.execute(contextB, itemId, versionId, newElement, "update tree", null);


  }


  private Element loadElementTree(SessionContext context, Id itemId, Id versionId) {
    Element element;
    Collection<ElementInfo> elementList = getElementList(contextB, itemId, versionId, null);
    if (elementList.size() > 0) {
      ElementInfo elementInfo = elementList.iterator().next();
      element = getElementData(context, itemId, versionId, elementInfo.getId());
      scanElement(context, itemId, versionId, element);
      return element;
    }
    throw new RuntimeException("element root not returned");

  }

  private Element scanElement(SessionContext context, Id itemId, Id versionId, Element element) {

    Element elementData = getElementData(context, itemId, versionId, element.getElementId());
    Collection<Element> subElements = elementData.getSubElements();
    element.getSubElements().clear();

    for (Element subElement : subElements) {
      element.getSubElements().add(scanElement(context, itemId, versionId, subElement));
    }
    return elementData;
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
