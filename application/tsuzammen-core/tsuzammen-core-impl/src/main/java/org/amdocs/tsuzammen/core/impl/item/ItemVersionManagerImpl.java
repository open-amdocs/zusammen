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
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptorFactory;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.impl.Messages;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {

  @Override
  public Collection<ItemVersion> list(SessionContext context, Id itemId) {
    return getStateAdaptor(context).listItemVersions(context, itemId);
  }

  @Override
  public ItemVersion get(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    return getStateAdaptor(context).getItemVersion(context, itemId, versionId);
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                       Info versionInfo) {
    Id versionId = new Id();
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void save(SessionContext context, Id itemId, Id versionId, Info versionInfo) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
    getStateAdaptor(context).saveItemVersion(context, itemId, versionId, versionInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    getStateAdaptor(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void sync(SessionContext context, Id itemId, Id versionId) {
    validateItemVersionExistence(context, itemId, versionId);
    getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    getStateAdaptor(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revert(SessionContext context, Id itemId, Id versionId,
                     String targetRevisionId) {
  }

  private void validateItemVersionExistence(SessionContext context, Id itemId,
                                            Id versionId) {
    String space = context.getUser().getUserName();
    if (!getStateAdaptor(context).isItemVersionExist(context, itemId, versionId)) {
      String message = getItemStateAdaptor(context).isItemExist(context, itemId)
          ? String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);// TODO: 12/20/2016 space!
      throw new RuntimeException(message);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionStateAdaptor getStateAdaptor(SessionContext context) {
    return ItemVersionStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemStateAdaptor getItemStateAdaptor(SessionContext context) {
    return ItemStateAdaptorFactory.getInstance().createInterface(context);
  }
}
