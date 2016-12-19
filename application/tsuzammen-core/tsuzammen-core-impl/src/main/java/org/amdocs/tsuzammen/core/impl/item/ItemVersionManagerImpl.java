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

package org.amdocs.tsuzammen.core.impl.item;


import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {

  @Override
  public Collection<ItemVersion> list(SessionContext context, String itemId) {
    return getStateAdaptor(context).listItemVersions(context, itemId);
  }

  @Override
  public ItemVersion get(SessionContext context, String itemId, String versionId) {
    return getStateAdaptor(context).getItemVersion(context, itemId, versionId);
  }

  @Override
  public String create(SessionContext context, String itemId, String baseVersionId,
                       Info versionInfo) {
    String versionId = new String(CommonMethods.nextUUID());
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void save(SessionContext context, String itemId, String versionId, Info versionInfo) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
    getStateAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
  }

  @Override
  public void delete(SessionContext context, String itemId, String versionId) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, String itemId, String versionId, String message) {
    getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    getStateAdaptor(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void sync(SessionContext context, String itemId, String versionId) {
    getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    getStateAdaptor(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revert(SessionContext context, String itemId, String versionId,
                     String targetRevisionId) {
  }

  private void validateExistence(SessionContext context, String itemId, String versionId) {
    getStateAdaptor(context).validateItemVersionExistence(context, itemId, versionId);
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
