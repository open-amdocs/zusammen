---
layout: inner
title: ItemVersion.Delete
permalink: /api/item_version_delete
group: ItemVersion
sortindex: 3
---
# ItemVersion.Delete

Delete a version.  

**NOTE:** _There is no validation for a delete operation. It is the responsibility of the calling app to validate that it is safe to delete the item, or to handle any broken references appropriately._


## Signature

`void delete(SessionContext context, Id itemId, Id versionId);`

## Flow Diagram

### Success

![ItemVersion.Delete Flow Diagram Success](../images/item_version_delete_success.png)

### Error in plug-in

![ItemVersion.Delete Flow Diagram Error](../images/item_version_delete_error.png)
