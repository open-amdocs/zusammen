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

package com.amdocs.zusammen.datatypes.response;

public class ReturnCode {
  private ErrorCode errorCode;
  private String message;
  private ReturnCode returnCode;

  public ReturnCode(int errorCode, Module module, String message, ReturnCode returnCode) {
    this.errorCode = new ErrorCode(errorCode,module);
    this.message = message;
    this.returnCode = returnCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ReturnCode getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(ReturnCode returnCode) {
    this.returnCode = returnCode;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();

    sb.append(errorCode.toString());
    if(message!= null) sb.append("-").append(message);
    sb.append(System.lineSeparator()).append("\t");
    if(returnCode!= null)    sb.append(returnCode.toString());
    return sb.toString();
  }
}
