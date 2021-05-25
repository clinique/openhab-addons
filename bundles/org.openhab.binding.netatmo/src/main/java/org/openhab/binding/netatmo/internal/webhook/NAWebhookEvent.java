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
package org.openhab.binding.netatmo.internal.webhook;

import java.util.Calendar;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.EventType;
import org.openhab.binding.netatmo.internal.api.dto.NAEvent;
import org.openhab.binding.netatmo.internal.api.dto.NAObjectMap;
import org.openhab.binding.netatmo.internal.api.dto.NAPerson;
import org.openhab.binding.netatmo.internal.api.dto.NASnapshot;

/**
 * The {@link NAWebhookEvent} is responsible to hold
 * data given back by the Netatmo API when calling the webhook
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class NAWebhookEvent extends NAEvent {
    private @NonNullByDefault({}) NAPushType pushType;
    private @NonNullByDefault({}) String homeId;
    private @Nullable String snapshotId;
    private @Nullable String snapshotKey;
    private NAObjectMap<NAPerson> persons = new NAObjectMap<>();
    // Webhook does not provide the event generation time, so we'll use the event reception time
    private long time = Calendar.getInstance().getTimeInMillis() / 1000;

    public String getHomeId() {
        return homeId;
    }

    public NAObjectMap<NAPerson> getPersons() {
        return persons;
    }

    @Override
    public EventType getEventType() {
        return pushType.getEvent();
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public @Nullable String getPersonId() {
        if (!persons.isEmpty()) {
            return persons.keySet().iterator().next();
        }
        return null;
    }

    @Override
    public @Nullable NASnapshot getSnapshot() {
        String sId = snapshotId;
        String key = snapshotKey;
        if (sId != null && key != null) {
            return new NASnapshot(sId, key);
        }
        return null;
    }
}
