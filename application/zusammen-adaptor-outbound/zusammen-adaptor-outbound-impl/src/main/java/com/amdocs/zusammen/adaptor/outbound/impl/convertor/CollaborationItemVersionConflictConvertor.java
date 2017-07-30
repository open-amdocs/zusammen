package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreItemVersionConflict;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationItemVersionConflict;

public class CollaborationItemVersionConflictConvertor {

  public static CoreItemVersionConflict convertToCoreItemVersionConflict
      (CollaborationItemVersionConflict source){
    CoreItemVersionConflict target = new CoreItemVersionConflict();
    target.setVersionDataConflict(source.getVersionDataConflict());
    source.getElementConflictDescriptors().forEach(elementConflictDescriptor ->
        target.addElementConflict(CoreElementConflictInfoConvertor
            .convertToCoreElementInfo(elementConflictDescriptor)));
    return target;
  }
}
