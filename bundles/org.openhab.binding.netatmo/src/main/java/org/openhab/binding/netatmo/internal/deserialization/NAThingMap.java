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
package org.openhab.binding.netatmo.internal.deserialization;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;

/**
 * The {@link NAThingMap} defines an hashmap of NAThings identified by their id
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class NAThingMap extends NAObjectMap<NAThing> {
    private static final long serialVersionUID = -7864636414965562293L;
}
