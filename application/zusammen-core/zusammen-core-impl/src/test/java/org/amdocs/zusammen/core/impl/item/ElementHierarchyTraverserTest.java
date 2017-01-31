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

import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.impl.TestUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class ElementHierarchyTraverserTest {
  private static final UserInfo USER = new UserInfo("ElementHierarchyTraverserTest_user");

  @Mock
  private ElementVisitor visitorMock;
  @InjectMocks
  private ElementHierarchyTraverser traverser;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testTraverseNoSubs() throws Exception {
    CoreElement element = new CoreElement();

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());
    Space space = Space.PRIVATE;
    traverser.traverse(context, elementContext, space, element, visitorMock);

    verify(visitorMock)
        .visit(context, elementContext, space, element);
  }

  @Test
  public void testTraverseHirarchy() throws Exception {
    CoreElement d1211 = createCoreElement(null, Action.CREATE, "d1211");

    CoreElement c121 = createCoreElement(new Id(), Action.UPDATE, "c121", d1211);

    CoreElement b11 = createCoreElement(new Id(), Action.DELETE, "b11");
    CoreElement b12 = createCoreElement(new Id(), Action.IGNORE, "b12", c121);
    CoreElement b21 = createCoreElement(null, Action.CREATE, "b21");
    CoreElement b22 = createCoreElement(null, Action.CREATE, "b22");

    CoreElement a1 = createCoreElement(new Id(), Action.UPDATE, "a1", b11, b12);
    CoreElement a2 = createCoreElement(null, Action.CREATE, "a2", b21, b22);

    CoreElement root = createCoreElement(new Id(), Action.IGNORE, "root", a1, a2);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());
    Space space = Space.PRIVATE;
    traverser.traverse(context, elementContext, space, root, visitorMock);

    verify(visitorMock).visit(context, elementContext, space, root);
    verify(visitorMock).visit(context, elementContext, space, a1);
    verify(visitorMock).visit(context, elementContext, space, a2);
    verify(visitorMock).visit(context, elementContext, space, b11);
    verify(visitorMock).visit(context, elementContext, space, b12);
    verify(visitorMock).visit(context, elementContext, space, b21);
    verify(visitorMock).visit(context, elementContext, space, b22);
    verify(visitorMock).visit(context, elementContext, space, c121);
    verify(visitorMock).visit(context, elementContext, space, d1211);
  }

  private CoreElement createCoreElement(Id id, Action action, String infoValue,
                                        CoreElement... subElements) {
    CoreElement a1 = new CoreElement();
    a1.setId(id);
    a1.setAction(action);
    a1.setInfo(TestUtils.createInfo(infoValue));
    a1.setSubElements(Arrays.asList(subElements));
    return a1;
  }
}