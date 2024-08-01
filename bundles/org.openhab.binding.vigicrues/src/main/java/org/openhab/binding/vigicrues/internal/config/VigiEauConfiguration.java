/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
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
package org.openhab.binding.vigicrues.internal.config;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.vigicrues.internal.dto.vigieau.UserKind;
import org.openhab.binding.vigicrues.internal.dto.vigieau.WaterType;
import org.openhab.core.library.types.PointType;

/**
 * The {@link VigiEauConfiguration} is the class used to match the
 * thing configuration.
 *
 * @author Gaël L"hopital - Initial contribution
 */
@NonNullByDefault
public class VigiEauConfiguration {
    public static final String LOCATION = "location";

    private String location = "";
    public int refresh = 12;
    public WaterType type = WaterType.AEP;
    public UserKind profile = UserKind.PART;

    public PointType getLocation() {
        return PointType.valueOf(location);
    }
}
