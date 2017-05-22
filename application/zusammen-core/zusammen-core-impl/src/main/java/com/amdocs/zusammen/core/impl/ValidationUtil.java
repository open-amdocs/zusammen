package com.amdocs.zusammen.core.impl;

import com.amdocs.zusammen.commons.log.ZusammenLogger;
import com.amdocs.zusammen.datatypes.response.Module;
import com.amdocs.zusammen.datatypes.response.Response;
import com.amdocs.zusammen.datatypes.response.ReturnCode;
import com.amdocs.zusammen.datatypes.response.ZusammenException;

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
