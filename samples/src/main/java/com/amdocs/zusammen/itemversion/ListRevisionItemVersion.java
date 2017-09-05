package com.amdocs.zusammen.itemversion;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.itemversion.Revision;
import com.amdocs.zusammen.datatypes.itemversion.ItemVersionRevisions;
import com.amdocs.zusammen.element.CreateElement;
import com.amdocs.zusammen.element.ElementScenarios;
import com.amdocs.zusammen.element.GetElementData;
import com.amdocs.zusammen.utils.fileutils.FileUtils;

public class ListRevisionItemVersion extends ItemVersion{


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA,itemId,null);
    Id elementId = initElement(contextA,itemId,versionId);
    ListRevisionItemVersion listRevisionItemVersion = new ListRevisionItemVersion();
    listRevisionItemVersion.updateElement(contextA,itemId,versionId,elementId,"key1","value1");
    listRevisionItemVersion.updateElement(contextA,itemId,versionId,elementId,"key2","value2");
    listRevisionItemVersion.updateElement(contextA,itemId,versionId,elementId,"key3","value3");
    ItemVersionRevisions itemVersionRevisions = listRevisionItemVersion.execute(contextA,
        itemId,versionId);

    printHistory(itemVersionRevisions);
    System.exit(0);
  }

  public static void printHistory(ItemVersionRevisions itemVersionRevisions) {
    System.out.print("log size:"+ itemVersionRevisions.getItemVersionRevisions().size());
    for(Revision revision : itemVersionRevisions.getItemVersionRevisions()){
      System.out.println("id:"+ revision.getRevisionId().getValue()+
      " time:"+ revision.getTime()+
      " user:"+ revision.getUser()+
      " message:"+ revision.getMessage());
    }

  }

  public void updateElement(SessionContext context, Id itemId, Id versionId,
                                    Id elementId,String key,String value) {

    Element element =
        getElementData(context, itemId, versionId, elementId);

    CreateElement updateElement = new CreateElement();
    Info newInfo = element.getInfo();
    newInfo.getProperties().put(key,value);
    Element newElement = ElementScenarios.prepareElement(elementId, Action.UPDATE,newInfo,element
            .getRelations(),
        new String(FileUtils.toByteArray(element.getData())),
        new String(FileUtils.toByteArray(element.getSearchableData())),
        new String(FileUtils.toByteArray(element.getVisualization())),
        element.getSubElements());
    System.out.println("update Element User A item id:"+itemId+ " version id:"+versionId);

    updateElement.execute(contextA,itemId,versionId,newElement,"first update User A",
        null);

  }

  public ItemVersionRevisions execute(SessionContext context, Id itemId, Id versionId) {
    return getItemVersionAdaptor(context).listRevisions(context,itemId,
        versionId).getValue();
  }

  public Element getElementData(SessionContext context, Id itemId, Id versionId, Id elementId){
    GetElementData getElementData = new GetElementData();
    return getElementData.getElementData(context,itemId,versionId,elementId);
  }
}
