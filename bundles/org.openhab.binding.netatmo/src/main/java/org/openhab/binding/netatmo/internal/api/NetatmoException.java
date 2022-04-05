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
package org.openhab.binding.netatmo.internal.api;

import java.io.IOException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An exception that occurred while communicating with Netatmo server or related processes.
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class NetatmoException extends IOException {
    private static final long serialVersionUID = 1513549973502021727L;
    private int statusCode = -1;

    public NetatmoException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public NetatmoException(String format, Object... args) {
        super(String.format(format, args));
    }

    public NetatmoException(Exception e, String message) {
        super(message, e);
    }

    public NetatmoException(Exception e, String format, Object... args) {
        this(e, String.format(format, args));
    }

    public NetatmoException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public @Nullable String getMessage() {
        String message = super.getMessage();
        return message == null ? null
                : String.format("Rest call failed: statusCode=%d, message=%s", statusCode, message);
    }
}
