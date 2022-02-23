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
package org.openhab.binding.netatmo.internal.handler.channelhelper;

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.*;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.toStringType;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NADashboard;
import org.openhab.core.types.State;

/**
 * The {@link PressureExtChannelHelper} handles specific behavior of modules measuring pressure
 * with pressure trend capability
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class PressureExtChannelHelper extends PressureChannelHelper {

    public PressureExtChannelHelper() {
        super(GROUP_PRESSURE_EXTENDED);
    }

    @Override
    protected @Nullable State internalGetDashboard(String channelId, NADashboard dashboard) {
        return channelId.equals(CHANNEL_TREND) ? toStringType(dashboard.getPressureTrend())
                : super.internalGetDashboard(channelId, dashboard);
    }
}
