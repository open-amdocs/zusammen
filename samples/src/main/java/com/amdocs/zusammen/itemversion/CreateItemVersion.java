package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.ItemVersionData;
import com.amdocs.zusammen.datatypes.item.Relation;
import com.amdocs.zusammen.datatypes.response.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateItemVersion extends ItemVersion {

  private static String TENANT = "test";
  private static String USER = "user";

  public static void main(String[] args) {
    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA, itemId, null);
    System.exit(0);
  }

  public Id execute(SessionContext context, Id itemId, Id versionId, ItemVersionData
      itemversionData) {
    Response<Id> response = getItemVersionAdaptor(context).create(context, itemId, versionId,
        itemversionData);
    Id itemVersionId = response.getValue();
    return itemVersionId;
  }


  public static ItemVersionData prepareItemVersionData() {

    ItemVersionData itemVersionData = new ItemVersionData();
    itemVersionData.setInfo(prepareItemVersionInfo());
    itemVersionData.setRelations(prepareItemVersionRelations());
    return itemVersionData;
  }

  private static Collection<Relation> prepareItemVersionRelations() {
    return null;
  }

  private static Info prepareItemVersionInfo() {
    Info info = new Info();
    info.setName("createItemVersionExample");
    info.setDescription("Create Item Version Example");
    Map<String, Object> props = new HashMap();
    props.put("type", "service version");
    List<String> tags = new ArrayList();
    tags.add("root version");
    props.put("tags", tags);
    props.put("version name", "initial version");
    info.setProperties(props);
    return info;
  }

}
