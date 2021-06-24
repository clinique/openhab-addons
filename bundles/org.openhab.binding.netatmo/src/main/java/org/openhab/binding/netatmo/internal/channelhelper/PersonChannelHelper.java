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
package org.openhab.binding.netatmo.internal.channelhelper;

import static org.openhab.binding.netatmo.internal.NetatmoBindingConstants.*;
import static org.openhab.binding.netatmo.internal.utils.ChannelTypeUtils.*;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.netatmo.internal.api.dto.NAPerson;
import org.openhab.binding.netatmo.internal.api.dto.NASnapshot;
import org.openhab.binding.netatmo.internal.api.dto.NAThing;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.types.State;

/**
 * The {@link PersonChannelHelper} handle specific behavior
 * of modules using batteries
 *
 * @author Gaël L'hopital - Initial contribution
 *
 */
@NonNullByDefault
public class PersonChannelHelper extends AbstractChannelHelper {

    public PersonChannelHelper() {
        super(Set.of(GROUP_PERSON));
    }

    @Override
    protected @Nullable State internalGetProperty(String channelId, NAThing naThing) {
        NAPerson naPerson = (NAPerson) naThing;
        if (CHANNEL_PERSON_AT_HOME.equals(channelId)) {
            return OnOffType.from(!naPerson.isOutOfSight());
        } else if (CHANNEL_LAST_SEEN.equals(channelId)) {
            return toDateTimeType(naPerson.getLastSeen());
        }
        NASnapshot avatar = naPerson.getFace();
        return avatar != null ? internalGetAvatar(avatar.getUrl(), channelId) : null;
    }

    private State internalGetAvatar(@Nullable String url, String channelId) {
        return CHANNEL_PERSON_AVATAR_URL.equals(channelId) ? toStringType(url)
                : CHANNEL_PERSON_AVATAR.equals(channelId) ? toRawType(url) : null;
    }
}
