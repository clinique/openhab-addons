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
package org.openhab.binding.vigicrues.internal.dto.vigieau;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Allowance} describres the (un)allowed water usage king status
 *
 * @author Gaël L'hopital - Initial contribution
 */
public class Allowance {
    @SerializedName("nom")
    public Usage usage;
    public String thematique;
    public String description;
    @SerializedName("concerneParticulier")
    private boolean part;
    @SerializedName("concerneEntreprise")
    private boolean entr;
    @SerializedName("concerneCollectivite")
    private boolean coll;
    @SerializedName("concerneExploitation")
    private boolean expl;

    public boolean concerns(UserKind kind) {
        return switch (kind) {
            case COLL -> coll;
            case ENTR -> entr;
            case EXPL -> expl;
            case PART -> part;
            default -> false;
        };
    }
}
