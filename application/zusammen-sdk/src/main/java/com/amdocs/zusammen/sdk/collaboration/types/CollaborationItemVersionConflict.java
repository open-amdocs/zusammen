package com.amdocs.zusammen.sdk.collaboration.types;

import com.amdocs.zusammen.datatypes.item.ItemVersionDataConflict;
import com.amdocs.zusammen.sdk.types.ElementConflictDescriptor;

import java.util.ArrayList;
import java.util.Collection;

public class CollaborationItemVersionConflict {
  private ItemVersionDataConflict versionDataConflict;
  private Collection<ElementConflictDescriptor> elementConflictDescriptors = new ArrayList<>();

  public ItemVersionDataConflict getVersionDataConflict() {
    return versionDataConflict;
  }

  public void setVersionDataConflict(
      ItemVersionDataConflict versionDataConflict) {
    this.versionDataConflict = versionDataConflict;
  }

  public Collection<ElementConflictDescriptor> getElementConflictDescriptors() {
    return elementConflictDescriptors;
  }

  public void setElementConflictDescriptors(
      Collection<ElementConflictDescriptor> elementDescriptors) {
    this.elementConflictDescriptors = elementDescriptors;
  }

  public void addElementConflictDescriptor(ElementConflictDescriptor elementConflictDescriptor) {
    elementConflictDescriptors.add(elementConflictDescriptor);
  }
}
