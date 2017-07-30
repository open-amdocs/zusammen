/*
 * Add Copyright © 2016-2017 European Support Limited
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

package com.amdocs.zusammen.sdk.types;

public class ElementConflictDescriptor {
  private ElementDescriptor localElementDescriptor;
  private ElementDescriptor remoteElementDescriptor;

  public void setLocalElementDescriptor(ElementDescriptor localElementDescriptor) {
    this.localElementDescriptor = localElementDescriptor;
  }

  public void setRemoteElementDescriptor(ElementDescriptor remoteElementDescriptor) {
    this.remoteElementDescriptor = remoteElementDescriptor;
  }

  public ElementDescriptor getLocalElementDescriptor() {
    return localElementDescriptor;
  }

  public ElementDescriptor getRemoteElementDescriptor() {
    return remoteElementDescriptor;
  }
}
