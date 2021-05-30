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
package org.openhab.binding.netatmo.internal.handler;

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_EVENT_CAMERA_ID;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_EVENT_SNAPSHOT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_EVENT_SNAPSHOT_URL;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_EVENT_SUBTYPE;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_EVENT_TIME;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_HOME_EVENT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_PERSON_AT_HOME;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PERSON;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_PERSON_EVENT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_WELCOME_EVENT;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.PROPERTY_MAX_EVENT_TIME;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.toDateTimeType;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.toRawType;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.toStringType;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.NetatmoDescriptionProvider;
import org.openhab.binding.netatmo.internal.api.ApiBridge;
import org.openhab.binding.netatmo.internal.api.EventType;
import org.openhab.binding.netatmo.internal.api.ModuleType;
import org.openhab.binding.netatmo.internal.api.dto.NAEvent;
import org.openhab.binding.netatmo.internal.api.dto.NAHomeEvent;
import org.openhab.binding.netatmo.internal.api.dto.NASnapshot;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.binding.netatmo.internal.channelhelper.AbstractChannelHelper;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.types.Command;
import org.openhab.core.types.StateOption;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link PersonHandler} is the class used to handle the Welcome Home Data
 *
 * @author Ing. Peter Weiss - Initial contribution
 *
 */
@NonNullByDefault
public class PersonHandler extends NetatmoDeviceHandler {
    private final Logger logger = LoggerFactory.getLogger(PersonHandler.class);
    private long maxEventTime;

    public PersonHandler(Bridge bridge, List<AbstractChannelHelper> channelHelpers, ApiBridge apiBridge,
            TimeZoneProvider timeZoneProvider, NetatmoDescriptionProvider descriptionProvider) {
        super(bridge, channelHelpers, apiBridge, timeZoneProvider, descriptionProvider);
        String lastEvent = editProperties().get(PROPERTY_MAX_EVENT_TIME);
        maxEventTime = lastEvent != null ? Long.parseLong(lastEvent) : 0;
    }

    private @Nullable HomeSecurityHandler getHomeHandler() {
        NetatmoDeviceHandler handler = super.getBridgeHandler(getBridge());
        return handler != null ? (HomeSecurityHandler) handler : null;
    }

    @Override
    public void initialize() {
        super.initialize();
        HomeSecurityHandler homeHandler = getHomeHandler();
        if (homeHandler != null) {
            List<NAHomeEvent> lastEvents = homeHandler.getLastEventOf(config.id);
            if (!lastEvents.isEmpty()) {
                setEvent(lastEvents.get(0));
            }
        }
    }

    @Override
    public void setNAThing(NAThing naModule) {
        super.setNAThing(naModule);
        HomeSecurityHandler homeHandler = getHomeHandler();
        if (homeHandler != null) {
            descriptionProvider.setStateOptions(
                    new ChannelUID(getThing().getUID(), GROUP_PERSON_EVENT, CHANNEL_EVENT_CAMERA_ID),
                    homeHandler.getCameras().stream().map(p -> new StateOption(p.getId(), p.getName()))
                            .collect(Collectors.toList()));
        }
    }

    @Override
    public void setEvent(NAEvent event) {
        if (event.getTime() > maxEventTime) {
            logger.debug("Updating person  with event : {}", event.toString());

            maxEventTime = event.getTime();
            updateProperty(PROPERTY_MAX_EVENT_TIME, Long.toString(maxEventTime));

            updateIfLinked(GROUP_PERSON_EVENT, CHANNEL_EVENT_TIME, toDateTimeType(event.getTime(), zoneId));
            updateIfLinked(GROUP_PERSON_EVENT, CHANNEL_EVENT_CAMERA_ID, toStringType(event.getCameraId()));
            updateIfLinked(GROUP_WELCOME_EVENT, CHANNEL_EVENT_SUBTYPE,
                    event.getSubTypeDescription().map(d -> toStringType(d)).orElse(UnDefType.NULL));

            NASnapshot snapshot = event.getSnapshot();
            if (snapshot != null) {
                String url = snapshot.getUrl();
                updateIfLinked(GROUP_PERSON_EVENT, CHANNEL_EVENT_SNAPSHOT, toRawType(url));
                updateIfLinked(GROUP_PERSON_EVENT, CHANNEL_EVENT_SNAPSHOT_URL, toStringType(url));
            }

            EventType eventType = event.getEventType();
            if (eventType.appliesOn(ModuleType.NAPerson)) {
                updateIfLinked(GROUP_PERSON, CHANNEL_PERSON_AT_HOME, OnOffType.from(eventType == EventType.PERSON));
                triggerChannel(CHANNEL_HOME_EVENT, eventType.name());
            }
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if ((command instanceof OnOffType) && CHANNEL_PERSON_AT_HOME.equals(channelUID.getIdWithoutGroup())) {
            HomeSecurityHandler homeHandler = getHomeHandler();
            if (homeHandler != null) {
                homeHandler.callSetPersonAway(config.id, command == OnOffType.OFF);
            }
        } else {
            super.handleCommand(channelUID, command);
        }
    }
}
