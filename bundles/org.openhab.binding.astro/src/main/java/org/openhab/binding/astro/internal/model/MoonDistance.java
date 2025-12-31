/*
 * Copyright (c) 2010-2025 Contributors to the openHAB project
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
package org.openhab.binding.astro.internal.model;

import static org.openhab.core.library.unit.MetricPrefix.KILO;
import static org.openhab.core.library.unit.SIUnits.METRE;

import java.time.Instant;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.astro.internal.util.DateTimeUtils;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;

/**
 * Holds a distance informations.
 *
 * @author Gerhard Riegler - Initial contribution
 * @author Christoph Weitkamp - Introduced UoM
 * @author Gaël L'hopital - made it immutable and use Instant
 */
@NonNullByDefault
public class MoonDistance {
    public static final MoonDistance NULL = new MoonDistance(null, Double.NaN);

    private final @Nullable Instant date;
    private final double distance;

    private MoonDistance(@Nullable Instant date, double distance) {
        this.date = date;
        this.distance = distance;
    }

    public MoonDistance(double jdDate, double distance) {
        this(DateTimeUtils.jdToInstant(jdDate), distance);
    }

    /**
     * Returns the date of the calculated distance.
     */
    public @Nullable Instant getDate() {
        return date;
    }

    /**
     * Returns the distance in kilometers.
     */
    public State getDistance() {
        return Double.isNaN(distance) ? UnDefType.NULL : new QuantityType<>(distance, KILO(METRE));
    }

    public double getDistanceAsDouble() {
        return distance;
    }
}
