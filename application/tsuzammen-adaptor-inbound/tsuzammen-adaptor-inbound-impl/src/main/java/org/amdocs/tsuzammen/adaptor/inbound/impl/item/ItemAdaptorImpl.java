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

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.Item;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.core.api.item.ItemManagerFactory;

import java.util.Collection;

public class ItemAdaptorImpl implements ItemAdaptor {

  @Override
  public Collection<Item> list(SessionContext context) {
    return getItemManager(context).list(context);
  }

  @Override
  public Item get(SessionContext context, Id itemId) {
    return getItemManager(context).get(context, itemId);
  }

  @Override
  public Id create(SessionContext context, Info itemInfo) {
    return getItemManager(context).create(context, itemInfo);
  }

  @Override
  public void save(SessionContext context, Id itemId, Info itemInfo) {
    getItemManager(context).save(context, itemId, itemInfo);
  }

  @Override
  public void delete(SessionContext context, Id itemId) {
    getItemManager(context).delete(context, itemId);
  }

  private ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
