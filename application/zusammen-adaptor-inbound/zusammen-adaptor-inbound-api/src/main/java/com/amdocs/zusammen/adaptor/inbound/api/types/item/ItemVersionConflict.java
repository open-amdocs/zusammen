package com.amdocs.zusammen.adaptor.inbound.api.types.item;



import com.amdocs.zusammen.datatypes.item.ItemVersionDataConflict;

import java.util.ArrayList;
import java.util.Collection;


public class ItemVersionConflict {
  private ItemVersionDataConflict versionDataConflict;
  private Collection<ElementConflictInfo> elementConflictInfos = new ArrayList<>();

  public ItemVersionDataConflict getVersionDataConflict() {
    return versionDataConflict;
  }

  public void setVersionDataConflict(
      ItemVersionDataConflict versionDataConflict) {
    this.versionDataConflict = versionDataConflict;
  }

  public Collection<ElementConflictInfo> getElementConflictInfos() {
    return elementConflictInfos;
  }

  public void setElementConflictInfos(
      Collection<ElementConflictInfo> elementConflictInfos) {
    this.elementConflictInfos = elementConflictInfos;
  }

  public void addElementConflictInfo
      (ElementConflictInfo elementConflict){
    elementConflictInfos.add(elementConflict);
  }
}
