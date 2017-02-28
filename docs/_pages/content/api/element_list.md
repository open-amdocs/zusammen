---
layout: inner
title: Element.List
permalink: /api/element_list
group: Element
sortindex: 4
---
# Element.List

List all sub-elements of element. If elementId is null, it returns the root elements of the item.

## Signature

`Collection<ElementInfo> list(SessionContext context, ElementContext elementContext, Id elementId)`

## Flow Diagram

### Success

![Element.List Flow Diagram Success](../images/element_list_success.png)

### Error in plugin

![Element.List Flow Diagram Error](../images/element_list_error.png)

