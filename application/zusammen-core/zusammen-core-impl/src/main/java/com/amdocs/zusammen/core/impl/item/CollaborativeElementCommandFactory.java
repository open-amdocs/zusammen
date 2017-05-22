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

import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptor;
import com.amdocs.zusammen.adaptor.outbound.api.CollaborationAdaptorFactory;
import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import com.amdocs.zusammen.datatypes.Id;
import com.amdocs.zusammen.datatypes.SessionContext;
import com.amdocs.zusammen.datatypes.item.Action;
import com.amdocs.zusammen.datatypes.response.ErrorCode;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

public class CollaborativeElementCommandFactory extends ElementCommandAbstarctFactory {

  private static ZusammenLogger logger = ZusammenLoggerFactory.getLogger(CollaborativeElementCommandFactory.class
      .getName());

  private CollaborativeElementCommandFactory() {
  }

  public static ElementCommandAbstarctFactory init() {
    final ElementCommandAbstarctFactory factory = new CollaborativeElementCommandFactory();

    factory.addCommand(Action.CREATE, (context, elementContext, space, element) -> {
      element.setId(new Id());
      Response response = getCollaborationAdaptor(context).createElement(context, elementContext,
          element);
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_CREATE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }
    });
    factory.addCommand(Action.UPDATE, (context, elementContext, space, element) -> {

      Response response = getCollaborationAdaptor(context).updateElement(context, elementContext,
          element);
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_UPDATE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }
    });
    factory.addCommand(Action.DELETE, (context, elementContext, space, element) -> {
      Response response = getCollaborationAdaptor(context).deleteElement(context, elementContext,
          element);
      if (!response.isSuccessful()) {
        ReturnCode returnCode = new ReturnCode(ErrorCode.ZU_ELEMENT_DELETE, Module.ZDB, null,
            response.getReturnCode());
        logger.error(returnCode.toString());
        throw new ZusammenException(returnCode);

      }
    });
    return factory;
  }

  private static CollaborationAdaptor getCollaborationAdaptor(SessionContext context) {
    return CollaborationAdaptorFactory.getInstance().createInterface(context);
  }
}
