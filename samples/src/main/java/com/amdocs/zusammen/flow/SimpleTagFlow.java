package com.amdocs.zusammen.flow;

import com.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptorFactory;
import com.amdocs.zusammen.element.Element;
import com.amdocs.zusammen.datatypes.Id;


public class SimpleTagFlow extends Element {
  public static void main(String[] args) {
    SimpleTagFlow
        flow = new SimpleTagFlow();
    try {
      flow.execute();
    } catch (RuntimeException e) {
      e.printStackTrace();
      System.exit(1);
    }
    System.exit(0);
  }


  private void execute() {
/*    System.out.println("create Item");
    Id itemId = initItem(contextA);

    System.out.println("create Version");
    Id versionId = initVersion(contextA, itemId, null);

    System.out.println("create Element User A");
    Id elementId = initElement(contextA, itemId, versionId);



*/
    Id itemId = new Id("58c42e80da16436fb4cac66f178af89e");
    Id versionId = new Id("afee07a37c3b489e8c4ba0159fdf33f5");
    Id elementId = new Id("8904d32eca8b4f5998b31985831557a7");

/*    System.out.println("get Element User A");
    Element elementA = getElementData(contextA, itemId, versionId, elementId);

    System.out.println("update Element User A item id:" + itemId + " version id:" + versionId);
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
    updateElementUserA
        .execute(contextA, itemId, versionId, newElementA, "first update User A",
            null);*/

    ItemVersionAdaptor itemVersionAdaptor =
        ItemVersionAdaptorFactory.getInstance().createInterface(contextA);

/*    itemVersionAdaptor.tag(contextA, itemId, versionId, new Id("f353d2f6fb110137595f3002d90fe432e9a88a5e"),
        new Tag("resetHereTag", "tag to reset to"));*/

/*    itemVersionAdaptor.tag(contextA, itemId, versionId,
        new Id("f353d2f6fb110137595f3002d90fe432e9a88a5e"), new Tag("tagName2", "tag desc 2"));*/

    // works with commit id, tag name, annotated tag name, null
    itemVersionAdaptor.resetRevision(contextA, itemId, versionId, null);

  }
}
