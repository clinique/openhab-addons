/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.netatmo.internal.api;

import static org.openhab.binding.netatmo.internal.api.data.NetatmoConstants.*;

import java.util.Collection;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.data.ModuleType;
import org.openhab.binding.netatmo.internal.api.data.NetatmoConstants.FeatureArea;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeData;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeStatus.HomeStatus;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeStatus.NAHomeStatusResponse;

/**
 * The {@link HomeApi} handles general API endpoints not requiring specific scope area
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */

@NonNullByDefault
public class HomeApi extends RestManager {

    public HomeApi(ApiBridge apiClient) {
        super(apiClient, FeatureArea.NONE);
    }

    public @Nullable HomeStatus getHomeStatus(String homeId) throws NetatmoException {
        UriBuilder uriBuilder = getApiUriBuilder(SUB_PATH_HOMESTATUS, PARAM_HOMEID, homeId);

        NAHomeStatusResponse response = get(uriBuilder, NAHomeStatusResponse.class);
        return response.getBody().getHomeStatus().orElse(null);
    }

    public @Nullable NAHomeData getHomeData(String homeId) throws NetatmoException {
        Collection<NAHomeData> result = getHomesData(homeId, null);
        return result.isEmpty() ? null : result.iterator().next();
    }

    public Collection<NAHomeData> getHomesData(@Nullable String homeId, @Nullable ModuleType type)
            throws NetatmoException {
        UriBuilder uriBuilder = getApiUriBuilder(SUB_PATH_HOMES_DATA, PARAM_HOMEID, homeId);

        if (type != null) {
            uriBuilder.queryParam(PARAM_GATEWAYTYPE, type.name());
        }

        NAHomeData.NAHomesDataResponse response = get(uriBuilder, NAHomeData.NAHomesDataResponse.class);
        return response.getBody().getElements();
    }
}
