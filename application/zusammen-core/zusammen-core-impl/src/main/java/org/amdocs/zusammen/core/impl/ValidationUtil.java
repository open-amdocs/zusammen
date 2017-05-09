package org.amdocs.zusammen.core.impl;

import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.Response;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;

public class ValidationUtil {

  public static <T> void validateResponse(Response<T> response, ZusammenLogger logger,
                                          int errorCode) {
    if (!response.isSuccessful()) {
      ReturnCode returnCode = new ReturnCode(errorCode, Module.ZDB, null, response.getReturnCode());
      logger.error(returnCode.toString());
      throw new ZusammenException(returnCode);
    }
  }
}
