/*
 * Add Copyright © 2016-2017 European Support Limited
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
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.core.impl.TestUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.datatypes.item.Info;
import org.amdocs.zusammen.datatypes.item.Item;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemManagerImplTest {
  private static final UserInfo USER = new UserInfo("ItemVersionManagerImplTest_user");
  private static final SessionContext context = TestUtils.createSessionContext(USER, "test");

  private static final String ITEM_NOT_EXIST = "Item with id .* does not exists.";

  @Spy
  private ItemManagerImpl itemManagerImpl;
  @Mock
  private ItemStateAdaptor stateAdaptorMock;
  @Mock
  private CollaborationAdaptor collaborationAdaptorMock;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(itemManagerImpl.getStateAdaptor(anyObject())).thenReturn(stateAdaptorMock);
    when(itemManagerImpl.getCollaborationAdaptor(anyObject())).thenReturn(collaborationAdaptorMock);
  }

  @Test
  public void testList() throws Exception {
    Id itemId = new Id();
    List<Item> retrievedItems = Arrays.asList(
        createItem(new Id(), "item1"),
        createItem(new Id(), "item2"),
        createItem(new Id(), "item3"));
    doReturn(retrievedItems).when(stateAdaptorMock).listItems(context);

    Collection<Item> items = itemManagerImpl.list(context);
    Assert.assertEquals(items, retrievedItems);
  }

  @Test
  public void testIsExist() throws Exception {
    Id itemId = new Id();
    doReturn(true).when(stateAdaptorMock).isItemExist(context, itemId);

    boolean exist = itemManagerImpl.isExist(context, itemId);
    Assert.assertTrue(exist);
  }

  @Test
  public void testIsExistWhenNot() throws Exception {
    boolean exist = itemManagerImpl.isExist(context, new Id());
    Assert.assertFalse(exist);
  }

  @Test
  public void testGet() throws Exception {
    Item retrievedItem = createItem(new Id(), "item1");
    doReturn(retrievedItem).when(stateAdaptorMock).getItem(context, retrievedItem.getId());

    Item item = itemManagerImpl.get(context, retrievedItem.getId());
    Assert.assertEquals(item, retrievedItem);
  }

  @Test
  public void testGetNonExisting() throws Exception {
    Item item = itemManagerImpl.get(context, new Id());
    Assert.assertNull(item);
  }

  @Test
  public void testCreate() throws Exception {
    Info info = TestUtils.createInfo("item1");
    Id itemId = itemManagerImpl.create(context, info);
    Assert.assertNotNull(itemId);

    verify(collaborationAdaptorMock).createItem(context, itemId, info);
    verify(stateAdaptorMock).createItem(context, itemId, info);
  }

  @Test
  public void testUpdate() throws Exception {
    Id itemId = new Id();
    Info info = TestUtils.createInfo("item1");
    doReturn(true).when(stateAdaptorMock).isItemExist(context, itemId);

    itemManagerImpl.update(context, itemId, info);

    verify(collaborationAdaptorMock).updateItem(context, itemId, info);
    verify(stateAdaptorMock).updateItem(context, itemId, info);
  }

  @Test(expectedExceptions = RuntimeException.class,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST)
  public void testUpdateNonExisting() throws Exception {
    itemManagerImpl.update(context, new Id(), new Info());
  }

  @Test
  public void testDelete() throws Exception {
    Id itemId = new Id();
    doReturn(true).when(stateAdaptorMock).isItemExist(context, itemId);

    itemManagerImpl.delete(context, itemId);

    verify(collaborationAdaptorMock).deleteItem(context, itemId);
    verify(stateAdaptorMock).deleteItem(context, itemId);
  }

  @Test(expectedExceptions = RuntimeException.class,
      expectedExceptionsMessageRegExp = ITEM_NOT_EXIST)
  public void testDeleteNonExisting() throws Exception {
    itemManagerImpl.delete(context, new Id());
  }

  private Item createItem(Id id, String name) {
    Item item = new Item();
    item.setId(id);
    item.setInfo(TestUtils.createInfo(name));
    return item;
  }
}