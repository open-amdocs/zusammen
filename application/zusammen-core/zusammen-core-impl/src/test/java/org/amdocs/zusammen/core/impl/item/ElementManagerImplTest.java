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
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ItemVersionStateAdaptor;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.impl.TestUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElementManagerImplTest {
  public static final UserInfo USER = new UserInfo("ElementManagerImpl_user");

  @Spy
  private ElementManagerImpl elementManager;

  @Mock
  private ElementStateAdaptor stateAdaptorMock;
  @Mock
  private CollaborationAdaptor collaborationAdaptorMock;
  @Mock
  private SearchIndexAdaptor searchIndexAdaptorMock;
  @Mock
  private ItemStateAdaptor itemStateAdaptorMock;
  @Mock
  private ItemVersionStateAdaptor versionStateAdaptorMock;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(elementManager.getItemVersionStateAdaptor(anyObject()))
        .thenReturn(versionStateAdaptorMock);
    when(elementManager.getItemStateAdaptor(anyObject())).thenReturn(itemStateAdaptorMock);

    when(elementManager.getStateAdaptor(anyObject())).thenReturn(stateAdaptorMock);
    when(elementManager.getCollaborationAdaptor(anyObject())).thenReturn(collaborationAdaptorMock);
    when(elementManager.getSearchIndexAdaptor(anyObject())).thenReturn(searchIndexAdaptorMock);

    doReturn(true)
        .when(versionStateAdaptorMock).isItemVersionExist(anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithCreateRoot() throws Exception {
    CoreElement a1 = createCoreElement(null, Action.CREATE, "a1");
    CoreElement root = createCoreElement(null, Action.CREATE, "root", a1);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    elementManager.save(context, elementContext, root, "update create root!");

    traverse(root, this::validateIdExistense);

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptorMock).createElement(context, elementContext, root);
    verify(collaborationAdaptorMock)
        .createElement(context, elementContext, a1);

    verify(stateAdaptorMock, never()).get(anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock, times(2)).create(anyObject(), anyObject(), anyObject(),
        anyObject());
  }

  @Test
  public void testSaveWithUpdateRoot() throws Exception {
    CoreElement a1 = createCoreElement(new Id(), Action.UPDATE, "a1");
    CoreElement a2 = createCoreElement(new Id(), Action.IGNORE, "a2");
    CoreElement a3 = createCoreElement(new Id(), Action.DELETE, "a3");
    CoreElement a4 = createCoreElement(null, Action.CREATE, "a4");
    CoreElement root = createCoreElement(new Id(), Action.UPDATE, "root", a1, a2, a3, a4);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    configMockReturn(context, elementContext, root);
    elementManager.save(context, elementContext, root, "update update root!");

    traverse(root, this::validateIdExistense);

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptorMock).updateElement(context, elementContext, root);
    verify(collaborationAdaptorMock)
        .updateElement(context, elementContext, a1);
    verify(collaborationAdaptorMock)
        .deleteElement(context, elementContext, a3);
    verify(collaborationAdaptorMock)
        .createElement(context, elementContext, a4);

    verify(stateAdaptorMock).get(context, elementContext, root.getId());
    verify(stateAdaptorMock).get(anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock, times(2)).update(anyObject(), anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock).create(anyObject(), anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock).delete(anyObject(), anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithDeleteRoot() throws Exception {
    CoreElement root = createCoreElement(new Id(), Action.DELETE, "root");

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    configMockReturn(context, elementContext, root);
    elementManager.save(context, elementContext, root, "update delete root!");

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptorMock).deleteElement(context, elementContext, root);

    verify(stateAdaptorMock).get(context, elementContext, root.getId());
    verify(stateAdaptorMock).get(anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock).delete(anyObject(), anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithIgnoreRoot() throws Exception {
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
    ElementContext elementContext = createElementContext();

    configMockReturn(context, elementContext, root);
    elementManager.save(context, elementContext, root, "update ignore root!");

    traverse(root, this::validateIdExistense);
    verifyAdaptorCalls(context, elementContext, root, a1, a2, b11, b12, b21, b22, c121, d1211);
  }

  private void configMockReturn(SessionContext context, ElementContext elementContext,
                                CoreElement root) {
    ElementInfo elementInfo = new ElementInfo(
        elementContext.getItemId(), elementContext.getVersionId(), root.getId(), null);
    elementInfo.setNamespace(new Namespace(Namespace.ROOT_NAMESPACE, root.getId()));
    doReturn(elementInfo).when(stateAdaptorMock).get(context, elementContext, root.getId());
  }

  private void verifyAdaptorCalls(SessionContext context, ElementContext elementContext,
                                  CoreElement root, CoreElement a1, CoreElement a2, CoreElement b11,
                                  CoreElement b12, CoreElement b21, CoreElement b22,
                                  CoreElement c121, CoreElement d1211) {
    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    Namespace a1Ns = getNamespace(rootNs, a1.getId());
    Namespace a2Ns = getNamespace(rootNs, a2.getId());
    Namespace b12Ns = getNamespace(a1Ns, b12.getId());
    Namespace c121Ns = getNamespace(b12Ns, c121.getId());

    verify(collaborationAdaptorMock).updateElement(context, elementContext, a1);
    verify(collaborationAdaptorMock)
        .deleteElement(context, elementContext, b11);
    verify(collaborationAdaptorMock).updateElement(context, elementContext, c121);
    verify(collaborationAdaptorMock)
        .createElement(context, elementContext, d1211);
    verify(collaborationAdaptorMock).createElement(context, elementContext, a2);
    verify(collaborationAdaptorMock)
        .createElement(context, elementContext, b21);
    verify(collaborationAdaptorMock)
        .createElement(context, elementContext, b22);

    verify(stateAdaptorMock).get(context, elementContext, root.getId());
    verify(stateAdaptorMock).get(anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock, times(4)).create(anyObject(), anyObject(), anyObject(),
        anyObject());
    verify(stateAdaptorMock, times(2)).update(anyObject(), anyObject(), anyObject(), anyObject());
    verify(stateAdaptorMock).delete(anyObject(), anyObject(), anyObject(), anyObject());
  }

  private void traverse(CoreElement element, Consumer<CoreElement> elementConsumer) {
    elementConsumer.accept(element);
    element.getSubElements().forEach(elementConsumer);
  }

  private void validateIdExistense(CoreElement element) {
    Assert.assertNotNull(element.getId());
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

  private ElementContext createElementContext() {
    ElementContext elementContext = new ElementContext();
    elementContext.setItemId(new Id());
    elementContext.setVersionId(new Id());
    return elementContext;
  }

  private Namespace getNamespace(Namespace parentNamespace, Id elementId) {
    return new Namespace(parentNamespace, elementId);
  }
}
