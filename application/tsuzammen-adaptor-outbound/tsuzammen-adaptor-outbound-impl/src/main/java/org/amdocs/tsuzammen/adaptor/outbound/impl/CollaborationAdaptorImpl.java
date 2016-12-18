package org.amdocs.tsuzammen.adaptor.outbound.impl;


import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.impl.item.EntityData;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.sdk.CollaborationStore;
import org.amdocs.tsuzammen.sdk.CollaborationStoreFactory;

import java.net.URI;
import java.util.Collection;

public class CollaborationAdaptorImpl implements CollaborationAdaptor {

  @Override
  public void createItem(SessionContext context, String itemId,
                         Info itemInfo) {
    getCollaborationStore(context).createItem(context, itemId,  itemInfo);
  }

  @Override
  public void saveItem(SessionContext context, String itemId, Info itemInfo) {
    //getCollaborationStore(context).saveItem(context, itemId, itemInfo);
  }

  @Override
  public void deleteItem(SessionContext context, String itemId) {
    getCollaborationStore(context).deleteItem(context, itemId);
  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                                String versionId, Info info) {
    getCollaborationStore(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, info);
  }

  @Override
  public void saveItemVersion(SessionContext context, String itemId, String versionId,
                              ItemVersion itemVersion, String message) {
    getCollaborationStore(context)
        .saveItemVersion(context, itemId, versionId, itemVersion, message);
  }

  @Override
  public void deleteItemVersion(SessionContext context, String itemId, String versionId) {
    getCollaborationStore(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publishItemVersion(SessionContext context, String itemId, String versionId,
                                 String message) {
    getCollaborationStore(context).publishItemVersion(context, itemId, versionId, message);
  }

  @Override
  public void syncItemVersion(SessionContext context, String itemId, String versionId) {
    getCollaborationStore(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revertItemVersion(SessionContext context, String itemId, String versionId,
                                String targetRevisionId) {

  }

  @Override
  public Collection listItemVersionRevisions(SessionContext context, String itemId,
                                             String versionId) {
    return null;
  }

  @Override
  public Collection listItemVersionMissingRevisions(SessionContext context, String itemId,
                                                    String versionId) {
    return null;
  }

  @Override
  public Collection listItemVersionOverRevisions(SessionContext context, String itemId,
                                                 String versionId) {
    return null;
  }

  @Override
  public void createItemVersionEntity(SessionContext context, String itemId, String versionId,
                                      URI namespace, Entity entity,
                                      Format dataFormat) {
    getCollaborationStore(context).createItemVersionEntity(context, itemId, versionId, namespace,
        entity.getId(), createEntityData(entity, dataFormat));
  }

  @Override
  public void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                                    URI namespace, Entity entity, Format dataFormat) {
    getCollaborationStore(context).saveItemVersionEntity(context, itemId, versionId, namespace,
        entity.getId(), createEntityData(entity, dataFormat));
  }

  private EntityData createEntityData(Entity entity, Format dataFormat) {
    EntityData entityData = new EntityData();
    entityData.setInfo(entity.getInfo());
    entityData.setRelations(entity.getRelations());
    entityData.setData(entity.getData());
    entityData.setDataFormat(dataFormat);
    entityData.setVisualization(entity.getVisualization());
    return entityData;
  }

  private CollaborationStore getCollaborationStore(SessionContext context) {
    return CollaborationStoreFactory.getInstance().createInterface(context);
  }
}
