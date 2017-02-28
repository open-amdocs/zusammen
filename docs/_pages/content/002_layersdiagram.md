---
layout: inner
title: Layers Diagram
permalink: /layersDiagram
group: arch
sortindex: 2
---
# Layers Diagram

The Zusammen Adapter sits in the hosting application, and provides the core functionalities (described in the [overview](overview)) through plug-ins. The plug-ins are specific to an implementation. 

## Abstraction

![Zusammen Layers](images/layers_diagram.png)

As shown in the preceding diagram, the functions that will be enabled by the implemented plug-ins are:
* Metadata store
* Search index
* Collaboration-aware data store 

## Implementation Example

The following diagram shows an example implementation where the collaboration store is implemented using Git, the search index is implemented using Elastic, and the metadata store is implemented using Cassandra.

![Zusammen Reference implementation Layers](images/implementation_example.png)

**NOTE:** _This is the out-of-the-box implementation. For details of the plug-ins, see_ [Plug-ins](plugins).

