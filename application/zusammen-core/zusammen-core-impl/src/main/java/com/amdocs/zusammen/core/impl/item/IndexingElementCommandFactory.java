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

import com.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.SearchIndexAdaptorFactory;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.item.ElementStateAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

public class IndexingElementCommandFactory extends ElementCommandAbstarctFactory {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(IndexingElementCommandFactory.class
      .getName());

  private IndexingElementCommandFactory() {
  }

  public static ElementCommandAbstarctFactory init() {
    final ElementCommandAbstarctFactory factory = new IndexingElementCommandFactory();

    factory.addCommand(Action.CREATE, (context, elementContext, space, element) -> {
      Response response = getStateAdaptor(context).create(context, elementContext, space, element);
      if(response.isSuccessful()) {
        response = getSearchIndexAdaptor(context).createElement(context, elementContext, space,
            element);
      }

      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_CREATE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }

    });
    factory.addCommand(Action.UPDATE, (context, elementContext, space, element) -> {
      Response response = getStateAdaptor(context).update(context, elementContext, space, element);
      if(response.isSuccessful()) {
        response = getSearchIndexAdaptor(context).updateElement(context, elementContext, space,
            element);
      }
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_UPDATE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }

    });
    factory.addCommand(Action.DELETE, (context, elementContext, space, element) -> {
      Response response = getStateAdaptor(context).delete(context, elementContext, space, element);
      if(response.isSuccessful()) {
        response =
            getSearchIndexAdaptor(context).deleteElement(context, elementContext, space, element);
      }
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_DELETE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }
    });
    return factory;
  }

  private static ElementStateAdaptor getStateAdaptor(SessionContext context) {
    return ElementStateAdaptorFactory.getInstance().createInterface(context);
  }

  private static SearchIndexAdaptor getSearchIndexAdaptor(SessionContext context) {
    return SearchIndexAdaptorFactory.getInstance().createInterface(context);
  }
}
