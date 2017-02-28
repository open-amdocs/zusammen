---
layout: inner
title: ItemVersion.Create
permalink: /api/item_version_create
group: ItemVersion
sortindex: 1
---
# ItemVersion.Create

Create a new version of an Item. Item versions are based on other Item versions.

## Signature

`Id create(SessionContext context, Id itemId, Id baseVersionId, Info versionInfo)`

## Flow Diagram

### Success

![ItemVersion.Create Flow Diagram Success](../images/item_version_create_success.png)

### Error in plug-in

![ItemVersion.Create Flow Diagram Error](../images/item_version_create_error.png)
