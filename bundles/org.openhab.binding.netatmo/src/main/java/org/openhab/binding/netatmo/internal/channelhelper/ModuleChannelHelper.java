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
package org.openhab.binding.netatmo.internal.channelhelper;

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.CHANNEL_LAST_SEEN;
import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.GROUP_MODULE;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NAModule;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.thing.Thing;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ModuleChannelHelper} handle specific behavior
 * of modules using batteries
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class ModuleChannelHelper extends AbstractChannelHelper {
    private final Logger logger = LoggerFactory.getLogger(ModuleChannelHelper.class);

    public ModuleChannelHelper(Thing thing, TimeZoneProvider timeZoneProvider) {
        super(thing, timeZoneProvider, GROUP_MODULE);
    }

    @Override
    protected @Nullable State internalGetProperty(NAThing naThing, String channelId) {
        NAModule module = (NAModule) naThing;
        logger.debug("internalGetProperty: {}", module.getName());
        return CHANNEL_LAST_SEEN.equals(channelId)
                ? ChannelTypeUtils.toDateTimeType(Math.max(module.getLastSeen(), module.getLastMessage()), zoneId)
                : null;
    }
}
