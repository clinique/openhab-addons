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
package org.openhab.transform.geohash.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.PointType;
import org.openhab.core.transform.TransformationException;
import org.openhab.core.transform.TransformationService;
import org.osgi.service.component.annotations.Component;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;

/**
 *
 * The implementation of {@link GeohashTransformationService} which simply maps coordinates
 * (latitude,longitude) to corresponding Geohash
 *
 * @author Gaël L'hopital - Initial contribution and API
 */
@Component(immediate = true, service = TransformationService.class, property = { "openhab.transform=GEOHASH" })
@NonNullByDefault
public class GeohashTransformationService implements TransformationService {
    private int DEFAULT_PRECISION = 6;

    @Override
    public @Nullable String transform(final String precision, final String coordinates) throws TransformationException {
        try {
            PointType point = PointType.valueOf(coordinates);
            try {
                int numcar = precision.isEmpty() ? DEFAULT_PRECISION : Integer.parseInt(precision);
                if (numcar > 0 && numcar <= 12) {
                    return GeoHash.withCharacterPrecision(point.getLatitude().doubleValue(),
                            point.getLongitude().doubleValue(), numcar).toBase32();
                } else {
                    throw new TransformationException("Valid range for Precision is [1,12]: '{}'".formatted(precision));
                }
            } catch (NumberFormatException e) {
                throw new TransformationException(
                        "The value '{}' is not valid precision level: {}".formatted(precision));
            }
        } catch (IllegalArgumentException e) {
            try {
                GeoHash hash = GeoHash.fromGeohashString(coordinates);
                WGS84Point centerPoint = hash.getBoundingBox().getCenter();
                return new PointType(new DecimalType(centerPoint.getLatitude()),
                        new DecimalType(centerPoint.getLongitude())).toString();
            } catch (NullPointerException e2) {
                throw new TransformationException(
                        "The value '{}' is not valid geohash nor a valid coordinate expression".formatted(coordinates));
            }
        }
    }
}
