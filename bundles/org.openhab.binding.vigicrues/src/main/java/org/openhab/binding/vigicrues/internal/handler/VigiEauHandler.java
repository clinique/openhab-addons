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
package org.openhab.binding.vigicrues.internal.handler;

import static org.openhab.binding.vigicrues.internal.VigiCruesBindingConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.vigicrues.internal.api.ApiHandler;
import org.openhab.binding.vigicrues.internal.api.VigiCruesException;
import org.openhab.binding.vigicrues.internal.config.VigiEauConfiguration;
import org.openhab.binding.vigicrues.internal.dto.vigieau.Answer;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.RawType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link VigiEauHandler} is responsible for querying the API and updating channels
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class VigiEauHandler extends BaseThingHandler {
    private final Logger logger = LoggerFactory.getLogger(VigiEauHandler.class);
    private @Nullable ScheduledFuture<?> refreshJob;
    private final ApiHandler apiHandler;

    public VigiEauHandler(Thing thing, ApiHandler apiHandler) {
        super(thing);
        this.apiHandler = apiHandler;
    }

    @Override
    public void initialize() {
        logger.debug("Initializing VigiEau handler.");

        VigiEauConfiguration config = getConfigAs(VigiEauConfiguration.class);
        logger.debug("config refresh = {} min", config.refresh);

        updateStatus(ThingStatus.UNKNOWN);
        refreshJob = scheduler.scheduleWithFixedDelay(this::updateAndPublish, 0, config.refresh, TimeUnit.HOURS);
    }

    @Override
    public void dispose() {
        logger.debug("Disposing the VigiEau handler.");

        ScheduledFuture<?> localJob = refreshJob;
        if (localJob != null) {
            localJob.cancel(true);
        }
        refreshJob = null;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            updateAndPublish();
        }
    }

    private void updateAndPublish() {
        VigiEauConfiguration config = getConfigAs(VigiEauConfiguration.class);
        try {
            Answer[] result = apiHandler.getRestrictions(config.getLocation(), config.profile);
            Arrays.stream(result).filter(item -> config.type.equals(item.type)).findFirst().ifPresent(item -> {
                updateString(ARRETE, item.arrete.cheminFichier);
                updateString(CADRE, item.arrete.cheminFichierArreteCadre);
                updateDate(BEGINNING_TIME,
                        ZonedDateTime.ofInstant(item.arrete.dateDebutValidite.toInstant(), ZoneId.systemDefault()));
                updateDate(ENDING_TIME,
                        ZonedDateTime.ofInstant(item.arrete.dateFinValidite.toInstant(), ZoneId.systemDefault()));
                updateState(ALERT, new DecimalType(item.gravity.ordinal()));
                item.allowances.forEach(allowance -> updateState(allowance.usage.name().toLowerCase(),
                        OnOffType.from(allowance.concerns(config.profile))));
                updateStatus(ThingStatus.ONLINE);
            });
        } catch (VigiCruesException e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
        }
    }

    private void updateString(String channelId, String value) {
        if (isLinked(channelId)) {
            updateState(channelId, new StringType(value));
        }
    }

    private void updateDate(String channelId, ZonedDateTime zonedDateTime) {
        if (isLinked(channelId)) {
            updateState(channelId, new DateTimeType(zonedDateTime));
        }
    }

    private void updateAlert(String channelId, int value) {
        String channelIcon = channelId + "-icon";
        if (isLinked(channelId)) {
            updateState(channelId, new DecimalType(value));
        }
        if (isLinked(channelIcon)) {
            byte[] resource = getResource(String.format("picto/crue-%d.svg", value));
            updateState(channelIcon, resource != null ? new RawType(resource, "image/svg+xml") : UnDefType.UNDEF);
        }
    }

    private byte @Nullable [] getResource(String iconPath) {
        ClassLoader classLoader = VigiEauHandler.class.getClassLoader();
        if (classLoader != null) {
            try (InputStream stream = classLoader.getResourceAsStream(iconPath)) {
                return stream != null ? stream.readAllBytes() : null;
            } catch (IOException e) {
                logger.warn("Unable to load ressource '{}' : {}", iconPath, e.getMessage());
            }
        }
        return null;
    }
}
