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
package org.openhab.binding.netatmo.internal.handler.capability;

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.VENDOR;
import static org.openhab.core.thing.Thing.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.data.ModuleType;
import org.openhab.binding.netatmo.internal.api.dto.Device;
import org.openhab.binding.netatmo.internal.api.dto.Event;
import org.openhab.binding.netatmo.internal.api.dto.HomeData;
import org.openhab.binding.netatmo.internal.api.dto.HomeEvent;
import org.openhab.binding.netatmo.internal.api.dto.HomeStatusModule;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeStatus.HomeStatus;
import org.openhab.binding.netatmo.internal.api.dto.NAMain;
import org.openhab.binding.netatmo.internal.api.dto.NAObject;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.binding.netatmo.internal.handler.CommonInterface;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.openhab.core.types.Command;

/**
 * The {@link Capability} is the base class for all inherited capabilities
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class Capability {
    protected final Thing thing;
    protected final CommonInterface handler;

    protected boolean firstLaunch;
    protected Map<String, String> properties = Map.of();
    protected ThingStatus thingStatus = ThingStatus.UNKNOWN;
    protected @Nullable String thingStatusReason = "";

    Capability(CommonInterface handler) {
        this.handler = handler;
        this.thing = handler.getThing();
    }

    public final void setNewData(@Nullable NAObject newData) {
        if (newData != null) {
            beforeNewData();
            if (newData instanceof HomeData) {
                updateHomeData((HomeData) newData);
            }
            if (newData instanceof HomeStatus) {
                updateHomeStatus((HomeStatus) newData);
            }
            if (newData instanceof HomeStatusModule) {
                updateHomeStatusModule((HomeStatusModule) newData);
            }
            if (newData instanceof Event) {
                updateEvent((Event) newData);
            }
            if (newData instanceof HomeEvent) {
                updateHomeEvent((HomeEvent) newData);
            }
            if (newData instanceof NAThing) {
                updateNAThing((NAThing) newData);
            }
            if (newData instanceof NAMain) {
                updateNAMain((NAMain) newData);
            }
            if (newData instanceof Device) {
                updateNADevice((Device) newData);
            }
            afterNewData(newData);
        }
    }

    protected void beforeNewData() {
        properties = new HashMap<>(thing.getProperties());
        firstLaunch = properties.isEmpty();
        ModuleType moduleType = ModuleType.from(thing.getThingTypeUID());
        if (firstLaunch && !moduleType.isLogical()) {
            properties.put(PROPERTY_VENDOR, VENDOR);
            properties.put(PROPERTY_MODEL_ID, moduleType.name());
        }
        thingStatus = ThingStatus.ONLINE;
        thingStatusReason = null;
    }

    protected void afterNewData(@Nullable NAObject newData) {
        if (!properties.equals(thing.getProperties())) {
            thing.setProperties(properties);
        }
        handler.setThingStatus(thingStatus, thingStatusReason);
    }

    protected void updateNAThing(NAThing newData) {
        String firmware = newData.getFirmware();
        if (firmware != null && !firmware.isBlank()) {
            properties.put(PROPERTY_FIRMWARE_VERSION, firmware);
        }
    }

    protected void updateNAMain(NAMain newData) {
        // do nothing by default, can be overridden by subclasses
    }

    protected void updateHomeEvent(HomeEvent newData) {
        // do nothing by default, can be overridden by subclasses
    }

    protected void updateHomeStatus(HomeStatus newData) {
        // do nothing by default, can be overridden by subclasses
    }

    protected void updateHomeData(HomeData newData) {
        // do nothing by default, can be overridden by subclasses
    }

    protected void updateEvent(Event newData) {
        // do nothing by default, can be overridden by subclasses
    }

    protected void updateNADevice(Device newData) {
        // do nothing by default, can be overridden by subclasses
    }

    public void initialize() {
        // do nothing by default, can be overridden by subclasses
    }

    public void expireData() {
        if (!handler.getCapabilities().containsKey(RefreshCapability.class)) {
            CommonInterface bridgeHandler = handler.getBridgeHandler();
            if (bridgeHandler != null) {
                bridgeHandler.expireData();
            }
        }
    }

    public void dispose() {
        // do nothing by default, can be overridden by subclasses
    }

    public void updateHomeStatusModule(HomeStatusModule newData) {
        // do nothing by default, can be overridden by subclasses
    }

    public void handleCommand(String channelName, Command command) {
        // do nothing by default, can be overridden by subclasses
    }

    public Collection<Class<? extends ThingHandlerService>> getServices() {
        return List.of();
    }

    public List<NAObject> updateReadings() {
        return List.of();
    }
}