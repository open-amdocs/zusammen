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
import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import com.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.core.api.item.ElementManager;
import com.amdocs.zusammen.core.api.item.ItemManager;
import com.amdocs.zusammen.core.api.item.ItemManagerFactory;
import com.amdocs.zusammen.core.api.item.ItemVersionManager;
import com.amdocs.zusammen.core.api.item.ItemVersionManagerFactory;
import com.amdocs.zusammen.core.api.types.CoreElement;
import com.amdocs.zusammen.core.api.types.CoreElementConflict;
import com.amdocs.zusammen.core.api.types.CoreElementInfo;
import com.amdocs.zusammen.core.impl.Messages;
import com.amdocs.zusammen.core.impl.ValidationUtil;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.Namespace;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.Space;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.item.ElementContext;
import com.amdocs.zusammen.datatypes.item.Resolution;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;
import com.amdocs.zusammen.datatypes.searchindex.SearchCriteria;
import com.amdocs.zusammen.datatypes.searchindex.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class ElementManagerImpl implements ElementManager {
  private ElementHierarchyTraverser traverser = ElementHierarchyTraverser.init();
  private ElementVisitor collaborativeStoreVisitor = CollaborativeStoreElementVisitor.init();
  private ElementVisitor indexingVisitor = IndexingElementVisitor.init();
  private static ZusammenLogger logger =
      ZusammenLoggerFactory.getLogger(ElementManagerImpl.class.getName());

  @Override
  public Collection<CoreElementInfo> list(SessionContext context, ElementContext elementContext,
                                          Id elementId) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    if (elementContext.getChangeRef() == null) {
      Response<Collection<CoreElementInfo>> infoListResponse =
          getStateAdaptor(context).list(context, elementContext, elementId);
      ValidationUtil.validateResponse(infoListResponse, logger, ErrorCode.ZU_ELEMENT_LIST);

      return infoListResponse.getValue();
    } else {
      Namespace namespace;
      if (elementId == null) {
        namespace = Namespace.ROOT_NAMESPACE;
      } else {
        namespace =
            getValidatedNamespace(context, elementContext, elementId, ErrorCode.ZU_ELEMENT_LIST);
        if (namespace == null) {
          return new ArrayList<>();
        }
      }

      Response<Collection<CoreElement>> elementsResponse = getCollaborationAdaptor(context)
          .listElements(context, elementContext, namespace, elementId);
      ValidationUtil.validateResponse(elementsResponse, logger, ErrorCode.ZU_ELEMENT_LIST);

      return elementsResponse.getValue()
          .stream().map(this::coreElementToCoreElementInfo)
          .collect(Collectors.toList());
    }
  }

  @Override
  public CoreElementInfo getInfo(SessionContext context, ElementContext elementContext,
                                 Id elementId) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    if (elementContext.getChangeRef() == null) {
      Response<CoreElementInfo> infoResponse =
          getStateAdaptor(context).get(context, elementContext, elementId);
      ValidationUtil.validateResponse(infoResponse, logger, ErrorCode.ZU_ELEMENT_GET_INFO);
      return infoResponse.getValue();
    } else {
      Namespace namespace =
          getValidatedNamespace(context, elementContext, elementId, ErrorCode.ZU_ELEMENT_GET_INFO);
      if (namespace == null) {
        return null;
      }
      Response<CoreElement> elementResponse =
          getCollaborationAdaptor(context)
              .getElement(context, elementContext, namespace, elementId);
      ValidationUtil.validateResponse(elementResponse, logger, ErrorCode.ZU_ELEMENT_GET_INFO);
      return coreElementToCoreElementInfo(elementResponse.getValue());
    }
  }

  @Override
  public CoreElement get(SessionContext context, ElementContext elementContext,
                         Id elementId) {
    Namespace namespace =
        getValidatedNamespace(context, elementContext, elementId, ErrorCode.ZU_ELEMENT_GET);
    if (namespace == null) {
      return null;
    }

    Response<CoreElement> response =
        getCollaborationAdaptor(context).getElement(context, elementContext, namespace, elementId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ELEMENT_GET);
    return response.getValue();
  }

  @Override
  public CoreElementConflict getConflict(SessionContext context, ElementContext elementContext,
                                         Id elementId) {
    Namespace namespace = getValidatedNamespace(context, elementContext, elementId,
        ErrorCode.ZU_ELEMENT_GET_CONFLICT);
    if (namespace == null) {
      return null;
    }

    Response<CoreElementConflict> response = getCollaborationAdaptor(context)
        .getElementConflict(context, elementContext, namespace, elementId);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_ELEMENT_GET_CONFLICT);
    return response.getValue();
  }

  @Override
  public CoreElement save(SessionContext context, ElementContext elementContext,
                          CoreElement element, String message) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    Namespace namespace = element.getAction() == Action.CREATE
        ? Namespace.ROOT_NAMESPACE
        : getValidatedNamespace(context, elementContext, element.getId(),
            ErrorCode.ZU_ELEMENT_SAVE);

    setElementHierarchyPosition(element, namespace);

    traverser.traverse(context, elementContext, Space.PRIVATE, element, collaborativeStoreVisitor);
    traverser.traverse(context, elementContext, Space.PRIVATE, element, indexingVisitor);
    getCollaborationAdaptor(context).commitElements(context, elementContext, message);
    getItemVersionManager(context)
        .updateModificationTime(context, Space.PRIVATE, elementContext.getItemId(),
            elementContext.getVersionId(), new Date());
    return element;
  }

  @Override
  public void resolveConflict(SessionContext context, ElementContext elementContext,
                              CoreElement element, Resolution resolution) {
    validateItemVersionExistence(context, Space.PRIVATE, elementContext.getItemId(),
        elementContext.getVersionId());

    setElementHierarchyPosition(element,
        getValidatedNamespace(context, elementContext, element.getId(),
            ErrorCode.ZU_ELEMENT_RESOLVE_CONFLICT));

    getCollaborationAdaptor(context)
        .resolveElementConflict(context, elementContext, element, resolution);
  }

  @Override
  public SearchResult search(SessionContext context, SearchCriteria searchCriteria) {
    Response<SearchResult> response =
        getSearchIndexAdaptor(context).search(context, searchCriteria);
    ValidationUtil.validateResponse(response, logger, ErrorCode.ZU_SEARCH);
    return response.getValue();
  }

  private Namespace getValidatedNamespace(SessionContext context, ElementContext elementContext,
                                          Id elementId, int errorCode) {
    Response<Namespace> namespaceResponse =
        getStateAdaptor(context).getNamespace(context, elementContext.getItemId(), elementId);
    ValidationUtil.validateResponse(namespaceResponse, logger, errorCode);
    return namespaceResponse.getValue();
  }

  private void setElementHierarchyPosition(CoreElement element, Namespace namespace) {
    element.setNamespace(namespace);
    element.setParentId(namespace.getParentElementId());
  }

  private void validateItemVersionExistence(SessionContext context, Space space, Id itemId,
                                            Id versionId) {
    if (!getItemVersionManager(context).isExist(context, space, itemId, versionId)) {
      String message = getItemManager(context).isExist(context, itemId)
          ? String.format(Messages.ITEM_VERSION_NOT_EXIST, itemId, versionId, space)
          : String.format(Messages.ITEM_NOT_EXIST, itemId);
      ReturnCode returnCode =
          new ReturnCode(ErrorCode.ZU_ITEM_VERSION_NOT_EXIST, Module.ZDB, message, null);
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }

  private CoreElementInfo coreElementToCoreElementInfo(CoreElement coreElement) {
    if (coreElement == null) {
      return null;
    }
    CoreElementInfo coreElementInfo = new CoreElementInfo();
    coreElementInfo.setId(coreElement.getId());
    coreElementInfo.setInfo(coreElement.getInfo());
    coreElementInfo.setRelations(coreElement.getRelations());
    coreElementInfo.setParentId(coreElement.getParentId());
    coreElementInfo.setNamespace(coreElement.getNamespace());
    coreElementInfo.setSubElements(new ArrayList<>());
    coreElement.getSubElements().forEach(element -> coreElementInfo.getSubElements().add
        (coreElementToCoreElementInfo(element)));

    return coreElementInfo;
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
