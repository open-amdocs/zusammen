package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.impl.item.mocks.CollaborationAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.StateAdaptorEmptyImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ItemVersionManagerImplTest {
  public static final UserInfo USER = new UserInfo("ItemVersionManagerImplTest_user");

  ItemVersionManagerImpl itemVersionManagerImpl = spy(new ItemVersionManagerImpl());

  @BeforeClass
  public void init() {
    when(itemVersionManagerImpl.getCollaborationAdaptor(anyObject()))
        .thenReturn(new CollaborationAdaptorEmptyImpl());
    when(itemVersionManagerImpl.getStateAdaptor(anyObject()))
        .thenReturn(new StateAdaptorEmptyImpl());
  }

  @Test
  public void testCreate() throws Exception {
    SessionContext context = createSessionContext(USER, "test");
    Info versionInfo = new Info();
    versionInfo.addProperty("Name", "name_value");
    versionInfo.addProperty("Desc", "desc_value");

    String versionId =
        itemVersionManagerImpl.create(context, "item1", null, versionInfo);
    Assert.assertNotNull(versionId);
  }

  @Test
  public void testSave() throws Exception {
    ItemVersion itemVersion = new ItemVersion();


  }

  @Test
  public void testDelete() throws Exception {

  }

  @Test
  public void testPublish() throws Exception {

  }

  @Test
  public void testSync() throws Exception {

  }

  @Test
  public void testRevert() throws Exception {

  }

  @Test
  public void testGet() throws Exception {

  }

  @Test
  public void testGetInfo() throws Exception {

  }

  static SessionContext createSessionContext(UserInfo user, String tenant) {
    SessionContext context = new SessionContext();
    context.setUser(user);
    context.setTenant(tenant);
    return context;
  }

}