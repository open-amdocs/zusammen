---
layout: inner
title: Zusammen as Open Source
permalink: /zusammenOpenSource
group: arch
sortindex: 5
---
# Zusammen as Open Source

Zusammen is an open source product that consists of five parts, each one in its own repository:
1. Core - Zusammen core functionality, and utilization of all of the components in Zusammen. 
2. Common - common tools and utilities used internally by Zusammen.
3. Collaboration store implementation using Git.
4. Search index implementation using Elastic.
5. Metadata store implementation using Cassandra.
 
Zusammen components are licensed under the Apache license ([Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)). This means they can be used freely in both open source and commercial projects, so long as the user is following the license terms.
All the Zusammen repositories are hosted on GitHub under the [Open Amdocs](https://github.com/open-amdocs) organization.
 
**NOTE:** _At the current time, users must compile Zusammen. In the near future, Amdocs will provide a binary version._

## Open Source used by Zusammen 

| Name                         | License                    |
| ---------------------------- | -------------------------- |
| Apache Commons Codec 1.6     | Apache License 2.0         |
| Apache Commons Logging 1.1   | Apache License 2.0         |
| BeanShell 2.0                | Sun Public License 1.0     |
| Cassandra Driver Core 3.1    | NA                         | 
| Cassandra Driver Mapping 3.1 | NA                         | 
| Components Httpclient 4.3    | Apache 2.0 License         | 
| Google Guava 16              | Apache License 2.0         |
| gson 2.5                     | Apache 2.0 License         |
| Httpcore 4.3                 | Apache License 2.0         |
| Java Git 4.3                 | Eclipse Public License 1.0 |
| javaewah 0.7                 | Apache License 2.0         |
| jcommander 1.48              | Apache License version 2.0 | 
| Jcraft Jsch 0.1.*            | BSD License                |
| jffi 1.2                     | Apache 2.0 License         |
| jnr constants 0.9            | NA                         | 
| jnr posix 3.0                | NA                         | 
| jnr-ffi 2.0                  | NA                         | 
| jnr-x86asm 1.0               | MIT License                |
| jQuery 1.7                   | MIT License                |
| metrics 3.1                  | Apache License 2.0         |
| mockito 1.10                 | MIT License                |
| Netty 4.0                    | Apache 2.0 License         | 
| ow2-asm 5.0                  | BSD License                |
| Slf4J API 1.7                | MIT License                |
