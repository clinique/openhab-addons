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
package org.openhab.binding.netatmo.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link NetatmoBinding} class defines common constants, which are used
 * across the whole binding.
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class NetatmoBindingConstants {

    public static final String BINDING_ID = "netatmo";
    public static final String SERVICE_PID = "org.openhab.binding." + BINDING_ID;
    public static final String VENDOR = "Netatmo";

    // Configuration keys
    public static final String EQUIPMENT_ID = "id";

    // Things properties
    public static final String PROPERTY_CITY = "city";
    public static final String PROPERTY_COUNTRY = "country";
    public static final String PROPERTY_TIMEZONE = "timezone";

    // Channel group ids
    public static final String GROUP_LAST_EVENT = "last-event";
    public static final String GROUP_TEMPERATURE = "temperature";
    public static final String GROUP_HUMIDITY = "humidity";
    public static final String GROUP_AIR_QUALITY = "airquality";
    public static final String GROUP_NOISE = "noise";
    public static final String GROUP_PRESSURE = "pressure";
    public static final String GROUP_TIMESTAMP = "timestamp";
    public static final String GROUP_RAIN = "rain";
    public static final String GROUP_WIND = "wind";
    public static final String GROUP_HOME_ENERGY = "energy";
    public static final String GROUP_SIGNAL = "signal";
    public static final String GROUP_BATTERY = "battery";
    public static final String GROUP_HOME_SECURITY = "security";
    public static final String GROUP_CAM_STATUS = "status";
    public static final String GROUP_CAM_LIVE = "live";
    public static final String GROUP_PRESENCE = "presence";
    public static final String GROUP_PERSON = "person";
    public static final String GROUP_PERSON_EVENT = "person-event";
    public static final String GROUP_ROOM_TEMPERATURE = "room-temperature";
    public static final String GROUP_ROOM_PROPERTIES = "room-properties";
    public static final String GROUP_TH_PROPERTIES = "th-properties";
    public static final String GROUP_TH_SETPOINT = "setpoint";
    public static final String GROUP_LOCATION = "location";

    // Alternative extended groups
    public static final String GROUP_EXTENSION = "-extended";
    public static final String GROUP_EXTENDED_TIMESTAMP = GROUP_TIMESTAMP + GROUP_EXTENSION;
    public static final String GROUP_BATTERY_EXTENDED = GROUP_BATTERY + GROUP_EXTENSION;
    public static final String GROUP_PRESSURE_EXTENDED = GROUP_PRESSURE + GROUP_EXTENSION;
    public static final String GROUP_TEMPERATURE_EXTENDED = GROUP_TEMPERATURE + GROUP_EXTENSION;
    public static final String GROUP_AIR_QUALITY_EXTENDED = GROUP_AIR_QUALITY + GROUP_EXTENSION;

    // Channel ids
    public static final String CHANNEL_VALUE = "value";
    public static final String CHANNEL_TREND = "trend";
    public static final String CHANNEL_MAX_TIME = "max-time";
    public static final String CHANNEL_MIN_TIME = "min-time";
    public static final String CHANNEL_MAX_VALUE = "max-today";
    public static final String CHANNEL_MIN_VALUE = "min-today";
    public static final String CHANNEL_HUMIDEX = "humidex";
    public static final String CHANNEL_CO2 = "co2";
    public static final String CHANNEL_HEALTH_INDEX = "health-index";
    public static final String CHANNEL_HUMIDEX_SCALE = "humidex-scale";
    public static final String CHANNEL_DEWPOINT = "dewpoint";
    public static final String CHANNEL_DEWPOINT_DEP = "dewpoint-depression";
    public static final String CHANNEL_HEAT_INDEX = "heat-index";
    public static final String CHANNEL_ABSOLUTE_PRESSURE = "absolute";
    public static final String CHANNEL_LAST_SEEN = "last-seen";
    public static final String CHANNEL_MEASURES_TIMESTAMP = "measures";
    public static final String CHANNEL_LOW_BATTERY = "low-battery";
    public static final String CHANNEL_BATTERY_STATUS = "status";
    public static final String CHANNEL_SIGNAL_STRENGTH = "strength";
    public static final String CHANNEL_SUM_RAIN1 = "sum-1";
    public static final String CHANNEL_SUM_RAIN24 = "sum-24";
    public static final String CHANNEL_WIND_ANGLE = "angle";
    public static final String CHANNEL_WIND_STRENGTH = "strength";
    public static final String CHANNEL_MAX_WIND_STRENGTH = "max-strength";
    public static final String CHANNEL_DATE_MAX_WIND_STRENGTH = "max-strength-date";
    public static final String CHANNEL_GUST_ANGLE = "gust-angle";
    public static final String CHANNEL_GUST_STRENGTH = "gust-strength";
    public static final String CHANNEL_SETPOINT_MODE = "mode";
    public static final String CHANNEL_SETPOINT_START_TIME = "start";
    public static final String CHANNEL_SETPOINT_END_TIME = "end";
    public static final String CHANNEL_THERM_RELAY = "relay-status";
    public static final String CHANNEL_ANTICIPATING = "anticipating";
    public static final String CHANNEL_ROOM_WINDOW_OPEN = "window-open";
    public static final String CHANNEL_ROOM_HEATING_POWER = "heating-power-request";
    public static final String CHANNEL_PLANNING = "planning";
    public static final String CHANNEL_PERSON_COUNT = "person-count";
    public static final String CHANNEL_UNKNOWN_COUNT = "unknown-count";
    public static final String CHANNEL_UNKNOWN_SNAPSHOT = "unknown-snapshot";
    public static final String CHANNEL_MONITORING = "monitoring";
    public static final String CHANNEL_SD_CARD = "sd-card";
    public static final String CHANNEL_ALIM_STATUS = "alim";
    public static final String CHANNEL_LIVEPICTURE = "picture";
    public static final String CHANNEL_LIVEPICTURE_URL = "picture-url";
    public static final String CHANNEL_LIVESTREAM_URL = "stream-url";
    public static final String CHANNEL_EVENT_TYPE = "type";
    public static final String CHANNEL_EVENT_SUBTYPE = "subtype";
    public static final String CHANNEL_EVENT_VIDEO_STATUS = "video-status";
    public static final String CHANNEL_EVENT_MESSAGE = "message";
    public static final String CHANNEL_EVENT_TIME = "time";
    public static final String CHANNEL_EVENT_SNAPSHOT = "snapshot";
    public static final String CHANNEL_EVENT_SNAPSHOT_URL = "snapshot-url";
    public static final String CHANNEL_EVENT_VIDEO_URL = "video-url";
    public static final String CHANNEL_EVENT_PERSON_ID = "person-id";
    public static final String CHANNEL_EVENT_CAMERA_ID = "camera-id";
    public static final String CHANNEL_PERSON_AT_HOME = "at-home";
    public static final String CHANNEL_PERSON_AVATAR = "avatar";
    public static final String CHANNEL_PERSON_AVATAR_URL = "avatar-url";
    public static final String CHANNEL_HOME_EVENT = "home-event";
    public static final String CHANNEL_SETPOINT_DURATION = "setpoint-duration";

    // Presence outdoor camera specific channels
    public static final String CHANNEL_CAMERA_FLOODLIGHT_AUTO_MODE = "auto-mode";
    public static final String CHANNEL_CAMERA_FLOODLIGHT = "floodlight";
}
