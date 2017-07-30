package com.amdocs.zusammen.adaptor.inbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreItemVersionConflict;
import com.amdocs.zusammen.adaptor.inbound.api.types.item.ItemVersionConflict;


public class ItemVersionConflictConvertor {

  public static ItemVersionConflict convert(CoreItemVersionConflict
                                           coreItemVersionConflict){

    ItemVersionConflict itemVersionConflict = new ItemVersionConflict();
    coreItemVersionConflict.getElementConflictInfos().forEach(coreElementconflictInfo ->
        itemVersionConflict.addElementConflictInfo(ElementConflictInfoConvertor.convert
            (coreElementconflictInfo)));
    itemVersionConflict.setVersionDataConflict(coreItemVersionConflict.getVersionDataConflict());
    return itemVersionConflict;

  }
}
