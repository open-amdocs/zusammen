package com.amdocs.zusammen.datatypes.item;

public enum SynchronizationStatus {
  UP_TO_DATE("Up to date"),
  OUT_OF_SYNC("Out of sync"),
  MERGING("Merging");

  private String displayName;

  SynchronizationStatus(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}

