package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.sdk.types.ElementDescriptor;

import java.util.stream.Collectors;

public class CoreElementInfoConvertor {

  public static CoreElementInfo convertToCoreElementInfo(ElementDescriptor source) {
    if (source == null) {
      return null;
    }

    CoreElementInfo target = new CoreElementInfo();
    target.setId(source.getId());
    target.setInfo(source.getInfo());
    target.setNamespace(source.getNamespace());
    target.setParentId(source.getParentId());
    target.setRelations(source.getRelations());
    target.setSubElements(source.getSubElements().stream()
        .map(CoreElementInfoConvertor::mapToElementInfo)
        .collect(Collectors.toList()));
    return target;
  }

  private static CoreElementInfo mapToElementInfo(Id id) {
    CoreElementInfo coreElementInfo = new CoreElementInfo();
    coreElementInfo.setId(id);
    return coreElementInfo;
  }
}
