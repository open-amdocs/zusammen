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

import org.amdocs.tsuzammen.commons.datatypes.SessionContext;
import org.amdocs.tsuzammen.commons.datatypes.UserInfo;
import org.amdocs.tsuzammen.commons.datatypes.item.Content;
import org.amdocs.tsuzammen.commons.datatypes.item.Entity;
import org.amdocs.tsuzammen.commons.datatypes.item.Format;
import org.amdocs.tsuzammen.commons.datatypes.item.ItemVersionData;
import org.amdocs.tsuzammen.core.impl.TestUtils;
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

public class ItemVersionContentManagerImplTest {
  public static final UserInfo USER = new UserInfo("ItemVersionContentManagerImpl_user");

  private ItemVersionContentManagerImpl itemVersionContentManager =
      spy(new ItemVersionContentManagerImpl());

  private StateAdaptorEmptyImpl stateAdaptorEmpty = spy(new StateAdaptorEmptyImpl());
  private CollaborationAdaptorEmptyImpl collaborationAdaptorEmpty =
      spy(new CollaborationAdaptorEmptyImpl());

  @BeforeClass
  public void init() {
    when(itemVersionContentManager.getCollaborationAdaptor(anyObject()))
        .thenReturn(collaborationAdaptorEmpty);

    when(itemVersionContentManager.getStateAdaptor(anyObject())).thenReturn(stateAdaptorEmpty);
  }


  @Test
  public void testSave() throws Exception {
    ItemVersionData versionData = new ItemVersionData();
    Content rootContent1 = new Content();
    rootContent1.setDataFormat(new Format());
    Entity e11 = new Entity();
    e11.setInfo(TestUtils.createInfo("e11"));
    rootContent1.getEntities().add(e11);
    Entity e12 = new Entity();
    e12.setId("e12");
    e12.setInfo(TestUtils.createInfo("e12"));
    Content subContent = new Content();
    subContent.setDataFormat(new Format());
    Entity e3 = new Entity();
    e3.setInfo(TestUtils.createInfo("e3"));
    subContent.getEntities().add(e3);
    e12.getContents().put("subContent", subContent);
    rootContent1.getEntities().add(e12);

    versionData.getContents().put("rootContent1", rootContent1);

    Content rootContent2 = new Content();
    rootContent2.setDataFormat(new Format());
    Entity e21 = new Entity();
    e21.setInfo(TestUtils.createInfo("e21"));
    rootContent2.getEntities().add(e21);
    Entity e22 = new Entity();
    e22.setInfo(TestUtils.createInfo("e22"));
    rootContent2.getEntities().add(e22);
    versionData.getContents().put("rootContent2", rootContent2);

    SessionContext context = TestUtils.createSessionContext(USER, "test");

    itemVersionContentManager.save(context, "item1", "version1", versionData, null, null,
        "save item version data!");

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

}