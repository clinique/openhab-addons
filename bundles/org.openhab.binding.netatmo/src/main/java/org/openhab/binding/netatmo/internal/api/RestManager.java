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

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.http.HttpMethod;
import org.openhab.binding.netatmo.internal.api.doc.NetatmoConstants.Scope;

/**
 * Base class for all various rest managers
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public abstract class RestManager {
    private static String SUB_URL = "api/";

    protected final ApiBridge apiHandler;
    private final Set<Scope> requiredScopes;

    public RestManager(ApiBridge apiHandler, Set<Scope> requiredScopes) {
        this.apiHandler = apiHandler;
        this.requiredScopes = requiredScopes;
    }

    public Set<Scope> getRequiredScopes() {
        return requiredScopes;
    }

    public <T> T get(String anUrl, Class<T> classOfT) throws NetatmoException {
        return apiHandler.executeUrl(SUB_URL + anUrl, HttpMethod.GET, null, classOfT, true);
    }

    public <T> T post(String anUrl, @Nullable String payload, Class<T> classOfT, boolean baseUrl)
            throws NetatmoException {
        return apiHandler.executeUrl(SUB_URL + anUrl, HttpMethod.POST, payload, classOfT, baseUrl);
    }
}
