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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.NetatmoConstants.SetpointMode;
import org.openhab.binding.netatmo.internal.api.dto.NADeviceDataBody;
import org.openhab.binding.netatmo.internal.api.dto.NAPlug;

/**
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */

@NonNullByDefault
public class EnergyApi extends RestManager {
    public static final String URL_HOMESTATUS = "homestatus";
    public static final String URL_THERMOSTAT = "getthermostatsdata";

    private class NAThermostatDataResponse extends ApiResponse<NADeviceDataBody<NAPlug>> {
    }

    // private class NAValveDataResponse extends ApiResponse<NADeviceDataBody<NRV>> {
    // }

    public EnergyApi(ApiBridge apiClient) {
        super(apiClient, NetatmoConstants.ALL_SCOPES);
    }

    private NAThermostatDataResponse getThermostatsData(@Nullable String equipmentId) throws NetatmoException {
        String req = URL_THERMOSTAT;
        if (equipmentId != null) {
            req += "?device_id=" + equipmentId;
        }
        return get(req, NAThermostatDataResponse.class);
    }

    // private NAValveDataResponse getValvesData(@Nullable String equipmentId, @Nullable ThingUID thingUID)
    //         throws NetatmoException {

    //     String req = URL_HOMESTATUS + "?home_id=" + thingUID.getId() + "&device_types=NRV";

    //     Homestatus homestatus = get(req, Homestatus.class);
    //     return new NAValveDataResponse();
    // }

    public NADeviceDataBody<NAPlug> getThermostatsDataBody(@Nullable String equipmentId) throws NetatmoException {
        return getThermostatsData(equipmentId).getBody();
    }

    public NAPlug getThermostatData(String equipmentId) throws NetatmoException {
        NADeviceDataBody<NAPlug> answer = getThermostatsData(equipmentId).getBody();
        NAPlug plug = answer.getDevice(equipmentId);
        if (plug != null) {
            return plug;
        }
        throw new NetatmoException(String.format("Unexpected answer searching device '%s' : not found.", equipmentId));
    }

    // public NRV getValveData(String equipmentId, @Nullable ThingUID thingUID) throws NetatmoException {
    //     NADeviceDataBody<NRV> answer = getValvesData(equipmentId, thingUID).getBody();
    //     NRV valve = answer.getDevice(equipmentId);
    //     if (valve != null) {
    //         return valve;
    //     }
    //     throw new NetatmoException(String.format("Unexpected answer cherching device '%s' : not found.", equipmentId));
    // }

    /**
     *
     * The method switchschedule switches the Thermostat&#x27;s schedule to another existing schedule.
     *
     * @param deviceId The relay id (required)
     * @param moduleId The thermostat id (required)
     * @param scheduleId The schedule id. It can be found in the getthermstate response, under the keys
     *            therm_program_backup and therm_program. (required)
     * @return boolean success
     * @throws NetatmoException If fail to call the API, e.g. server error or cannot deserialize the
     *             response body
     */
    public boolean switchschedule(String deviceId, String moduleId, String scheduleId) throws NetatmoException {
        String req = "switchschedule?device_id=%s&module_id=%s&schedule_id=%s";
        req = String.format(req, deviceId, moduleId, scheduleId);
        ApiOkResponse response = post(req, null, ApiOkResponse.class, true);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessfull schedule change : %s", response.getStatus()));
        }
        return true;
    }

    /**
     *
     * The method setthermpoint changes the Thermostat manual temperature setpoint.
     *
     * @param deviceId The relay id (required)
     * @param moduleId The thermostat id (required)
     * @param targetMode Chosen setpoint_mode (required)
     * @param setpointEndtime When using the manual or max setpoint_mode, this parameter defines when the setpoint
     *            expires. (optional)
     * @param setpointTemp When using the manual setpoint_mode, this parameter defines the temperature setpoint (in
     *            Celcius) to use. (optional)
     * @return ApiOkResponse
     * @throws NetatmoCommunicationException If fail to call the API, e.g. server error or cannot deserialize the
     *             response body
     */
    public boolean setthermpoint(String deviceId, String moduleId, SetpointMode targetMode, long setpointEndtime,
            double setpointTemp) throws NetatmoException {
        String req = "setthermpoint?device_id=%s&module_id=%s&setpoint_mode=%s";
        req = String.format(req, deviceId, moduleId, targetMode.getDescriptor());
        if (targetMode == SetpointMode.MANUAL || targetMode == SetpointMode.MAX) {
            req += "&setpoint_endtime=" + setpointEndtime;
            if (targetMode == SetpointMode.MANUAL) {
                req += "&setpoint_temp=" + setpointTemp;
            }
        }

        ApiOkResponse response = post(req, null, ApiOkResponse.class, true);
        if (!response.isSuccess()) {
            throw new NetatmoException(String.format("Unsuccessfull setpoint change : %s", response.getStatus()));
        }
        return true;
    }
}
