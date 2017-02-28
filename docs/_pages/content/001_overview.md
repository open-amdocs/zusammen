---
layout: inner
title: Overview
permalink: /overview
group: arch
sortindex: 1
---
# Overview

Zusammen enables collaboration between application users, and makes parallelism of data-oriented tasks easy.  

Zusammen is the perfect platform for applications that focus on experts and knowledge workers who are working in collaboration. Its capabilities include data model awareness and search, and it is schema free.

<span id="collobarationAnchor"></span>
Whether your application enables a team to write an article together, to design a model together, or to contribute to other joint efforts, Zusammen user
collaboration will make collaborative application development both fast and easy.

Zusammen is open source, and has been designed from day one for user extensibility. This means that you can use Zusammen as a whole, or replace the default plug-ins with plug-ins that better meet your organizational needs.

## Collaboration

Each element in Zusammen can be edited by a number of users. Each user works at their own pace in their own "work area". This work area is a local encapsulated environment, seen only by the worker. When the collaborating users wish to share their work (at any time), they do so using the Zusammen merge capabilities.

## Data model awareness

Zusammen is aware of the needs of the business layer.

The major business model object can be modeled as an item, and its data and sub-objects can be
broken down into one or more entities (themselves containing other entities).  Relations between entities in the same item, and relations between entities
in different items, are preserved through data. 

<span id="versioningAnchor"></span>
Some business items have visualization data (color, icon, location, on-screen, and so on), this data
should be saved but is not usually part of the business data. Zusammen supplies an easy mechanism to save the visualization data close to
the associated element, but not as part of it. 

## Versioning

Each collaborative effort has its release goals, some internal and some external. Zusammen comes with strong versioning capabilities to support these goals.

Users can manage releases, patch releases, and test intermediate versions. They can also manage as many revisions as they wish, with published revisions being transformed into versions. Each version can be used as the baseline for a future version for patch releases. 

**NOTE:** _Depending on the user's business needs, this can be either semantic versioning or any other versioning schema._

## Schema free

Zusammen is data format and schema agnostic. 

The user can persist any data type in any format to Zusammen, and make use of the format's particular capabilities.

<span id="searchAnchor"></span>
**TIP:** _To get the most out of the supplied implementation, it is recommended that you use a text-based, merge-friendly format, such as JSON, text, or yaml. Other file formats (especially binary formats, such as JPG images or encrypted data) might require development of a dedicated merge plug-in._

## Search

Having lots of data revisions requires tools that make it easy to manage, retrieve, and search through the data. 

To ensure this need is met, Zusammen provides out-of-the-box search capabilities and flexible indexing mechanisms for business-specific data.
