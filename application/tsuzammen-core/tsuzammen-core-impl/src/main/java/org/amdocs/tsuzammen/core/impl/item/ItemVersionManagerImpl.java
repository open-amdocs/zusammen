package org.amdocs.tsuzammen.core.impl.item;


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
  public String create(SessionContext context, String itemId,String baseVersionId,  Info versionInfo) {
    //String versionId = baseVersionId == null ? MAIN_VERSION : new String(CommonMethods.nextUUID());
    String versionId = new String(CommonMethods.nextUUID());
    getCollaborationAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    getStateAdaptor(context)
        .createItemVersion(context, itemId, baseVersionId, versionId, versionInfo);

    return versionId;
  }

  @Override
  public void save(SessionContext context, String itemId, String versionId, ItemVersion itemVersion,
                   String message) {
    validateExistence(context, itemId, versionId);


    getCollaborationAdaptor(context)
        .saveItemVersion(context, itemId, versionId, itemVersion, message);
    //getStateAdaptor(context).saveItemVersion(context, itemId, versionId, itemVersion);
  }

  @Override
  public void delete(SessionContext context, String itemId, String versionId) {
    validateExistence(context, itemId, versionId);

    getCollaborationAdaptor(context).deleteItemVersion(context, itemId, versionId);
    getStateAdaptor(context).deleteItemVersion(context, itemId, versionId);
  }

  @Override
  public void publish(SessionContext context, String itemId, String versionId, String message) {
    getCollaborationAdaptor(context).publishItemVersion(context, itemId, versionId, message);
    getStateAdaptor(context).publishItemVersion(context, itemId, versionId);
  }

  @Override
  public void sync(SessionContext context, String itemId, String versionId) {
    getCollaborationAdaptor(context).syncItemVersion(context, itemId, versionId);
    getStateAdaptor(context).syncItemVersion(context, itemId, versionId);
  }

  @Override
  public void revert(SessionContext context, String itemId, String versionId,
                     String targetRevisionId) {

  }

  @Override
  public Collection<ItemVersion> get(SessionContext context, String itemId, String versionId) {
    // TODO: 12/4/2016 add filter
    return null;
  }

  @Override
  public Collection<ItemVersion> getInfo(SessionContext context, String itemId, String versionId) {
    return null;
  }

  private void validateExistence(SessionContext context, String itemId, String versionId) {
    getStateAdaptor(context).validateItemVersionExistence(context, itemId, versionId);
  }

  private CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  private StateAdaptor getStateAdaptor(SessionContext context) {
    return StateAdaptorFactory.getInstance().createInterface(context);
  }
}
