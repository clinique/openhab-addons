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

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */

@NonNullByDefault
public class NAMeasureBodyElem<T> {
    // private long begTime;
    // private long stepTime;
    private List<List<T>> value = List.of();

    // public long getBegTime() {
    // return begTime;
    // }
    //
    // public long getStepTime() {
    // return stepTime;
    // }

    public List<List<T>> getValue() {
        return value;
    }

    public @Nullable T getSingleValue() {
        if (value.size() > 0) {
            List<T> first = value.get(0);
            if (first.size() > 0) {
                return first.get(0);
            }
        }
        return null;
    }
}
