/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
package org.openhab.binding.toyota.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.toyota.internal.ToyotaException;
import org.openhab.binding.toyota.internal.api.MyTHttpApi;
import org.openhab.binding.toyota.internal.config.ApiBridgeConfiguration;
import org.openhab.binding.toyota.internal.deserialization.MyTDeserializer;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseBridgeHandler;
import org.openhab.core.thing.util.ThingWebClientUtil;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link MyTBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class MyTBridgeHandler extends BaseBridgeHandler {
    private final Logger logger = LoggerFactory.getLogger(MyTBridgeHandler.class);
    // private final HttpClientFactory httpClientFactory;
    // private final MyTDeserializer deserializer;
    private final MyTHttpApi api;;

    public MyTBridgeHandler(Bridge bridge, MyTDeserializer deserializer, HttpClientFactory httpClientFactory) {
        super(bridge);
        // this.httpClientFactory = httpClientFactory;
        // this.deserializer = deserializer;
        String clientName = ThingWebClientUtil.buildWebClientConsumerName(thing.getUID(), null);
        try {
            this.api = new MyTHttpApi(clientName, deserializer, httpClientFactory);
        } catch (ToyotaException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void initialize() {
        logger.debug("Initializing MyT API bridge handler.");
        ApiBridgeConfiguration configuration = getConfigAs(ApiBridgeConfiguration.class);
        try {
            api.initialize(configuration);
        } catch (ToyotaException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
        }
    }

    @Override
    public void dispose() {
        try {
            api.dispose();
        } catch (Exception e) {
            logger.warn("Unable to stop myTApi : {}", e.getMessage());
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub
    }
}
