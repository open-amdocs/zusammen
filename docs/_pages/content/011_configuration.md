---
layout: inner
title: Configuration
permalink: /configuration
group: arch
sortindex: 11
---
# Configuration

Zusammen requires some operational configuration to be able to work. Configuration (which is usually associated with the plug-ins) is set during initialization, according to the details specified in a JSON object. 

## Example JSON 

''{
    "logger": {
      "name": "SLF4J"
    },
    "collaborativeStore": {
      "allowLog": true,
      "data": "plug-in specific JSON object"
    },
    "index":{
      "allowLog": true,
      "data" : "plug-in specific JSON object"
    },
    "metadataStore": {
      "allowLog" : true,
      "data" : "plug-in specific JSON object"
    }
}''

## Attribute Values

* _logger_ - Indicates the logging mechanism to use. If this is left empty, no logs will be written. For more information about logging, see [Logging](logging).
*  For each plug-in: 
  * _allowLog_ - Indicates whether or not the plug-in will produce logs. **NOTE:** _The purpose of this attribute is to allow the user to silence logging, which allows noise to be reduced. This is particularly useful during production debugging._
  * _data_ - Indicates the plug-in specific data. This is usually the URI of the servers or resources.
