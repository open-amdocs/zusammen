/*
 * Copyright © 2016-2017 European Support Limited
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

package org.amdocs.zusammen.core.impl.item;

import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.impl.CollaborationAdaptorImpl;
import org.amdocs.zusammen.adaptor.outbound.impl.item.ItemVersionStateAdaptorImpl;
import org.amdocs.zusammen.core.api.item.ItemManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreMergeChange;
import org.amdocs.zusammen.core.api.types.CoreMergeResult;
import org.amdocs.zusammen.core.api.types.CorePublishResult;
import org.amdocs.zusammen.core.impl.TestUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ItemVersion;
import org.amdocs.zusammen.datatypes.item.ItemVersionChange;
import org.amdocs.zusammen.datatypes.item.ItemVersionData;
import org.amdocs.zusammen.datatypes.item.Relation;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemVersionManagerImplTest {
  private static final UserInfo USER = new UserInfo("ItemVersionManagerImplTest_user");
  private static final SessionContext context = TestUtils.createSessionContext(USER, "test");

  private static final String ITEM_NOT_EXIST = "Item with id .* does not exists.";
  private static final String ITEM_VERSION_NOT_EXIST =
      "Item Id .*, version Id .* does not exist in .* space";

  @Mock
  private ItemVersionStateAdaptor stateAdaptorMock = new ItemVersionStateAdaptorImpl();
  @Mock
  private CollaborationAdaptor collaborationAdaptorMock = new CollaborationAdaptorImpl();
  @Mock
  private ItemManager itemManagerMock;
  @Mock
  private ElementVisitor elementVisitorMock;
  @InjectMocks
  @Spy
  private ItemVersionManagerImpl itemVersionManagerImpl;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(itemVersionManagerImpl.getStateAdaptor(anyObject())).thenReturn(stateAdaptorMock);
    when(itemVersionManagerImpl.getCollaborationAdaptor(anyObject()))
        .thenReturn(collaborationAdaptorMock);
    when(itemVersionManagerImpl.getItemManager(anyObject())).thenReturn(itemManagerMock);
  }

  @Test
  public void testList() throws Exception {
    Id itemId = new Id();
    List<ItemVersion> retrievedVersions = Arrays.asList(
        TestUtils.createItemVersion(new Id(), new Id(), "v1"),
        TestUtils.createItemVersion(new Id(), new Id(), "v2"),
        TestUtils.createItemVersion(new Id(), new Id(), "v3"));
    Response<List<ItemVersion>> itemListResponse = new Response<List<ItemVersion>>
        (retrievedVersions);
    doReturn(true).when(itemManagerMock).isExist(anyObject(),
        anyObject());
    doReturn(itemListResponse).when(stateAdaptorMock)
        .listItemVersions(context, Space.PRIVATE, itemId);

    Collection<ItemVersion> versions = itemVersionManagerImpl.list(context, Space.PRIVATE, itemId);
    Assert.assertEquals(versions, retrievedVersions);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testListOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.list(context, Space.PRIVATE, new Id());
  }

  @Test
  public void testGet() throws Exception {
    Id itemId = new Id();
    ItemVersion retrievedVersion = TestUtils.createItemVersion(new Id(), new Id(), "v1");
    Response<ItemVersion> itemVersionResponse = new Response<ItemVersion>(retrievedVersion);
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(itemVersionResponse).when(stateAdaptorMock)
        .getItemVersion(context, Space.PRIVATE, itemId, retrievedVersion.getId());

    ItemVersion version =
        itemVersionManagerImpl.get(context, Space.PRIVATE, itemId, retrievedVersion.getId());
    Assert.assertEquals(version, retrievedVersion);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testGetOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.get(context, Space.PRIVATE, new Id(), new Id());
  }

  @Test
  public void testGetNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();

    ItemVersion itemVersion = null;
    Response<ItemVersion> itemVersionResponse = new Response<ItemVersion>(itemVersion);

    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(itemVersionResponse).when(stateAdaptorMock)
        .getItemVersion(context, Space.PRIVATE, itemId, versionId);
    ItemVersion version = itemVersionManagerImpl.get(context, Space.PRIVATE, itemId, versionId);
    Assert.assertNull(version);
  }

  @Test
  public void testCreate() throws Exception {
    Id itemId = new Id();
    Id baseVersionId = new Id();
    ItemVersionData data = new ItemVersionData();
    data.setInfo(TestUtils.createInfo("v1"));
    data.setRelations(Arrays.asList(new Relation(), new Relation()));

    doReturn(true)
        .when(itemVersionManagerImpl).isExist(context, Space.PRIVATE, itemId, baseVersionId);
    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock).createItemVersion
        (anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock).createItemVersion
        (anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
    Id versionId = itemVersionManagerImpl.create(context, itemId, baseVersionId, data);
    Assert.assertNotNull(versionId);

    verify(collaborationAdaptorMock)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);
    verify(stateAdaptorMock)
        .createItemVersion(context, Space.PRIVATE, itemId, baseVersionId, versionId, data);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testCreateOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.create(context, new Id(), new Id(), new ItemVersionData());
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testCreateBasedOnNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);

    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock).isItemVersionExist(context, Space
            .PRIVATE, itemId,
        versionId);
    itemVersionManagerImpl.create(context, itemId, versionId, new ItemVersionData());
  }

  @Test
  public void testUpdate() throws Exception {
    ItemVersionData data = new ItemVersionData();
    data.setInfo(TestUtils.createInfo("v1 updated"));
    data.setRelations(Arrays.asList(new Relation(), new Relation()));

    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true)
        .when(itemVersionManagerImpl).isExist(context, Space.PRIVATE, itemId, versionId);
    doReturn(new Response<Boolean>(true))
        .when(collaborationAdaptorMock).updateItemVersion(context, itemId, versionId,data);
    doReturn(new Response(Void.TYPE))
        .when(stateAdaptorMock).updateItemVersion(context,Space.PRIVATE, itemId, versionId,data);
    itemVersionManagerImpl.update(context, itemId, versionId, data);

    verify(collaborationAdaptorMock).updateItemVersion(context, itemId, versionId, data);
    verify(stateAdaptorMock).updateItemVersion(context, Space.PRIVATE, itemId, versionId, data);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testUpdateOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.update(context, new Id(), new Id(), new ItemVersionData());
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testUpdateNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock).isItemVersionExist(context, Space
        .PRIVATE, itemId, versionId);
    itemVersionManagerImpl.update(context, itemId, versionId, new ItemVersionData());
  }

  @Test
  public void testDelete() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true)
        .when(itemVersionManagerImpl).isExist(context, Space.PRIVATE, itemId, versionId);

    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock).deleteItemVersion
        (context, itemId, versionId);
    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock).deleteItemVersion
        (context, Space.PRIVATE, itemId, versionId);

    itemVersionManagerImpl.delete(context, itemId, versionId);

    verify(collaborationAdaptorMock).deleteItemVersion(context, itemId, versionId);
    verify(stateAdaptorMock).deleteItemVersion(context, Space.PRIVATE, itemId, versionId);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testDeleteOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.delete(context, new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testDeleteNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock).deleteItemVersion
        (context, itemId, versionId);
    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock).deleteItemVersion
        (context, Space.PRIVATE, itemId, versionId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock).isItemVersionExist(context,
        Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.delete(context, itemId, versionId);
  }

  @Test
  public void testSuccessfulPublishNew() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    CoreMergeChange change = testSuccessfulPublish(itemId, versionId, true);

    verify(stateAdaptorMock).createItemVersion(context, Space.PUBLIC, itemId,
        change.getChangedVersion().getItemVersion().getBaseId(), versionId,
        change.getChangedVersion().getItemVersion().getData());
  }

  @Test
  public void testSuccessfulPublishExsiting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    CoreMergeChange change = testSuccessfulPublish(itemId, versionId, false);

    verify(stateAdaptorMock).updateItemVersion(context, Space.PUBLIC, itemId, versionId,
        change.getChangedVersion().getItemVersion().getData());
  }

  private CoreMergeChange testSuccessfulPublish(Id itemId, Id versionId, boolean newVersion) {
    String message = "publish message";
    CorePublishResult publishResult = new CorePublishResult();
    CoreMergeChange change = createMergeChange(versionId, newVersion);
    publishResult.setChange(change);
    Response<CorePublishResult> corePublishResultResponse = new Response<CorePublishResult>
        (publishResult);
    doReturn(true)
        .when(itemVersionManagerImpl).isExist(context, Space.PRIVATE, itemId, versionId);
    doReturn(corePublishResultResponse).when(collaborationAdaptorMock)
        .publishItemVersion(context, itemId, versionId, message);

    itemVersionManagerImpl.publish(context, itemId, versionId, message);

    verifySaveChangedElements(itemId, versionId, Space.PUBLIC, change.getChangedElements());

    return change;
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testPublishOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.publish(context, new Id(), new Id(), "");
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testPublishNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();

    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock).isItemVersionExist(context, Space
        .PRIVATE, itemId, versionId);

    itemVersionManagerImpl.publish(context, itemId, versionId, "");
  }

  @Test
  public void testSuccessfulSyncNew() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    CoreMergeChange change = testSuccessfulSync(itemId, versionId, true);

    verify(stateAdaptorMock).createItemVersion(context, Space.PRIVATE, itemId,
        change.getChangedVersion().getItemVersion().getBaseId(), versionId,
        change.getChangedVersion().getItemVersion().getData());
  }

  @Test
  public void testSuccessfulSyncExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    CoreMergeChange change = testSuccessfulSync(itemId, versionId, false);

    verify(stateAdaptorMock).updateItemVersion(context, Space.PRIVATE, itemId, versionId,
        change.getChangedVersion().getItemVersion().getData());
  }

  private CoreMergeChange testSuccessfulSync(Id itemId, Id versionId, boolean newVersion) {
    CoreMergeResult retrievedSyncResult = new CoreMergeResult();
    CoreMergeChange change = createMergeChange(versionId, newVersion);
    retrievedSyncResult.setChange(change);
    Response<CoreMergeResult> coreMergeResultResponse = new Response<CoreMergeResult>
        (retrievedSyncResult);
    doReturn(true)
        .when(itemVersionManagerImpl).isExist(context, Space.PUBLIC, itemId, versionId);
    doReturn(coreMergeResultResponse).when(collaborationAdaptorMock)
        .syncItemVersion(context, itemId, versionId);

    CoreMergeResult syncResult = itemVersionManagerImpl.sync(context, itemId, versionId);
    Assert.assertEquals(syncResult, retrievedSyncResult);

    verifySaveChangedElements(itemId, versionId, Space.PRIVATE, change.getChangedElements());

    return change;
  }


  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testSyncOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.sync(context, new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testSyncNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock).isItemVersionExist(context, Space
        .PUBLIC, itemId, versionId);
    itemVersionManagerImpl.sync(context, itemId, versionId);
  }

  @Test
  public void testMerge() throws Exception {
    // TODO: 1/26/2017
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST*/)
  public void testMergeOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.merge(context, new Id(), new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testMergeNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    Id versionBaseId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock)
        .isItemVersionExist(context, Space.PRIVATE, itemId, versionId);
    doReturn(new Response<Boolean>(true)).when(stateAdaptorMock)
        .isItemVersionExist(context, Space.PRIVATE, itemId,
            versionBaseId);
    doReturn(new Response<CoreMergeResult>(new CoreMergeResult())).when(collaborationAdaptorMock)
        .mergeItemVersion(context, itemId, versionId, versionBaseId);
    itemVersionManagerImpl.merge(context, itemId, versionId, versionBaseId);
  }

  @Test(expectedExceptions = ZusammenException.class/*,
      expectedExceptionsMessageRegExp = ITEM_VERSION_NOT_EXIST*/)
  public void testMergeNonExistingSource() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    Id versionBaseId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(true).when(itemVersionManagerImpl).isExist(context, Space.PRIVATE, itemId, versionId);
    doReturn(new Response<Boolean>(true)).when(stateAdaptorMock)
        .isItemVersionExist(context, Space.PRIVATE, itemId, versionId);
    doReturn(new Response<Boolean>(false)).when(stateAdaptorMock)
        .isItemVersionExist(context, Space.PRIVATE, itemId,
            versionBaseId);
    itemVersionManagerImpl.merge(context, itemId, versionId, versionBaseId);
  }

  private void verifySaveChangedElements(Id itemId, Id versionId, Space space,
                                         Collection<CoreElement> changedElements) {
    ElementContext elementContext = new ElementContext(itemId, versionId);
    changedElements.forEach(element ->
        verify(elementVisitorMock)
            .visit(eq(context), eq(elementContext), eq(space), eq(element)));
  }

  private CoreMergeChange createMergeChange(Id versionId, boolean newVersion) {
    CoreMergeChange change = new CoreMergeChange();

    ItemVersionChange changedVersion = new ItemVersionChange();
    changedVersion.setAction(newVersion ? Action.CREATE : Action.UPDATE);
    changedVersion.setItemVersion(TestUtils.createItemVersion(versionId, new Id(), "v1"));
    change.setChangedVersion(changedVersion);

    CoreElement element1 = createElement(Action.CREATE);
    CoreElement element2 = createElement(newVersion ? Action.CREATE : Action.UPDATE);
    CoreElement element3 = createElement(newVersion ? Action.CREATE : Action.DELETE);
    change.setChangedElements(Arrays.asList(element1, element2, element3));
    return change;
  }

  private CoreElement createElement(Action action) {
    CoreElement element = new CoreElement();
    element.setId(new Id());
    element.setAction(action);
    return element;
  }
}