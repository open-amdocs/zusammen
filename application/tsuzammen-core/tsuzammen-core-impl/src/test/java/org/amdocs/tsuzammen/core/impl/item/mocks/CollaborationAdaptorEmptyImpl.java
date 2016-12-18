package org.amdocs.tsuzammen.core.impl.item.mocks;

import org.amdocs.tsuzammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;

import java.net.URI;
import java.util.Collection;

public class CollaborationAdaptorEmptyImpl implements CollaborationAdaptor {
  @Override
  public void createItem(SessionContext context, String itemId,  Info info) {

  }

  @Override
  public void saveItem(SessionContext context, String itemId, Info itemInfo) {

  }

  @Override
  public void deleteItem(SessionContext context, String itemId) {

  }

  @Override
  public void createItemVersion(SessionContext context, String itemId, String baseVersionId,
                                String versionId, Info info) {

  }

  /*@Override
  public void saveItemVersion(SessionContext context, String itemId, String versionId,
                              ItemVersion itemVersion, String message) {

  }
*/
  @Override
  public void deleteItemVersion(SessionContext context, String itemId, String versionId) {

  }

  @Override
  public void publishItemVersion(SessionContext context, String itemId, String versionId,
                                 String message) {

  }

  @Override
  public void syncItemVersion(SessionContext context, String itemId, String versionId) {

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
                                      URI namespace, Entity entity, Format dataFormat) {

  }

  @Override
  public void saveItemVersionEntity(SessionContext context, String itemId, String versionId,
                                    URI namespace, Entity entity, Format dataFormat) {

  }
}
