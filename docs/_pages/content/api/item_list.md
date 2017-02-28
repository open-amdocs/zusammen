---
layout: inner
title: Item.List
permalink: /api/item_list
group: Item
sortindex: 5
---
# Item.List

Lists all of the items the user can access, based on:
1. Tenant
2. Collaboration groups

## Signature

`Collection<Item> list(SessionContext context)`

## Flow Diagram

### Success

![Item.List Flow Diagram Success](../images/item_list_success.jpg)

### Error in plug-in

![Item.List Flow Diagram Error](../images/item_list_error.jpg)
