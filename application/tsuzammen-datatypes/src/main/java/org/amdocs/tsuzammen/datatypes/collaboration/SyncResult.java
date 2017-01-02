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

public class SyncResult {
  private boolean resultStatus=true;

  private byte[] source;
  private byte[] target;

  private byte[] sourceConflict;
  private byte[] targetConflict;



  public boolean isSuccesses(){
      return this.resultStatus;
    }

  public InputStream getSource(){
    return new ByteArrayInputStream(this.source);
  }

  public InputStream getTarget(){
    return new ByteArrayInputStream(this.target);
  }

  public InputStream getSourceConflict(){
    return new ByteArrayInputStream(this.sourceConflict);
  }

  public InputStream getTargetConflict(){
    return new ByteArrayInputStream(this.targetConflict);
  }

  public void setSource(InputStream source) {
    this.source = FileUtils.toByteArray(source);
  }

  public void setSource(byte[] source) {
    this.source = source;
  }

  public void setTarget(InputStream target) {
    this.target = FileUtils.toByteArray(target);
  }

  public void setTarget(byte[] target) {
    this.target = target;
  }

  public void setSourceConflict(InputStream sourceConflict) {
    this.sourceConflict = FileUtils.toByteArray(sourceConflict);
  }

  public void setSourceConflict(byte[] sourceConflict) {
    this.sourceConflict = sourceConflict;
  }

  public void setTargetConflict(InputStream targetConflict) {
    this.targetConflict = FileUtils.toByteArray(targetConflict);
  }

  public void setTargetConflict(byte[] targetConflict) {
    this.targetConflict = targetConflict;
  }

  public void setResultStatus(boolean status){
    this.resultStatus = status;
  }


}
