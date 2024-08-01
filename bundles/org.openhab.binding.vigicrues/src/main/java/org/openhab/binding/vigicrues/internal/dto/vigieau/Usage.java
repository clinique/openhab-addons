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
 * The {@link Usage} describes kinds of water usage
 *
 * @author Gaël L'hopital - Initial contribution
 */
public enum Usage {
    @SerializedName("Alimentation des fontaines publiques et privées d’ornement")
    FOUNTAINS,
    @SerializedName("Arrosage des golfs(Conformément à l'accord cadre golf et environnement 2019-2024")
    GOLFS,
    @SerializedName("Arrosage des jardins potagers")
    GARDENS,
    @SerializedName("Arrosage des pelouses, massifs fleuris")
    GRASS,
    @SerializedName("Arrosage des terrains de sport")
    SPORTS,
    @SerializedName("ICPE soumises à un APC relatif à la sécheresse")
    ICPE,
    @SerializedName("Irrigation par aspersion des cultures")
    CULTURES,
    @SerializedName("Lavage de véhicules par des professionnels")
    VEHICLES,
    @SerializedName("Nettoyage des façades, toitures, trottoirs et autres surfaces imperméabilisées")
    ROOFS,
    @SerializedName("Remplissage et vidange de piscines privées (de plus d'1 m3)")
    POOLS,
    @SerializedName("Remplissage / vidange des plans d'eau")
    PONDS;
}
