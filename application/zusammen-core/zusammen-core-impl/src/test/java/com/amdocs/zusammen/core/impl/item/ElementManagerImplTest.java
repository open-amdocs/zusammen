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

package com.amdocs.zusammen.core.impl.item;

import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import com.amdocs.zusammen.core.api.item.ItemVersionManager;
import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.core.impl.TestUtils;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.UserInfo;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Info;
import com.amdocs.zusammen.datatypes.item.Relation;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ElementManagerImplTest {
  private static final UserInfo USER = new UserInfo("ElementManagerImpl_user");

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

  @Test
  public void testListRootsByChangeRef() throws Exception {
    testListByChangeRef(null);
  }

  @Test
  public void testListSubsByChangeRef() throws Exception {
    testListByChangeRef(new Id());
  }

  private void testList(Id parentElementId) {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    Namespace namespace = parentElementId == null
        ? Namespace.ROOT_NAMESPACE
        : new Namespace(Namespace.ROOT_NAMESPACE, parentElementId);

    Collection<CoreElementInfo> retrievedElementInfos = Arrays.asList(
        createCoreElementInfo(new Id(), parentElementId, namespace, new Info(),
            Arrays.asList(new Relation(), new Relation()), new CoreElementInfo()),
        createCoreElementInfo(new Id(), parentElementId, namespace, new Info(),
            Collections.singletonList(new Relation()), new CoreElementInfo(),
            new CoreElementInfo()),
        createCoreElementInfo(new Id(), parentElementId, namespace, new Info(), new ArrayList<>()));
    Response<Collection<CoreElementInfo>> elementInfosResponse =
        new Response<>(retrievedElementInfos);

    doReturn(elementInfosResponse).when(stateAdaptorMock)
        .list(context, elementContext, parentElementId);

    Collection<CoreElementInfo> elementInfos =
        elementManager.list(context, elementContext, parentElementId);

    Assert.assertEquals(elementInfos, retrievedElementInfos);
  }


  private void testListByChangeRef(Id parentElementId) {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId, "changeRef");

    Namespace namespace = parentElementId == null
        ? Namespace.ROOT_NAMESPACE
        : new Namespace(Namespace.ROOT_NAMESPACE, parentElementId);
    doReturn(new Response<>(namespace))
        .when(stateAdaptorMock).getNamespace(context, itemId, parentElementId);

    CoreElement sub1 = new CoreElement();
    sub1.setId(new Id());
    CoreElement sub2 = new CoreElement();
    sub2.setId(new Id());
    List<CoreElement> elements = Arrays.asList(
        createCoreElement(new Id(), Action.IGNORE, parentElementId, namespace,
            "element1", Arrays.asList(new Relation(), new Relation()), sub1, sub2),
        createCoreElement(new Id(), Action.IGNORE, parentElementId, namespace,
            "element2", Collections.singletonList(new Relation())));
    doReturn(new Response<>(elements)).when(collaborationAdaptorMock)
        .listElements(context, elementContext, namespace, parentElementId);

    Collection<CoreElementInfo> elementInfos =
        elementManager.list(context, elementContext, parentElementId);

    //Assert.assertEquals(elementInfos, retrievedElementInfos);
  }

  @Test
  public void testGetInfo() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    Id elementId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    CoreElementInfo retrievedElementInfo =
        createCoreElementInfo(elementId, new Id(), Namespace.ROOT_NAMESPACE);
    Response<CoreElementInfo> coreElementInfoResponse = new Response<>(retrievedElementInfo);
    doReturn(coreElementInfoResponse).when(stateAdaptorMock)
        .get(context, elementContext, elementId);

    CoreElementInfo elementInfo = elementManager.getInfo(context, elementContext, elementId);

    Assert.assertEquals(elementInfo, retrievedElementInfo);
  }

  @Test
  public void testGetInfoByChangeRef() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    Id elementId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId, "changeRef");


    Namespace namespace = Namespace.ROOT_NAMESPACE;
    doReturn(new Response<>(namespace))
        .when(stateAdaptorMock).getNamespace(context, itemId, elementId);

    CoreElement sub1 = new CoreElement();
    sub1.setId(new Id());
    CoreElement sub2 = new CoreElement();
    sub2.setId(new Id());
    CoreElement retrievedCoreElement = createCoreElement(elementId, Action.IGNORE, null, namespace,
        "infoValue", Arrays.asList(new Relation(), new Relation()), sub1, sub2);

    Response<CoreElement> CoreElementRes = new Response<>(retrievedCoreElement);
    doReturn(CoreElementRes).when(collaborationAdaptorMock)
        .getElement(context, elementContext, namespace, elementId);

    CoreElementInfo elementInfo = elementManager.getInfo(context, elementContext, elementId);

    assertEquals(retrievedCoreElement, elementInfo);
  }

  private void assertEquals(CoreElement element, CoreElementInfo elementInfo) {
    Assert.assertEquals(elementInfo.getId(), element.getId());
    Assert.assertEquals(elementInfo.getInfo(), element.getInfo());
    Assert.assertEquals(elementInfo.getRelations(), element.getRelations());
    Assert.assertEquals(elementInfo.getSubElements().size(), element.getSubElements().size());

    Set<Id> subIds = elementInfo.getSubElements().stream()
        .map(CoreElementInfo::getId)
        .collect(Collectors.toSet());

    for (CoreElement sub : element.getSubElements()) {
      Assert.assertTrue(subIds.contains(sub.getId()));
    }
  }

  @Test
  public void testGet() throws Exception {
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    Id itemId = new Id();
    Id versionId = new Id();
    Id elementId = new Id();
    ElementContext elementContext = new ElementContext(itemId, versionId);

    Namespace namespace = Namespace.ROOT_NAMESPACE;
    doReturn(new Response<>(namespace))
        .when(stateAdaptorMock).getNamespace(context, itemId, elementId);

    CoreElement retrievedCoreElement = new CoreElement();
    Response<CoreElement> CoreElementRes = new Response<>(retrievedCoreElement);
    doReturn(CoreElementRes).when(collaborationAdaptorMock)
        .getElement(context, elementContext, namespace, elementId);

    CoreElement element = elementManager.get(context, elementContext, elementId);

    Assert.assertEquals(element, retrievedCoreElement);
  }

  private CoreElementInfo createCoreElementInfo(Id id, Id parentId, Namespace parentNamespace) {
    CoreElementInfo elementInfo = new CoreElementInfo();
    elementInfo.setId(id);
    elementInfo.setParentId(parentId);
    elementInfo.setNamespace(new Namespace(parentNamespace, parentId));
    return elementInfo;
  }

  @Test
  public void testSaveWithCreateRoot() throws Exception {
    CoreElement root = createCoreElement(null, Action.CREATE, null, null, "root", null);
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());

    CoreElement returnedRoot =
        elementManager.save(context, elementContext, root, "save create root!");

    Assert.assertEquals(returnedRoot.getParentId(), null);
    Assert.assertEquals(returnedRoot.getNamespace(), Namespace.ROOT_NAMESPACE);
    verify(traverserMock)
        .traverse(context, elementContext, Space.PRIVATE, root, collaborativeStoreVisitorMock);
    verify(traverserMock).traverse(context, elementContext, Space.PRIVATE, root,
        indexingVisitorMock);
  }

  @Test
  public void testSaveWithNonCreateRoot() throws Exception {
    CoreElement root = createCoreElement(new Id(), Action.UPDATE, null, null, "root", null);
    SessionContext context = TestUtils.createSessionContext(USER, "test");
    ElementContext elementContext = new ElementContext(new Id(), new Id());

    Id parentElementId = new Id();
    Namespace namespace = new Namespace(Namespace.ROOT_NAMESPACE, parentElementId);
    doReturn(new Response<>(namespace))
        .when(stateAdaptorMock).getNamespace(context, elementContext.getItemId(), root.getId());

    CoreElement returnedRoot =
        elementManager.save(context, elementContext, root, "save non-create root!");

    Assert.assertEquals(returnedRoot.getId(), root.getId());
    Assert.assertEquals(root.getParentId(), parentElementId);
    Assert.assertEquals(root.getNamespace(), namespace);
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
    Response<SearchResult> searchResultResponse = new Response<>(retrievedSearchResult);
    doReturn(searchResultResponse).when(searchIndexAdaptorMock).search(context, searchCriteria);

    SearchResult searchResult = elementManager.search(context, searchCriteria);

    Assert.assertEquals(searchResult, retrievedSearchResult);
  }

  private CoreElementInfo createCoreElementInfo(Id elementId, Id parentId, Namespace
      namespace, Info info, Collection<Relation> relations, CoreElementInfo... subElements) {
    CoreElementInfo coreElementInfo = new CoreElementInfo();
    coreElementInfo.setId(elementId);
    coreElementInfo.setInfo(info);
    coreElementInfo.setRelations(relations);
    coreElementInfo.setSubElements(Arrays.asList(subElements));
    return coreElementInfo;
  }

  private CoreElement createCoreElement(Id elementId, Action action, Id parentId,
                                        Namespace namespace, String infoValue,
                                        Collection<Relation> relations,
                                        CoreElement... subElements) {
    CoreElement element = new CoreElement();
    element.setId(elementId);
    element.setAction(action);
    element.setParentId(parentId);
    element.setNamespace(namespace);
    element.setInfo(TestUtils.createInfo(infoValue));
    element.setRelations(relations);
    element.setSubElements(Arrays.asList(subElements));
    return element;
  }
}
