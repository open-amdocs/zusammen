/*
 * Copyright © 2016-2017 European Support Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amdocs.zusammen.core.impl;

public class Messages {
  public static final String UNSUPPORTED_VERSION_ACTION =
      "Item Id %s, version Id %s, action %s is not supported";
  public static final String UNSUPPORTED_ELEMENT_ACTION =
      "Item Id %s, version Id %s, element Id %s, action %s is not supported";
  public static final String ITEM_NOT_EXIST = "Item with id %s does not exist.";
  public static final String ITEM_ֹID_ALREADY_EXIST = "Item with id %s already exists.";
  public static final String ITEM_ID_TO_CREATE_CANNOT_BE_NULL = "Item cannot be created with a null id.";
  public static final String ITEM_VERSION_NOT_EXIST =
      "Item Id %s, version Id %s does not exist in %s space";
  public static final String ITEM_VERSION_ֹID_ALREADY_EXIST =
          "Item Id %s, version Id %s already exists.";
  public static final String VERSION_ID_TO_CREATE_CANNOT_BE_NULL = "Item Id %s, version cannot be created with a null id.";
  public static final String ITEM_VERSION_ELEMENT_NOT_EXIST =
      "Item Id %s, version Id %s, element Id %s does not exist in %s space";
  public static final String ITEM_VERSION_PUBLISH_NOT_ALLOWED =
      "Item Id %s, version Id %s: Publish is not allowed since the version status is %s";
}
