package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.util.Collection;

public class ItemVersionStateAdaptorEmptyImpl implements ItemVersionStateAdaptor {
  @Override
  public Collection<ItemVersion> listItemVersions(SessionContext context, String itemId) {
    return null;
  }

  @Override
  public boolean isItemVersionExist(SessionContext context, String itemId, String versionId) {
    return true;
  }

  @Override
  public ItemVersion getItemVersion(SessionContext context, String itemId, String versionId) {
    return null;
  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                                String versionId, Info versionInfo) {

  }

  @Override
  public void saveItemVersion(SessionContext context, String itemId, String versionId,
                              Info versionInfo) {

  }

  @Override
  public void deleteItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void syncItemVersion(SessionContext context, String itemId, String versionId) {

  }
}
