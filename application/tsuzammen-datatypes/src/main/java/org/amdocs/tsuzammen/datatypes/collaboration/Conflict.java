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

package org.amdocs.tsuzammen.datatypes.collaboration;

import org.amdocs.tsuzammen.utils.fileutils.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class Conflict {

  private byte[] localConflict;
  private byte[] remoteConflict;

  public InputStream getLocalConflict(){
    return new ByteArrayInputStream(this.localConflict);
  }

  public InputStream getRemoteConflict(){
    return new ByteArrayInputStream(this.remoteConflict);
  }

  public void setLocalConflict(InputStream localConflict) {
    this.localConflict = FileUtils.toByteArray(localConflict);
  }

  public void setLocalConflict(byte[] localConflict) {
    this.localConflict = localConflict;
  }

  public void setRemoteConflict(InputStream remoteConflict) {
    this.remoteConflict = FileUtils.toByteArray(remoteConflict);
  }

  public void setRemoteConflict(byte[] remoteConflict) {
    this.remoteConflict = remoteConflict;
  }

}
