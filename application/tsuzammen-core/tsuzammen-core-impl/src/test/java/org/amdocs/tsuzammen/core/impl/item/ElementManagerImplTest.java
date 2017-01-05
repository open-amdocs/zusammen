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

import org.amdocs.tsuzammen.core.api.types.CoreElement;
import org.amdocs.tsuzammen.core.impl.TestUtils;
import org.amdocs.tsuzammen.core.impl.item.mocks.CollaborationAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ElementStateAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ItemStateAdaptorEmptyImpl;
import org.amdocs.tsuzammen.core.impl.item.mocks.ItemVersionStateAdaptorEmptyImpl;
import org.amdocs.tsuzammen.datatypes.Id;
import org.amdocs.tsuzammen.datatypes.Namespace;
import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.UserInfo;
import org.amdocs.tsuzammen.datatypes.item.ElementAction;
import org.amdocs.tsuzammen.datatypes.item.ElementContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElementManagerImplTest {
  public static final UserInfo USER = new UserInfo("ElementManagerImpl_user");

  private ElementManagerImpl elementManager = spy(new ElementManagerImpl());

  private ElementStateAdaptorEmptyImpl stateAdaptor;
  private CollaborationAdaptorEmptyImpl collaborationAdaptor;

  @BeforeMethod
  public void init() {
    stateAdaptor = spy(new ElementStateAdaptorEmptyImpl());
    collaborationAdaptor = spy(new CollaborationAdaptorEmptyImpl());

    when(elementManager.getStateAdaptor(anyObject())).thenReturn(stateAdaptor);
    when(elementManager.getItemVersionStateAdaptor(anyObject()))
        .thenReturn(new ItemVersionStateAdaptorEmptyImpl());
    when(elementManager.getItemStateAdaptor(anyObject()))
        .thenReturn(new ItemStateAdaptorEmptyImpl());
    when(elementManager.getCollaborationAdaptor(anyObject())).thenReturn(collaborationAdaptor);
  }

  @Test
  public void testSaveWithCreateRoot() throws Exception {
    CoreElement a1 = createCoreElement(null, ElementAction.CREATE, "a1");
    CoreElement root = createCoreElement(null, ElementAction.CREATE, "root", a1);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    elementManager.save(context, elementContext, root, "save create root!");

    traverse(root, this::validateIdExistense);

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptor).createElement(context, elementContext, rootNs, root);
    verify(collaborationAdaptor)
        .createElement(context, elementContext, getNamespace(rootNs, a1.getId()), a1);

    verify(stateAdaptor, never()).getNamespace(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor, times(2)).create(anyObject(), anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithUpdateRoot() throws Exception {
    CoreElement a1 = createCoreElement(new Id(), ElementAction.UPDATE, "a1");
    CoreElement a2 = createCoreElement(new Id(), ElementAction.IGNORE, "a2");
    CoreElement a3 = createCoreElement(new Id(), ElementAction.DELETE, "a3");
    CoreElement a4 = createCoreElement(null, ElementAction.CREATE, "a4");
    CoreElement root = createCoreElement(new Id(), ElementAction.UPDATE, "root", a1, a2, a3, a4);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    elementManager.save(context, elementContext, root, "save update root!");

    traverse(root, this::validateIdExistense);

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptor).saveElement(context, elementContext, rootNs, root);
    verify(collaborationAdaptor)
        .saveElement(context, elementContext, getNamespace(rootNs, a1.getId()), a1);
    verify(collaborationAdaptor)
        .deleteElement(context, elementContext, getNamespace(rootNs, a3.getId()), a3);
    verify(collaborationAdaptor)
        .createElement(context, elementContext, getNamespace(rootNs, a4.getId()), a4);

    verify(stateAdaptor).getNamespace(context, elementContext, root.getId());
    verify(stateAdaptor).getNamespace(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor, times(2)).save(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor).create(anyObject(), anyObject(), anyObject(), anyObject());
    verify(stateAdaptor).delete(anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithDeleteRoot() throws Exception {
    CoreElement root = createCoreElement(new Id(), ElementAction.DELETE, "root");

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    elementManager.save(context, elementContext, root, "save delete root!");

    Namespace rootNs = getNamespace(new Namespace(), root.getId());
    verify(collaborationAdaptor).deleteElement(context, elementContext, rootNs, root);

    verify(stateAdaptor).getNamespace(context, elementContext, root.getId());
    verify(stateAdaptor).getNamespace(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor).delete(anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testSaveWithIgnoreRoot() throws Exception {
    CoreElement d1211 = createCoreElement(null, ElementAction.CREATE, "d1211");

    CoreElement c121 = createCoreElement(new Id(), ElementAction.UPDATE, "c121", d1211);

    CoreElement b11 = createCoreElement(new Id(), ElementAction.DELETE, "b11");
    CoreElement b12 = createCoreElement(new Id(), ElementAction.IGNORE, "b12", c121);
    CoreElement b21 = createCoreElement(null, ElementAction.CREATE, "b21");
    CoreElement b22 = createCoreElement(null, ElementAction.CREATE, "b22");

    CoreElement a1 = createCoreElement(new Id(), ElementAction.UPDATE, "a1", b11, b12);
    CoreElement a2 = createCoreElement(null, ElementAction.CREATE, "a2", b21, b22);

    CoreElement root = createCoreElement(new Id(), ElementAction.IGNORE, "root", a1, a2);

    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = createElementContext();

    elementManager.save(context, elementContext, root, "save ignore root!");

    traverse(root, this::validateIdExistense);
    verifyAdaptorCalls(context, elementContext, root, a1, a2, b11, b12, b21, b22, c121, d1211);
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

    verify(collaborationAdaptor).saveElement(context, elementContext, a1Ns, a1);
    verify(collaborationAdaptor)
        .deleteElement(context, elementContext, getNamespace(a1Ns, b11.getId()), b11);
    verify(collaborationAdaptor).saveElement(context, elementContext, c121Ns, c121);
    verify(collaborationAdaptor)
        .createElement(context, elementContext, getNamespace(c121Ns, d1211.getId()), d1211);
    verify(collaborationAdaptor).createElement(context, elementContext, a2Ns, a2);
    verify(collaborationAdaptor)
        .createElement(context, elementContext, getNamespace(a2Ns, b21.getId()), b21);
    verify(collaborationAdaptor)
        .createElement(context, elementContext, getNamespace(a2Ns, b22.getId()), b22);

    verify(stateAdaptor).getNamespace(context, elementContext, root.getId());
    verify(stateAdaptor).getNamespace(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor, times(4)).create(anyObject(), anyObject(), anyObject(), anyObject());
    verify(stateAdaptor, times(2)).save(anyObject(), anyObject(), anyObject());
    verify(stateAdaptor).delete(anyObject(), anyObject(), anyObject());
  }

  private void traverse(CoreElement element, Consumer<CoreElement> elementConsumer) {
    elementConsumer.accept(element);
    element.getSubElements().forEach(elementConsumer);
  }

  private void validateIdExistense(CoreElement element) {
    Assert.assertNotNull(element.getId());
  }

  private CoreElement createCoreElement(Id id, ElementAction action, String infoValue,
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
