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
package org.openhab.binding.netatmo.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.handler.capability.CapabilityMap;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.binding.builder.BridgeBuilder;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link AccountHandler} is the base class for all Netatmo bridges
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class AccountHandler extends BaseBridgeHandler implements CommonInterface {
    private final Logger logger = LoggerFactory.getLogger(AccountHandler.class);
    private CapabilityMap capabilities = new CapabilityMap();

    public AccountHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing handler for bridge {}", getThing().getUID());
        commonInitialize(scheduler);
    }

    @Override
    public void dispose() {
        commonDispose();
        super.dispose();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        commonHandleCommand(channelUID, command);
    }

    @Override
    public void setThingStatus(ThingStatus thingStatus, @Nullable String thingStatusReason) {
        updateStatus(thingStatus, ThingStatusDetail.NONE, thingStatusReason);
    }

    @Override
    public CapabilityMap getCapabilities() {
        return capabilities;
    }

    @Override
    public BridgeBuilder editThing() {
        return super.editThing();
    }

    @Override
    public void updateThing(Thing thing) {
        super.updateThing(thing);
    }

    @Override
    public void updateState(ChannelUID channelUID, State state) {
        super.updateState(channelUID, state);
    }

    @Override
    public boolean isLinked(ChannelUID channelUID) {
        return super.isLinked(channelUID);
    }

    @Override
    public @Nullable Bridge getBridge() {
        return super.getBridge();
    }

    @Override
    public void triggerChannel(String channelID, String event) {
        super.triggerChannel(channelID, event);
    }
}