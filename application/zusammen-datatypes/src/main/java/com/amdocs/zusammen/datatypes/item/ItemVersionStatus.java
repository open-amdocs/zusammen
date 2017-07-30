package com.amdocs.zusammen.datatypes.item;

public class ItemVersionStatus {
  private SynchronizationStatus synchronizationStatus;
  private boolean dirty;

  public ItemVersionStatus(SynchronizationStatus synchronizationStatus, boolean dirty) {
    this.synchronizationStatus = synchronizationStatus;
    this.dirty = dirty;
  }

  public SynchronizationStatus getSynchronizationStatus() {
    return synchronizationStatus;
  }

  public boolean isDirty() {
    return dirty;
  }
}
