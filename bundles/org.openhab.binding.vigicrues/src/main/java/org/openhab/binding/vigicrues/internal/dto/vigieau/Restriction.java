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
package org.openhab.binding.vigicrues.internal.dto.vigieau;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Restriction} describes categories of water
 *
 * @author Gaël L'hopital - Initial contribution
 */
public enum Restriction {
    @SerializedName("pas de restriction")
    NO_RESTRICTION,
    @SerializedName("vigilance")
    VIGILANCE,
    @SerializedName("alerte")
    ALERT,
    @SerializedName("crise")
    CRISIS;
}
