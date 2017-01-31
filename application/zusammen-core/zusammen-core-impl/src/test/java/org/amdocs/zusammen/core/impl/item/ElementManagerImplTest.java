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
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.impl.TestUtils;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.UserInfo;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.item.ElementInfo;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElementManagerImplTest {
  public static final UserInfo USER = new UserInfo("ElementManagerImpl_user");

  @Mock
  private ElementStateAdaptor stateAdaptorMock;
  @Mock
  private CollaborationAdaptor collaborationAdaptorMock;
  @Mock
  private SearchIndexAdaptor searchIndexAdaptorMock;
  @Mock
  private ItemVersionManager versionManagerMock;
  @Mock
  private ElementHierarchyTraverser traverserMock;
  @Mock(name = "collaborativeStoreVisitor")
  private ElementVisitor collaborativeStoreVisitorMock;
  @Mock(name = "indexingVisitor")
  private ElementVisitor indexingVisitorMock;
  @InjectMocks
  @Spy
  private ElementManagerImpl elementManager;

  @BeforeMethod
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(elementManager.getItemVersionManager(anyObject())).thenReturn(versionManagerMock);
    when(elementManager.getStateAdaptor(anyObject())).thenReturn(stateAdaptorMock);
    when(elementManager.getCollaborationAdaptor(anyObject())).thenReturn(collaborationAdaptorMock);
    when(elementManager.getSearchIndexAdaptor(anyObject())).thenReturn(searchIndexAdaptorMock);

    doReturn(true)
        .when(versionManagerMock).isExist(anyObject(), anyObject(), anyObject(), anyObject());
  }

  @Test
  public void testListRoots() throws Exception {
    testList(null);
  }

  @Test
  public void testListSubs() throws Exception {
    testList(new Id());
  }

  private void testList(Id elementId) {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    Collection<ElementInfo> retrievedElementInfos = Arrays.asList(
        new ElementInfo(itemId, versionId, new Id(), new Id()),
        new ElementInfo(itemId, versionId, new Id(), new Id()),
        new ElementInfo(itemId, versionId, new Id(), new Id()));
    doReturn(retrievedElementInfos).when(stateAdaptorMock)
        .list(context, elementContext, elementId);

    Collection<ElementInfo> elementInfos = elementManager.list(context, elementContext, elementId);

    Assert.assertEquals(elementInfos, retrievedElementInfos);
  }

  @Test
  public void testGetInfo() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    Id elementId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    ElementInfo retrievedElementInfo = new ElementInfo(itemId, versionId, elementId, new Id());
    doReturn(retrievedElementInfo).when(stateAdaptorMock).get(context, elementContext, elementId);

    ElementInfo elementInfo = elementManager.getInfo(context, elementContext, elementId);

    Assert.assertEquals(elementInfo, retrievedElementInfo);
  }

  @Test
  public void testGet() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    Id elementId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    ElementInfo retrievedElementInfo = new ElementInfo(itemId, versionId, elementId, new Id());
    retrievedElementInfo
        .setNamespace(new Namespace(Namespace.ROOT_NAMESPACE, retrievedElementInfo.getParentId()));
    doReturn(retrievedElementInfo).when(stateAdaptorMock).get(context, elementContext, elementId);

    CoreElement retrievedCoreElement = new CoreElement();
    doReturn(retrievedCoreElement).when(collaborationAdaptorMock)
        .getElement(context, elementContext, retrievedElementInfo.getNamespace(), elementId);

    CoreElement element = elementManager.get(context, elementContext, elementId);

    Assert.assertEquals(element, retrievedCoreElement);
  }

  @Test
  public void testSaveWithCreateRoot() throws Exception {
    CoreElement root = createCoreElement(null, Action.CREATE, "root");
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());

    elementManager.save(context, elementContext, root, "save create root!");

    Assert.assertEquals(root.getParentId(), null);
    Assert.assertEquals(root.getNamespace(), Namespace.ROOT_NAMESPACE);
    verify(traverserMock)
        .traverse(context, elementContext, Space.PRIVATE, root, collaborativeStoreVisitorMock);
    verify(traverserMock).traverse(context, elementContext, Space.PRIVATE, root,
        indexingVisitorMock);
  }

  @Test
  public void testSaveWithNonCreateRoot() throws Exception {
    CoreElement root = createCoreElement(new Id(), Action.UPDATE, "root");
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());

    ElementInfo retrievedElementInfo = new ElementInfo(
        elementContext.getItemId(), elementContext.getVersionId(), root.getId(), new Id());
    retrievedElementInfo
        .setNamespace(new Namespace(Namespace.ROOT_NAMESPACE, retrievedElementInfo.getParentId()));
    doReturn(retrievedElementInfo).when(stateAdaptorMock)
        .get(context, elementContext, root.getId());

    elementManager.save(context, elementContext, root, "save non-create root!");

    Assert.assertEquals(root.getParentId(), retrievedElementInfo.getParentId());
    Assert.assertEquals(root.getNamespace(), retrievedElementInfo.getNamespace());
    verify(traverserMock)
        .traverse(context, elementContext, Space.PRIVATE, root, collaborativeStoreVisitorMock);
    verify(traverserMock).traverse(context, elementContext, Space.PRIVATE, root,
        indexingVisitorMock);
  }

  @Test
  public void testSearch() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    SearchCriteria searchCriteria = new SearchCriteria() {
    };

    SearchResult retrievedSearchResult = new SearchResult() {
    };
    doReturn(retrievedSearchResult).when(searchIndexAdaptorMock).search(context, searchCriteria);

    SearchResult searchResult = elementManager.search(context, searchCriteria);

    Assert.assertEquals(searchResult, retrievedSearchResult);
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
