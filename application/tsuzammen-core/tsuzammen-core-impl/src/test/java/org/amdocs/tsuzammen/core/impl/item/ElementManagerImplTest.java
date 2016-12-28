/*
 * Copyright © 2016 Amdocs Software Systems Limited
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

package org.amdocs.tsuzammen.core.impl.item;

import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.core.impl.item.mocks.CollaborationAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ElementStateAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ItemStateAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ItemVersionStateAdaptorEmptyImpl;
import org.testng.annotations.BeforeClass;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ElementManagerImplTest {
  public static final UserInfo USER = new UserInfo("ItemVersionContentManagerImpl_user");

  private ElementManagerImpl entityManager = spy(new ElementManagerImpl());

  private ElementStateAdaptorEmptyImpl stateAdaptor = spy(new ElementStateAdaptorEmptyImpl());

  private CollaborationAdaptorEmptyImpl collaborationAdaptor =
      spy(new CollaborationAdaptorEmptyImpl());

  @BeforeClass
  public void init() {
    when(entityManager.getStateAdaptor(anyObject())).thenReturn(stateAdaptor);
    when(entityManager.getItemVersionStateAdaptor(anyObject()))
        .thenReturn(new ItemVersionStateAdaptorEmptyImpl());
    when(entityManager.getItemStateAdaptor(anyObject()))
        .thenReturn(new ItemStateAdaptorEmptyImpl());

    when(entityManager.getCollaborationAdaptor(anyObject())).thenReturn(collaborationAdaptor);
  }/*

  @Test
  public void testSave() throws Exception {
    CoreEntity rootEntity = new CoreEntity();

    List<CoreEntity> rootContent1 = new ArrayList<>();
    CoreEntity e11 = new CoreEntity();
    e11.setInfo(TestUtils.createInfo("e11"));
    rootContent1.add(e11);
    CoreEntity e12 = new CoreEntity();
    e12.setId("e12");
    e12.setInfo(TestUtils.createInfo("e12"));
    List<CoreEntity> subContent = new ArrayList<>();
    CoreEntity e3 = new CoreEntity();
    e3.setInfo(TestUtils.createInfo("e3"));
    subContent.add(e3);
    e12.getContents().put("subContent", subContent);
    rootContent1.add(e12);
    rootEntity.getContents().put("rootContent1", rootContent1);

    List<CoreEntity> rootContent2 = new ArrayList<>();
    CoreEntity e21 = new CoreEntity();
    e21.setInfo(TestUtils.createInfo("e21"));
    rootContent2.add(e21);
    CoreEntity e22 = new CoreEntity();
    e22.setInfo(TestUtils.createInfo("e22"));
    rootContent2.add(e22);
    rootEntity.getContents().put("rootContent2", rootContent2);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    entityManager.update(context, "item1", rootEntity, "update item version data!");
*//*
    Assert.assertNotNull(e11.getId());
    Assert.assertNotNull(e3.getId());
    Assert.assertNotNull(e21.getId());
    Assert.assertNotNull(e22.getId());*//*

    verifyStateAdaptorCalls(context, e11, e12, e3, e21, e22);
    verifyCollaborationAdaptorCalls(context, e11, e12, e3, e21, e22);
  }

  private void verifyStateAdaptorCalls(SessionContext context, CoreEntity e11, CoreEntity e12,
                                       CoreEntity e3,
                                       CoreEntity e21, CoreEntity e22) throws URISyntaxException {
    URI rootContent1Uri = new URI("rootContent1");
    URI subContentUri = new URI("rootContent1/e12/subContent");
    URI rootContent2Uri = new URI("rootContent2");

    verify(stateAdaptor).create(context, "item1", "version1", e11);
    verify(stateAdaptor).update(context, "item1", "version1", e12);
    verify(stateAdaptor).create(context, "item1", "version1", e3);
    verify(stateAdaptor).create(context, "item1", "version1", e21);
    verify(stateAdaptor).create(context, "item1", "version1", e22);
  }

  private void verifyCollaborationAdaptorCalls(SessionContext context, CoreEntity e11,
                                               CoreEntity e12,
                                               CoreEntity e3, CoreEntity e21, CoreEntity e22)
      throws URISyntaxException {
    URI rootContent1Uri = new URI("rootContent1");
    URI subContentUri = new URI("rootContent1/e12/subContent");
    URI rootContent2Uri = new URI("rootContent2");

    verify(collaborationAdaptor).createEntity(context, "item1", "version1", rootContent1Uri, e11);
    verify(collaborationAdaptor).saveEntity(context, "item1", "version1", rootContent1Uri, e12);
    verify(collaborationAdaptor).createEntity(context, "item1", "version1", subContentUri, e3);
    verify(collaborationAdaptor).createEntity(context, "item1", "version1", rootContent2Uri, e21);
    verify(collaborationAdaptor).createEntity(context, "item1", "version1", rootContent2Uri, e22);
  }
*/
}