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

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.*;
import static org.openhab.binding.netatmo.internal.api.doc.NetatmoConstants.TEMPERATURE_UNIT;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NADashboard;
import org.openhab.core.i18n.TimeZoneProvider;
import org.openhab.core.thing.Thing;
import org.openhab.core.types.State;

/**
 * The {@link TemperatureChannelHelper} handle specific behavior
 * of modules measuring temperature
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class TemperatureChannelHelper extends AbstractChannelHelper {

    public TemperatureChannelHelper(Thing thing, TimeZoneProvider timeZoneProvider) {
        super(thing, timeZoneProvider, GROUP_TEMPERATURE);
    }

    @Override
    protected @Nullable State internalGetDashboard(NADashboard dashboard, String channelId) {
        switch (channelId) {
            case CHANNEL_VALUE:
                return toQuantityType(dashboard.getTemperature(), TEMPERATURE_UNIT);
            case CHANNEL_MIN_VALUE:
                return toQuantityType(dashboard.getMinTemp(), TEMPERATURE_UNIT);
            case CHANNEL_MAX_VALUE:
                return toQuantityType(dashboard.getMaxTemp(), TEMPERATURE_UNIT);
            case CHANNEL_TREND:
                return toStringType(dashboard.getTempTrend());
            case CHANNEL_MIN_TIME:
                return toDateTimeType(dashboard.getDateMinTemp(), zoneId);
            case CHANNEL_MAX_TIME:
                return toDateTimeType(dashboard.getDateMaxTemp(), zoneId);
        }
        return null;
    }
}
