package org.amdocs.zusammen;

import org.amdocs.zusammen.datatypes.UserInfo;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.element.CreateElement;
import org.amdocs.zusammen.element.ElementScenarios;
import org.amdocs.zusammen.element.GetElementData;
import org.amdocs.zusammen.element.GetElementInfo;
import org.amdocs.zusammen.element.GetElementList;
import org.amdocs.zusammen.item.CreateItem;
import org.amdocs.zusammen.itemversion.CreateItemVersion;
import org.amdocs.zusammen.itemversion.PublishItemVersion;
import org.amdocs.zusammen.itemversion.SyncItemVersion;

import java.util.Collection;
import java.util.UUID;

public class Zusammen {

  private static String TENANT = "test";
  private static String USER_A = "user_A";
  private static String USER_B = "user_B";

  public static SessionContext contextA = createSessionContext(USER_A, TENANT);

  private static SessionContext createSessionContext(String user, String tenant) {
    SessionContext sessionContext = new SessionContext();
    sessionContext.setTenant(tenant);
    sessionContext.setUser(new UserInfo(user));
    return sessionContext;
  }

  public static SessionContext contextB = createSessionContext(USER_B, TENANT);


  public static Id initItem(SessionContext context) {


    Info itemInfo = CreateItem.prepareItemInfo();
    CreateItem createItem = new CreateItem();
    Id itemId = createItem.createItem(context, itemInfo);
    System.out.println("Item Id:" + itemId);


    return itemId;

  }

  public static Id initVersion(SessionContext context, Id itemId, Id baseVersionId) {
    CreateItemVersion createItemVersion = new CreateItemVersion();
    ItemVersionData itemversionData = createItemVersion.prepareItemVersionData();
    Id versionId =
        createItemVersion.execute(context, itemId, baseVersionId, itemversionData);
    System.out.println("version id:" + versionId);

    return versionId;

  }

  public static Id initElement(SessionContext context, Id itemId, Id versionId) {
    CreateElement createElement = new CreateElement();
    Element element = ElementScenarios.getSingleElement();
    Id elementId = createElement.execute(context, itemId, versionId, element, "create root " +
        "element", UUID.randomUUID().toString());
    System.out.println("element id:" + elementId);

    return elementId;

  }

  public void publishItemVersion(SessionContext context, Id itemId, Id versionId, String message) {
    PublishItemVersion publish = new PublishItemVersion();
    publish.execute(context, itemId, versionId, message);
  }

  public MergeResult syncItemVersion(SessionContext context, Id itemId, Id versionId) {
    SyncItemVersion sync = new SyncItemVersion();
    return sync.execute(context, itemId, versionId);
  }

  public Collection<ElementInfo> getElementList(SessionContext context, Id itemId, Id versionId,
                                                Id elementId) {
    GetElementList getElementList = new GetElementList();
    return getElementList.getElements(context, itemId, versionId, null);
  }

  public ElementInfo getElementInfo(SessionContext context, Id itemId, Id versionId, Id elementId) {
    GetElementInfo getElementInfo = new GetElementInfo();
    return getElementInfo.getElement(context, itemId, versionId, elementId);
  }

  public Element getElementData(SessionContext context, Id itemId, Id versionId, Id elementId) {
    GetElementData getElementData = new GetElementData();
    return getElementData.getElementData(context, itemId, versionId, elementId);
  }


}
