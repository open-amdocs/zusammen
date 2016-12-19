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

package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemVersionAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersionData;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManagerFactory;

import java.util.Collection;

public class ItemVersionAdaptorImpl implements ItemVersionAdaptor {

  @Override
  public Collection<ItemVersion> list(SessionContext context, String itemId) {
    return getItemVersionManager(context).list(context, itemId);
  }

  @Override
  public ItemVersion get(SessionContext context, String itemId, String versionId) {
    return getItemVersionManager(context).get(context, itemId, versionId);
  }

  @Override
  public String create(SessionContext context, String itemId, String baseVersionId,
                       Info versionInfo) {

    return getItemVersionManager(context).create(context, itemId, baseVersionId, versionInfo);
  }

  @Override
  public void saveInfo(SessionContext context, String itemId, String versionId,
                       Info versionInfo) {
    getItemVersionManager(context).save(context, itemId, versionId, versionInfo);
  }

  @Override
  public void publish(SessionContext context, String itemId, String versionId, String message) {
    getItemVersionManager(context).publish(context, itemId, versionId, message);
  }

  @Override
  public void sync(SessionContext context, String itemId, String versionId,
                   boolean overrideInd) {
    getItemVersionManager(context).sync(context, itemId, versionId);
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
