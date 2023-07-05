/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
package org.openhab.binding.toyota.internal.dto;

import com.google.gson.annotations.SerializedName;

/**
 * This class holds the status of all the car windows
 *
 * @author Gaël L'hopital - Initial contribution
 */
public class Windows {
    public boolean warning;
    @SerializedName("driverSeatWindow")
    public Window driver;
    @SerializedName("passengerSeatWindow")
    public Window passenger;
    @SerializedName("rearRightSeatWindow")
    public Window rearRight;
    @SerializedName("rearLeftSeatWindow")
    public Window rearLeft;
}
