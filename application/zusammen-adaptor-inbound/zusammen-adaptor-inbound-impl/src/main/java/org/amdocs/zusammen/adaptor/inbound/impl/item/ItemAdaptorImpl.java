/*
 * Copyright © 2016-2017 European Support Limited
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

import org.amdocs.zusammen.adaptor.inbound.api.item.ItemAdaptor;
import org.amdocs.zusammen.core.api.item.ItemManager;
import org.amdocs.zusammen.core.api.item.ItemManagerFactory;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Item;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

import java.util.Collection;

public class ItemAdaptorImpl implements ItemAdaptor {

  @Override
  public Response<Collection<Item>> list(SessionContext context) {
    Response response;
    try {
      Collection<Item> itemCollection = getItemManager(context).list(context);
      response = new Response(itemCollection);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<Item> get(SessionContext context, Id itemId) {
    Response response;
    try {
      Item item = getItemManager(context).get(context, itemId);
      response = new Response(item);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<Id> create(SessionContext context, Info itemInfo) {
    Response response;
    try {
      Id id = getItemManager(context).create(context, itemInfo);
      response = new Response(id);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }

    return response;

  }

  @Override
  public Response<Void> update(SessionContext context, Id itemId, Info itemInfo) {
    Response response;
    try {
      getItemManager(context).update(context, itemId, itemInfo);
      response = new Response(Void.TYPE);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }
    return response;
  }

  @Override
  public Response<Void> delete(SessionContext context, Id itemId) {
    Response response;
    try {
      getItemManager(context).delete(context, itemId);
      response = new Response(Void.TYPE);
    } catch (ZusammenException e) {
      response = new Response(e.getReturnCode());
    }
    return response;
  }

  private ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
