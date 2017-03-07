package org.amdocs.zusammen.element;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.Element;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.ZusammenElement;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Relation;
import org.amdocs.zusammen.datatypes.item.RelationEdge;

import org.amdocs.zusammen.utils.fileutils.json.JsonUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ElementScenarios {

  private  String[] data = {"AVI","SHALOM","TALIS","HALFON","SHIRI","LIRON","TALIO","ZIV",
      "STAS","MIRIAM","EYNAT","ILANA","YAEL"};


  private Action[] actions = new Action[]{Action.IGNORE,Action.UPDATE};

  private int index=0;
  private int index2Print=0;

  public static ElementScenarios getInstance(){
    return new ElementScenarios();
  }


  public  Element getRandomTreeElement(int minWidth,int maxWidth,int
      numberElements){

    int width=0;
    int depth=0;
    numberElements=numberElements-1;
    System.out.println("numberElements:"+numberElements);
    ZusammenElement element = createRandomElement(width,minWidth,maxWidth,depth,
        numberElements);
    return element;
  }

  private  ZusammenElement createRandomElement(int width,int minWidth, int maxWidth,
      int depth,int numberElements) {

    int actualWidth =  (int)(Math.random() * maxWidth)+minWidth;
    index2Print+=1;
    System.out.println("in createRandomElement: index:"+index+" index2Prinnt:"+index2Print);
    System.out.println("element number:"+index2Print+" defth:"+depth+" actualWidth:"+actualWidth);
    Info info = new Info();
    info.setName("element("+index2Print+")_"+depth+"_"+width);

    info.setProperties(getProps(width,depth));

    Collection<Relation> relations = populateRelations();

    ZusammenElement element = (ZusammenElement) prepareElement(null,Action.CREATE,info,relations,
        data[
        (int)(Math
            .random() * (data.length-1))],null,null,null);


    if(index+actualWidth>numberElements && index<numberElements){
      actualWidth = numberElements-index;
    }

    if(index+actualWidth>numberElements) {
      System.out.println("end recursive function: index:"+index+" index2Prinnt:"+index2Print);
      return element;
    }

    Collection<Element> subElements = new ArrayList<>();

    index+=actualWidth;
    for (int i=0;i<actualWidth;i++){

      Element subElement = createRandomElement(i,minWidth,maxWidth,depth+1,
          numberElements);
      if(subElement!=null)
        subElements.add(subElement);
    }

    element.setSubElements(subElements);

    return element;
  }

  private static Collection<Relation> populateRelations() {
    int size =  (int)(Math.random() * 5)+1;
    Collection<Relation> relations = new ArrayList<>();
    for(int i=0;i<size;i++){
      Relation relation = populateRelation();
      relations.add(relation);
    }
    return relations;
  }

  private static Relation populateRelation() {
    Relation relation = new Relation();
    relation.setType("elementRelation");
    RelationEdge edg1 = new RelationEdge();
    edg1.setElementId(new Id(UUID.randomUUID().toString()));
    relation.setEdge1(edg1);
    RelationEdge edg2 = new RelationEdge();
    edg2.setElementId(new Id(UUID.randomUUID().toString()));
    relation.setEdge2(edg2);
    return relation;
  }

  private static Map<String,Object> getProps(int width, int depth) {
    Map<String,Object> prop = new HashMap<>();
    prop.put("width",width);
    prop.put("depth",depth);
    return prop;
  }


  public static Element getElementAndSubElement(){
    ZusammenElement element = (ZusammenElement)getSingleElement();
    Collection<Element> subElements = new ArrayList<>();
    Element subElement =  getSubElement();
    subElements.add(subElement);
    element.setSubElements(subElements);
    return element;
  }

  private static Element getSubElement() {
    Info info = new Info();
    info.setName("subelement");
    info.setDescription("sub element");

    return prepareElement(null,Action.CREATE,info,null,"sub element data",null,null,null);
  }


  public static Element getSingleElement(){
      return prepareElement(null,
          Action.CREATE,
          prepareSingleElementInfo(),
          prepareSingleElementRelations(),
          "root element",
          "{\"elementType\":\"serviceOffer\"}",
          "left",null);
    }


  public static Element prepareElement(Id elementId, Action action, Info info, Collection<Relation>
      relations,
                                       String
      data, String searchableData, String visualizationData, Collection<Element> subElements) {

    ZusammenElement element = new ZusammenElement();
    if(elementId != null) element.setElementId(elementId);
    element.setAction(action);
    if(info!= null)
      element.setInfo(info);
    if(relations!= null)
      element.setRelations(relations);
    if(data!= null)
      element.setData(new ByteArrayInputStream(data.getBytes()));



   /* if(searchableData!= null) {
      EsSearchableData searchData =
          createSearchableData(new ByteArrayInputStream(searchableData.getBytes()));

      element
          .setSearchableData(new ByteArrayInputStream(JsonUtil.object2Json(searchData).getBytes()));
    }*/
    if(visualizationData != null)
      element.setVisualization(new ByteArrayInputStream(visualizationData.getBytes()));
    if(subElements!=null)
        element.setSubElements(subElements);
    return element;
  }

 /* private static EsSearchableData createSearchableData(InputStream searchableData) {
    EsSearchableData esSearchableData = new EsSearchableData();
    esSearchableData.setType("enrichedElementData");
    esSearchableData.setData(searchableData);
    return esSearchableData;
  }*/

  private static Collection<Relation> prepareSingleElementRelations(){
      return populateRelations();
    }

  private static Info prepareSingleElementInfo() {
    Info info = new Info();
    info.setName("createElementExample");
    info.setDescription("Create Element Example");
    Map<String,Object> props = new HashMap();
    props.put("type","service root element");
    List<String> tags = new ArrayList();
    tags.add("root element");
    props.put("tags",tags);
    props.put("element type","general information");
    info.setProperties(props);
    return info;
  }


  public void updateElementTree(Element element) {
    Action action = actions[(int)(Math.random() * 2)];
    ((ZusammenElement)element).setAction(action);
    if(action == Action.UPDATE){
      updateElement((int)(Math.random() * 2),element);

    }
    Collection<Element> elementList = element.getSubElements();
    for(Element subElement:elementList){
      updateElementTree(subElement);
    }

  }

  private void updateElement(int changeType,Element element) {
    switch (changeType){
      case 0:

        if(element.getInfo() == null) ((ZusammenElement)element).setInfo(new Info());
        if(element.getInfo().getProperties() ==null) ((ZusammenElement)element).getInfo()
            .setProperties(new HashMap<>());
        element.getInfo().getProperties().put("time",convertMillTime(System.currentTimeMillis()));
        break;
      case 1:
        Relation relation = new Relation();
        relation.setEdge1(new RelationEdge());
        relation.getEdge1().setElementId(new Id(UUID.randomUUID().toString()));
        relation.setEdge2(new RelationEdge());
        relation.getEdge2().setElementId(new Id(UUID.randomUUID().toString()));
        if(element.getRelations().size()==0) ((ZusammenElement)element).setRelations(new
            ArrayList<Relation>());
        element.getRelations().add(relation);
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
    }
  }



  public String convertMillTime(long millis){
    return String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
        TimeUnit.MILLISECONDS.toSeconds(millis) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
  }
}
