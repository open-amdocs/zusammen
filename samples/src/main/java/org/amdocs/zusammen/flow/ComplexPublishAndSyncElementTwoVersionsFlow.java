package org.amdocs.zusammen.flow;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.datatypes.Id;

import java.util.Collection;

public class ComplexPublishAndSyncElementTwoVersionsFlow
    extends org.amdocs.zusammen.element.Element {

  public static void main(String[] args) {
    ComplexPublishAndSyncElementTwoVersionsFlow
        flow = new ComplexPublishAndSyncElementTwoVersionsFlow();
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
    publishItemVersion(contextA, itemId, versionId, "Publish empty version");
    syncItemVersion(contextB, itemId, versionId);

    Id newVersionId = initVersion(contextA, itemId, versionId);
    Id elementId = initElement(contextA, itemId, newVersionId);
    publishItemVersion(contextA, itemId, newVersionId,
        "Publish new version based on previous with one element");
    syncItemVersion(contextB, itemId, newVersionId);

    System.out.println("======================print B Element List==============================");
    Collection<ElementInfo> elementsB = getElementList(contextB, itemId, newVersionId, null);
    elementsB.forEach(org.amdocs.zusammen.element.Element::printElement);
    System.out.println("======================print B Element Info==============================");
    ElementInfo elementInfoB = getElementInfo(contextB, itemId, newVersionId, elementId);
    printElement(elementInfoB);
    System.out.println("======================print B Element Data==============================");
    Element elementB = getElementData(contextB, itemId, newVersionId, elementId);
    printElementData(elementB);
  }
}
