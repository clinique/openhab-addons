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
package org.openhab.binding.toyota.internal;

import static org.openhab.binding.toyota.internal.ToyotaBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.toyota.internal.deserialization.MyTDeserializer;
import org.openhab.binding.toyota.internal.handler.MyTBridgeHandler;
import org.openhab.binding.toyota.internal.handler.VehicleHandler;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ToyotaHandlerFactory} is responsible for creating things and thing handlers.
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.toyota", service = ThingHandlerFactory.class)
public class ToyotaHandlerFactory extends BaseThingHandlerFactory {
    private final Logger logger = LoggerFactory.getLogger(ToyotaHandlerFactory.class);
    private final MyTDeserializer deserializer;
    private final HttpClientFactory httpClientFactory;
    private final TimeZoneProvider timeZoneProvider;

    @Activate
    public ToyotaHandlerFactory(@Reference HttpClientFactory httpClientFactory,
            @Reference TimeZoneProvider timeZoneProvider, @Reference MyTDeserializer deserializer) {
        this.httpClientFactory = httpClientFactory;
        this.timeZoneProvider = timeZoneProvider;
        this.deserializer = deserializer;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (APIBRIDGE_THING_TYPE.equals(thingTypeUID)) {
            return new MyTBridgeHandler((Bridge) thing, deserializer, httpClientFactory);
        } else if (VEHICLE_THING_TYPE.equals(thingTypeUID)) {
            return new VehicleHandler(thing, timeZoneProvider);
        }
        logger.warn("ThingHandler not found for {}", thing.getThingTypeUID());
        return null;
    }
}
