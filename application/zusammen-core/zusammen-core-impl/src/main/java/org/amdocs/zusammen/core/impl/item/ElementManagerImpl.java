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
import org.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import org.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.core.api.item.ElementManager;
import org.amdocs.zusammen.core.api.item.ItemManager;
import org.amdocs.zusammen.core.api.item.ItemManagerFactory;
import org.amdocs.zusammen.core.api.item.ItemVersionManager;
import org.amdocs.zusammen.core.api.item.ItemVersionManagerFactory;
import org.amdocs.zusammen.core.api.types.CoreElement;
import org.amdocs.zusammen.core.api.types.CoreElementInfo;
import org.amdocs.zusammen.core.impl.Messages;
import org.amdocs.zusammen.datatypes.Id;
import org.amdocs.zusammen.datatypes.Namespace;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.Space;
import org.amdocs.zusammen.datatypes.item.Action;
import org.amdocs.zusammen.datatypes.item.ElementContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import org.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.Collection;

public class ElementManagerImpl implements ElementManager {
  private ElementHierarchyTraverser traverser = ElementHierarchyTraverser.init();
  private ElementVisitor collaborativeStoreVisitor = CollaborativeStoreElementVisitor.init();
  private ElementVisitor indexingVisitor = IndexingElementVisitor.init();
  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(ElementManagerImpl.class
      .getName());
  @Override
  public Collection<CoreElementInfo> list(SessionContext context, ElementContext elementContext,
                                          Id elementId) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());
    Response<Collection<CoreElementInfo>> response;
    response = getStateAdaptor(context).list(context, elementContext, elementId);
    if(!response.isSuccessful()){
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ELEMENT_LIST, Module.ZUS,null,response.getReturnCode
          ());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response.getValue();
  }

  @Override
  public CoreElementInfo getInfo(SessionContext context, ElementContext elementContext,
                                 Id elementId) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    Response<CoreElementInfo> response;
    response = getStateAdaptor(context).get(context, elementContext, elementId);
    if(!response.isSuccessful()){
      ReturnCode returnCode = new ReturnCode(ErrorCode.ST_ELEMENT_GET, Module.ZUS,null,response.getReturnCode
          ());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response.getValue();
  }

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId) {
    CoreElementInfo elementInfo = getInfo(context, elementContext, elementId);
    if (elementInfo == null) {
      return null;
    }
    Namespace namespace = elementInfo.getNamespace();

    Response<CoreElement> response;
    response = getCollaborationAdaptor(context)
        .getElement(context, elementContext, namespace, elementId);
    if(!response.isSuccessful()){
      ReturnCode returnCode = new ReturnCode(ErrorCode.CL_ELEMENT_GET, Module.ZUS,null,response.getReturnCode
          ());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response.getValue();

  }

  @Override
  public void save(SessionContext context, ElementContext elementContext,
                   CoreElement element, String message) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    if (element.getAction() == Action.CREATE) {
      setElementHierarchyPosition(element, Namespace.ROOT_NAMESPACE, null);
    } else {

      CoreElementInfo elementInfo = getInfo(context, elementContext, element.getId());

      setElementHierarchyPosition(element, elementInfo.getNamespace(), elementInfo.getParentId());
    }

    traverser.traverse(context, elementContext, Space.PRIVATE, element, collaborativeStoreVisitor);
    traverser.traverse(context, elementContext, Space.PRIVATE, element, indexingVisitor);
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    Response<SearchResult> response;
    response = getSearchIndexAdaptor(context).search(context, searchCriteria);
    if(!response.isSuccessful()){
      ReturnCode returnCode = new ReturnCode(ErrorCode.IN_SEARCH,Module.ZUS,null,response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
    return response.getValue();
  }

  private void setElementHierarchyPosition(CoreElement element, Namespace namespace, Id parentId) {
    element.setParentId(parentId);
    element.setNamespace(namespace);
  }

  private void validateItemVersionExistence(SessionContext context, Space space, Id itemId,
                                            Id versionId) {
    if (!getItemVersionManager(context).isExist(context, space, itemId, versionId)) {
      String message = getItemManager(context).isExist(context, itemId)
          ? String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);
      ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ITEM_VERSION_NOT_EXIST,Module.ZUS,message,null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  protected CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }

  protected ElementStateAdaptor getStateAdaptor(SessionContext context) {
    return ElementStateAdaptorFactory.getInstance().createInterface(context);
  }

  protected SearchIndexAdaptor getSearchIndexAdaptor(SessionContext context) {
    return SearchIndexAdaptorFactory.getInstance().createInterface(context);
  }

  protected ItemVersionManager getItemVersionManager(SessionContext context) {
    return ItemVersionManagerFactory.getInstance().createInterface(context);
  }

  protected ItemManager getItemManager(SessionContext context) {
    return ItemManagerFactory.getInstance().createInterface(context);
  }
}
