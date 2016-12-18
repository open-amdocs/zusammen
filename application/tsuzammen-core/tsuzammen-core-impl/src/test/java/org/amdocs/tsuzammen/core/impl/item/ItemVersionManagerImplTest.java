package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Content;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.Info;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersion;
import org.amdocs.tsuzammen.core.impl.item.mocks.CollaborationAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.StateAdaptorEmptyImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemVersionManagerImplTest {
  public static final UserInfo USER = new UserInfo("ItemVersionManagerImplTest_user");

  private ItemVersionManagerImpl itemVersionManagerImpl = spy(new ItemVersionManagerImpl());

  private StateAdaptorEmptyImpl stateAdaptorEmpty = spy(new StateAdaptorEmptyImpl());
  private CollaborationAdaptorEmptyImpl collaborationAdaptorEmpty =
      spy(new CollaborationAdaptorEmptyImpl());

  @BeforeClass
  public void init() {
    when(itemVersionManagerImpl.getCollaborationAdaptor(anyObject()))
        .thenReturn(collaborationAdaptorEmpty);

    when(itemVersionManagerImpl.getStateAdaptor(anyObject())).thenReturn(stateAdaptorEmpty);
  }

  @Test
  public void testCreate() throws Exception {
    SessionContext context = createSessionContext(USER, "test");
    Info versionInfo = createInfo("v1");

    String versionId =
        itemVersionManagerImpl.create(context, "item1", null, versionInfo);
    Assert.assertNotNull(versionId);
  }

  @Test
  public void testSave() throws Exception {
    ItemVersion itemVersion = new ItemVersion();
    Content rootContent1 = new Content();
    rootContent1.setDataFormat(new Format());
    Entity e11 = new Entity();
    e11.setInfo(createInfo("e11"));
    rootContent1.getEntities().add(e11);
    Entity e12 = new Entity();
    e12.setId("e12");
    e12.setInfo(createInfo("e12"));
    Content subContent = new Content();
    subContent.setDataFormat(new Format());
    Entity e3 = new Entity();
    e3.setInfo(createInfo("e3"));
    subContent.getEntities().add(e3);
    e12.getContents().put("subContent", subContent);
    rootContent1.getEntities().add(e12);


    itemVersion.getContents().put("rootContent1", rootContent1);

    Content rootContent2 = new Content();
    rootContent2.setDataFormat(new Format());
    Entity e21 = new Entity();
    e21.setInfo(createInfo("e21"));
    rootContent2.getEntities().add(e21);
    Entity e22 = new Entity();
    e22.setInfo(createInfo("e22"));
    rootContent2.getEntities().add(e22);
    itemVersion.getContents().put("rootContent2", rootContent2);

    SessionContext context = createSessionContext(USER, "test");

    itemVersionManagerImpl.save(context, "item1", "version1", itemVersion, "save item!");

    Assert.assertNotNull(e11.getId());
    Assert.assertNotNull(e3.getId());
    Assert.assertNotNull(e21.getId());
    Assert.assertNotNull(e22.getId());

    verifyStateAdaptorCalls(context, e11, e12, e3, e21, e22);
    verifyCollaborationAdaptorCalls(context, e11, e12, e3, e21, e22,
        rootContent1.getDataFormat(), subContent.getDataFormat(), rootContent2.getDataFormat());
  }

  private void verifyStateAdaptorCalls(SessionContext context, Entity e11, Entity e12, Entity e3,
                                       Entity e21, Entity e22) throws URISyntaxException {
    URI rootContent1Uri = new URI("rootContent1");
    URI subContentUri = new URI("rootContent1/e12/subContent");
    URI rootContent2Uri = new URI("rootContent2");

    verify(stateAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent1Uri, e11);
    verify(stateAdaptorEmpty)
        .saveItemVersionEntity(context, "item1", "version1", rootContent1Uri, e12);
    verify(stateAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", subContentUri, e3);
    verify(stateAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent2Uri, e21);
    verify(stateAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent2Uri, e22);
  }

  private void verifyCollaborationAdaptorCalls(SessionContext context, Entity e11, Entity e12,
                                               Entity e3, Entity e21, Entity e22,
                                               Format rootContent1Format,
                                               Format subContentFormat, Format rootContent2Format)
      throws URISyntaxException {
    URI rootContent1Uri = new URI("rootContent1");
    URI subContentUri = new URI("rootContent1/e12/subContent");
    URI rootContent2Uri = new URI("rootContent2");

    verify(collaborationAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent1Uri, e11,
            rootContent1Format);
    verify(collaborationAdaptorEmpty)
        .saveItemVersionEntity(context, "item1", "version1", rootContent1Uri, e12,
            rootContent1Format);
    verify(collaborationAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", subContentUri, e3, subContentFormat);
    verify(collaborationAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent2Uri, e21,
            rootContent2Format);
    verify(collaborationAdaptorEmpty)
        .createItemVersionEntity(context, "item1", "version1", rootContent2Uri, e22,
            rootContent2Format);
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

  private Info createInfo(String value) {
    Info info = new Info();
    info.addProperty("Name", "name_" + value);
    info.addProperty("Desc", "desc_" + value);
    return info;
  }

  static SessionContext createSessionContext(UserInfo user, String tenant) {
    SessionContext context = new SessionContext();
    context.setUser(user);
    context.setTenant(tenant);
    return context;
  }

}