package com.amdocs.zusammen.item;

import com.amdocs.zusammen.adaptor.inbound.api.item.ItemAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.item.ItemAdaptorFactory;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.UserInfo;
import com.amdocs.zusammen.datatypes.item.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateItem {

  private static String TENANT = "test";
  private static String USER = "user";

  public static void main(String[] args) {
    SessionContext context = createSessionContext(USER, TENANT);
    Info itemInfo = prepareItemInfo();
    CreateItem createItem = new CreateItem();
    Id itemId = createItem.createItem(context, itemInfo);
    System.out.print("Item Id:" + itemId);
    System.exit(0);
  }

  public Id createItem(SessionContext context, Info itemInfo) {
    String user = USER;
    String tenant = TENANT;

    Id itemId = getItemAdaptor(context).create(context, itemInfo).getValue();
    return itemId;
  }

  public static Info prepareItemInfo() {

    Info info = new Info();
    info.setName("createItemExample");
    info.setDescription("Create Item Example");
    Map<String, Object> props = new HashMap();
    props.put("type", "service");
    List<String> tags = new ArrayList();
    tags.add("root");
    tags.add("amdocs");
    props.put("tags", tags);
    info.setProperties(props);

    return info;
  }

  private ItemAdaptor getItemAdaptor(SessionContext context) {
    return ItemAdaptorFactory.getInstance().createInterface(context);
  }

  private static SessionContext createSessionContext(String user, String tenant) {
    SessionContext sessionContext = new SessionContext();
    sessionContext.setTenant(tenant);
    sessionContext.setUser(new UserInfo(user));
    return sessionContext;
  }

}
