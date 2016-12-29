package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.item.Info;
import org.amdocs.tsuzammen.datatypes.item.ItemVersion;

import java.util.Collection;

public class ItemVersionStateAdaptorEmptyImpl implements ItemVersionStateAdaptor {
  @Override
  public Collection<ItemVersion> listItemVersions(SessionContext context, Id itemId) {
    return null;
  }

  @Override
  public boolean isItemVersionExist(SessionContext context, Id itemId, Id versionId) {
    return true;
  }

  @Override
  public ItemVersion getItemVersion(SessionContext context, Id itemId, Id versionId) {
    return null;
  }

  @Override
  public void createItemVersion(SessionContext context, Id itemId, Id baseVersionId,
                                Id versionId, Info versionInfo) {

  }

  @Override
  public void saveItemVersion(SessionContext context, Id itemId, Id versionId,
                              Info versionInfo) {

  }

  @Override
  public void deleteItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, Id itemId, Id versionId) {

  }

  @Override
  public void syncItemVersion(SessionContext context, Id itemId, Id versionId) {

  }
}
