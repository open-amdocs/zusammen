---
layout: inner
title: Element.Save
permalink: /api/element_save
group: Element
sortindex: 1
---
# Element.Save

Gets a tree of one or more elements, then travels through the tree performing the element operation (do-nothing, create, update, or delete). Returns the updated tree. 

The operation may: 
1. Return the ID for any newly created elements.
2. Delete elements.
3. Set the operation to "do-nothing".

**NOTE:** _Save does not validate the tree. This means that elements can be saved when marked for invalid operations or combinations of operations. As an example, if the user marks entity e1 for delete and its child entity e2 for update, both e1 and e2 will be
deleted. In this and similar scenarios, the update operation never happens because of the delete operation._

## Signature

`void save(SessionContext context, ElementContext elementContext, Element element,String message)`

## Flow Diagram

### Success

![Element.Save Flow Diagram Success](../images/element_save_success.png)

### Error in any plugin

![Element.Save Flow Diagram Error](../images/element_save_error.png)
