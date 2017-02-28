---
layout: inner
title: Logging
permalink: /logging
group: arch
sortindex: 10
---
# Logging

Zusammen logging has the following features: 

1. The hosting application can enable Zusammen logging as part of its own logging mechanism.
2. The plug-ins are able to log events. 
3. The logging performed by the plug-in can be toggled to prevent misbehaving plug-ins from flooding the logs. (For example, this can be avoided by not following the log-level concepts.) 
4. The standard log-levels are supported, which are as follows: 
 * trace 
 * debug
 * info 
 * warn
 * error
**NOTE:** _The preceding list of log-levels is in sequence from the least serious logged item (trace) to the most serious logged item (error)._

## Logging mechanism

Zusammen exposes a logging interface that implements the slf4j interface. The interface exposes the `getLogger` function, so that Zusammen's  classes are able to instantiate and utilize the log-level functions.

The user is able to configure Zusammen to stop plug-ins logging. This is a safety measure against misbehaving plug-ins, and is intended as a mean of separating the signal from _noise in production_ debugging.

![Logging Mechanism](images/logging.png)

![Logger Interface](images/logger_interface.png)
