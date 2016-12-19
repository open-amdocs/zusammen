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
import org.amdocs.tsuzammen.commons.datatypes.item.Item;
import org.amdocs.tsuzammen.core.api.item.ItemManager;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;

public class ItemManagerImpl implements ItemManager {


  @Override
  public Collection<Item> list(SessionContext context) {
    return getStateAdaptor(context).listItems(context);
  }

  @Override
  public Item get(SessionContext context, String itemId) {
    return getStateAdaptor(context).getItem(context, itemId);
  }

  @Override
  public String create(SessionContext context, Info itemInfo) {
    String itemId = new String(CommonMethods.nextUUID());
    getCollaborationAdaptor(context).createItem(context, itemId, itemInfo);
    getStateAdaptor(context).createItem(context, itemId, itemInfo);
    return itemId;
  }

  @Override
  public void save(SessionContext context, String itemId, Info itemInfo) {
    getCollaborationAdaptor(context).saveItem(context, itemId, itemInfo);
    getStateAdaptor(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void delete(SessionContext context, String itemId) {
    getCollaborationAdaptor(context).deleteItem(context, itemId);
    getStateAdaptor(context).deleteItem(context, itemId);
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
