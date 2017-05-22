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

package com.amdocs.zusammen.adaptor.outbound.impl.convertor;

import com.amdocs.zusammen.core.api.types.CoreElementConflict;
import com.amdocs.zusammen.core.api.types.CoreMergeConflict;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationElementConflict;
import com.amdocs.zusammen.sdk.collaboration.types.CollaborationMergeConflict;

import java.util.stream.Collectors;

public class CollaborationMergeConflictConvertor {

  public static CoreMergeConflict convert(CollaborationMergeConflict collaborationMergeConflict) {
    if (collaborationMergeConflict == null) {
      return null;
    }

    CoreMergeConflict coreMergeConflict = new CoreMergeConflict();
    coreMergeConflict.setVersionDataConflict(collaborationMergeConflict.getVersionDataConflict());
    coreMergeConflict.setElementConflicts(collaborationMergeConflict.getElementConflicts().stream()
        .map(CollaborationMergeConflictConvertor::convertElementConflict)
        .collect(Collectors.toList()));
    return coreMergeConflict;
  }

  private static CoreElementConflict convertElementConflict(
      CollaborationElementConflict elementDataConflict) {
    CoreElementConflict coreElementConflict = new CoreElementConflict();
    coreElementConflict.setLocalElement(
        CollaborationElementConvertor.convertToCoreElement(elementDataConflict.getLocalElement()));
    coreElementConflict.setRemoteElement(
        CollaborationElementConvertor.convertToCoreElement(elementDataConflict.getRemoteElement()));
    return coreElementConflict;
  }
}
