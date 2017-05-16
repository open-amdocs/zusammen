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

package org.amdocs.zusammen.adaptor.outbound.impl.health;


import org.amdocs.zusammen.adaptor.outbound.api.health.HealthAdaptor;
import org.amdocs.zusammen.commons.health.data.HealthInfo;
import org.amdocs.zusammen.commons.log.ZusammenLogger;
import org.amdocs.zusammen.commons.log.ZusammenLoggerFactory;
import org.amdocs.zusammen.datatypes.SessionContext;
import org.amdocs.zusammen.datatypes.response.ErrorCode;
import org.amdocs.zusammen.datatypes.response.Module;
import org.amdocs.zusammen.datatypes.response.ReturnCode;
import org.amdocs.zusammen.datatypes.response.ZusammenException;
import org.amdocs.zusammen.sdk.collaboration.CollaborationStore;
import org.amdocs.zusammen.sdk.collaboration.CollaborationStoreFactory;
import org.amdocs.zusammen.sdk.searchindex.SearchIndexFactory;
import org.amdocs.zusammen.sdk.state.StateStore;
import org.amdocs.zusammen.sdk.state.StateStoreFactory;
import org.amdocs.zusammen.sdk.searchindex.SearchIndex;

public class HealthAdaptorImpl implements HealthAdaptor {
    private static final ZusammenLogger logger = ZusammenLoggerFactory.getLogger(HealthAdaptorImpl.class.getSimpleName());

    private CollaborationStore getCollaborationStore(SessionContext context) throws ZusammenException {
        try {
            return CollaborationStoreFactory.getInstance().createInterface(context);
        } catch (ZusammenException ze) {
            throw ze;
        } catch (Throwable t) {
            logger.error("Failed to generate adapter ", t);
            ReturnCode returnCode = new ReturnCode(ErrorCode.HC_MISSING_PLUGIN, Module.ZHC, null, null);
            throw new ZusammenException(returnCode);
        }
    }

    private StateStore getStateStore(SessionContext context) throws ZusammenException {
        try {
            return StateStoreFactory.getInstance().createInterface(context);
        } catch (ZusammenException ze) {
            throw ze;
        } catch (Throwable t) {
            logger.error("Failed to generate adapter ", t);
            ReturnCode returnCode = new ReturnCode(ErrorCode.HC_MISSING_PLUGIN, Module.ZHC, null, null);
            throw new ZusammenException(returnCode);
        }
    }

    private SearchIndex getSearchIndex(SessionContext context) throws ZusammenException {
        try {
            return SearchIndexFactory.getInstance().createInterface(context);
        } catch (ZusammenException ze) {
            throw ze;
        } catch (Throwable t) {
            logger.error("Failed to generate adapter ", t);
            ReturnCode returnCode = new ReturnCode(ErrorCode.HC_MISSING_PLUGIN, Module.ZHC, null, null);
            throw new ZusammenException(returnCode);
        }
    }


    @Override
    public HealthInfo getCollaborationStatus(SessionContext sessionContext) throws ZusammenException {
        return getCollaborationStore(sessionContext).checkHealth(sessionContext).getValue();
    }

    @Override
    public HealthInfo getStateStatus(SessionContext sessionContext) throws ZusammenException {
        return getStateStore(sessionContext).checkHealth(sessionContext).getValue();
    }

    @Override
    public HealthInfo getSearchStatus(SessionContext sessionContext) throws ZusammenException {
        return getSearchIndex(sessionContext).checkHealth(sessionContext).getValue();
    }
}
