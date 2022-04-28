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
package org.openhab.binding.netatmo.internal.handler.capability;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.binding.netatmo.internal.handler.CommonInterface;
import org.openhab.core.thing.ThingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RefreshCapability} is the class used to embed the refreshing needs calculation for devices
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class RefreshCapability extends Capability {
    private static final Duration DEFAULT_DELAY = Duration.of(20, SECONDS);
    private static final Duration PROBING_INTERVAL = Duration.of(120, SECONDS);

    private final Logger logger = LoggerFactory.getLogger(RefreshCapability.class);
    private final ScheduledExecutorService scheduler;

    private Duration dataValidity;
    private Instant dataTimeStamp = Instant.now();
    private @Nullable Instant dataTimeStamp0;
    private Optional<ScheduledFuture<?>> refreshJob = Optional.empty();

    public RefreshCapability(CommonInterface handler, ScheduledExecutorService scheduler, int refreshInterval) {
        super(handler);
        this.scheduler = scheduler;
        this.dataValidity = Duration.ofMillis(Math.max(0, refreshInterval));
        handler.setThingStatus(ThingStatus.ONLINE, null);
        freeJobAndReschedule(2);
    }

    @Override
    public void dispose() {
        super.dispose();
        freeJobAndReschedule(0);
    }

    @Override
    public void expireData() {
        dataTimeStamp = Instant.now().minus(dataValidity);
        freeJobAndReschedule(1);
    }

    private Duration dataAge() {
        return Duration.between(dataTimeStamp, Instant.now());
    }

    private boolean probing() {
        return dataValidity.getSeconds() <= 0;
    }

    private void proceedWithUpdate() {
        handler.proceedWithUpdate();
        if (ThingStatus.ONLINE.equals(handler.getThing().getStatus())) {
            long delay = (probing() ? PROBING_INTERVAL : dataValidity.minus(dataAge()).plus(DEFAULT_DELAY)).toSeconds();
            delay = delay < 2 ? PROBING_INTERVAL.toSeconds() : delay;
            logger.debug("Module refreshed, next one in {} s", delay);
            freeJobAndReschedule(delay);
        }
    }

    @Override
    protected void updateNAThing(NAThing newData) {
        super.updateNAThing(newData);
        newData.getLastSeen().ifPresent(timestamp -> {
            Instant tsInstant = timestamp.toInstant();
            if (probing()) { // we're still probin
                Instant firstTimeStamp = dataTimeStamp0;
                if (firstTimeStamp == null) {
                    dataTimeStamp0 = tsInstant;
                    logger.debug("First data timestamp is {}", dataTimeStamp0);
                } else if (tsInstant.isAfter(firstTimeStamp)) {
                    dataValidity = Duration.between(firstTimeStamp, tsInstant);
                    logger.debug("Data validity period identified to be {}", dataValidity);
                } else {
                    logger.debug("Data validity period not yet found - data timestamp unchanged");
                }
            }
            dataTimeStamp = tsInstant;
        });
    }

    private void freeJobAndReschedule(long delay) {
        refreshJob.ifPresent(job -> job.cancel(true));
        refreshJob = Optional.ofNullable(delay == 0 ? null //
                : scheduler.schedule(() -> proceedWithUpdate(), delay, TimeUnit.SECONDS));
    }
}