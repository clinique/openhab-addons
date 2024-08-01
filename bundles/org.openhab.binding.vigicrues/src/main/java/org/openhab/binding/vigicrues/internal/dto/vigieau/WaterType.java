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

/**
 * The {@link WaterType} describes categories of water
 *
 * @author Gaël L'hopital - Initial contribution
 */
public enum WaterType {
    SUP("Superficielle"),
    SOU("Souterraines"),
    AEP("Potable"),
    UNKNOWN("");

    public final String lib;

    WaterType(String lib) {
        this.lib = lib;
    }
}
