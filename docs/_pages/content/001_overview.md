---
layout: inner
title: Overview
permalink: /overview
group: arch
sortindex: 1
---
# Overview

Zusammen was created for application-users, in order to enable a new effiecient and easier way for collaboration and parallelism of data-oriented tasks.  

Zusammen is the perfect platform for applications that focus on _experts_ and _knowledge-workers_, who are working in collaboration. Its capabilities include _Data Model Awareness_ and _Search_, and it is Schema-free.

<span id="collobarationAnchor"></span>
Whether your application enables a team to write an article, a group to design a model or a few friends to contribute to any other joint effort - if you do it _together_, Zusammen user-collaboration will make the collaborative application development both _fast_ and _easy_.

Zusammen is an Open Source initiative, and was designed from day one to be extended and keep evolving. It means you can use Zusammen in any way you find valuable - as a whole, or replace any of the default plug-ins with whichever plug-ins that could better meet your organizational needs.

## Collaboration

Each element in Zusammen can be edited by a number of users, each works at their own pace, in their own "work area". This work area is a _Local Encapsulated Environment_, visiable only to the specific contributer. At any time when the collaborating users wish to share their work, they can do so by using the Zusammen _Merge Capabilities_.

## Data model awareness

Zusammen is aware of the needs of the Business Layer.

The major business model object can be modeled as an item, and its data and sub-objects can be
broken down into one or more entities. Those, in turn, could contain other entities.  Relations between entities within the same item, and relations between entities
in different items, are preserved through data. 

<span id="versioningAnchor"></span>
Some business items have _visualization data_ (that is - color, icon, location, on-screen, and so on). This type of data
should be saved but _is not usually part of the business data_. Zusammen provides an easy mechanism to save the visualization data close to
the associated element, but not as part of it. 

## Versioning

Each collaborative effort has its release goals - some internal and some external. Zusammen comes with strong versioning capabilities to support such goals.

Users can manage releases, patch releases, and test intermediate versions. They can also manage as many revisions as they wish, with published revisions being transformed into versions. Each version can be used as a baseline for a future version for patch releases. 

**NOTE:** _Depending on the user's business needs, this can be either semantic versioning or any other versioning schema._

## Schema free

Zusammen is data format and schema agnostic. 

The user can persist any data type in any format to Zusammen, and make use of the format's particular capabilities.

<span id="searchAnchor"></span>
**TIP:** _In order to get the most out of the supplied implementation, it is recommended that you use a text-based, merge-friendly format, such as JSON, text, or yaml. Other file formats (especially binary formats, such as JPG images or encrypted data) might require development of a dedicated merge plug-in._

## Search

Having lots of data revisions requires tools, in order to make it easy to manage, retrieve, and search through the data. 

To address this need, Zusammen provides out-of-the-box search capabilities and flexible indexing mechanisms for business-specific data.
