---
layout: inner
title: ItemVersion.Publish
permalink: /api/item_version_publish
group: ItemVersion
sortindex: 6
---
# ItemVersion.Publish

Publish ItemVersion from work area to shared area.

## Signature

`void publish(SessionContext context, Id itemId, Id versionId, String message)`

## Flow Diagram

### Success

![ItemVersion.Publish Flow Diagram Success](../images/item_version_publish_success.png)

### Error in plug-in

![ItemVersion.Publish Flow Diagram Error](../images/item_version_publish_error.png)
