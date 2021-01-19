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
package org.openhab.binding.netatmo.internal.api;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link ApiResponse} models a response returned by API call
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class ApiResponse<T> {
    private @NonNullByDefault({}) String status;
    private T body;

    public String getStatus() {
        return status;
    }

    public T getBody() {
        return body;
    }
}
