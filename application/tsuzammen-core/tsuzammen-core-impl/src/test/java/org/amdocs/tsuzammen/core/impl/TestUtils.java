/*
 * Copyright © 2016 European Support Limited
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

package org.amdocs.tsuzammen.core.impl;

import org.amdocs.tsuzammen.datatypes.SessionContext;
import org.amdocs.tsuzammen.datatypes.UserInfo;
import org.amdocs.tsuzammen.datatypes.item.Info;

public class TestUtils {
  public static Info createInfo(String value) {
    Info info = new Info();
    info.setName(value);
    info.addProperty("Name", "name_" + value);
    info.addProperty("Desc", "desc_" + value);
    return info;
  }

  public static SessionContext createSessionContext(UserInfo user, String tenant) {
    SessionContext context = new SessionContext();
    context.setUser(user);
    context.setTenant(tenant);
    return context;
  }
}
