package com.amdocs.zusammen.core.api.types;

import com.amdocs.zusammen.datatypes.item.ItemVersionDataConflict;

import java.util.ArrayList;
import java.util.Collection;

public class CoreItemVersionConflict {
  private ItemVersionDataConflict versionDataConflict;
  private Collection<CoreElementConflictInfo> elementConflicts = new ArrayList<>();

  public ItemVersionDataConflict getVersionDataConflict() {
    return versionDataConflict;
  }

  public void setVersionDataConflict(
      ItemVersionDataConflict versionDataConflict) {
    this.versionDataConflict = versionDataConflict;
  }

  public Collection<CoreElementConflictInfo> getElementConflictInfos() {
    return elementConflicts;
  }

  public void setElementConflictInfos(
      Collection<CoreElementConflictInfo> elementConflictInfos) {
    this.elementConflicts = elementConflictInfos;
  }

  public void addElementConflict(CoreElementConflictInfo coreElementConflictInfo) {
    elementConflicts.add(coreElementConflictInfo);
  }
}
