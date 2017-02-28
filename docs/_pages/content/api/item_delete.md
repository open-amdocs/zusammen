---
layout: inner
title: Item.Delete
permalink: /api/item_delete
group: Item
sortindex: 3
---
# Item.Delete

Deletes an Item and all of its data.

**NOTE:** _There is no validation for a delete operation. It is the responsibility of the calling app to validate that it is safe to delete the item, or to handle any broken references appropriately._

## Signature

`void delete(SessionContext context,Id itemId)`

## Flow Diagram

### Success

![Item.Delete Flow Diagram Success](../images/item_delete_success.png)

### Error in any plug-in (index as example)

![Item.Delete Flow Diagram Error](../images/item_delete_error.png)
