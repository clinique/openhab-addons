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

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.BINDING_ID;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_BATTERY;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_CO2;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_DEVICE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_HEALTH;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_HOME_ENERGY;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_HOME_SECURITY;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_HUMIDITY;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_MODULE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_NOISE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PERSON;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PERSON_EVENT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PLUG;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PRESENCE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PRESSURE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_RAIN;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_ROOM_PROPERTIES;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_ROOM_TEMPERATURE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_SIGNAL;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_TEMPERATURE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_TH_PROPERTIES;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_TH_SETPOINT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_TH_TEMPERATURE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_WELCOME;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_WELCOME_EVENT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_WIND;
import static org.openhab.binding.netatmo.internal.api.NetatmoConstants.NO_RADIO;
import static org.openhab.binding.netatmo.internal.api.NetatmoConstants.RADIO_SIGNAL_LEVELS;
import static org.openhab.binding.netatmo.internal.api.NetatmoConstants.WIFI_SIGNAL_LEVELS;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NAModule;
import org.openhab.binding.netatmo.internal.api.dto.NAPlug;
import org.openhab.binding.netatmo.internal.api.dto.NARoom;
import org.openhab.binding.netatmo.internal.api.dto.NAThermostat;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.binding.netatmo.internal.api.dto.NAWelcome;
import org.openhab.binding.netatmo.internal.api.dto.NRV;
import org.openhab.binding.netatmo.internal.channelhelper.AbstractChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.BatteryHelper;
import org.openhab.binding.netatmo.internal.channelhelper.CameraChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.Co2ChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.DeviceChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.HomeCoachChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.HomeEnergyChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.HomeSecurityChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.HumidityChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.MeasuresChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.ModuleChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.NoiseChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.PersonChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.PlugChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.PresenceChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.PressureChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.RainChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.RoomPropsChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.RoomSetpointChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.RoomTempChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.TemperatureChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.Therm1PropsChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.Therm1SetpointChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.Therm1TempChannelHelper;
import org.openhab.binding.netatmo.internal.channelhelper.WindChannelHelper;
import org.openhab.binding.netatmo.internal.handler.CameraHandler;
import org.openhab.binding.netatmo.internal.handler.HomeCoachHandler;
import org.openhab.binding.netatmo.internal.handler.HomeEnergyHandler;
import org.openhab.binding.netatmo.internal.handler.HomeSecurityHandler;
import org.openhab.binding.netatmo.internal.handler.MainHandler;
import org.openhab.binding.netatmo.internal.handler.NRVHandler;
import org.openhab.binding.netatmo.internal.handler.NetatmoDeviceHandler;
import org.openhab.binding.netatmo.internal.handler.PersonHandler;
import org.openhab.binding.netatmo.internal.handler.PlugHandler;
import org.openhab.binding.netatmo.internal.handler.PresenceHandler;
import org.openhab.binding.netatmo.internal.handler.RoomHandler;
import org.openhab.binding.netatmo.internal.handler.Therm1Handler;
import org.openhab.core.thing.ThingTypeUID;

