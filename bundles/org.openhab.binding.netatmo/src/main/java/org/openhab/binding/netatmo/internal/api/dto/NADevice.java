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
package org.openhab.binding.netatmo.internal.api.dto;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.NAObjectMap;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class NADevice<CHILDS extends NAModule> extends NAThing {
    @SerializedName(value = "modules", alternate = { "cameras" })
    private NAObjectMap<CHILDS> childs = new NAObjectMap<>();
    private boolean co2Calibrating;
    private long dateSetup;
    private long lastUpgrade;
    private @NonNullByDefault({}) NAPlace place;

    public NAObjectMap<CHILDS> getChilds() {
        return childs;
    }

    public @Nullable CHILDS getChild(String key) {
        return childs.get(key);
    }

    public long getDateSetup() {
        return dateSetup;
    }

    public long getLastUpgrade() {
        return lastUpgrade;
    }

    public NAPlace getPlace() {
        return place;
    }

    public boolean isCo2Calibrating() {
        return co2Calibrating;
    }
}
