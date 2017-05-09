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
import org.amdocs.zusammen.datatypes.itemversion.ItemVersionHistory;
import org.amdocs.zusammen.datatypes.itemversion.Tag;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemVersionManagerImplTest {
  private static final UserInfo USER = new UserInfo("ItemVersionManagerImplTest_user");
  private static final SessionContext context = TestUtils.createSessionContext(USER, "test");

  @Mock
  private ItemVersionStateAdaptor stateAdaptorMock;
  @Mock
  private CollaborationAdaptor collaborationAdaptorMock;
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
    doReturn(true).when(itemManagerMock).isExist(anyObject(), anyObject());
    doReturn(new Response<>(retrievedVersions)).when(stateAdaptorMock)
        .listItemVersions(context, Space.PRIVATE, itemId);

    Collection<ItemVersion> versions = itemVersionManagerImpl.list(context, Space.PRIVATE, itemId);
    Assert.assertEquals(versions, retrievedVersions);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testListOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.list(context, Space.PRIVATE, new Id());
  }

  @Test
  public void testGet() throws Exception {
    Id itemId = new Id();
    doReturn(true).when(itemManagerMock).isExist(context, itemId);

    Id versionId = new Id();
    ItemVersion retrievedVersion = TestUtils.createItemVersion(versionId, new Id(), "v1");
    doReturn(new Response<>(retrievedVersion)).when(stateAdaptorMock)
        .getItemVersion(context, Space.PRIVATE, itemId, retrievedVersion.getId());

    ItemVersion version =
        itemVersionManagerImpl.get(context, Space.PRIVATE, itemId, versionId);
    Assert.assertEquals(version, retrievedVersion);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testGetOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.get(context, Space.PRIVATE, new Id(), new Id());
  }

  @Test
  public void testGetNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();

    ItemVersion itemVersion = null;
    Response<ItemVersion> itemVersionResponse = new Response<>(itemVersion);

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
    mockExistingVersion(Space.PRIVATE, itemId, baseVersionId);

    ItemVersionData data = new ItemVersionData();
    data.setInfo(TestUtils.createInfo("v1"));
    data.setRelations(Arrays.asList(new Relation(), new Relation()));

    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock).createItemVersion
        (anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock).createItemVersion
        (anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject());
    Id versionId = itemVersionManagerImpl.create(context, itemId, baseVersionId, data);
    Assert.assertNotNull(versionId);

    verify(collaborationAdaptorMock)
        .createItemVersion(context, itemId, baseVersionId, versionId, data);
    verify(stateAdaptorMock)
        .createItemVersion(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(),
            anyObject(), anyObject());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testCreateOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.create(context, new Id(), new Id(), new ItemVersionData());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testCreateBasedOnNonExisting() throws Exception {
    Id itemId = new Id();
    Id baseVersionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, baseVersionId);
    itemVersionManagerImpl.create(context, itemId, baseVersionId, new ItemVersionData());
  }

  @Test
  public void testUpdate() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    ItemVersionData data = new ItemVersionData();
    data.setInfo(TestUtils.createInfo("v1 updated"));
    data.setRelations(Arrays.asList(new Relation(), new Relation()));
    doReturn(new Response<>(true))
        .when(collaborationAdaptorMock).updateItemVersion(context, itemId, versionId, data);

    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock)
        .updateItemVersion(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(),
            anyObject());

    itemVersionManagerImpl.update(context, itemId, versionId, data);

    verify(collaborationAdaptorMock).updateItemVersion(context, itemId, versionId, data);

    verify(stateAdaptorMock)
        .updateItemVersion(anyObject(), anyObject(), anyObject(), anyObject(), anyObject(),
            anyObject());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testUpdateOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.update(context, new Id(), new Id(), new ItemVersionData());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testUpdateNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.update(context, itemId, versionId, new ItemVersionData());
  }

  @Test
  public void testDelete() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock)
        .deleteItemVersion(context, itemId, versionId);
    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock)
        .deleteItemVersion(context, Space.PRIVATE, itemId, versionId);

    itemVersionManagerImpl.delete(context, itemId, versionId);

    verify(collaborationAdaptorMock).deleteItemVersion(context, itemId, versionId);
    verify(stateAdaptorMock).deleteItemVersion(context, Space.PRIVATE, itemId, versionId);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testDeleteOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.delete(context, new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testDeleteNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.delete(context, itemId, versionId);
  }

  @Test
  public void testTagVersion() throws Exception {
    testTag(null);
  }

  @Test
  public void testTagChange() throws Exception {
    testTag(new Id());
  }

  private void testTag(Id changeId) {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    Tag tag = new Tag("tagName", "tagDesc");
    doReturn(new Response<>(Void.TYPE)).when(collaborationAdaptorMock)
        .tagItemVersion(context, itemId, versionId, changeId, tag);

    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock)
        .updateItemVersionModificationTime(eq(context), eq(Space.PRIVATE), eq(itemId),
            eq(versionId), any(Date.class));

    itemVersionManagerImpl.tag(context, itemId, versionId, changeId, tag);

    verify(collaborationAdaptorMock).tagItemVersion(context, itemId, versionId, changeId, tag);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testTagOnNonExistingItem() throws Exception {
    itemVersionManagerImpl
        .tag(context, new Id(), new Id(), new Id(), new Tag("tagName", "tagDesc"));
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testTagNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.tag(context, itemId, versionId, new Id(), new Tag("tagName", "tagDesc"));
  }

  @Test
  public void testSuccessfulPublishNew() throws Exception {
    testSuccessfulPublish(new Id(), new Id(), true);
  }

  @Test
  public void testSuccessfulPublishExisting() throws Exception {
    testSuccessfulPublish(new Id(), new Id(), false);
  }

  private CoreMergeChange testSuccessfulPublish(Id itemId, Id versionId, boolean newVersion) {
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    String message = "publish message";

    CorePublishResult publishResult = new CorePublishResult();
    CoreMergeChange change = createMergeChange(versionId, newVersion);
    publishResult.setChange(change);
    doReturn(new Response<>(publishResult)).when(collaborationAdaptorMock)
        .publishItemVersion(context, itemId, versionId, message);

    mockItemVersionChangeSave(Space.PUBLIC, itemId, versionId, newVersion, change);

    itemVersionManagerImpl.publish(context, itemId, versionId, message);

    verifySaveChangedElements(itemId, versionId, Space.PUBLIC, change.getChangedElements());

    return change;
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testPublishOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.publish(context, new Id(), new Id(), "");
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testPublishNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);

    itemVersionManagerImpl.publish(context, itemId, versionId, "");
  }

  @Test
  public void testSuccessfulSyncNew() throws Exception {
    testSuccessfulSync(new Id(), new Id(), true);
  }

  @Test
  public void testSuccessfulSyncExisting() throws Exception {
    testSuccessfulSync(new Id(), new Id(), false);
  }

  private CoreMergeChange testSuccessfulSync(Id itemId, Id versionId, boolean newVersion) {
    mockExistingVersion(Space.PUBLIC, itemId, versionId);

    CoreMergeResult retrievedSyncResult = new CoreMergeResult();
    CoreMergeChange change = createMergeChange(versionId, newVersion);
    retrievedSyncResult.setChange(change);
    doReturn(new Response<>(retrievedSyncResult))
        .when(collaborationAdaptorMock).syncItemVersion(context, itemId, versionId);

    mockItemVersionChangeSave(Space.PRIVATE, itemId, versionId, newVersion, change);

    CoreMergeResult syncResult = itemVersionManagerImpl.sync(context, itemId, versionId);
    Assert.assertEquals(syncResult, retrievedSyncResult);

    verifySaveChangedElements(itemId, versionId, Space.PRIVATE, change.getChangedElements());

    return change;
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testSyncOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.sync(context, new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testSyncNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PUBLIC, itemId, versionId);
    itemVersionManagerImpl.sync(context, itemId, versionId);
  }

  @Test
  public void testMerge() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    Id sourceVersionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, sourceVersionId);

    CoreMergeResult retrievedMergeResult = new CoreMergeResult();
    CoreMergeChange change = createMergeChange(sourceVersionId, false);
    retrievedMergeResult.setChange(change);
    doReturn(new Response<>(retrievedMergeResult))
        .when(collaborationAdaptorMock)
        .mergeItemVersion(context, itemId, versionId, sourceVersionId);

    mockItemVersionChangeSave(Space.PRIVATE, itemId, sourceVersionId, false, change);

    CoreMergeResult result =
        itemVersionManagerImpl.merge(context, itemId, versionId, sourceVersionId);

    Assert.assertEquals(result, retrievedMergeResult);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testMergeOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.merge(context, new Id(), new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testMergeNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);

    itemVersionManagerImpl.merge(context, itemId, versionId, new Id());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testMergeNonExistingSource() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    Id sourceVersionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, sourceVersionId);

    itemVersionManagerImpl.merge(context, itemId, versionId, sourceVersionId);
  }

  @Test
  public void testListHistory() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    ItemVersionHistory retrievedHistory = new ItemVersionHistory();
    doReturn(new Response<>(retrievedHistory)).when(collaborationAdaptorMock)
        .listItemVersionHistory(context, itemId, versionId);

    ItemVersionHistory history = itemVersionManagerImpl.listHistory(context, itemId, versionId);

    Assert.assertEquals(history, retrievedHistory);
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testListHistoryOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.listHistory(context, new Id(), new Id());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testListHistoryNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.listHistory(context, itemId, versionId);
  }

  @Test
  public void testResetHistory() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockExistingVersion(Space.PRIVATE, itemId, versionId);

    String changeRef = "changeRef";
    CoreMergeChange mergeChange = createMergeChange(versionId, false);
    doReturn(new Response<>(mergeChange))
        .when(collaborationAdaptorMock)
        .resetItemVersionHistory(context, itemId, versionId, changeRef);

    doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock).updateItemVersion(eq(context), eq
        (Space.PRIVATE), eq(itemId), eq(versionId), eq(mergeChange.getChangedVersion()
        .getItemVersion().getData()), any(Date.class));

    itemVersionManagerImpl.resetHistory(context, itemId, versionId, changeRef);

    verifySaveChangedElements(itemId, versionId, Space.PRIVATE, mergeChange.getChangedElements());
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testResetHistoryOnNonExistingItem() throws Exception {
    itemVersionManagerImpl.resetHistory(context, new Id(), new Id(), "changeRef");
  }

  @Test(expectedExceptions = ZusammenException.class)
  public void testResetHistoryNonExisting() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    mockNonExistingVersion(Space.PRIVATE, itemId, versionId);
    itemVersionManagerImpl.resetHistory(context, itemId, versionId, "changeRef");
  }

  @Test
  public void testUpdateItemVersionModificationTime() throws Exception {
    Id itemId = new Id();
    Id versionId = new Id();
    Date modificationTime = new Date();
    Space space = Space.PRIVATE;
    itemVersionManagerImpl
        .updateModificationTime(context, space, itemId, versionId, modificationTime);

    verify(stateAdaptorMock)
        .updateItemVersionModificationTime(context, space, itemId, versionId, modificationTime);
    verify(itemManagerMock).updateModificationTime(context, itemId, modificationTime);
  }

  private void mockExistingVersion(Space space, Id itemId, Id versionId) {
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<>(true)).when(stateAdaptorMock)
        .isItemVersionExist(context, space, itemId, versionId);
  }

  private void mockNonExistingVersion(Space space, Id itemId, Id versionId) {
    doReturn(true).when(itemManagerMock).isExist(context, itemId);
    doReturn(new Response<>(false)).when(stateAdaptorMock)
        .isItemVersionExist(context, space, itemId, versionId);
  }

  private void mockItemVersionChangeSave(Space space, Id itemId, Id versionId, boolean newVersion,
                                         CoreMergeChange change) {
    if (newVersion) {
      doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock)
          .createItemVersion(eq(context), eq(space), eq(itemId),
              eq(change.getChangedVersion().getItemVersion().getBaseId()), eq(versionId),
              eq(change.getChangedVersion().getItemVersion().getData()), any(Date.class));
    } else {
      doReturn(new Response<>(Void.TYPE)).when(stateAdaptorMock)
          .updateItemVersion(eq(context), eq(space), eq(itemId), eq(versionId),
              eq(change.getChangedVersion().getItemVersion().getData()), any(Date.class));
    }
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