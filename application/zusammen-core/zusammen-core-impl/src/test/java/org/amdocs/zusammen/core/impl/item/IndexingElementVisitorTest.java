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
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

public class IndexingElementVisitorTest {
  private static final UserInfo USER = new UserInfo("IndexingElementVisitorTest_user");

  @Mock
  private ElementCommandAbstarctFactory elementCommandFactoryMock;
  @InjectMocks
  private IndexingElementVisitor visitor;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testVisit() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());
    Space space = Space.PRIVATE;
    CoreElement element = new CoreElement();
    visitor.visit(context, elementContext, space, element);

    verify(elementCommandFactoryMock).executeCommand(context, elementContext, space, element);
  }

}