---
layout: inner
title: ItemVersion.Sync
permalink: /api/item_version_sync
group: ItemVersion
sortindex: 7
---
# ItemVersion.Sync

Sync the ItemVersion in the work area with the ItemVersion from shared area. (Make the local copy the same as the shared copy.)

## Signature

`MergeResult sync(SessionContext context, Id itemId, Id versionId)`

## Flow Diagram

### Success

![ItemVersion.Sync Flow Diagram Success](../images/item_version_sync_success.png)

### Error in plug-in

![ItemVersion.Sync Flow Diagram Error](../images/item_version_sync_error.png)
