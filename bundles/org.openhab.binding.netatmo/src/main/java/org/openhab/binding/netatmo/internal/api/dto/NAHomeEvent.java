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
package org.openhab.binding.netatmo.internal.api.dto;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.ApiResponse;
import org.openhab.binding.netatmo.internal.api.BodyResponse;
import org.openhab.binding.netatmo.internal.api.data.EventSubType;
import org.openhab.binding.netatmo.internal.api.data.EventType;
import org.openhab.binding.netatmo.internal.api.data.NetatmoConstants.EventCategory;
import org.openhab.binding.netatmo.internal.api.data.NetatmoConstants.VideoStatus;

/**
 * The {@link NAHomeEvent} holds information transferred by the webhook about a home event.
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */

@NonNullByDefault
public class NAHomeEvent extends NAEvent {
    public class NAEventsDataResponse extends ApiResponse<BodyResponse<NAHome>> {
    }

    private @NonNullByDefault({}) ZonedDateTime time;
    private @Nullable String personId;
    private EventCategory category = EventCategory.UNKNOWN;
    private @Nullable NASnapshot snapshot;
    private @Nullable String videoId;
    private VideoStatus videoStatus = VideoStatus.UNKNOWN;
    private boolean isArrival;

    @Override
    public ZonedDateTime getTime() {
        return time;
    }

    @Override
    public @Nullable String getPersonId() {
        return personId;
    }

    public @Nullable String getVideoId() {
        return videoId;
    }

    public VideoStatus getVideoStatus() {
        return videoStatus;
    }

    @Override
    public Optional<EventSubType> getSubTypeDescription() {
        // Blend extra information provided by this kind of event in subcategories...
        if (isArrival && type == EventType.PERSON) {
            this.subType = EventSubType.PERSON_ARRIVAL.subType;
        } else {
            switch (category) {
                case ANIMAL:
                    this.subType = EventSubType.MOVEMENT_ANIMAL.subType;
                    break;
                case HUMAN:
                    this.subType = EventSubType.MOVEMENT_HUMAN.subType;
                    break;
                case VEHICLE:
                    this.subType = EventSubType.MOVEMENT_VEHICLE.subType;
                    break;
                default:
                    break;
            }
        }
        // ... and let ancestor do his work
        return super.getSubTypeDescription();
    }

    @Override
    public @Nullable String getSnapshotUrl() {
        NASnapshot localSnap = snapshot;
        return localSnap != null ? localSnap.getUrl() : null;
    }

    public void setTime(ZonedDateTime eventTime) {
        this.time = eventTime;
    }
}
