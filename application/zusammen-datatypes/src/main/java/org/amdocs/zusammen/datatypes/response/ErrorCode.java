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


  public static final int SS_ITEM_LIST = 20100;
  public static final int SS_ITEM_IS_EXIST = 20200;
  public static final int SS_ITEM_GET = 20300;
  public static final int SS_ITEM_CREATE = 20400;
  public static final int SS_ITEM_UPDATE = 20500;
  public static final int SS_ITEM_DELETE = 20600;
  public static final int SS_ITEM_VERSIONS_LIST = 20700;
  public static final int SS_ITEM_VERSION_GET = 20800;
  public static final int SS_ITEM_VERSION_CREATE = 20900;
  public static final int SS_ITEM_VERSION_UPDATE = 21000;
  public static final int SS_ITEM_VERSION_DELETE = 21100;
  public static final int SS_ITEM_VERSION_IS_EXIST = 21200;
  public static final int SS_ELEMENT_GET = 21300;
  public static final int SS_ELEMENT_CREATE = 21400;
  public static final int SS_ELEMENT_DELETE = 21500;
  public static final int SS_ELEMENT_UPDATE = 21600;
  public static final int SS_WORKSPACE_CREATE = 21700;
  public static final int SS_WORKSPACE_SAVE = 21800;
  public static final int SS_WORKSPACE_DELETE = 21900;
  public static final int SS_WORKSPACE_LIST = 22000;
  public static final int SS_ELEMENT_LIST = 22100;

  public static final int CL_ITEM_VERSION_UPDATE = 30100;
  public static final int CL_ITEM_VERSIONS_LIST = 30200;
  public static final int CL_ITEM_VERSION_GET = 30300;
  public static final int CL_ITEM_VERSION_CREATE = 30400;
  public static final int CL_ITEM_VERSION_DELETE = 30500;
  public static final int CL_ITEM_VERSION_IS_EXIST = 30600;
  public static final int CL_ITEM_UPDATE = 30700;
  public static final int CL_ITEM_LIST = 30800;
  public static final int CL_ITEM_GET = 30900;
  public static final int CL_ITEM_CREATE = 31000;
  public static final int CL_ITEM_DELETE = 31100;
  public static final int CL_ITEM_IS_EXIST = 31200;
  public static final int CL_ELEMENT_UPDATE = 30700;
  public static final int CL_ELEMENT_LIST = 30800;
  public static final int CL_ELEMENT_GET = 30900;
  public static final int CL_ELEMENT_CREATE = 31000;
  public static final int CL_ELEMENT_DELETE = 31100;
  public static final int CL_ELEMENT_IS_EXIST = 31200;
  public static final int CL_ITEM_VERSION_PUBLISH = 31300;
  public static final int CL_ITEM_VERSION_SYNC = 31400;
  public static final int CL_ITEM_VERSION_MERGE = 31500;
  public static final int CL_ITEM_VERSION_HISTORY = 31600;
  public static final int CL_ITEM_VERSION_REVERT_HISTORY = 31700;


  public static final int IN_SEARCH = 40100;
  public static final int IN_ELEMENT_CREATE = 40200;
  public static final int IN_ELEMENT_DELETE = 40300;
  public static final int IN_ELEMENT_UPDATE = 40400;


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
}
