package com.amdocs.zusammen.element;

import com.amdocs.zusammen.Zusammen;
import com.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptor;
import com.amdocs.zusammen.adaptor.inbound.api.item.ElementAdaptorFactory;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementInfo;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.utils.fileutils.FileUtils;
import com.amdocs.zusammen.utils.fileutils.json.JsonUtil;

public abstract class Element extends Zusammen {


  public ElementAdaptor getElementAdaptor(SessionContext context) {
    return ElementAdaptorFactory.getInstance().createInterface(context);
  }


  public static void printElement(ElementInfo elementInfo) {
    System.out.println("==================element Info =========================");
    System.out.println("elementId:" + elementInfo.getId());

    if (elementInfo.getInfo() != null) {
      System.out.println("element Info:" + JsonUtil.object2Json(elementInfo.getInfo()));
    }
    if (elementInfo.getRelations() != null) {
      System.out.println("element Relations:" + JsonUtil.object2Json(elementInfo.getRelations()));
    }
    if (elementInfo.getSubElements() != null && elementInfo.getSubElements().size() > 0) {
      elementInfo.getSubElements().forEach(subElementInfo -> System.out.println("sub " +
          "elementid:" + subElementInfo.getId()));
    }
    System.out.println("==================END=========================");
  }

  public static void printElementData(
      com.amdocs.zusammen.adaptor.inbound.api.types.item.Element element) {
    System.out.println("==================element  =========================");
    System.out.println("elementId:" + element.getElementId());

    if (element.getInfo() != null) {
      System.out.println("element Info:" + JsonUtil.object2Json(element.getInfo()));
    }
    if (element.getRelations() != null) {
      System.out.println("element Relations:" + JsonUtil.object2Json(element.getRelations()));
    }

    if (element.getData() != null) {
      System.out.println("element Data:" + new String(FileUtils.toByteArray(element.getData
          ())));
    }

    if (element.getVisualization() != null) {
      System.out.println(
          "element visualization:" + new String(FileUtils.toByteArray(element.getVisualization
              ())));
    }
    if (element.getSearchableData() != null) {
      System.out.println("element searchableData:" + new String(FileUtils.toByteArray(element
          .getSearchableData
              ())));
    }

    if (element.getSubElements() != null && element.getSubElements().size() > 0) {
      element.getSubElements().forEach(subElementInfo -> System.out.println("sub " +
          "elementid:" + subElementInfo.getElementId()));
    }
    System.out.println("==================END=========================");
  }


}
