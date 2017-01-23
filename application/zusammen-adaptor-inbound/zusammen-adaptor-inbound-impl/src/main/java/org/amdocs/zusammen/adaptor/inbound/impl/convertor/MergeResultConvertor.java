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

package org.amdocs.zusammen.adaptor.inbound.impl.convertor;

import org.amdocs.zusammen.adaptor.inbound.api.types.item.ElementConflict;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.InfoConflict;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.MergeResult;
import org.amdocs.zusammen.core.api.types.CoreElementConflict;
import org.amdocs.zusammen.core.api.types.CoreItemVersionInfoConflict;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;

import java.util.stream.Collectors;

public class MergeResultConvertor {
  public static MergeResult getMergeResult(CoreMergeResult coreMergeResult) {
    MergeResult mergeResult = new MergeResult();
    mergeResult.setConflicts(coreMergeResult.getConflict().getElementConflicts().stream()
        .map(MergeResultConvertor::convertElementConflict)
        .collect(Collectors.toList()));
    mergeResult.setInfoConflict(getInfoConflict(coreMergeResult.getConflict()
        .getInfoConflict()));
    return mergeResult;
  }

  private static InfoConflict getInfoConflict(
      CoreItemVersionInfoConflict coreItemVersionInfoConflict) {
    InfoConflict infoConflict = new InfoConflict();
    infoConflict.setLocalInfo(coreItemVersionInfoConflict.getLocalInfo());
    infoConflict.setRemoteInfo(coreItemVersionInfoConflict.getRemoteInfo());

    return infoConflict;

  }

  private static ElementConflict convertElementConflict(CoreElementConflict conflict) {
    ElementConflict elementConflict = new ElementConflict();
    elementConflict.setLocalElement(ElementConvertor.getElement(conflict.getLocalElement()));
    elementConflict.setRemoteElement(ElementConvertor.getElement(conflict.getRemoteElement()));
    return elementConflict;
  }
}
