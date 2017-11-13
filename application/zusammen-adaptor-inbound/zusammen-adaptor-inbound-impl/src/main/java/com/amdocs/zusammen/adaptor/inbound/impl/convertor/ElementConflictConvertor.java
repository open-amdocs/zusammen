package com.amdocs.zusammen.adaptor.inbound.impl.convertor;

import com.amdocs.zusammen.adaptor.inbound.api.types.item.ElementConflict;
import com.amdocs.zusammen.core.api.types.CoreElementConflict;

public class ElementConflictConvertor {
  public static ElementConflict convert(CoreElementConflict coreElementConflict) {
    if (coreElementConflict == null) {
      return null;
    }
    ElementConflict elementConflict = new ElementConflict();
    elementConflict
        .setLocalElement(ElementConvertor.convert(coreElementConflict.getLocalElement()));
    elementConflict
        .setRemoteElement(ElementConvertor.convert(coreElementConflict.getRemoteElement()));
    return elementConflict;
  }
}
