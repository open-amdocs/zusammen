package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreElementConflictInfo;
import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.sdk.types.ElementConflictDescriptor;

import java.util.stream.Collectors;

public class CoreElementConflictInfoConvertor {
  public static CoreElementConflictInfo convertToCoreElementInfo(ElementConflictDescriptor source) {
    CoreElementConflictInfo target = new CoreElementConflictInfo();

    if(source.getLocalElementDescriptor() != null)
      target.setLocalCoreElementInfo(CoreElementInfoConvertor.convertToCoreElementInfo(source.getLocalElementDescriptor()));
    if(source.getRemoteElementDescriptor() != null)
      target.setRemoteCoreElementInfo(CoreElementInfoConvertor.convertToCoreElementInfo(source.getRemoteElementDescriptor()));

    return target;
  }

  private static CoreElementInfo mapToElementInfo(Id id) {
    CoreElementInfo coreElementInfo = new CoreElementInfo();
    coreElementInfo.setId(id);
    return coreElementInfo;
  }
}
