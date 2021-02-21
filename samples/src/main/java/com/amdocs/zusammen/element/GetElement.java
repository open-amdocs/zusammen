package com.amdocs.zusammen.element;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.ZusammenElement;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.datatypes.itemversion.Revision;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.itemversion.GetItemVersion;

import static com.amdocs.zusammen.Zusammen.*;

public class GetElement extends Element {

    public static void main(String[] args) {
        //getElementWithoutRevisionId();
        getElementWithRevisionId();

        System.exit(0);
    }

    private static void getElementWithoutRevisionId() {
        Id itemId = initItem(contextA);
        Id versionId = initVersion(contextA, itemId, null);
        ElementContext elementContext = new ElementContext(itemId, versionId, null);
        Id elementId = initElement(contextA, itemId, versionId);

        GetElement getElement = new GetElement();

        Response<com.amdocs.zusammen.adaptor.inbound.api.types.item.Element> element = getElement.getElementAdaptor(contextA).get(contextA,Space.PRIVATE, elementContext, elementId);
        System.out.println("name PRIVATE= " + element.getValue().getInfo().getName());

        GetItemVersion getItemVersion = new GetItemVersion();
        getItemVersion.getItemVersionAdaptor(contextA).publish(contextA, itemId, versionId, "first push");

        ZusammenElement zusammenElement = (ZusammenElement) element.getValue();
        zusammenElement.getInfo().setName("changeName");
        zusammenElement.setAction(Action.UPDATE);

        getElement.getElementAdaptor(contextA).save(contextA, elementContext, zusammenElement, "change name");

        Response<com.amdocs.zusammen.datatypes.item.ItemVersion> itemVersion = getItemVersion.getItemVersionAdaptor(contextA).get(contextA, Space.PUBLIC, itemId, versionId);
        getItemVersion.getItemVersionAdaptor(contextA).update(contextA, itemId, versionId, itemVersion.getValue().getData());

        element = getElement.getElementAdaptor(contextA).get(contextA,Space.PUBLIC, elementContext, elementId);
        System.out.println("name PUBLIC= " + element.getValue().getInfo().getName());

        element = getElement.getElementAdaptor(contextA).get(contextA,Space.PRIVATE, elementContext, elementId);
        System.out.println("name PRIVATE= " + element.getValue().getInfo().getName());
    }

    private static void getElementWithRevisionId() {
        Id itemId = initItem(contextA);
        Id versionId = initVersion(contextA, itemId, null);
        ElementContext elementContext = new ElementContext(itemId, versionId, null);
        Id elementId = initElement(contextA, itemId, versionId);

        GetElement getElement = new GetElement();

        Response<com.amdocs.zusammen.adaptor.inbound.api.types.item.Element> element = getElement.getElementAdaptor(contextA).get(contextA,Space.PRIVATE, elementContext, elementId);
        System.out.println("name PRIVATE= " + element.getValue().getInfo().getName());


        ZusammenElement zusammenElement = (ZusammenElement) element.getValue();
        zusammenElement.getInfo().setName("changeName1");
        zusammenElement.setAction(Action.UPDATE);

        getElement.getElementAdaptor(contextA).save(contextA, elementContext, zusammenElement, "change name");

        GetItemVersion getItemVersion = new GetItemVersion();
        getItemVersion.getItemVersionAdaptor(contextA).publish(contextA, itemId, versionId, "first push");

        zusammenElement.getInfo().setName("changeName2");
        zusammenElement.setAction(Action.UPDATE);

        getElement.getElementAdaptor(contextA).save(contextA, elementContext, zusammenElement, "change name");

        getItemVersion = new GetItemVersion();
        getItemVersion.getItemVersionAdaptor(contextA).publish(contextA, itemId, versionId, "second push");

        Response<com.amdocs.zusammen.datatypes.item.ItemVersion> itemVersion = getItemVersion.getItemVersionAdaptor(contextA).get(contextA, Space.PUBLIC, itemId, versionId);
        getItemVersion.getItemVersionAdaptor(contextA).update(contextA, itemId, versionId, itemVersion.getValue().getData());

        Response<ItemVersionRevisions> revisionList = getItemVersion.getItemVersionAdaptor(contextA).listRevisions(contextA, itemId, versionId);
        Revision revision = revisionList.getValue().getItemVersionRevisions().get(1); //first push

        elementContext.setRevisionId(revision.getRevisionId());

        element = getElement.getElementAdaptor(contextA).get(contextA,Space.PUBLIC, elementContext, elementId);
        System.out.println("name PUBLIC= " + element.getValue().getInfo().getName());

        element = getElement.getElementAdaptor(contextA).get(contextA,Space.PRIVATE, elementContext, elementId);
        System.out.println("name PRIVATE= " + element.getValue().getInfo().getName());
    }

}
