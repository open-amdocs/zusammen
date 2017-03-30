/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.zusammen.datatypes.response;

public class ErrorCode {


  public static final int SYSTEM_ERROR = 10000;
  public static final int ZU_ITEM_VERSION_LIST = 10100;
  public static final int ZU_ITEM_VERSION_CREATE = 10200;
  public static final int ZU_ITEM_VERSION_UPDATE = 10300;
  public static final int ZU_ITEM_VERSION_DELETE = 10400;
  public static final int ZU_ITEM_VERSION_GET = 10500;
  public static final int ZU_ITEM_VERSION_IS_EXIST = 10600;
  public static final int ZU_ITEM_VERSION_PUBLISH = 10700;
  public static final int ZU_ITEM_VERSION_SYNC = 10800;
  public static final int ZU_ITEM_VERSION_MERGE = 10800;
  public static final int ZU_ITEM_VERSION_HISTORY = 10900;
  public static final int ZU_ITEM_VERSION_REVERT_HISTORY = 11000;
  public static final int ZU_ITEM_LIST = 11100;
  public static final int ZU_ITEM_CREATE = 11200;
  public static final int ZU_ITEM_UPDATE = 11300;
  public static final int ZU_ITEM_DELETE = 11400;
  public static final int ZU_ITEM_GET = 11500;
  public static final int ZU_ITEM_IS_EXIST = 11600;
  public static final int ZU_ITEM_VERSION_NOT_EXIST = 11700;
  public static final int ZU_STATE_STORE_INIT = 11800;
  public static final int ZU_ITEM_DOES_NOT_EXIST = 11900;
  public static final int ZU_ELEMENT_LIST = 12000;
  public static final int ZU_ELEMENT_GET = 12100;
  public static final int ZU_SEARCH = 12200;
  public static final int ZU_ELEMENT_DELETE = 12300;
  public static final int ZU_ELEMENT_CREATE = 12400;
  public static final int ZU_ELEMENT_UPDATE = 12500;
  public static final int ZU_ELEMENT_GET_INFO = 12600;
  public static final int ZU_ELEMENT_SAVE = 12700;
  public static final int ZU_ELEMENT_SEARCH = 12800;


  public static final int MD_ITEM_UPDATE = 20100;
  public static final int MD_ITEM_DELETE = 20200;
  public static final int MD_ITEM_CREATE = 20300;
  public static final int MD_ITEM_GET = 20400;
  public static final int MD_ITEM_IS_EXIST = 20500;
  public static final int MD_ITEM_LIST = 20600;
  public static final int MD_ITEM_VERSIONS_LIST = 20700;
  public static final int MD_ITEM_VERSION_IS_EXIST = 20800;
  public static final int MD_ITEM_VERSION_GET = 20900;
  public static final int MD_ITEM_VERSION_CREATE = 21000;
  public static final int MD_ITEM_VERSION_UPDATE = 21100;
  public static final int MD_ITEM_VERSION_DELETE = 21200;
  public static final int MD_ELEMENT_LIST = 21300;
  public static final int MD_ELEMENT_IS_EXIST = 21400;
  public static final int MD_ELEMENT_GET = 21500;
  public static final int MD_ELEMENT_CREATE = 21600;
  public static final int MD_ELEMENT_UPDATE = 21700;
  public static final int MD_ELEMENT_DELETE = 21800;
  public static final int MD_ITEM_VERSION_PUBLISH = 21900;
  public static final int MD_ITEM_VERSION_SYNC = 22000;
  public static final int MD_ITEM_VERSION_MERGE = 22100;
  public static final int MD_ITEM_VERSION_HISTORY = 22200;
  public static final int MD_ITEM_VERSION_REVERT_HISTORY = 22300;
  public static final int MD_SEARCH = 22400;
  public static final int MD_COMMIT = 22500;

  public static final int ST_ITEM_LIST = 50100;
  public static final int ST_ITEM_IS_EXIST = 50200;
  public static final int ST_ITEM_GET = 50300;
  public static final int ST_ITEM_CREATE = 50400;
  public static final int ST_ITEM_UPDATE = 50500;
  public static final int ST_ITEM_DELETE = 50600;
  public static final int ST_ITEM_VERSIONS_LIST = 50700;
  public static final int ST_ITEM_VERSION_GET = 50800;
  public static final int ST_ITEM_VERSION_CREATE = 50900;
  public static final int ST_ITEM_VERSION_UPDATE = 51000;
  public static final int ST_ITEM_VERSION_DELETE = 51100;
  public static final int ST_ITEM_VERSION_IS_EXIST = 51200;
  public static final int ST_ELEMENT_GET = 51300;
  public static final int ST_ELEMENT_CREATE = 51400;
  public static final int ST_ELEMENT_DELETE = 51500;
  public static final int ST_ELEMENT_UPDATE = 51600;
  public static final int ST_WORKSPACE_CREATE = 51700;
  public static final int ST_WORKSPACE_SAVE = 51800;
  public static final int ST_WORKSPACE_DELETE = 51900;
  public static final int ST_WORKSPACE_LIST = 52000;
  public static final int ST_ELEMENT_LIST = 52100;
  public static final int ST_ELEMENT_IS_EXIST = 52200;

  public static final int CL_ITEM_VERSION_UPDATE = 30100;
  public static final int CL_ITEM_VERSION_CREATE = 30200;
  public static final int CL_ITEM_VERSION_DELETE = 30300;
  public static final int CL_ITEM_CREATE = 30400;
  public static final int CL_ITEM_DELETE = 30500;
  public static final int CL_ELEMENT_UPDATE = 30600;
  public static final int CL_ELEMENT_GET = 30700;
  public static final int CL_ELEMENT_CREATE = 30800;
  public static final int CL_ELEMENT_DELETE = 30900;
  public static final int CL_ITEM_VERSION_PUBLISH = 31000;
  public static final int CL_ITEM_VERSION_SYNC = 31100;
  public static final int CL_ITEM_VERSION_MERGE = 31200;
  public static final int CL_ITEM_VERSION_HISTORY = 31300;
  public static final int CL_ITEM_VERSION_REVERT_HISTORY = 31400;


  public static final int IN_SEARCH = 40100;
  public static final int IN_ELEMENT_CREATE = 40200;
  public static final int IN_ELEMENT_DELETE = 40300;
  public static final int IN_ELEMENT_UPDATE = 40400;
  ;


  private int errorCode;
  private Module module;

  public ErrorCode(int errorCode, Module module) {
    this.errorCode = errorCode;
    this.module = module;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String toString() {
    return module.name() + "-" + errorCode;
  }
}
