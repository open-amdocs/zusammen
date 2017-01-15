/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.zusammen.adaptor.inbound.impl.item;

import org.amdocs.zusammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import org.amdocs.zusammen.adaptor.inbound.impl.convertor.ConverterSyncResultCoreSynResult;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.core.api.types.CoreSyncResult;
import org.amdocs.zusammen.adaptor.inbound.api.types.item.SyncResult;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.item.ItemVersionManagerFactory;

import java.util.Collection;

public class ItemVersionAdaptorImpl implements ItemVersionAdaptor {

  @Override
  public Collection<ItemVersion> list(SessionContext context, Id itemId) {
    return getItemVersionManager(context).list(context, itemId);
  }

  @Override
  public ItemVersion get(SessionContext context, Id itemId, Id versionId) {
    return getItemVersionManager(context).get(context, itemId, versionId);
  }

  @Override
  public Id create(SessionContext context, Id itemId, Id baseVersionId,
                       Info versionInfo) {

    return getItemVersionManager(context).create(context, itemId, baseVersionId, versionInfo);
  }

  @Override
  public void save(SessionContext context, Id itemId, Id versionId,
                   Info versionInfo) {
    getItemVersionManager(context).save(context, itemId, versionId, versionInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    getItemVersionManager(context).delete(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    getItemVersionManager(context).publish(context, itemId, versionId, message);
  }

  @Override
  public SyncResult sync(SessionContext context, Id itemId, Id versionId) {
    CoreSyncResult coreSyncResult = getItemVersionManager(context).sync(context, itemId, versionId);
    SyncResult syncResult = ConverterSyncResultCoreSynResult.getSyncResult(coreSyncResult);
    return syncResult;
  }

  @Override
  public SyncResult merge(SessionContext context, Id itemId, Id versionId, Id sourceVersionId) {
    CoreSyncResult coreSyncResult = getItemVersionManager(context).merge(context, itemId, versionId,
        sourceVersionId);
    return null;
  }

  private SessionContext createSessionContext(UserInfo user) {
    SessionContext context = new SessionContext();
    context.setUser(user);
    return context;
  }

  private ItemVersionManager getItemVersionManager(SessionContext context) {
    return ItemVersionManagerFactory.getInstance().createInterface(context);
  }
}
