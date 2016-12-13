package org.amdocs.tsuzammen.adaptor.inbound.impl.item;

import org.amdocs.tsuzammen.adaptor.inbound.api.item.ItemVersionAdaptor;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManager;
import org.amdocs.tsuzammen.core.api.item.ItemVersionManagerFactory;

/**
 * Created by TALIG on 11/27/2016.
 */
public class ItemVersionAdaptorImpl implements ItemVersionAdaptor {

  @Override
  public String create(SessionContext context, String itemId, String baseVersionId, Info versionInfo) {

    return getItemVersionManager(context).create(context, itemId, baseVersionId, versionInfo);
  }

  @Override
  // TODO: 12/4/2016 one commit (becaues of the one save message) ?!
  public void save(SessionContext context, String itemId, String versionId, ItemVersion itemVersion,
                   String message) {

    getItemVersionManager(context).save(context, itemId, versionId, itemVersion, message);
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
