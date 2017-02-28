---
layout: inner
title: Installation
permalink: /installation
group: arch
sortindex: 9
---
# Installation Guide

This installation guide describes installation of a single node (all-in-one) Zusammen sample application and Zusammen. The installed application uses the reference implementation plug-ins described in the 'Implementation Example' section of the [Layers Diagram](layersDiagram).

This guide assumes the following set up: 
* Vanilla CentOS 6.5 - x86_64 - 64-bit 
* 32 GB of RAM 
* 4 vCPUs
* 6GB HDD.

## Technical stack

The following table describes the packages that Zusammen was tested with. 

| Name      | Value     |
| --------- | --------- |
| Java      | OpenJDK 8 |
| Git       | 2.1.11    |
| Cassandra | 2.1.9     |
| Elastic   | 5.1.1     |    

## Parliamentary step

* Prior to starting the installation process, ensure all package indexes are up-to-date by running the command:  
`sudo yum check-update`

**NOTE:** _In some cases, you will need to configure your yum proxy settings to follow the proxy settings of your organization._

## Prerequisites

### Java

Zusammen is written in Java 8, and requires Java to run.

Being an Open Source project, Zusammen uses OpenJDK. 

1. To install OpenJDK, run the following command and answer the subsequent questions:  
`sudo yum install java-1.8.0-openjdk.x86_64`
2. To validate that OpenJDK has been installed, run the following command and check the version:  
`java -version`

### Git

Amdocs recommends using Git 2.11 or later.

**NOTE:** _The Centos repository contains a very old version of Git. You can either install Git from source or use other repositories (such as [https://ius.io](https://ius.io), mentioned in the Git documentation). To get a newer packaged version of Git use the command `yum install git2u`._

* To validate that the correct version of Git is installed, run the following command:  
`git --version`

### Cassandra

1. Create a datastax.repo file.  
`/etc/yum.repos.d/datastax.repo`
2. Add the DataStax community repository settings to the datastax.repo file:  
`[datastax]`  
`name = DataStax Repo for Apache Cassandra`  
`baseurl = https://rpm.datastax.com/community`  
`enabled = 1`  
`gpgcheck = 0`  
3. Install Cassandra 2.1.9.  
`sudo yum install dsc21-2.1.9-1 cassandra2.1.9-1`
4. Validate the version of Cassandra is 2.1.9:  
`cassandra -v`

### Elastic

1. Install Elastic's public signing key  
`sudo rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch`
2. Create a elasticsearch.repo file.  
`/etc/yum.repos.d/elasticsearch.repo`
3. Add the Elastic repository settings to the elasticsearch.repo file:
`[elasticsearch-5.x]`  
`name=Elasticsearch repository for 5.x packages`  
`baseurl=https://artifacts.elastic.co/packages/5.x/yum`  
`gpgcheck=1`  
`gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch`  
`enabled=1`  
`autorefresh=1`  
`type=rpm-md`  
4. Install Elastic 5.x.  
`sudo yum install elasticsearch`  
5. Start the Elastic daemon.  
`sudo -i service elasticsearch start`

