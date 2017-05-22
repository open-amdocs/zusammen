package com.amdocs.zusammen;

import com.amdocs.zusammen.datatypes.UserInfo;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.element.CreateElement;
import com.amdocs.zusammen.element.ElementScenarios;
import com.amdocs.zusammen.element.GetElementData;
import com.amdocs.zusammen.element.GetElementInfo;
import com.amdocs.zusammen.element.GetElementList;
import com.amdocs.zusammen.item.CreateItem;
import com.amdocs.zusammen.itemversion.CreateItemVersion;
import com.amdocs.zusammen.itemversion.PublishItemVersion;
import com.amdocs.zusammen.itemversion.SyncItemVersion;

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
