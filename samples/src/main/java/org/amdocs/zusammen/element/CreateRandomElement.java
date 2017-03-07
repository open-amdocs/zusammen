package org.amdocs.zusammen.element;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.item.CreateItem;
import org.amdocs.zusammen.itemversion.CreateItemVersion;

import java.util.Collection;
import java.util.UUID;

public class CreateRandomElement extends org.amdocs.zusammen.element.Element {


  public static void main(String[] args) {
    try {

      CreateItem createItem = new CreateItem();
      Info itemInfo = createItem.prepareItemInfo();
      Id itemId = createItem.createItem(contextA, itemInfo);
      System.out.println("item id:" + itemId);

      CreateItemVersion createItemVersion = new CreateItemVersion();
      ItemVersionData itemversionData = createItemVersion.prepareItemVersionData();
      Id versionId = createItemVersion.execute(contextA, itemId, null, itemversionData);
      System.out.println("version id:" + versionId);

      CreateRandomElement createElement = new CreateRandomElement();
      Element element = ElementScenarios.getInstance().getRandomTreeElement(2, 4, 10);
      long start = System.currentTimeMillis();
      Id elementId =
          createElement.execute(contextA, itemId, versionId, element, "create root " +
              "element", UUID.randomUUID().toString());
      System.out.println("element id:" + elementId);
    } finally {
      System.exit(0);
    }

  }

  public Id execute(SessionContext context, Id itemId, Id versionId, Element element, String
      message, String identifier) {

    ElementContext elementContext = new ElementContext(itemId, versionId);
    element.getInfo().getProperties().put("Identifier", identifier);
    long before = System.currentTimeMillis();
    getElementAdaptor(context).save(context, elementContext, element, message);
    long after = System.currentTimeMillis();


    System.out.println("time to save (sec):" + ((int) (after - before) / 1000));
    Id elementId = getElementId(context, itemId, versionId, identifier);
    return elementId;
  }

  public Id getElementId(SessionContext context, Id itemId, Id versionId, String
      identifier) {
    Collection<ElementInfo> list =
        getElementAdaptor(context).list(context, new ElementContext(itemId, versionId), null).getValue();
    for (ElementInfo elementInfo : list) {
      if (elementInfo.getInfo().getProperties().get("Identifier").equals(identifier)) {
        return elementInfo.getId();
      }
    }

    return null;
  }


}
