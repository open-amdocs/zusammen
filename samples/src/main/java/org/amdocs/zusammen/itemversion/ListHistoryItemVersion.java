package org.amdocs.zusammen.itemversion;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.itemversion.Change;
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.element.CreateElement;
import org.amdocs.zusammen.element.ElementScenarios;
import org.amdocs.zusammen.element.GetElementData;
import org.amdocs.zusammen.utils.fileutils.FileUtils;

public class ListHistoryItemVersion extends ItemVersion{


  public static void main(String[] args) {

    Id itemId = initItem(contextA);
    Id versionId = initVersion(contextA,itemId,null);
    Id elementId = initElement(contextA,itemId,versionId);
    ListHistoryItemVersion listHistoryItemVersion = new ListHistoryItemVersion();
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key1","value1");
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key2","value2");
    listHistoryItemVersion.updateElement(contextA,itemId,versionId,elementId,"key3","value3");
    ItemVersionHistory itemVersionHistory = listHistoryItemVersion.execute(contextA,
        itemId,versionId);

    printHistory(itemVersionHistory);
    System.exit(0);
  }

  public static void printHistory(ItemVersionHistory itemVersionHistory) {
    System.out.print("log size:"+itemVersionHistory.getItemVersionChanges().size());
    for(Change change :itemVersionHistory.getItemVersionChanges()){
      System.out.println("id:"+change.getChangeId().getValue()+
      " time:"+change.getTime()+
      " user:"+change.getUser()+
      " message:"+change.getMessage());
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

  public ItemVersionHistory execute(SessionContext context, Id itemId, Id versionId) {
    return getItemVersionAdaptor(context).listHistory(context,itemId,
        versionId).getValue();
  }

  public Element getElementData(SessionContext context, Id itemId, Id versionId, Id elementId){
    GetElementData getElementData = new GetElementData();
    return getElementData.getElementData(context,itemId,versionId,elementId);
  }
}
