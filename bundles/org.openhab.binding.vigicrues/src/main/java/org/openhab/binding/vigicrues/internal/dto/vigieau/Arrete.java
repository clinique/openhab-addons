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

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Arrete} holds administrative informations regarding water restrictions
 *
 * @author Gaël L'hopital - Initial contribution
 */
public class Arrete {
    public int id;
    @SerializedName("dateDebutValidite")
    public Date dateDebutValidite;
    @SerializedName("dateFinValidite")
    public Date dateFinValidite;
    @SerializedName("cheminFichier")
    public String cheminFichier;
    @SerializedName("cheminFichierArreteCadre")
    public String cheminFichierArreteCadre;
}
