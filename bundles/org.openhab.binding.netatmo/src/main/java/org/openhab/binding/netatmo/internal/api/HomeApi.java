/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.NetatmoConstants.PresenceLightMode;
import org.openhab.binding.netatmo.internal.api.dto.NAHome;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeData;
import org.openhab.binding.netatmo.internal.api.dto.NAPing;
import org.openhab.binding.netatmo.internal.api.dto.energy.Homestatus;

/**
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */

@NonNullByDefault
public class HomeApi extends RestManager {

    public HomeApi(ApiBridge apiClient) {
        super(apiClient, Set.of());
    }

    public class NAHomesDataResponse extends ApiResponse<NAHomeData> {
    }

    public List<NAHome> getHomeData() throws NetatmoException {
        String req = "gethomedata";
        NAHomesDataResponse response = get(req, NAHomesDataResponse.class);
        return response.getBody().getHomes();
    }

    public List<NAHome> getHomeList(@Nullable ModuleType type) throws NetatmoException {
        String req = "homesdata";
        if (type != null) {
            req += "?gateway_types=" + type.name();
        }
        NAHomesDataResponse response = get(req, NAHomesDataResponse.class);
        return response.getBody().getHomes();
    }

    public NAHome getHomesData(String homeId) throws NetatmoException {
        String req = "homesdata?home_id=" + homeId;
        NAHomesDataResponse response = get(req, NAHomesDataResponse.class);
        return response.getBody().getHomes().get(0);
    }

    public Homestatus getHomeStatus(String homeId) throws NetatmoException {
        String req = "homestatus?home_id=" + homeId;
        Homestatus response = get(req, Homestatus.class);
        return response;
    }

    public boolean setpersonsaway(String homeId, String personId) throws NetatmoException {
        String req = "setpersonsaway";
        String payload = String.format("{\"home_id\":\"%s\",\"person_id\":\"%s\"}", homeId, personId);
        ApiOkResponse response = post(req, payload, ApiOkResponse.class, false);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessful person away command : %s", response.getStatus()));
        }
        return true;
    }

    public boolean setpersonshome(String homeId, String personId) throws NetatmoException {
        String req = "setpersonshome";
        String payload = String.format("{\"home_id\":\"%s\",\"person_ids\":[\"%s\"]}", homeId, personId);
        ApiOkResponse response = post(req, payload, ApiOkResponse.class, false);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessfull person away command : %s", response.getStatus()));
        }
        return true;
    }

    public String ping(String vpnUrl) throws NetatmoException {
        String url = vpnUrl + "/command/ping";
        NAPing response = get(url, NAPing.class);
        return response.getStatus();
    }

    public boolean changeStatus(String localCameraURL, boolean isOn) throws NetatmoException {
        String url = localCameraURL + "/command/changestatus?status=" + (isOn ? "on" : "off");
        ApiOkResponse response = post(url, null, ApiOkResponse.class, false);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessfull camara status change : %s", response.getStatus()));
        }
        return true;
    }

    public boolean changeFloodLightMode(String localCameraURL, PresenceLightMode mode) throws NetatmoException {
        String url = localCameraURL + "/command/floodlight_set_config?config=%7B%22mode%22:%22" + mode.toString()
                + "%22%7D";
        ApiOkResponse response = get(url, ApiOkResponse.class);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessfull camara status change : %s", response.getStatus()));
        }
        return true;
    }
}
