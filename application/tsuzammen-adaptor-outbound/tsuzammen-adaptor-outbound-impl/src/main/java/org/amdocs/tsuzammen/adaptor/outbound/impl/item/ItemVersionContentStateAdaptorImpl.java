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

import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionContentStateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.impl.OutboundAdaptorUtils;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;

import java.net.URI;

public class ItemVersionContentStateAdaptorImpl implements ItemVersionContentStateAdaptor {

  @Override
  public boolean isItemVersionEntityExist(SessionContext context, String itemId, String versionId,
                                          URI namespace, String entityId) {
    return OutboundAdaptorUtils.getStateStore(context)
        .isItemVersionEntityExist(context, itemId, versionId, namespace, entityId);
  }

  @Override
  public Entity getItemVersionEntity(SessionContext context, String itemId, String versionId,
                                     URI namespace, String entityId) {
    return createEntity(entityId,
        OutboundAdaptorUtils.getStateStore(context)
            .getItemVersionEntity(context, itemId, versionId, namespace, entityId));
  }

  @Override
  public void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                                      URI namespace, Entity entity) {
    OutboundAdaptorUtils.getStateStore(context)
        .createItemVersionEntity(context, itemId, versionId, namespace,
            entity.getId(), createEntityInfo(entity));
  }

  @Override
  public void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                                    URI namespace, Entity entity) {
    OutboundAdaptorUtils.getStateStore(context)
        .saveItemVersionEntity(context, itemId, versionId, namespace,
            entity.getId(), createEntityInfo(entity));
  }


  private EntityInfo createEntityInfo(Entity entity) {
    EntityInfo entityInfo = new EntityInfo();
    entityInfo.setInfo(entity.getInfo());
    entityInfo.setRelations(entity.getRelations());
    return entityInfo;
  }

  private Entity createEntity(String entityId, EntityInfo entityInfo) {
    Entity entity = new Entity();
    entity.setId(entityId);
    entity.setInfo(entityInfo.getInfo());
    entity.setRelations(entityInfo.getRelations());
    return entity;
  }

}
