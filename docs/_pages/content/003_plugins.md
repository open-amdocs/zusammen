---
layout: inner
title: Plug-ins
permalink: /plugins
group: arch
sortindex: 3
---
# Plug-ins

Major Zusammen capabilities are based on plug-ins. The Zusammen Adapter accesses a set of configurable plug-ins, each of which encapsulates a single functionality.

The default implementation is supplied with: 

1. A **Collaboration store** that is implemented using Git. The collaboration store defines a collaboration interface with support for the following objects: item, entity, user area, save, publish,
sync, delete, and merge. 
2. A **Metadata store** that is implemented using Cassandra. The Metadata store defines a Metadata interface with support for save, update, quick fetch, items
management, collaboration groups management, and user work-space management. The metadata store is schema free.
3. A **Search Index** that is implemented using Elastic. Queries from the calling application must be valid queries that the search engine can perform.

While Zusammen defines strict functional requirements through its plug-in interfaces, the non-functional requirements are defined by the hosting
application and the selected plug-ins. 

The rest of this topic describes an enterprise-grade non-functional implementation, based on the reference plug-ins.

## Consistency

The Zusammen contract states that an operation is successful once the underlying plug-in returns **success**. Success might mean that the successful operation is not consistent yet. (Plug-ins may eventually be consistent or have some inner latency, for example.) 

Examples: 

1. When using a database that uses the eventual consistency model as the Metadata Store plug-in, the plug-in creator must decide when data is considered written. The default configuration for most eventual consistency databases considers a record to be **written** on first-write or quorum majority. At
this point the plug-in should return a success response to the middleware.
2. The search index takes some time to actually index the data, search indexes usually return control to the caller at this point.

The developers of the calling application should make their plug-in selection based on the their application requirements, while keeping in mind the [CAP
theorem](https://en.wikipedia.org/wiki/CAP_theorem).

## High availability

This section describes the high availability architecture of the Zusammen reference implementation. (High availability of the host application is out of scope for this document.)

High availability for Zusammen is achieved at the plug-in level.  

### Search index

The reference implementation uses Elastic Search, following the guidelines described in [Scale Practices](engineeringGuidelines).

**Highly Recommended:** For a better understanding of scaling using Elastic, you are advised to read about [Elastic Search mechanisms](https://www.elastic.co/guide/en/elasticsearch/guide/current/distributed-cluster.html).

### Metadata store

The reference implementation uses Cassandra as its Metadata store, following the guidelines described in [Scale Practices](engineeringGuidelines). 

**Highly Recommended:** For a better understanding of scaling using Cassandra, you are advised to read about [Casandra's high availability features](http://cassandra.apache.org/doc/latest/operating/topo_changes.html).

### Collaboration aware store - Git

Git has two parts that require high availability architecture:
1. Git servers. 

 Git by its nature is a distributed system. This means servers have no state and work in the scope of a single session (`== call`). The Git plug-in requires a
cluster of Git servers accessed via a load balancer.
2. Git storage.  

 To maintain high availability, the Git plug-in requires highly available storage (fault-proof solution, proprietary volume, or RAID combined
with HA-NFS).

![High Availability](images/high_availability.png)

## Geographic Redundancy (GEoR) Strategy

The reference implementation utilizes Active-Passive GEoR. Users access a GSS, which directs them to the active site's load balancer.
The load balancer health checks the application nodes, and directs users to healthy application nodes only. 

The node health-check checks the health of all of the resources required for the application node's operation. If one of the resources fails
(for example, because the Cassandra cluster is down), all application nodes report "not healthy", the load balancer reports "not healthy site" to the GSS, and that makes site 2 the active site.



### Search index

Each element indexed to the Active site is indexed to the passive site as well, utilizing the Git hook mechanism (see [diagram](images/geor.png)).

### Metadata store

Cassandra replication will be set as follows: 

| Site | Number of replicas | Quorum size |
|------|--------------------|-------------|
| 1    | 2                  | 3           |
| 2    | 2                  | 3           |

Casandra will manage the cross-site replication.

### Git

On each commit to private data or push to public data in the active site, a Git hook is fired and pushes or commits to the passive site. 

![Geographic Redundancy](images/geor.png)
