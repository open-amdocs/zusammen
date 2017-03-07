package org.amdocs.zusammen.element;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;

import java.util.Collection;

public class GetElementList extends org.amdocs.zusammen.element.Element {


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA, itemId, null);
    Id elementID = initElement(contextA, itemId, versionId);

    GetElementList getElementList = new GetElementList();
    Collection<ElementInfo> elementList = getElementList.getElements(contextA, itemId, versionId,
        null);
    elementList.forEach(elementInfo -> printElement(elementInfo));
    System.exit(0);
  }

  public void printElement(SessionContext context, Id itemId, Id versionId, Id elementId) {
    ElementInfo elementInfo = getElementAdaptor(context).getInfo(context, new ElementContext(itemId,
            versionId),
        elementId).getValue();
    printElement(elementInfo);
  }


  public Collection<ElementInfo> getElements(SessionContext context, Id itemId, Id versionId,
                                             Id elementId) {
    return getElementAdaptor(context)
        .list(context, new ElementContext(itemId, versionId), elementId).getValue();
  }

}