/**
 * This enum all handled Netatmo modules and devices along with their capabilities
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public enum ModuleType {
    // Security Group
    NAHomeSecurity(HomeSecurityHandler.class, RefreshPolicy.CONFIG, null, null, Set.of(HomeSecurityChannelHelper.class),
            List.of(GROUP_HOME_SECURITY), null),
    NAPerson(PersonHandler.class, RefreshPolicy.PARENT, NAHomeSecurity, null, Set.of(PersonChannelHelper.class),
            List.of(GROUP_PERSON, GROUP_PERSON_EVENT), null),
    NACamera(CameraHandler.class, RefreshPolicy.PARENT, NAHomeSecurity, null, Set.of(CameraChannelHelper.class),
            List.of(GROUP_WELCOME, GROUP_WELCOME_EVENT), NAWelcome.class),
    NOC(PresenceHandler.class, RefreshPolicy.PARENT, NAHomeSecurity, null,
            Set.of(CameraChannelHelper.class, PresenceChannelHelper.class),
            List.of(GROUP_WELCOME, GROUP_WELCOME_EVENT, GROUP_PRESENCE), NAWelcome.class),

    // Weather group
    NAMain(MainHandler.class, RefreshPolicy.AUTO, null, List.of("measure", "measure-timestamp"),
            Set.of(PressureChannelHelper.class, NoiseChannelHelper.class, HumidityChannelHelper.class,
                    TemperatureChannelHelper.class, Co2ChannelHelper.class, DeviceChannelHelper.class,
                    MeasuresChannelHelper.class),
            List.of(GROUP_TEMPERATURE, GROUP_HUMIDITY, GROUP_CO2, GROUP_NOISE, GROUP_PRESSURE, GROUP_DEVICE,
                    GROUP_SIGNAL),
            NAThing.class),
    NAModule1(NetatmoDeviceHandler.class, RefreshPolicy.PARENT, NAMain, List.of("measure", "measure-timestamp"),
            Set.of(HumidityChannelHelper.class, TemperatureChannelHelper.class, BatteryHelper.class,
                    ModuleChannelHelper.class, MeasuresChannelHelper.class),
            List.of(GROUP_TEMPERATURE, GROUP_HUMIDITY, GROUP_MODULE, GROUP_SIGNAL, GROUP_BATTERY), NAModule.class),
    NAModule2(NetatmoDeviceHandler.class, RefreshPolicy.PARENT, NAMain, null,
            Set.of(WindChannelHelper.class, BatteryHelper.class, ModuleChannelHelper.class),
            List.of(GROUP_WIND, GROUP_MODULE, GROUP_SIGNAL, GROUP_BATTERY), NAModule.class),
    NAModule3(NetatmoDeviceHandler.class, RefreshPolicy.PARENT, NAMain, List.of("sum-rain"),
            Set.of(RainChannelHelper.class, BatteryHelper.class, ModuleChannelHelper.class,
                    MeasuresChannelHelper.class),
            List.of(GROUP_RAIN, GROUP_MODULE, GROUP_SIGNAL, GROUP_BATTERY), NAModule.class),
    NAModule4(NetatmoDeviceHandler.class, RefreshPolicy.PARENT, NAMain, List.of("measure", "measure-timestamp"),
            Set.of(HumidityChannelHelper.class, TemperatureChannelHelper.class, Co2ChannelHelper.class,
                    BatteryHelper.class, ModuleChannelHelper.class, MeasuresChannelHelper.class),
            List.of(GROUP_TEMPERATURE, GROUP_HUMIDITY, GROUP_CO2, GROUP_MODULE, GROUP_SIGNAL, GROUP_BATTERY),
            NAModule.class),

    // Aircare group
    NHC(HomeCoachHandler.class, RefreshPolicy.AUTO, null, List.of("measure", "measure-timestamp"),
            Set.of(NoiseChannelHelper.class, HumidityChannelHelper.class, PressureChannelHelper.class,
                    TemperatureChannelHelper.class, Co2ChannelHelper.class, HomeCoachChannelHelper.class,
                    DeviceChannelHelper.class, MeasuresChannelHelper.class),
            List.of(GROUP_HEALTH, GROUP_TEMPERATURE, GROUP_HUMIDITY, GROUP_PRESSURE, GROUP_CO2, GROUP_NOISE,
                    GROUP_DEVICE, GROUP_SIGNAL),
            NAThing.class),

    // Energy group
    NAHomeEnergy(HomeEnergyHandler.class, RefreshPolicy.AUTO, null, null,
            Set.of(HomeEnergyChannelHelper.class),
            List.of(GROUP_HOME_ENERGY), null),
    NAPlug(PlugHandler.class, RefreshPolicy.CONFIG, NAHomeEnergy, null,
            Set.of(PlugChannelHelper.class, DeviceChannelHelper.class),
            List.of(GROUP_PLUG, GROUP_DEVICE, GROUP_SIGNAL),
            NAPlug.class),
    NATherm1(Therm1Handler.class, RefreshPolicy.PARENT, NAPlug, null,
            Set.of(Therm1PropsChannelHelper.class, Therm1SetpointChannelHelper.class, Therm1TempChannelHelper.class,
                    BatteryHelper.class, ModuleChannelHelper.class),
            List.of(GROUP_TH_PROPERTIES, GROUP_TH_SETPOINT, GROUP_TH_TEMPERATURE, GROUP_MODULE, GROUP_SIGNAL,
                    GROUP_BATTERY),
            NAThermostat.class),
    NARoom(RoomHandler.class, RefreshPolicy.CONFIG, NAHomeEnergy, null,
            Set.of(RoomPropsChannelHelper.class, RoomTempChannelHelper.class, RoomSetpointChannelHelper.class),
            List.of(GROUP_ROOM_PROPERTIES, GROUP_TH_SETPOINT, GROUP_ROOM_TEMPERATURE), NARoom.class),
    NRV(NRVHandler.class, RefreshPolicy.CONFIG, NAHomeEnergy, null,
            Set.of(BatteryHelper.class, ModuleChannelHelper.class),
            List.of(GROUP_MODULE, GROUP_SIGNAL, GROUP_BATTERY),
            NRV.class),
    // Left for future implementation
    // NACamDoorTag : self explaining
    // NSD : smoke detector
    // NIS : indoor siren
    // NDB : doorbell
    ;

    public enum RefreshPolicy {
        AUTO,
        PARENT,
        CONFIG;
    }

    public final List<String> groups;
    public final @Nullable List<String> extensions;
    public RefreshPolicy refreshPeriod;
    public final @Nullable ThingTypeUID bridgeThingType;
    public final ThingTypeUID thingTypeUID = new ThingTypeUID(BINDING_ID, this.name());
    public final Class<?> handlerClass;
    public final Set<Class<? extends AbstractChannelHelper>> channelHelpers;
    public final @Nullable Class<?> dto;

    ModuleType(Class<?> handlerClass, RefreshPolicy refreshPeriod, @Nullable ModuleType bridge,
            @Nullable List<String> extensions, Set<Class<? extends AbstractChannelHelper>> setOfHelpers,
            List<String> groups, @Nullable Class<?> dto) {
        this.handlerClass = handlerClass;
        this.groups = groups;
        this.refreshPeriod = refreshPeriod;
        this.extensions = extensions;
        this.channelHelpers = setOfHelpers;
        this.bridgeThingType = bridge != null ? bridge.thingTypeUID : null;
        this.dto = dto;
    }

    public boolean matches(ThingTypeUID otherThingTypeUID) {
        return thingTypeUID.equals(otherThingTypeUID);
    }

    public int[] getSignalLevels() {
        return groups.contains(GROUP_SIGNAL)
                ? (groups.contains(GROUP_BATTERY) ? RADIO_SIGNAL_LEVELS : WIFI_SIGNAL_LEVELS)
                : NO_RADIO;
    }
}
