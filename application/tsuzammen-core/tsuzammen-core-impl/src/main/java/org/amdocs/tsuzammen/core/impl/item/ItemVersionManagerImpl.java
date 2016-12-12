package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.Id;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptor;
import org.amdocs.tsuzammen.adaptor.outbound.api.StateAdaptorFactory;
import org.amdocs.tsuzammen.utils.common.CommonMethods;

import java.util.Collection;

public class ItemVersionManagerImpl implements ItemVersionManager {



  @Override
  public Id create(SessionContext context, Id itemId,Id baseVersionId,  Info versionInfo) {
    //Id versionId = baseVersionId == null ? MAIN_VERSION : new Id(CommonMethods.nextUUID());
    Id versionId = new Id(CommonMethods.nextUUID());
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void save(SessionContext context, Id itemId, Id versionId, ItemVersion itemVersion,
                   String message) {
    validateExistence(context, itemId, versionId);


    getCollaborationAdaptor(context)
        .saveItemVersion(context, itemId, versionId, itemVersion, message);
    //getStateAdaptor(context).saveItemVersion(context, itemId, versionId, itemVersion);
  }

  @Override
  public void delete(SessionContext context, Id itemId, Id versionId) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, Id itemId, Id versionId, String message) {
    getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    getStateAdaptor(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void sync(SessionContext context, Id itemId, Id versionId) {
    getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    getStateAdaptor(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revert(SessionContext context, Id itemId, Id versionId,
                     String targetRevisionId) {

  }

  @Override
  public Collection<ItemVersion> get(SessionContext context, Id itemId, Id versionId) {
    // TODO: 12/4/2016 add filter
    return null;
  }

  @Override
  public Collection<ItemVersion> getInfo(SessionContext context, Id itemId, Id versionId) {
    return null;
  }

  private void validateExistence(SessionContext context, Id itemId, Id versionId) {
    getStateAdaptor(context).validateItemVersionExistence(context, itemId, versionId);
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
