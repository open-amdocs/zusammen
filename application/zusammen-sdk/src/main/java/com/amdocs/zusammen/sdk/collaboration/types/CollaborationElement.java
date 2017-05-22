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

package com.amdocs.zusammen.sdk.collaboration.types;

import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.sdk.types.ElementDescriptor;
import com.amdocs.zusammen.utils.fileutils.FileUtils;

import java.io.InputStream;
import java.util.Objects;

public class CollaborationElement extends ElementDescriptor {
  private byte[] data;
  private byte[] searchableData;
  private byte[] visualization;

  public CollaborationElement(Id itemId, Id versionId, Namespace namespace,
                              Id id) {
    super(itemId, versionId, namespace, id);
  }

  public InputStream getData() {
    return getInputStream(this.data);
  }

  public void setData(InputStream data) {
    this.data = getBytes(data);
  }

  public InputStream getSearchableData() {
    return getInputStream(searchableData);
  }

  public void setSearchableData(InputStream searchableData) {
    this.searchableData = getBytes(searchableData);
  }

  public InputStream getVisualization() {
    return getInputStream(this.visualization);
  }

  public void setVisualization(InputStream visualization) {
    this.visualization = getBytes(visualization);
  }

  private InputStream getInputStream(byte[] bytes) {
    return Objects.isNull(bytes) || bytes.length == 0 ? null : FileUtils.toInputStream(bytes);
  }

  private byte[] getBytes(InputStream inputStream) {
    return inputStream == null ? null : FileUtils.toByteArray(inputStream);
  }
}
