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

package org.amdocs.tsuzammen.adaptor.outbound.impl.item;

import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.Item;

import java.util.Collection;

public class ItemStateAdaptorImpl implements ItemStateAdaptor {
  @Override
  public Collection<Item> listItems(SessionContext context) {
    return OutboundAdaptorUtils.getStateStore(context).listItems(context);
  }

  @Override
  public boolean isItemExist(SessionContext context, Id itemId) {
    return OutboundAdaptorUtils.getStateStore(context).isItemExist(context, itemId);
  }

  @Override
  public Item getItem(SessionContext context, Id itemId) {
    return OutboundAdaptorUtils.getStateStore(context).getItem(context, itemId);
  }

  @Override
  public void createItem(SessionContext context, Id itemId, Info itemInfo) {
    OutboundAdaptorUtils.getStateStore(context).createItem(context, itemId, itemInfo);
  }

  @Override
  public void saveItem(SessionContext context, Id itemId, Info itemInfo) {
    OutboundAdaptorUtils.getStateStore(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void deleteItem(SessionContext context, Id itemId) {
    OutboundAdaptorUtils.getStateStore(context).deleteItem(context, itemId);
  }

}
