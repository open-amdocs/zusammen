---
layout: inner
title: Overview
permalink: /api/error_handling
group: Error Handling
sortindex: 1
---
# Error handling

Zusammen comprises three logical layers:

1. The Core layer (or Zusammen Adapter).
2. The Middleware layer.
3. The Plug-ins layer. 

The preceding layers are described in the [Layers Diagram](../layersDiagram).

The core and middleware layers are fully managed by the Zusammen developers. 

The plug-in layer is designed for easy extension by delivery teams. 

This distinction between the two upper layers and the lower layer led to the understanding that plug-ins should be
treated as not safe. Each plug-in call is, therefore, wrapped by a `try-catch` block, and has a configurable grace period and retry count.

All layers utilize well-managed error codes at the relevant layers. This pattern is favored over 'exceptions at all layers' because wrapping each inter-layer call in both Zusammen and the calling app will
create barely readable code with heavy maintenance costs. The selected design is also considered preferable because plug-ins might be "eventually consistent" or code might behave 
asynchronously; in this case, the meaning of exceptions may be lost because exceptions imply a break of execution, but execution might continue 
even after the exception is thrown. 

Developers that are responsible for writing plug-ins are encouraged to use error messages, and to supply as much root-cause data as possible to the users for better in-field
debugging and issue reporting. Each middleware component adds some context data (exception or error message) to the error data provided by the plug-in. The Core layer might also add some more context data.

Error objects always contain all of the collected error data, and return that data to the calling app (see [API](../api) documentation for sequence diagrams describing error
handling). 

## Errors structure

Errors will be reported from several domains, and have the structure `prefix-12345`, where:
* `prefix` identifies the causing subsystem.
* `12345` is a five digit number that identifies the actual error in the subsystem.

**NOTE:** _Plug-in writers should not use the Zusammen errors, since there is no trust policy._

## Errors are grouped as follows: 

* [Zusammen Core (ZDB)](error_zdb)
* [Collaboration store (ZCS)](error_zcs)
* [Metadata store (ZMD)](error_zmd)
* [Search index (ZSI)](error_zsi)

**NOTE:** _The three digit code in brackets (for example, ZDB) is the identifier for the associated layer._
