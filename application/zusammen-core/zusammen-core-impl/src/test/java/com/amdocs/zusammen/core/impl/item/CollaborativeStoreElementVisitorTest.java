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

package com.amdocs.zusammen.core.impl.item;

import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.impl.TestUtils;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.UserInfo;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class CollaborativeStoreElementVisitorTest {
  private static final UserInfo USER = new UserInfo("CollaborativeStoreElementVisitorTest_user");

  @Mock
  private ElementCommandAbstarctFactory elementCommandFactoryMock;
  @InjectMocks
  private CollaborativeStoreElementVisitor visitor;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testVisitElementWithoutSubs() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());
    Space space = Space.PRIVATE;
    CoreElement element = new CoreElement();

    visitor.visit(context, elementContext, space, element);

    verify(elementCommandFactoryMock).executeCommand(context, elementContext, space, element);
  }

  @Test
  public void testVisitElementWithSubs() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());
    Space space = Space.PRIVATE;
    CoreElement element = new CoreElement();
    element.setId(new Id());
    element.setNamespace(Namespace.ROOT_NAMESPACE);
    element.setSubElements(Arrays.asList(new CoreElement(), new CoreElement(), new CoreElement()));

    visitor.visit(context, elementContext, space, element);

    verify(elementCommandFactoryMock).executeCommand(context, elementContext, space, element);
    element.getSubElements().forEach(subElement -> {
      Assert.assertEquals(subElement.getParentId(), element.getId());
      Assert.assertEquals(subElement.getNamespace(),
          new Namespace(element.getNamespace(), element.getId()));
    });
  }
}